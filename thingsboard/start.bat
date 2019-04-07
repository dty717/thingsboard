#@ECHO OFF
REM deploy.bat
cd C:\Users\xqy\Desktop\github\dty717\thingsboard\thingsboard\target
SET CATALINA_HOME=C:\Users\xqy\Desktop\apache-tomcat-9.0.0.M22 - ¸±±¾
copy /Y "aaa.war" "%CATALINA_HOME%\webapps"

REM "%CATALINA_HOME%\bin\catalina.bat" run
