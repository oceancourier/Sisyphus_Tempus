@echo off
echo �������� Sisyphus Tempus ��Ŀ...

REM ���浱ǰĿ¼
set CURRENT_DIR=%CD%

echo ������˷���...
cd /d %CURRENT_DIR%\backend_java\demo
start cmd /k "mvn spring-boot:run"

echo �ȴ��������...
timeout /t 15 /nobreak

echo ����ǰ�˷���...
cd /d %CURRENT_DIR%
start cmd /k "cd frontend/Sisyphus && npm install && npm run dev"

echo ��Ŀ�����У���ȴ�������Զ���...
echo ��������û���Զ��򿪣������: http://localhost:5173
echo ���API��ַ: http://localhost:8080/api

REM ���ص�ԭ����Ŀ¼
cd /d %CURRENT_DIR%