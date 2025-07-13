@echo off
set JAVA_HOME=C:\Program Files\Microsoft\jdk-11.0.27.6-hotspot
set PATH=%JAVA_HOME%\bin;%PATH%
java -cp "srv/main/java" com.amazonflex.AmazonFlexGrabberApplication
pause