@echo off
echo 正在启动 Sisyphus Tempus 项目...

REM 保存当前目录
set CURRENT_DIR=%CD%

echo 启动后端服务...
cd /d %CURRENT_DIR%\backend_java\demo
start cmd /k "mvn spring-boot:run"

echo 等待后端启动...
timeout /t 15 /nobreak

echo 启动前端服务...
cd /d %CURRENT_DIR%
start cmd /k "cd frontend/Sisyphus && npm install && npm run dev"

echo 项目启动中，请等待浏览器自动打开...
echo 如果浏览器没有自动打开，请访问: http://localhost:5173
echo 后端API地址: http://localhost:8080/api

REM 返回到原来的目录
cd /d %CURRENT_DIR%