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
      <el-form-item label="状态" prop="status">
        <!--参考激活或者什么-->
        <el-select
          v-model="queryParams.status"
          placeholder="任务状态"
          clearable
          size="small"
          style="width: 240px"
        >
          <el-option
            v-for="dict in statusOptions"
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

    <el-table v-loading="loading" :data="roleList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="任务编号" prop="taskId" width="120" />
      <el-table-column label="任务名称" prop="taskName" :show-overflow-tooltip="true" width="150" />
      <el-table-column label="关联项目id" prop="projectId" :show-overflow-tooltip="true" width="150" />
      <el-table-column label="任务状态" prop="status" width="120" />
      <el-table-column label="任务类型（0视频 1图片）" prop="type" width="120" />
      <el-table-column label="图片总数量" prop="size" width="120" />
      <el-table-column label="图片完成标注数量" prop="finishedCount" width="120" />
      <el-table-column label="任务创建人" prop="createBy" width="120" />
      <el-table-column labe
                       l="创建时间" align="center" prop="createTime" width="120">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="备注信息" prop="remark" width="120" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope" v-if="scope.row.type== 0">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-download"
            @click="taskDownload(scope.row)"
            v-hasPermi="['business:labelData:taskDownload']"
          >下载</el-button>
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


  </div>
</template>

<script>
import { listLabelTask, getLabelTaskUnfinishedStream , getLabelTaskStream, assignLabelTaskStream} from "@/api/business/labelTask"
import {downLoadLabelData} from "@/api/business/labelData"
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
      // 任务表格数据
      roleList: [],
      taskStreamList: [],
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
      // 数据范围选项
      dataScopeOptions: [
        {
          value: "1",
          label: "全部数据权限"
        },
        {
          value: "2",
          label: "自定数据权限"
        },
        {
          value: "3",
          label: "本部门数据权限"
        },
        {
          value: "4",
          label: "本部门及以下数据权限"
        },
        {
          value: "5",
          label: "仅本人数据权限"
        }
      ],
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
        taskName: [
          { required: true, message: "任务名称不能为空", trigger: "blur" }
        ],
        projectId: [
          { required: true, message: "权限字符不能为空", trigger: "blur" }
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
    taskDownload(row){
      downLoadLabelData(row.taskName).then(
        response =>{
          const aLink =document.createElement('a')
          const blob = new Blob([response], { type: 'application/zip' })
          let fileName =`${row.taskName}`
          aLink.href = window.URL.createObjectURL(blob)
          aLink.setAttribute('download', fileName)
          document.body.appendChild(aLink)
          aLink.click()
          document.body.appendChild(aLink)
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
            //this.msgInfo("此任务下未你有未完成标注的stream，即将跳转标注界面");
            alert("此任务下未你有未完成标注的stream，即将跳转标注界面")
            let taskStream=this.taskStreamList[0];
            window.open('http://10.66.66.101:8082/?token=' + token.toString() + '&streamId=' + taskStream.stream_id);
          }else{
            this.msgInfo("请选定stream标定");
            this.reset();
            this.open = true;
            this.title = row.taskName;
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
      alert(row.streamId);
      assignLabelTaskStream(row.streamId, row.taskName, token);
      this.msgSuccess("选定成功，开始标注");
      this.open = false;
      window.open('http://10.66.66.101:8082/?token=' + token + '&streamId=' + row.streamId);
    },


  }
};
</script>
