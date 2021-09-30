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

    <el-table v-loading="loading" :data="taskList" >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="任务编号" prop="id" width="120" />
      <el-table-column label="任务名称" prop="taskName" :show-overflow-tooltip="true" width="150" />
      <el-table-column label="关联项目名称" prop="projectName" :show-overflow-tooltip="true" width="150" />
      <el-table-column label="任务类型":formatter="typeFormat" prop="type" width="120" />
      <el-table-column label="任务创建人" prop="createBy" width="120" />
      <el-table-column label="创建时间" align="center" prop="createTime" width="120">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="备注信息" prop="remark" width="120" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            v-if="scope.row.type== 0"
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="downloadTask(scope.row)"
            v-hasPermi="['business:labelTask:download']"
          >下载</el-button>
          <el-button
            v-if="scope.row.type!= 0"
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleImagesTask(scope.row,'label')"
            v-hasPermi="['business:labelTask:label']"
          >标注</el-button>
          <el-button
            v-if="scope.row.type!= 0"
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="filterStream(scope.row)"
            v-hasPermi="['business:labelTask:filterStream']"
          >筛选</el-button>
          <el-button
            v-if="scope.row.type!= 0"
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleImagesTask(scope.row, 'qa1')"
            v-hasPermi="['business:labelTask:qa1']"
          >一级审核</el-button>
          <el-button
            v-if="scope.row.type!= 0"
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleImagesTask(scope.row, 'qa2')"
            v-hasPermi="['business:labelTask:qa2']"
          >二级审核</el-button>
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


    <!-- 添加task框 -->
    <el-dialog :title="title1" :visible.sync="open1" width="600px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="项目名称" prop="projectName">
              <el-select v-model="form.projectName" placeholder="请选择">
                <el-option
                  v-for="item in projectOptions"
                  :key="item.id"
                  :label="item.projectName"
                  :value="item.projectName"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="任务名称" prop="taskName">
              <el-input v-model="form.taskName" placeholder="请输入任务名称" maxlength="30" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="任务类型" prop="type">
              <el-select v-model="form.type" placeholder="请选择">
                <el-option
                  v-for="item in typeOptions"
                  :key="item.dictValue"
                  :label="item.dictLabel"
                  :value="item.dictValue"
                ></el-option>
              </el-select>
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

    <!--拉取任务界面 -->
    <el-dialog :title="title3" :visible.sync="open3" width="500px" append-to-body>
      <el-form ref="form" :model="form3" :rules="pullRules" label-width="80px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="任务名称" prop="taskName">
              <el-input v-model="form3.taskName" disabled="disabled" maxlength="30" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="操作类型" prop="taskName">
              <el-input v-model="form3.type" disabled="disabled" maxlength="30" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="任务图片总数" prop="total">
              <el-input v-model="form3.total" disabled="disabled" maxlength="30" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="可拉取数量" prop="unLabel">
              <el-input v-model="form3.rest" disabled="disabled" maxlength="30" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="选取数量" prop="number">
              <el-input v-model="form3.number" placeholder="请输入选取数量" maxlength="30" />
            </el-form-item>
          </el-col>
        </el-row>

      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm3">确 定</el-button>
        <el-button @click="cancel3">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  listLabelTask, getkUnfinishedTaskStream, assignTaskStream, addLabelTask,
  getTaggedStreamList, addTaskStream, getUnAssignedTaskStream, getTaskDetail, pullTaskImage, checkTaskImage
} from "@/api/business/labelTask"

import { getToken } from "@/utils/auth";
import {listLabelProject} from "@/api/business/labelProject";
import {downLoadStreamData, downLoadTaskData} from "@/api/business/labelData";



export default {
  name: "Role",
  data() {
    var validatePass = (rule, value, callback) => {
      console.log(value);
      if (!value) {
        return callback(new Error('数量不能为空'));
      };
      if (Number.isInteger(value)) {
        callback(new Error('请输入数字值'));
      } else {
        if (value > this.form3.rest) {
          callback(new Error('不能大于可拉取数量'));
        } else {
          callback();
        }
      }
    };
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
      taskList: [],
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

      title3: "",
      open3: false,

      openDataScope: false,
      menuExpand: false,
      menuNodeAll: false,
      deptExpand: true,
      deptNodeAll: false,
      // 日期范围
      dateRange: [],
      // 状态数据字典
      typeOptions: [],
      projectOptions: [],
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
      form3: {},
      defaultProps: {
        children: "children",
        label: "label"
      },


      // 表单校验
      rules: {
        /**
        taskName: [
          {required: true, message: "任务名称不能为空", trigger: "blur"}
        ],
        projectId: [
          {required: true, message: "项目名称不能为空", trigger: "blur"}
        ],
        type: [
          {required: true, message: "任务不能为空", trigger: "blur"}
        ]
         **/
      },

      pullRules: {
        number: [
          { validator:validatePass, trigger:"change"}
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
    handleSelectionChange(){
    },
    /** 查询任务列表 */
    downloadTask(row){
      downLoadTaskData(row.taskName).then(
        response =>{
          this.open6 = false;
          const aLink =document.createElement('a')
          const blob = new Blob([response], { type: 'application/zip' })
          let fileName =`${row.taskName}`
          //let fileName = this.taskStreams[0].groupName
          aLink.href = window.URL.createObjectURL(blob)
          aLink.setAttribute('download', fileName)
          document.body.appendChild(aLink)
          aLink.click()
          document.body.appendChild(aLink)
        }
      );
    },
    getList() {
      listLabelProject().then(response => {
        this.projectOptions= response.rows;
        this.reset();
        //this.open = true;
        this.title = "添加场景";
      });
      this.loading = true;
      let token=getToken();
      //alert(token)
      listLabelTask(this.addDateRange(this.queryParams, this.dateRange)).then(
        response => {
          this.taskList = response.rows;
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
    cancel3(){
      this.open3= false;
    },
    // 取消按钮（数据权限）
    cancelDataScope() {
      this.openDataScope = false;
      this.reset();
    },
    handleAdd() {
      this.reset();
      this.open1 = true;
      this.title1 = "添加任务";
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
    filterStream(row){
      this.taskName= row.taskName;
      getTaggedStreamList(row.projectName, row.taskName).then(response =>{
        this.taggedStreamList= response.rows;
        this.open2= true;
        this.title2= "请添加stream";
      })
    },
    addStreamToTask(row){
      //alert(row.streamId);
      addTaskStream({streamId: row.streamId,taskName: this.taskName, size:row.frameSize})
      this.open2 = false;
    },
    /** 选取stream_id按钮操作 */
    handleImagesTask(row, type) {
      /*
      this.reset();
      this.open = true;
      this.title = row.taskName.toString();
      getUnAssignedTaskImage(row.taskName, "label").then(
              response => {
                this.streamList = response.rows;
                this.total = response.total;
              });
       */
      checkTaskImage(row.taskName, type).then(response =>{
        console.log(response.data);
        if(response.data> 0){
          alert("存在未完成的图片");
          let token=getToken();
          if(type=="label"){
            window.open('http://10.66.33.113:8082//split.html?token=' + token + '&taskName='+
              row.taskName+ '&type=' + type);
          }else{
            window.open('http://10.66.33.113:8082//check.html?token=' + token + '&taskName='+
              row.taskName+ '&qa_type=' + type +"&streamId="+ "");
          }
          return;
        }else{
          this.open3= true;
          this.title3= "拉取图片"+ type;
          getTaskDetail(row.taskName).then(response=>{
            this.form3= response.data;
            this.form3.type= type;
            if(type=="label"){
              this.form3.rest= response.data.unLabel;
            }else if (type=="qa1"){
              this.form3.rest= response.data.labeled;
            }else if(type=="qa2"){
              this.form3.rest= response.data.qa1ed;
            }
          });
        }
      })
    },
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != undefined) {

          } else {
            addLabelTask(this.form).then(response => {
              this.msgSuccess("新增成功");
              this.form= {};
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    submitForm3(){
      this.$refs["form"].validate((valid) => {
        if (valid) {
          console.log('submit!');
          pullTaskImage({taskName: this.form3.taskName, number: this.form3.number, type: this.form3.type}).then(response =>{
            alert("获取成功，即将彼跳转界面");
            let token= getToken();
            this.open3= false;
            if(this.form3.type=="label"){
              window.open('http://10.66.33.113:8082//split.html?token=' + token + '&taskName='+
                this.form3.taskName+ '&type=' + this.form3.type + "&streamId=" );
            }else {
              window.open('http://10.66.33.113:8082//check.html?token=' + token + '&taskName=' +
                this.form3.taskName + '&qa_type=' + this.form3.type+ "&streamId=");
            }
          });
        } else {
          alert('提交错误');
          return ;
        }
      });
    }
  }
};
</script>
