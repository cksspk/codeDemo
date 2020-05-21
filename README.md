# 分支操作相关

``` bash
# 1.初始化拉取分支
git clone -b baseCode git@github.com:cksspk/codeDemo.git

# 2.修改后上传到远程
git push origin baseCode

# 3.拉取更新继续
git pull origin baseCode

```

### 介绍

前后台分离基础增删改查项目，资源使用阿里云`oss`对象储存。

前台：`vue-admin-template`

后台：`springboot` `mybatis`