<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" v-show="showSearch" :inline="true">
      <el-form-item label="任务名称" prop="taskName">
        <el-input
          v-model="queryParams.taskName"
          placeholder="请输入任务名称"
          clearable
          size="small"
          style="width: 240px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="关联项目id" prop="projectId">
        <el-input
          v-model="queryParams.projectId"
          placeholder="请输入项目id"
          clearable
          size="small"
          style="width: 240px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="任务类型" prop="status">
        <!--参考激活或者什么-->
        <el-select
          v-model="queryParams.status"
          placeholder="视频 图片 点云"
          clearable
          size="small"
          style="width: 240px"
        >
          <el-option
            v-for="dict in typeOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
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
          v-hasPermi="['business:labelTask:add']"
        >新增</el-button>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="groupList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="组编号" prop="id" width="120" />
      <el-table-column label="组名称" prop="groupName" :show-overflow-tooltip="true" width="150" />
      <el-table-column label="任务名称" prop="taskName" :show-overflow-tooltip="true" width="150" />
      <el-table-column label="任务创建人" prop="createBy" width="120" />
      <el-table-column label="创建时间" align="center" prop="createTime" width="120">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="备注信息" prop="remark" width="120" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope" >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="listId(scope.row)"
            v-hasPermi="['business:labelTask:filterStream']"
          >id列表</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleImagesTask(scope.row)"
            v-hasPermi="['business:labelTask:label']"
          >标注</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="filterStream(scope.row)"
            v-hasPermi="['business:labelTask:filterStream']"
          >筛选</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改任务配置对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-table v-loading="loading" :data="streamList">
        <el-table-column label="streamId" prop="streamId" width="120" />
        <el-table-column label="大小" prop="size" width="120" />
        <el-table-column label="创建时间" prop="createTime" width="120" />
        <el-table-column label="备注" prop="remark" width="120" />
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="text"
              icon="el-icon-edit"
              @click="selectStream(scope.row)"
            >选取标注</el-button>
          </template>
        </el-table-column>
      </el-table>
      <pagination
        v-show="total>0"
        :total="total"
        :page.sync="queryParams.pageNum"
        :limit.sync="queryParams.pageSize"
        @pagination="getStream(streamList.get(0).taskName)"
      />
    </el-dialog>

    <!-- 添加group框 -->
    <el-dialog :title="title1" :visible.sync="open1" width="600px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="任务名称" prop="taskName">
              <el-select v-model="form.taskName" placeholder="请选择">
                <el-option
                  v-for="item in taskOptions"
                  :key="item.id"
                  :label="item.taskName"
                  :value="item.taskName"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="组名称" prop="groupName">
              <el-input v-model="form.groupName" placeholder="请输入任务名称" maxlength="30" />
            </el-form-item>
          </el-col>
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

    <!-- 添加或修改任务配置对话框 -->
    <el-dialog :title="title2" :visible.sync="open2" width="500px" append-to-body>
      <el-table v-loading="loading" :data="taggedStreamList">
        <el-table-column label="streamId" prop="streamId" width="120" />
        <el-table-column label="大小" prop="frameSize" width="120" />
        <el-table-column label="备注" prop="remark" width="120" />
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="text"
              icon="el-icon-edit"
              @click="addStreamToTask(scope.row)"
            >选取添加到任务</el-button>
          </template>
        </el-table-column>
      </el-table>
      <pagination
        v-show="total>0"
        :total="total"
        :page.sync="queryParams.pageNum"
        :limit.sync="queryParams.pageSize"
        @pagination="getStream(taggedStreamList.get(0).taskName)"
      />
    </el-dialog>


    <!-- 查看id-->
    <el-dialog :title="title3" :visible.sync="open3" width="1500px" append-to-body>
      <el-row :gutter="10" class="mb8">
        <el-col :span="1.5">
          <el-button
            type="primary"
            plain
            icon="el-icon-plus"
            size="mini"
            @click="addGroupId"
            v-hasPermi="['business:labelTask:add']"
          >新增</el-button>
        </el-col>
      </el-row>
      <el-table v-loading="loading" :data="idList">
        <el-table-column label="组名" prop="groupName" width="120" />
        <el-table-column label="personId" prop="personId" width="120" />
        <el-table-column label="名称" prop="personName" width="120" />
        <el-table-column label="图片列表" prop="imgUrl" width="220" >
          <template   slot-scope="scope">
            <img :src="scope.row.imgUrl"  min-width="140" height="100" />
          </template>
        </el-table-column>
        <el-table-column label="备注" prop="remark" width="120" />

        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="text"
              icon="el-icon-edit"
              @click="editGroupId(scope.row)"
            >修改</el-button>
          </template>
        </el-table-column>
      </el-table>
      <pagination
        v-show="total>0"
        :total="total"
        :page.sync="queryParams.pageNum"
        :limit.sync="queryParams.pageSize"
        @pagination="getStream(taggedStreamList.get(0).taskName)"
      />
    </el-dialog>

    <!-- 添加group框 -->
    <el-dialog :title="title4" :visible.sync="open4" width="600px" append-to-body>
      <el-form ref="form4" :model="form4" :rules="rules" label-width="80px">
        <el-row>
          <el-col :span="24">
            <el-form-item label="id">
              <el-input v-model="form4.personId" type="textarea" placeholder="请输入id"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="name">
              <el-input v-model="form4.personName" type="textarea" placeholder="请输入名称"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="图片链接">
              <el-input v-model="form4.imgUrl" type="textarea" placeholder="请输入图片连接地址"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="备注">
              <el-input v-model="form4.remark" type="textarea" placeholder="请输入内容"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm4">确 定</el-button>
        <el-button @click="cancel4">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listLabelTask, getkUnfinishedTaskStream, assignTaskStream, addLabelTask,
  getTaggedStreamList, addTaskStream, getUnAssignedTaskStream} from "@/api/business/labelTask"
import { getToken } from "@/utils/auth";
import {listLabelProject} from "@/api/business/labelProject";
import {
  addLabelVideoGroup,
  addLabelVideoGroupId,
  listVideoGroup,
  listVideoGroupId
} from "@/api/business/labelVideoGroup";



export default {
  name: "Role",
  data() {
    return {
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
      // 任务表格数据
      groupList: [],
      streamList: [],
      taskName: undefined,

      taggedStreamList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      title1: "",
      open1: false,

      title2: "",
      open2: false,

      idList: [],
      title3: "",
      open3: false,

      title4: "",
      open4: false,

      groupName: undefined,

      openDataScope: false,
      menuExpand: false,
      menuNodeAll: false,
      deptExpand: true,
      deptNodeAll: false,
      // 日期范围
      dateRange: [],
      // 状态数据字典
      typeOptions: [],
      taskOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        taskName: undefined,
        projectId: undefined,
        status: undefined
      },
      // 表单参数
      form: {},
      form4: {},
      defaultProps: {
        children: "children",
        label: "label"
      },
      // 表单校验
      rules: {
        taskName: [
          { required: true, message: "任务名称不能为空", trigger: "blur" }
        ],
        projectId: [
          { required: true, message: "项目名称不能为空", trigger: "blur" }
        ],
        type: [
          { required: true, message: "任务不能为空", trigger: "blur" }
        ]
      }
    };
  },
  created() {
    this.getList();
    this.getDicts("business_labelTask_type").then(response => {
      this.typeOptions = response.data;
    });
  },
  methods: {
    /** 查询任务列表 */
    getList() {
      listVideoGroup(this.addDateRange(this.queryParams, this.dateRange)).then(
        response => {
          this.groupList = response.rows;
          this.total = response.total;
          this.loading = false;
        }
      );
    },
    typeFormat(row, column) {
      return this.selectDictLabel(this.typeOptions, row.type);
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
    handleAdd(){
      listLabelTask(this.addDateRange(this.queryParams, this.dateRange)).then(response =>{
        this.taskOptions= response.rows;
      })
      this.reset();
      this.open1 = true;
      this.title1 = "添加组";
    },
    addGroupId(row){
      this.open4= true;
      this.title4= "新增id";
    },
    listId(row){
        this.groupName= row.groupName;
      listVideoGroupId(row.groupName).then(response =>{
        this.idList= response.rows;
        this.open3= true;
        this.title3= "id列表";
      })},
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
    filterStream(row){
      this.taskName= row.taskName;
      this.groupName= row.groupName;
      getTaggedStreamList(row.projectName, row.taskName).then(response =>{
        this.taggedStreamList= response.rows;
        this.open2= true;
        this.title2= "请添加stream";
      })
    },
    addStreamToTask(row){
      //alert(row.streamId);
      addTaskStream({streamId: row.streamId,taskName: this.taskName, size:row.frameSize, groupName: this.groupName})
      this.open2 = false;
    },
    /** 选取stream_id按钮操作 */
    handleImagesTask(row) {
      this.taskName= row.taskName;
      let token= getToken();
      getkUnfinishedTaskStream(row.taskName,row.groupName, "label").then(
        response =>{
          let taskStreamList = response.rows;
          if(taskStreamList.length>0){
            if(true){
              if (taskStreamList[0].status== 4){
                alert("你有审批驳回的任务，即将跳转标注界面")
                window.open('http://10.66.66.121:8082/?token=' + token + '&taskName=' +this.taskName +'&streamId='+ taskStreamList[0].streamId);
              }else if(taskStreamList[0].status== 0){
                alert("你有未完成标注的任务，即将跳转标注界面")
                window.open('http://10.66.66.121:8082/?token=' + token + '&taskName=' +this.taskName +'&streamId='+ taskStreamList[0].streamId);
              }
            }else{
              alert("图片标注待开发");
              //alert(aa)
            };
           // window.open('http://10.66.66.121:8082/?token=' + token.toString() + '&taskName=' +row.taskName);
          }else{
            this.msgInfo("请选定stream标定");
            this.reset();
            this.open = true;
            this.title = row.taskName.toString();
            getUnAssignedTaskStream(row.taskName, row.groupName,"label").then(
              response => {
                this.streamList = response.rows;
                this.total = response.total;
              });
          }
        }
      )
    },
    selectStream(row){
      assignTaskStream({streamId: row.streamId, taskName: this.taskName, type: "label"}).then(response =>{
        this.msgSuccess("选定成功，开始标注");
        this.open = false;
        let token=getToken();
        window.open('http://10.66.66.121:8082/?token=' + token + '&taskName=' +this.taskName +'&streamId=' +row.streamId);
      });

    },
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != undefined) {
          } else {
            addLabelVideoGroup(this.form).then(response => {
              this.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    submitForm4: function() {
      this.$refs["form4"].validate(valid => {
        if (valid) {
          if (this.form4.id != undefined) {
          } else {
            this.form4.groupName= this.groupName
            addLabelVideoGroupId(this.form4).then(response => {
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
