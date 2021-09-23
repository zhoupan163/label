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
          v-hasPermi="['business:labelVideGroup:add']"
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
            v-hasPermi="['business:labelVideGroup:idList']"
          >id列表</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleImagesTask(scope.row)"
            v-hasPermi="['business:labelVideGroup:label']"
          >标注</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="filterStream(scope.row)"
            v-hasPermi="['business:labelVideGroup:filterStream']"
          >筛选</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="qa(scope.row,1)"
            v-hasPermi="['business:labelVideGroup:qa1']"
          >一级审核</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="qa(scope.row,2)"
            v-hasPermi="['business:labelVideGroup:qa2']"
          >二级审核</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="previewResult(scope.row)"
            v-hasPermi="['business:labelVideGroup:previewResult']"
          >标注结果预览下载</el-button>
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
              <el-input v-model="form.groupName" placeholder="请输入视频组名称" maxlength="30" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="备注" prop="remark">
              <el-input v-model="form.remark" type="textarea" placeholder="请输入备注"></el-input>
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
      <el-table v-loading="loading" :data="taggedStreamList" >
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
            v-hasPermi="['business:labelVideGroupId:add']"
          >新增</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="danger"
            plain
            icon="el-icon-delete"
            size="mini"
            :disabled="multiple"
            @click="handleDelete"
            v-hasPermi="['business:labelVideGroupId:remove']"
          >删除</el-button>
        </el-col>
      </el-row>
      <el-table v-loading="loading3" :data="idList" @selection-change="handleSelectionChange1">
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="id" prop="id" width="120" v-show="false" v-if="false" />
        <el-table-column label="组名" prop="groupName" width="120" />
        <el-table-column label="personId" prop="personId" width="120" />
        <el-table-column label="名称" prop="personName" width="120" />
        <el-table-column label="图片列表" prop="imgUrl" width="220" >
          <template   slot-scope="scope">
            <img :src="scope.row.imgUrl"  min-width="140" height="100" />
          </template>
        </el-table-column>
        <el-table-column label="备注" prop="remark" width="120" />

      </el-table>
      <pagination
        v-show="total3>0"
        :total="total3"
        :page.sync="queryParams.pageNum"
        :limit.sync="queryParams.pageSize"
        @pagination="getStream(taggedStreamList.get(0).taskName)"
      />
    </el-dialog>

    <!-- 添加id框 -->
    <el-dialog :title="title4" :visible.sync="open4" width="400px" append-to-body>
      <el-form ref="form4" :model="form4" :rules="idRules" label-width="80px">
        <el-row>
          <el-col :span="24">
            <el-form-item label="id"  prop="personId">
              <el-input v-model="form4.personId"  placeholder="请输入id"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="name" prop="personName">
              <el-input v-model="form4.personName"  placeholder="请输入名称"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="224">
            <el-form-item label="图片" >
              <el-upload
                limit=limitNum
                :auto-upload="false"
                accept=".jpg"
                :before-upload="beforeUploadFile"
                :on-change="fileChange"
                :on-exceed="exceedFile"
                :on-success="handleSuccess"
                :on-error="handleError"
                :file-list="fileList"
               >
                <el-button size="small" plain>选择文件</el-button>
                <div slot="tip" class="el-upload__tip">只能上传jpg文件，且不超过10M</div>
              </el-upload>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="24">
            <el-form-item label="备注"  prop="remark">
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

    <!-- 添加或修改任务配置对话框 -->
    <el-dialog :title="title5" :visible.sync="open5" width="500px" append-to-body>
      <el-table v-loading="loading5" :data="taskStreamList" >
        <el-table-column label="stream_id" prop="streamId" width="120" />
        <el-table-column label="任务名称" prop="taskName" width="120" />
        <el-table-column label="图片数量" prop="size" width="120" />
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="text"
              icon="el-icon-edit"
              @click="qaStream(scope.row)"
            >选取审核</el-button>
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

    <!-- 添加或修改任务配置对话框 -->
    <el-dialog :title="title6" :visible.sync="open6" width="1400px" append-to-body>
      <el-table v-loading="loading6" :data="taskStreamList" @selection-change="handleSelectionChange2">
        <el-table-column type="selection" width="45" align="center" />
        <el-table-column label="id" prop="id" width="100" />
        <el-table-column label="任务名称" prop="taskName" width="100" />
        <el-table-column label="stream_id" prop="streamId" width="100" />
        <el-table-column label="视频组名" prop="groupName" width="100" />
        <el-table-column label="图片数量" prop="size" width="80" />
        <el-table-column label="状态" prop="status":formatter="statusFormat" width="100" />
        <el-table-column label="标注人" prop="label" width="100" />
        <el-table-column label="一级审核人" prop="qa1" width="100" />
        <el-table-column label="二级审核人" prop="qa2" width="100" />
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="text"
              icon="el-icon-edit"
              @click="view(scope.row)"
            >预览</el-button>
          </template>
        </el-table-column>
      </el-table>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="downLoad">下 载</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
      <pagination
        v-show="total5>0"
        :total="total5"
        :page.sync="queryParams.pageNum"
        :limit.sync="queryParams.pageSize"
        @pagination="getLabelTaskStream(taskStreamList.get(0).taskName)"
      />
    </el-dialog>
  </div>
</template>

<script>
import {
  listLabelTask, getkUnfinishedTaskStream, assignTaskStream, addLabelTask,
  getTaggedStreamList, addTaskStream, getUnAssignedTaskStream, getTaskStream
} from "@/api/business/labelTask"
import { getToken } from "@/utils/auth";
import {listLabelProject} from "@/api/business/labelProject";
import {
  addLabelVideoGroup,
  addLabelVideoGroupId, delLabelVideoGroupId, delLabelVideoGroupIds,
  listVideoGroup,
  listVideoGroupId
} from "@/api/business/labelVideoGroup";
import {delRole} from "@/api/system/role";
import {downLoadLabelData, downLoadStreamData} from "@/api/business/labelData";



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
      total3: 0,
      loading3: true,

      title5: "",
      open5: false,
      total5: 0,
      loading5: true,

      title6: "",
      open6: false,
      total6: 0,
      loading6: true,

      title4: "",
      open4: false,

      groupName: undefined,

      statusOptions: [],

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

      //图片
      limitNum: 1,
      formLabelWidth: '80px',

      taskStreamIds: [],
      taskStreams: [],

      fileList: [],

      defaultProps: {
        children: "children",
        label: "label"
      },
      // 表单校验
      rules: {
        taskName: [
          { required: true, message: "任务名称不能为空", trigger: "blur" }
        ],
        groupName: [
          { required: true, message: "视频组名称不能为空", trigger: "blur" }
        ],
        remark: [
          { required: true, message: "备注不能为空", trigger: "blur" }
        ]
      },
      idRules: {
        personId: [
          { required: true, message: "id不能为空", trigger: "blur" }
        ],
        personName: [
          { required: true, message: "名称不能为空", trigger: "blur" }
        ],
        image: [
          { required: true, message: "图片不能为空", trigger: "blur" }
        ],
        remark: [
          { required: true, message: "备注不能为空", trigger: "blur" }
        ]
      }
    };
  },
  created() {
    this.getList();
    this.getDicts("business_labelTask_type").then(response => {
      this.typeOptions = response.data;
    });
    this.getDicts("business_label_status").then(response => {
      this.statusOptions = response.data;
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
    /** 查询任务列表 */
    getIdList() {
      listVideoGroupId(this.groupName).then(response =>{
        this.idList= response.rows;
        this.total3= true;
        this.loading3= false;
      })},
    typeFormat(row, column) {
      return this.selectDictLabel(this.typeOptions, row.type);
    },
    statusFormat(row, column) {
      return this.selectDictLabel(this.statusOptions, row.status);
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 取消按钮
    cancel4() {
      this.open4 = false;
      this.reset4();
      listVideoGroupId(this.groupName).then(response =>{
        this.idList= response.rows;
        this.open3= true;
        this.loading3= false;
        this.title3= "id列表";
      })
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

    // 多选框选中数据
    handleSelectionChange1(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!=1
      this.multiple = !selection.length
    },
    // 多选框选中数据
    handleSelectionChange2(selection) {
      this.taskStreams = selection
      this.taskStreamIds= selection.map(item => item.id)
      this.single = selection.length!=1
      this.multiple = !selection.length
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$confirm('是否确认删除数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function() {
        return delLabelVideoGroupIds(ids);
      }).then(() => {
        this.msgSuccess("删除成功");
        this.cancel4();
      }).catch(() => {});
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
        this.total3= response.total;
        this.loading3= false;
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
    reset4() {
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
      this.resetForm("form3");
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

    // 文件超出个数限制时的钩子
    exceedFile(files, fileList) {
      this.$notify.warning({
        title: '警告',
        message: `只能选择 ${this.limitNum} 个文件，当前共选择了 ${files.length + fileList.length} 个`
      });
    },
    // 文件状态改变时的钩子
    fileChange(file, fileList) {
      console.log('change')
      console.log(file)
      this.form4.file = file.raw
      console.log(this.form4.file)
      console.log(fileList)
    },
    // 上传文件之前的钩子, 参数为上传的文件,若返回 false 或者返回 Promise 且被 reject，则停止上传
    beforeUploadFile(file) {
      console.log('before upload')
      console.log(file)
      let extension = file.name.substring(file.name.lastIndexOf('.')+1)
      let size = file.size / 1024 / 1024
      if(extension !== 'xlsx') {
        this.$notify.warning({
          title: '警告',
          message: `只能上传后缀jpg的文件`
        });
      }
      if(size > 10) {
        this.$notify.warning({
          title: '警告',
          message: `文件大小不得超过10M`
        });
      }
    },
    // 文件上传成功时的钩子
    handleSuccess(res, file, fileList) {
      this.$notify.success({
        title: '成功',
        message: `文件上传成功`
      });
    },
    // 文件上传失败时的钩子
    handleError(err, file, fileList) {
      this.$notify.error({
        title: '错误',
        message: `文件上传失败`
      });
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
                window.open('http://10.66.66.121:8082/?token=' + token + '&taskName=' +this.taskName +'&streamId='+
                  taskStreamList[0].streamId);
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
            this.form4.groupName= this.groupName;
            let formData = new FormData();
            formData.append("personId", this.form4.personId);
            formData.append("personName", this.form4.personName);
            formData.append("remark", this.form4.remark);
            formData.append("groupName", this.groupName);
            if(!this.form4.file){
              alert("请选择图片");
              return;
            };
            formData.append('file', this.form4.file);

            addLabelVideoGroupId(formData).then(response => {
              this.msgSuccess("新增成功");
              this.cancel4();
            });
          }
        }
      });
    },
    /** 选取stream_id按钮操作 */
    qa(row, qa_level) {
      let token=getToken();
      this.qa_type="qa"+qa_level;
      getkUnfinishedTaskStream(row.taskName, row.groupName, this.qa_type).then(
        response =>{
          this.taskName= row.taskName;
          this.taskStreamList = response.rows;
          if(this.taskStreamList.length>0){
            //this.msgInfo("此任务下未你有未完成审核的stream，即将跳转审核界面");
            alert("此任务下未你有未完成审核的图片，即将跳转审核界面");
            window.open('http://10.66.66.121:8082/check.html?taskName=' + row.taskName + "&qa_type="+
              this.qa_type+ '&streamId=' +this.taskStreamList[0].streamId + "&token="+ token);
          }else{
            //this.reset();
            getUnAssignedTaskStream(row.taskName, row.groupName, this.qa_type).then(
              response => {
                this.taskStreamList = response.rows;
                this.total = response.total;
                this.open5 = true;
                this.title5 = row.groupName;
                this.loading5= false;
              });
          }
        }
      )
    },
    //选取审核
    qaStream(row){
      assignTaskStream({streamId: row.streamId, taskName:this.taskName , type: this.qa_type}).then(response =>{
        this.msgSuccess("选定成功，开始审核");
        this.open5 = false;
        let token=getToken();
        window.open('http://10.66.66.121:8082/check.html?taskName=' + row.taskName + "&qa_type="+ this.qa_type +'' +
          '&streamId=' +row.streamId+ "&token="+ token);
      })
    },
    previewResult(row) {
      getTaskStream(row.taskName, row.groupName, " previewResult").then(
        response => {
          this.taskStreamList = response.rows;
          this.total5 = response.total;
          this.open6 = true;
          this.title6 = row.groupName;
          this.loading6 = false;
        });
    },
    view(row){
      this.open6 = false;
      let token=getToken();
      window.open('http://10.66.66.121:8082/check.html?taskName=' + row.taskName + "&qa_type="+ "view" +'' +
        '&streamId='+ row.streamId+ "&token="+ token);
    },
    downLoad(){
      //alert(typeof this.taskStreams)
      //alert(this.taskStreams.length)
      //alert(this.taskStreams[0].taskName)
      if(this.taskStreamIds.length< 1){
        alert("未选取下载任务")
        return;
      }
      downLoadStreamData(this.taskStreamIds).then(
        response =>{
          this.open6 = false;
          const aLink =document.createElement('a')
          const blob = new Blob([response], { type: 'application/zip' })
          //let fileName =`${row.taskName}`
          let fileName = this.taskStreams[0].groupName
          aLink.href = window.URL.createObjectURL(blob)
          aLink.setAttribute('download', fileName)
          document.body.appendChild(aLink)
          aLink.click()
          document.body.appendChild(aLink)
        }
      );
    }
  }
    };
</script>
