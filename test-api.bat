@echo off
echo Testing Amazon Flex API...

echo.
echo Testing Login Endpoint:
curl -X POST http://localhost:8080/api/flex/login ^
  -H "Content-Type: application/json" ^
  -d "{\"email\":\"test@example.com\",\"password\":\"password\"}"

echo.
echo.
echo Testing Blocks Endpoint:
curl -X GET http://localhost:8080/api/flex/blocks

pause