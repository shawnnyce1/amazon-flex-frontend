@echo off
echo Testing Amazon Flex Login with Real Credentials
echo WARNING: Use your actual Amazon Flex credentials

set /p EMAIL="Enter your Amazon Flex email: "
set /p PASSWORD="Enter your Amazon Flex password: "

echo.
echo Testing login...
curl -X POST http://localhost:8080/api/flex/login ^
  -H "Content-Type: application/json" ^
  -d "{\"email\":\"%EMAIL%\",\"password\":\"%PASSWORD%\"}"

echo.
echo.
echo Testing block grabbing with $25/hour minimum...
curl -X POST http://localhost:8080/api/flex/blocks/filter ^
  -H "Content-Type: application/json" ^
  -d "{\"minRate\":25.0}"

pause