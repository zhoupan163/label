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

    <el-table v-loading="loading" :data="streamList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="id" prop="id" width="120" />
      <el-table-column label="传感器位置" prop="sensorLocation" width="120" />
      <el-table-column label="传感器类型" prop="sensorType" width="120" />
      <el-table-column label="topic类型" prop="topicType" width="120" />
      <el-table-column label="标记状态" prop="tagStatus":formatter="statusFormat" width="120" />
      <el-table-column label="任务创建人" prop="createBy" width="120" />
      <el-table-column label="创建时间" align="center" prop="createTime" width="220">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="备注信息" prop="remark" width="120" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope" v-if="scope.row.tagStatus== 0">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="tagStream(scope.row)"
            v-hasPermi="['business:labelTask:label']"
          >标记</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="tagStream(scope.row)"
            v-hasPermi="['business:labelTask:label']"
          >查看标记</el-button>
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

    <!-- 选择指定的项目 -->
    <el-dialog :title="title1" :visible.sync="open1" width="500px" append-to-body>
      <el-table v-loading="loading" :data="projectList">
        <el-table-column label="id" prop="id" width="120" />
        <el-table-column label="项目名称" prop="projectName" width="120" />
        <el-table-column label="备注" prop="remark" width="120" />
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="text"
              icon="el-icon-edit"
              @click="selectProject(scope.row)"
            >选取项目</el-button>
          </template>
        </el-table-column>
      </el-table>
      <pagination
        v-show="total>0"
        :total="total"
        :page.sync="queryParams.pageNum"
        :limit.sync="queryParams.pageSize"
        @pagination="getProjectList(taskStreamList.get(0).taskName)"
      />
    </el-dialog>

    <el-dialog :title="title" :visible.sync="open" width="1000px" append-to-body>
      <el-row :gutter="20">
        <el-col :span="56">
          <div class="app1">
            <el-table v-loading="loading" :data="imageTableList">
              <el-table-column label="图片列表" prop="jpgUrl" width="120" >
                <template   slot-scope="scope">
                  <img :src="scope.row.jpgUrl"  min-width="140" height="100" />
                </template>
              </el-table-column>
            </el-table>
          </div>
        </el-col>
        <el-col :span="6">
          <div class="app2">
            <div class="tag1" v-for="(value ,key) in tagJson":key="key">
              <div class= "sence" >{{key}}</div>
              <div class= "tags" v-for=" tag in value" >
                <el-checkbox @change="handleCheckedTag($event, key, tag.id)">{{tag.tagName}}</el-checkbox>
              </div>
            </div>
          </div>
        </el-col>
    </el-row>
      <pagination
        v-show="total>0"
        :total="imageTotal"
        :page.sync="queryParams.pageNum"
        :limit.sync="queryParams.pageSize"
        @pagination="getList1()"
      />
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listLabelProject} from "@/api/business/labelProject"
import { tagStream ,selectStreamTagList , selectImageListByStreamId, selectTagListByProjectId} from "@/api/business/labelStreamTag"
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
      streamList: [],

      streamId: undefined,
      pId: undefined,
      projectTotal: 0,
      projectList: [],
      projectTableList:[],
      // 弹出层标题
      title1: "",
      // 是否显示弹出层
      open1: false,

      imageTotal: 0,
      imageList: [],
      imageTableList: [],
      tagStatusDict: {},
      tagStatusOptions: [],
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
      typeOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        taskName: undefined,
        projectId: undefined,
        status: undefined
      },
      tagJson: {},
      tagIdList: [],
      // 表单参数
      form: {},
      defaultProps: {
        children: "children",
        label: "label"
      },
      tagList:{},
      // 表单校验
      rules: {
        taskName: [
          {required: true, message: "任务名称不能为空", trigger: "blur"}
        ],
        projectId: [
          {required: true, message: "权限字符不能为空", trigger: "blur"}
        ]
      }
    };
  },
  created() {
    this.getList();
    this.getDicts("tag_status").then(response => {
      this.tagStatusOptions = response.data;
      this.tagStatusOptions.forEach(row =>{
        this.tagStatusDict[row.dict_value]= row.dict_label;
      })
    });
  },
  methods: {
    /** 查询任务列表 */
    getList1(){
      this.imageTableList= this.imageList.slice((this.queryParams.pageNum-1)*this.queryParams.pageSize, this.queryParams
        .pageNum*this.queryParams.pageSize)
    },
    handleCheckedTag(value ,key, tag){
      if(value){
          this.tagIdList.push(tag);
      }else {
        this.tagIdList.pop(tag);
      }
    },
    getList() {
      this.getDicts("tag_status").then(response => {
        this.tagStatusOptions = response.data;
        this.tagStatusOptions.forEach(row =>{
          this.tagStatusDict[row.dictValue]= row.dictLabel;
        })
      });
      this.loading = true;
      selectStreamTagList().then(
        response => {
          this.streamList = response.rows;
          this.total = response.total;
          this.loading = false;
        }
      );
    },
    statusFormat(row, column) {
      return this.tagStatusDict[row.tagStatus];
    },

    // 取消按钮
    cancel() {
      this.open = false;
      this.open1= false;
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
    tagStream(row) {
      this.streamId= row.id;
      listLabelProject().then(response =>{
        this.projectList= response.rows;
        this.projectTotal= response.total;
        //alert(this.imageTotal);
        this.projectTableList= this.projectList.slice((this.queryParams.pageNum-1)*this.queryParams.pageSize, this.queryParams
          .pageNum*this.queryParams.pageSize)
        this.open1 = true;
        this.title1= "选取项目"
      })
      },
    selectProject(row){
      this.pId= row.id;
      selectTagListByProjectId(row.id).then(response =>{
           var list= response.rows;
           for(var row in list){
             var sceneName= list[row].sceneName;
             if(this.tagJson.hasOwnProperty(sceneName)){
               var tagList= this.tagJson[sceneName];
               tagList.push(list[row]);
               this.tagJson[sceneName]= tagList;
             }else{
               this.tagJson[sceneName] = [list[row]];
             }
           }
      });
      selectImageListByStreamId(this.streamId).then(response => {
        this.imageList= response.rows;
        this.imageTotal= response.total;
        this.imageTableList= this.imageList.slice((this.queryParams.pageNum-1)*this.queryParams.pageSize, this.queryParams
          .pageNum*this.queryParams.pageSize)
        this.open = true;
        this.title= "标记stream"
      })
    },
    submitForm(){
      alert(this.tagIdList);
      const tagIds= this.tagIdList.join(",");
      alert(tagIds);
      tagStream({streamId: this.streamId, projectId: this.pId, tagIds: tagIds}).then(response =>{
         alert("标记成功");
         this.cancel();
         this.getList();
       })
    }
 }
};
</script>
