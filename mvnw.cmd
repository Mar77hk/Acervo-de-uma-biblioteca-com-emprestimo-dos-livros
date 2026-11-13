@echo off
setlocal
set "MAVEN_VERSION=3.9.11"
set "CACHE_DIR=%USERPROFILE%\.m2\wrapper\dists\apache-maven-%MAVEN_VERSION%"
set "MAVEN_HOME=%CACHE_DIR%\apache-maven-%MAVEN_VERSION%"
if not exist "%MAVEN_HOME%\bin\mvn.cmd" (
  echo Apache Maven %MAVEN_VERSION% nao encontrado. Baixando pela primeira vez...
  if not exist "%CACHE_DIR%" mkdir "%CACHE_DIR%"
  powershell -NoProfile -ExecutionPolicy Bypass -Command "$ErrorActionPreference='Stop'; $zip=Join-Path $env:USERPROFILE '.m2\wrapper\dists\apache-maven-3.9.11\maven.zip'; Invoke-WebRequest -Uri 'https://archive.apache.org/dist/maven/maven-3/3.9.11/binaries/apache-maven-3.9.11-bin.zip' -OutFile $zip; Expand-Archive -Path $zip -DestinationPath (Split-Path $zip) -Force; Remove-Item $zip"
  if errorlevel 1 (
    echo Falha ao baixar Maven. Verifique sua conexao com a internet.
    exit /b 1
  )
)
if not exist "%MAVEN_HOME%\bin\mvn.cmd" (
  echo Nao foi possivel localizar o Maven em %MAVEN_HOME%.
  exit /b 1
)
call "%MAVEN_HOME%\bin\mvn.cmd" %*
exit /b %ERRORLEVEL%
