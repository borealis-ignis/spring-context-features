rem SET /P PID= < shutdown.pid
rem taskkill /pid %PID% /f
curl -X POST http://localhost:8085/manage/shutdown