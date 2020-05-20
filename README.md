# 代码Demo

- 上传下载更新操作

  ```bash
  # 1.初始化拉取分支 xxx为指定分支
  git clone -b xxx git@github.com:cksspk/codeDemo.git
  
  # 2.修改后上传到远程
  git add .
  git commit -m "update"
  git push origin xxx
  
  # 3.拉取更新继续
  git pull origin xxx
  ```

  