<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" v-show="showSearch" :inline="true">

      <el-form-item label="项目id" prop="sceneId">
        <el-input
          v-model="queryParams.id"
          placeholder="请输入项目id"
          clearable
          size="small"
          style="width: 240px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>

      <el-form-item label="项目名称" prop="taskName">
        <el-input
          v-model="queryParams.sceneName"
          placeholder="请输入任务名称"
          clearable
          size="small"
          style="width: 240px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>

      <el-form-item label="创建时间">
        <el-date-picker
          v-model="dateRange"
          size="small"
          style="width: 240px"
          value-format="yyyy-MM-dd"
          type="daterange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        ></el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['business:labelProject:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['business:labelProject:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['business:labelProject:remove']"
        >删除</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="sceneList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="标签id" prop="id" width="120" />
      <el-table-column label="工程名称" prop="projectName" :show-overflow-tooltip="true" width="150" />
      <el-table-column label="场景名称" prop="sceneName" :show-overflow-tooltip="true" width="150" />
      <el-table-column label="标签名称" prop="tagName" :show-overflow-tooltip="true" width="150" />
      <el-table-column label="任务创建人" prop="createBy" width="120" />
      <el-table-column label="创建时间" align="center" prop="createTime" width="180" />
      <el-table-column label="修改时间" align="center" prop="createTime" width="180"/>
      <el-table-column label="备注信息" prop="remark" width="120" />
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改参数配置对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="600px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="tag名称" prop="sceneName">
              <el-input v-model="form.tagName" placeholder="请输入标签名称" maxlength="30" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-form-item label="项目名称">
            <el-select v-model="form.projectName" placeholder="请选择" @change="getSenceName">
              <el-option
                v-for="item in projectOptions"
                :key="item.id"
                :label="item.label"
                :value="item.label"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-row>
        <el-row>
          <el-form-item label="场景名称">
            <el-select v-model="form.sceneName" placeholder="请选择" @change="change">
              <el-option
                v-for="item in sceneOptions"
                :label="item.label"
                :value="item.id"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="备注">
              <el-input v-model="form.remark" type="textarea" placeholder="请输入内容"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
import {listLabelScene} from "@/api/business/labelScene"
import {listLabelTag, addLabelTag} from "@/api/business/labelTag";



export default {
  name: "Tag",
  data() {
    return {
      columns:[],
      sceneList: [],
      // 遮罩层
      loading: true,
      // 导出遮罩层
      exportLoading: false,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 是否显示弹出层（数据权限）
      openDataScope: false,
      menuExpand: false,
      menuNodeAll: false,
      deptExpand: true,
      deptNodeAll: false,
      // 日期范围
      dateRange: [],
      menuOptions: [],
      sceneOptions: [],
      projectOptions:[],
      projectSceneDict: {},
      // 查询参数
      tagList: [],
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        sceneName: undefined
      },
      // 表单参数
      form: {},
      defaultProps: {
        children: "children",
        label: "label"
      },
      // 表单校验
      rules: {
        tagName: [
          { required: true, message: "标签名称不能为空", trigger: "blur" }
        ]
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    handleUpdate(){

},
    handleSelectionChange(){

    },
    handleDelete(){

    },
    /** 查询任务列表 */
    getList() {
      this.loading = true;
      listLabelTag(this.addDateRange(this.queryParams, this.dateRange)).then(
        response => {
          this.sceneList= response.rows;
          this.total = response.total;
          this.loading = false;
        }
      );
    },
    /** 新增按钮操作 */
    handleAdd() {
      listLabelScene().then(response => {
        var projectSceneList= response.rows;
        for(var i in projectSceneList){
          var projectScene= projectSceneList[i];
          var projectName= projectScene["projectName"];
          var sceneName= projectScene["sceneName"];
          if(this.projectOptions.indexOf(projectName)== -1){
            this.projectOptions.push({label: projectName, id: projectScene['id']})
          };
          if(this.projectSceneDict.hasOwnProperty(projectName)){
            //alert(this.projectSceneDict[projectName]);
            var sceneList= this.projectSceneDict[projectName];
            if(sceneList.indexOf(sceneName)== -1){
               sceneList.push(sceneName);
            }
            this.projectSceneDict[projectName]= sceneList;
          }else {
            this.projectSceneDict[projectName]= []
            this.projectSceneDict[projectName].push(sceneName);
          }
        };
        this.reset();
        this.open = true;
        this.title = "添加标签";
      })
    },
    getSenceName(){
      //this.form.sceneName= "";
      this.sceneOptions= this.projectSceneDict[this.form.projectName].map((d,i) => ({id: i, label: d}));
      console.log(this.sceneOptions, this.projectSceneDict)
      console.log(this.sceneOptions)
    },
    change(){
      console.log(1212)
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 取消按钮（数据权限）
    cancelDataScope() {
      this.openDataScope = false;
      this.reset();
    },
    // 表单重置
    reset() {
      if (this.$refs.menu != undefined) {
        this.$refs.menu.setCheckedKeys([]);
      }
      this.menuExpand = false,
      this.menuNodeAll = false,
      this.deptExpand = true,
      this.deptNodeAll = false,
      this.form = {
        limitSIze: 100,
        inputSIze: 10
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.dateRange = [];
      this.resetForm("queryForm");
      this.handleQuery();
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != undefined) {
            //updateLabelProject(this.form).then(response => {
            //  this.msgSuccess("修改成功");
              //this.open = false;
             // this.getList();
           // });
          } else {
            addLabelTag(this.form).then(response => {
              this.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
  }
};
</script>
