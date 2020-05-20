<template>

  <div class="app-container">
    <!-- 搜索栏 -->
    <el-form :inline="true" label-width="68px">
      <el-form-item label="用户名称">
        <el-input v-model="queryParams.userName" placeholder="请输入用户名称" clearable size="small"
                  @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item label="手机号码">
        <el-input v-model="queryParams.phone" placeholder="请输入手机号码" clearable size="small"
                  @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item label="状态">
        <el-select v-model="queryParams.status" placeholder="用户状态" clearable size="small">
          <el-option
            v-for="dict in statusOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="创建时间">
        <el-date-picker v-model="dateRange" size="small" value-format="yyyy-MM-dd"
                        type="daterange" range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期"/>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini"
                   @click="handleQuery">搜索
        </el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <!-- 操作栏 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" icon="el-icon-plus" size="mini"
                   @click="handleAdd">新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" icon="el-icon-edit" size="mini" :disabled="single"
                   @click="handleUpdate(null)">修改
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" icon="el-icon-delete" size="mini" :loading="delLoading" :disabled="multiple"
                   @click="handleDelete">删除
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" icon="el-icon-download" size="mini"
                   @click="handleExport">导出
        </el-button>
      </el-col>
    </el-row>
    <!-- 数据表格 -->
    <el-table v-loading="loading" :data="list" @selection-change="handleSelectionChange">
      <el-table-column type="selection" align="center"/>
      <el-table-column label="用户名称" align="center" prop="userName"/>
      <el-table-column label="用户昵称" align="center" prop="nickName"/>
      <el-table-column label="手机号码" align="center" prop="phone"/>
      <el-table-column label="状态" align="center">
        <template slot-scope="scope">
          <el-switch v-model="scope.row.status" active-value="0" inactive-value="1"
                     @change="handleStatusChange(scope.row)"/>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" align="center" prop="createTime" width="160">
        <template slot-scope="scope">
          <!-- <span>{{ parseTime(scope.row.createTime) }}</span> -->
          <span>{{ scope.row.createTime }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="180" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-edit"
                     @click="handleUpdate(scope.row)">修改
          </el-button>
          <el-popover :ref="scope.row.id" placement="top" width="180">
            <p>确定删除本条数据吗？</p>
            <div style="text-align: right; margin: 0">
              <el-button size="mini" type="text" @click="$refs[scope.row.id].doClose()">取消
              </el-button>
              <el-button :loading="loading" type="primary" size="mini" @click="handleSubDelete(scope.row.id)">确定
              </el-button>
            </div>
            <el-button slot="reference" type="text" icon="el-icon-delete" size="mini">删除
            </el-button>
          </el-popover>
          <!-- 不使用重置操作 -->
          <!-- <el-button size="mini" type="text" icon="el-icon-key"
                     @click="handleResetPwd(scope.row)">重置
          </el-button> -->
          </template>
        </el-table-column>
      </el-table>
    <!-- 分页器 -->
    <pagination v-show="total>0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
                @pagination="getList"/>
    
    <!-- 添加或修改参数配置对话框 -->
   <el-dialog :close-on-click-modal="false" :title="title" :visible.sync="open" width="600px">
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="用户昵称" prop="nickName">
              <el-input v-model="form.nickName" placeholder="请输入用户昵称"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="手机号码" prop="phone">
              <el-input v-model="form.phone" placeholder="请输入手机号码" maxlength="11"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="邮箱" prop="email">
              <el-input v-model="form.email" placeholder="请输入邮箱" maxlength="50"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="用户名称" prop="userName">
              <el-input v-model="form.userName" placeholder="请输入用户名称"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item v-if="form.id == undefined" label="用户密码" prop="password">
              <el-input v-model="form.password" placeholder="请输入用户密码" type="password"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="用户性别">
              <el-select v-model="form.sex" placeholder="请选择">
                <el-option
                  v-for="dict in sexOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="状态">
              <el-radio-group v-model="form.status">
                <el-radio
                  v-for="dict in statusOptions"
                  :key="dict.dictValue"
                  :label="dict.dictValue"
                >{{dict.dictLabel}}
                </el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item label="角色">
              <el-select v-model="form.roleIds" multiple placeholder="请选择" @change="$forceUpdate()">
                <el-option
                  v-for="item in roleOptions"
                  :key="item.id"
                  :label="item.roleName"
                  :value="item.id"
                  :disabled="item.status == 1"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
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
import { listUser, delUser, getUser, addUser, updateUser, changeUserStatus } from '@/api/system/user'
import { listRole, optionSelect } from '@/api/system/role'
//字典配置，使用配置常量，可采用服务器端传入 TODO
import { Sex_dict, Account_dict} from '@/config/dict'

export default {

  data(){
    return {
      title: '',  //弹框的标题
      list: null, // 数据列表
      open: false,  // 是否显示弹出层
      form: {}, // 表单参数
      delLoading: false,  //删除按钮遮罩层
      multiple: true, // 删除按钮非多个禁用
      single : true,  // 修改按钮非单个禁用
      ids: [],   // table选中数组
      row :{},   // 选中的行数据
      dateRange: [],// 日期范围
      total: 0, // 总条数
      //*************************分割线 */
      // 默认密码
      initPassword: "123456",
      // 状态数据字典
      statusOptions: [],
      // 性别状态字典
      sexOptions: [],
      // // 角色选项
      roleOptions: [],
      // defaultProps: {
      //   children: "children",
      //   label: "label"
      // },
      // // 查询参数
      queryParams: {
        //输入栏参数
        userName: undefined,
        phone: undefined,
        status: undefined,

        pageNum: 1,
        pageSize: 10,
        orderByColumn: "create_time",
      },
      // // 表单校验
      rules: {
        userName: [
          {required: true, message: "用户名称不能为空", trigger: "blur"}
        ],
        nickName: [
          {required: true, message: "用户昵称不能为空", trigger: "blur"}
        ],
        password: [
          {required: true, message: "用户密码不能为空", trigger: "blur"}
        ],
        email: [
          {
            type: "email",
            message: "'请输入正确的邮箱地址",
            trigger: ["blur", "change"]
          }
        ],
        phone: [
          {
            pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/,
            message: "请输入正确的手机号码",
            trigger: "blur"
          }
        ]
      }
    }
  },
  created(){
    this.getList()
    //账号状态配置
    this.statusOptions = Account_dict
    //获取性别的字典
    this.sexOptions = Sex_dict   
  },
  methods :{
    //查询用户
    getList() {
      // console.log('加载用户')
      this.loading = true;
      listUser(this.addDateRange(this.queryParams, this.dateRange)).then(response=>{
        // debugger
        if(response.code === 200){
          this.total = response.total;
          this.list = response.rows;
          // console.log(response)
          // console.log('this.list:',this.list)
        }
        this.loading = false
      })
    },
    /** 查询角色列表 */
    getRoles() {
      optionSelect().then(response => {
        // debugger
        // console.log('角色ids',response.data.roles)
        this.roleOptions = response.data;
        // console.log('roleOptions:',this.roleOptions)
      });
    },

    /** 搜索查询 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList()
    },
    /**重置当前搜索框*/
    resetQuery() {
      this.dateRange = [];
      this.queryParams={};
      this.handleQuery();
    },

    //根据id删除用户
    handleSubDelete(id){
      this.loading = true;
      // console.log("点击删除id : ", id)
      delUser(id).then(response=>{
        this.$refs[id].doClose();
        this.loading = false;
        if(response.code === 200){
          //删除成功
          this.$message({
            type: 'success',
            message: '删除成功!'
          })
          this.getList()
        }
      }).catch(()=>{
          //删除失败
          this.$refs[id].doClose();
          this.loading = false;
          this.$message({
              type: 'error',
              message: '删除失败'
          })
      })
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      // console.log("多选框数据selection",selection)
      this.row = selection[0];
      this.ids = selection.map(item => item.id);
      this.single = selection.length!=1
      this.multiple = !selection.length
    },
    //导出操作
    handleExport(){
      console.log('点击导出')
    },

    //批量删除
    handleDelete(){
      this.delLoading = true;
      //取出选中ids用户的名字
      const names = this.list.filter(item => this.ids.some(id=>id === item.id)).map(user=>user.userName)
      let $this = this;
      this.$confirm('是否确认删除用户<strong>'+names+'</strong>的数据项?', "警告", {
        dangerouslyUseHTMLString : true,
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        delUser($this.ids).then(response => {
          $this.delLoading = false;
          if (response.code === 200) {
            $this.getList()
            $this.msgSuccess("删除成功");
          } else {
            $this.msgError("删除失败");
          }
        }).catch(err => {
          $this.delLoading = false
        })
      }).catch(err => {
        $this.delLoading = false
      })
    },
    // 用户状态修改
    handleStatusChange(row) {
      let text = row.status === "0" ? "启用" : "停用";
      this.$confirm('确认要"' + text + '""' + row.userName + '"用户吗?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return changeUserStatus(row.id, row.status);
      }).then((response) => {
        if (response.code === 200) {
          this.msgSuccess(text + "成功");
        } else {
          this.msgError(text + "失败");
        }
      }).catch(function () {
        row.status = row.status === "0" ? "1" : "0";
      });
    },

    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.getRoles();
      this.open = true;
      this.title = "添加用户";
      this.form.password = this.initPassword;
    },

    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.$nextTick(() => {
        if (this.$refs["form"] !== undefined) {
          this.$refs["form"].resetFields();
        }
      });
      // form初始化，默认打开用户状态为启用，对roleIds强制为[]
      this.form = {status : "0",roleIds:[]};
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      row = row || this.row
      this.reset();
      this.getRoles();
      getUser(row.id).then(response => {
        debugger
        this.form = response.data;
        this.form.roleIds = response.roleIds;
        this.open = true;
        this.title = "修改用户";
        this.form.password = "";
      });
    },
    /** 提交按钮 */
    submitForm: function () {
      this.$refs["form"].validate(valid => {
        debugger
        if (valid) {
          console.log('提交表单：this.form',this.form)
          if (this.form.id != undefined) {
            //临时修改权限
            updateUser(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                // this.init();
                 this.getList()
              } else {
                this.msgError(response.msg);
              }
            });
          } else {
            addUser(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("新增成功");
                this.open = false;
                // this.init();
                 this.getList()
              } else {
                this.msgError(response.msg);
              }
            });
          }
        }
      });
    },
  }
}
</script>

<style>

</style>