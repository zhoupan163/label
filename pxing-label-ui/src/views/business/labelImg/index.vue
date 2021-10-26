<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" v-show="showSearch" :inline="true">
      <el-form-item label="任务名称" prop="taskName">
        <!--参考激活或者什么-->
        <el-select
          v-model="queryParams.taskName"
          placeholder="请选择"
          clearable
          size="small"
          style="width: 240px"
        >
          <el-option
            v-for="item in taskList"
            :key="item.taskName"
            :label="item.taskName"
            :value="item.taskName"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="streamId" prop="streamId">
        <el-input
          v-model="queryParams.streamId"
          placeholder="请输入"
          clearable
          size="small"
          style="width: 240px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="imageId" prop="imageId">
        <el-input
          v-model="queryParams.imageId"
          placeholder="请输入"
          clearable
          size="small"
          style="width: 240px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="标注人" prop="label">
        <el-input
          v-model="queryParams.label"
          placeholder="请输入"
          clearable
          size="small"
          style="width: 240px"
          @keyup.enter.native="handleQuery"
        />
        <el-form-item label="一级审核人" prop="qa1">
          <el-input
            v-model="queryParams.qa1"
            placeholder="请输入"
            clearable
            size="small"
            style="width: 240px"
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="二级审核人" prop="qa2">
          <el-input
            v-model="queryParams.qa2"
            placeholder="请输入"
            clearable
            size="small"
            style="width: 240px"
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <!--参考激活或者什么-->
        <el-select
          v-model="queryParams.status"
          placeholder="请选择"
          clearable
          size="small"
          style="width: 240px"
        >
          <el-option
            v-for="item in statusOptions"
            :key="item.dictLabel"
            :label="item.dictLabel"
            :value="item.dictValue"
          />
        </el-select>
      </el-form-item>



      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-table v-loading="loading" :data=taskImgList @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="streamId" prop="streamId" width="120" />
      <el-table-column label="任务名称" prop="taskName" width="120" />
      <el-table-column label="imageId" prop="imageId" width="120" />
      <el-table-column label="标注人" prop="label" width="120" />
      <el-table-column label="一级审核人" prop="qa1" width="120" />
      <el-table-column label="二级审核人" prop="qa2" width="120" />
      <el-table-column label="状态" prop="status":formatter="statusFormat" width="120" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope" >
          <el-button
            v-if="scope.row.tagStatus!= 0"
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="view(scope.row, 'add')"
            v-hasPermi="['business:labelImg:view']"
          >预览</el-button>
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

import {listLabelTask} from "@/api/business/labelTask"
import {listLabelTaskImg} from "@/api/business/labelImg";
import {getToken} from "@/utils/auth";
export default {
  name: "labelImg",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 显示搜索条件
      showSearch: true,
      sensorLocationList: ["front", "back"],
      // 总条数
      total: 0,
      taskImgList: [],
      taskList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      statusOptions: [],
      statusDict: {},
      // 日期范围
      dateRange: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        taskName: undefined,
        status: undefined,
        streamId: undefined,
        imageId: undefined,
        label: undefined,
        qa1: undefined,
        qa2: undefined,
        dateRange: []
      }
    };
  },
  created() {
    this.getList();
    listLabelTask().then(response => {
      this.taskList = response.rows;
    });
    console.log(this.projectList);
    this.getDicts("business_label_status").then(response => {
      this.statusOptions = response.data;
      console.log("statusOptions")
      console.log(this.statusOptions);
      this.statusOptions.forEach(row => {
        console.log("row")
        console.log(row)
        console.log(row.dictValue)
        console.log(row.dictLabel)
        this.statusDict[row.dictValue] = row.dictLabel;
        console.log(this.statusDict)
      })
    });

  },
  methods: {
    handleSelectionChange() {
    },
    getList() {
      this.loading = true;
      console.log(this.queryParams);
      listLabelTaskImg(this.queryParams).then(
        response => {
          this.taskImgList = response.rows;
          this.total = response.total;
          this.loading = false;
        }
      );
    },
    statusFormat(row, column) {
      console.log(this.statusDict)
      return this.statusDict[row.status];
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
    view(row){
      let token=getToken();
      window.open(this.labelUrl + '/check.html?taskName=' + row.taskName + "&qa_type="+ "view" +'' + "&token="+ token + "&imageId=" + row.imageId);
    }
 }
};
</script>
