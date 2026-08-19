# Script to download and run Tomcat 10.1.17 for Lab 7
$tomcatVersion = "10.1.17"
$tomcatUrl = "https://archive.apache.org/dist/tomcat/tomcat-10/v${tomcatVersion}/bin/apache-tomcat-${tomcatVersion}.zip"
$targetDir = "$PSScriptRoot\tomcat"
$tomcatDir = "$targetDir\apache-tomcat-${tomcatVersion}"
$warFile = "$PSScriptRoot\target\lab07-crud-mvc.war"
$webappsDir = "$tomcatDir\webapps"

# Create target directory if it doesn't exist
if (-not (Test-Path $targetDir)) {
    New-Item -ItemType Directory -Path $targetDir | Out-Null
}

# Download Tomcat if not already present
$tomcatZip = "$targetDir\apache-tomcat-${tomcatVersion}.zip"
if (-not (Test-Path $tomcatDir)) {
    Write-Host "Downloading Apache Tomcat ${tomcatVersion}..." -ForegroundColor Cyan
    Invoke-WebRequest -Uri $tomcatUrl -OutFile $tomcatZip
    
    Write-Host "Extracting Tomcat..." -ForegroundColor Cyan
    Expand-Archive -Path $tomcatZip -DestinationPath $targetDir -Force
    Write-Host "Tomcat extracted to: $tomcatDir" -ForegroundColor Green
}

# Copy WAR file to webapps
if (Test-Path $warFile) {
    Write-Host "Deploying WAR file to Tomcat..." -ForegroundColor Cyan
    Copy-Item -Path $warFile -Destination "$webappsDir\lab07-crud-mvc.war" -Force
    Write-Host "WAR deployed: $webappsDir\lab07-crud-mvc.war" -ForegroundColor Green
} else {
    Write-Host "ERROR: WAR file not found at $warFile" -ForegroundColor Red
    exit 1
}

# Start Tomcat
Write-Host "Starting Tomcat 10.1.17 on port 8080..." -ForegroundColor Cyan
$env:CATALINA_HOME = $tomcatDir
$env:CATALINA_BASE = $tomcatDir
$env:CATALINA_OPTS = "-Xmx512m"

& cmd /c "$tomcatDir\bin\catalina.bat" run
