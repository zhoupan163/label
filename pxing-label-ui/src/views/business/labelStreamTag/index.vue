<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" v-show="showSearch" :inline="true">
      <el-form-item label="项目名称" prop="status">
        <!--参考激活或者什么-->
        <el-select
          v-model="queryParams.projectName"
          placeholder="请选择"
          clearable
          size="small"
          style="width: 240px"
        >
          <el-option
            v-for="item in projectList"
            :key="item.projectName"
            :label="item.projectName"
            :value="item.projectName"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="传感器位置" prop="status">
        <el-input
          v-model="queryParams.sensorLocation"
          placeholder="请输入"
          clearable
          size="small"
          style="width: 240px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="标记状态" prop="status">
        <!--参考激活或者什么-->
        <el-select
          v-model="queryParams.tagStatus"
          placeholder="请选择"
          clearable
          size="small"
          style="width: 240px"
        >
          <el-option
            v-for="item in tagStatusOptions"
            :key="item.dictLabel"
            :label="item.dictLabel"
            :value="item.dictValue"
          />
        </el-select>
      </el-form-item>

      <el-form-item label="备注信息" >
        <el-input
          v-model="queryParams.remark"
          placeholder="请输入"
          clearable
          size="small"
          style="width: 240px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="创建时间">
        <el-date-picker
          v-model="queryParams.dateRange"
          type="datetimerange"
          value-format="yyyy-MM-dd HH:mm:ss"
          :picker-options="pickerOptions"
          range-separator="至"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          align="right">
        </el-date-picker>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-table v-loading="loading" :data="streamList1" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="streamId" prop="streamId" width="120" />
      <el-table-column label="工程名称" prop="projectName" width="120" />
      <el-table-column label="size" prop="frameSize" width="120" />
      <el-table-column label="传感器位置" prop="sensorLocation" width="120" />
      <el-table-column label="传感器类型" prop="sensorType" width="120" />
      <el-table-column label="topic类型" prop="topicType" width="120" />
      <el-table-column label="标记状态" prop="tagStatus":formatter="statusFormat" width="120" />
      <el-table-column label="上传时间" align="center" prop="updateTime" width="220">
        <template slot-scope="scope">
          <span>{{ formatDate(scope.row.updateTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="备注信息" prop="comment" width="120" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope" >
          <el-button
            v-if="scope.row.tagStatus!= 1"
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="tag(scope.row, 'add')"
            v-hasPermi="['business:labelStreamTag:tag']"
          >标记</el-button>
          <el-button
            v-if="scope.row.tagStatus== 1"
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="tag(scope.row, 'edit')"
            v-hasPermi="['business:labelStreamTag:tagUpdate']"
          >修改标记</el-button>
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
            <div class="tag1" v-for="(value ,key) in tagJson":key="key" >
              <div class= "sence" >{{key}}</div>
              <el-checkbox-group v-model="tagIdList">
                <el-checkbox v-for="tag in value" :label="tag.id" :key="tag.id">{{tag.tagName}}</el-checkbox>
              </el-checkbox-group>
            </div>
          </div>
        </el-col>
    </el-row>
      <pagination
        v-show="total>0"
        :total="imageTotal"
        :page.sync="queryParams1.pageNum"
        :limit.sync="queryParams1.pageSize"
        @pagination="getList2()"
      />
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm" >提交</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>

import {
  selectStreamList,
  listStreamList,
  selectImageListByStreamId,
  selectTagListByProjectName,
  selectTagListByStreamId, addTagStream, updateTagStream
} from "@/api/business/labelStreamTag"
import Moment from 'moment'
import {listLabelProject} from "@/api/business/labelProject"
export default {
  name: "labelStreamTag",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 显示搜索条件
      showSearch: true,
      sensorLocationList:["front","back"],
      // 总条数
      total: 0,
      // 任务表格数据
      streamList: [],
      streamList1: [],

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
        projectName: undefined,
        tagStatus: undefined,
        sensorLocation: undefined,
        dateRange: [],
        remark: undefined,
      },
      queryParams1: {
        pageNum: 1,
        pageSize: 10,
      },
      streamId: undefined,
      tagJson: {},
      tagIdList: [],
      // 表单参数
      form: {
        disabled: true
      },
      type: "add",
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
    listLabelProject().then(response => {
      this.projectList= response.rows;
    });
    console.log(this.projectList);
    this.getDicts("tag_status").then(response => {
      this.tagStatusOptions = response.data;
      console.log(this.tagStatusOptions);
      this.tagStatusOptions.forEach(row =>{
        this.tagStatusDict[row.dict_value]= row.dict_label;
      })
    });
  },
  methods: {
    handleSelectionChange(){

    },
    handleCheckedTag(value ,key, tag){
      if(value){
         if(this.tagIdList.indexOf(tag)== -1){
           this.tagIdList.push(tag);
         }
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
      console.log(this.queryParams);
      listStreamList(this.queryParams).then(
        response => {
          this.streamList1 = response.rows;
          this.loading = false;
          //this.getList1()
          this.total = response.total;
        }
      );
    },
    getList1(){
      this.streamList1= this.streamList.slice(this.queryParams.pageSize * (this.queryParams.pageNum -1),
        this.queryParams.pageSize * this.queryParams.pageNum)
      this.loading = false;
    },
    getList2(){
      this.imageTableList= this.imageList.slice((this.queryParams1.pageNum-1)*this.queryParams1.pageSize, this.queryParams1
        .pageNum*this.queryParams1.pageSize)
      this.open = true;
    },
    //时间戳转换方法
    formatDate(stamp) {
       var date = new Date(stamp);
       const time = Moment(date*1000).format('YYYY-MM-DD HH:mm:ss')
       return time;
     },
    statusFormat(row, column) {
      //alert(row.tagStatus);
      if(row.tagStatus== null){
        return "未标记";
      }
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
    tag(row, type) {
      this.type= type;
      this.streamId= row.streamId;
      selectTagListByProjectName(row.projectName).then(response =>{
        this.tagJson= {};
           var list= response.rows;
           for(var row in list){
             var sceneName= list[row].sceneName;
             if(this.tagJson.hasOwnProperty(sceneName)){
               var tagList= this.tagJson[sceneName];
               if(tagList.indexOf(list[row])== -1){
                 tagList.push(list[row]);
               }
               this.tagJson[sceneName]= tagList;
             }else{
               this.tagJson[sceneName] = [list[row]];
             }
           }
      });
      selectImageListByStreamId(row.streamId).then(response => {
        this.imageList= response.rows;
        this.imageTotal= response.total;
        this.getList2();
      });
      if(type== "edit"){
        selectTagListByStreamId(row.streamId).then(response => {
          this.tagIdList=response.rows.map(item=> item.tagId);
          this.title= "修改stream"
          //alert("ok")
        })
      }else{
        this.title= "标记stream"
      };
    },
    submitForm(){
      //alert(this.tagIdList);
      if(this.tagIdList.length <1){
        alert("至少需要选中一个标签");
        return;
      };
      const tagIds= this.tagIdList.join(",");
      //alert(tagIds);
      if(this.type =="edit"){
        updateTagStream({streamId: this.streamId, tagIds: tagIds}).then(response =>{
          alert("标记成功");
          this.cancel();
          this.tagList= [];
          this.tagJson={};
          this.getList();
        })
      } else{
        addTagStream({streamId: this.streamId, tagIds: tagIds}).then(response =>{
          alert("标记成功");
          this.cancel();
          this.tagList= [];
          this.tagJson={};
          this.getList();
        })
      };

    }
 }
};
</script>
