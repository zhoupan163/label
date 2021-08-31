<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" v-show="showSearch" :inline="true">

      <el-form-item label="项目id" prop="projectId">
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
          v-model="queryParams.projectName"
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

    <el-table v-loading="loading" :data="roleList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="工程id" prop="id" width="120" />
      <el-table-column label="工程名称" prop="projectName" :show-overflow-tooltip="true" width="150" />
      <el-table-column label="任务创建人" prop="createBy" width="120" />
      <el-table-column labe
                       l="创建时间" align="center" prop="createTime" width="120">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column labe
                       l="修改时间" align="center" prop="createTime" width="120">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="备注信息" prop="remark" width="120" />
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
      <el-table v-loading="loading" :data="taskStreamList">
        <el-table-column label="stream_id" prop="streamId" width="120" />
        <el-table-column label="任务名称" prop="taskName" width="120" />
        <el-table-column label="图片数量" prop="size" width="120" />
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="text"
              icon="el-icon-edit"
              @click="selectStream(scope.row)"
              v-hasPermi="['business:labelTask:labelTaskStreamSelect']"
            >选取标注</el-button>
          </template>
        </el-table-column>
      </el-table>
      <pagination
        v-show="total>0"
        :total="total"
        :page.sync="queryParams.pageNum"
        :limit.sync="queryParams.pageSize"
        @pagination="getLabelTaskStream(taskStreamList.get(0).taskName)"
      />
    </el-dialog>
  </div>
</template>

<script>
import { listLabelTask, getLabelTaskUnfinishedStream , getLabelTaskStream, assignLabelTaskStream} from "@/api/business/labelTask"
import { getToken } from "@/utils/auth";

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
      // 状态数据字典
      statusOptions: [],
      // 数据范围选项,
      // 菜单列表
      menuOptions: [],
      // 部门列表
      deptOptions: [],
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
      defaultProps: {
        children: "children",
        label: "label"
      },
      // 表单校验
      rules: {
        projectName: [
          { required: true, message: "任务名称不能为空", trigger: "blur" }
        ]
      }
    };
  },
  created() {
    this.getList();
    this.getDicts("sys_normal_disable").then(response => {
      this.statusOptions = response.data;
    });
  },
  methods: {
    /** 查询任务列表 */
    getList() {
      this.loading = true;
      let token=getToken();
      //alert(token)
      listLabelTask(this.addDateRange(this.queryParams, this.dateRange)).then(
        response => {
          this.roleList = response.rows;
          this.total = response.total;
          this.loading = false;
        }
      );
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
    /** 选取stream_id按钮操作 */
    handleImagesTask(row) {
      let token=getToken();
      getLabelTaskUnfinishedStream(row.taskName, token).then(
        response =>{
          this.taskStreamList = response.rows;
          if(this.taskStreamList.length>0){
            //alert(this.taskStreamList[0].stream_id);
            //alert(this.taskStreamList[0].image_status);
            if(row.type==0){
              if (this.taskStreamList[0].status== 4){
                alert("你有审批驳回的任务，即将跳转标注界面")
              }else {
                alert("你有未完成标注的任务，即将跳转标注界面")
              };
              window.open('http://10.66.66.121:8082/?token=' + token.toString() + '&taskName=' +row.taskName + '' + '&streamId=' +this.taskStreamList[0].streamId);
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
            getLabelTaskStream(row.taskName).then(
              response => {
                this.taskStreamList = response.rows;
                //alert(response.rows)
                this.total = response.total;
                //this.loading = false;
              });
          }
        }
      )
    },
    selectStream(row){
      let token=getToken();
      //alert(row.streamId);
      assignLabelTaskStream(row.streamId, row.taskName, token, "label");
      this.msgSuccess("选定成功，开始标注");
      this.open = false;
      window.open('http://10.66.66.121:8082/?token=' + token + '&taskName=' +row.taskName +'&streamId=' +row.streamId);
    },


  }
};
</script>
