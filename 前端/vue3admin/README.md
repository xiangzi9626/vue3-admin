vue3项目单文件使用history路由模式,需要在nginx.config文件添加下面代码
location / {
try_files $uri $uri/ /index.html;
 }          