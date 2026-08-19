# Download Tomcat 10.1.17 and run Lab 8
$tomcatVersion = "10.1.17"
$tomcatUrl = "https://archive.apache.org/dist/tomcat/tomcat-10/v${tomcatVersion}/bin/apache-tomcat-${tomcatVersion}.zip"
$targetDir = "$PSScriptRoot\tomcat"
$tomcatDir = "$targetDir\apache-tomcat-${tomcatVersion}"
$tomcatZip = "$targetDir\apache-tomcat-${tomcatVersion}.zip"
$webappsDir = "$tomcatDir\webapps"

Write-Host "=== Lab 8: Setup & Run ===" -ForegroundColor Cyan

# Create directory
if (-not (Test-Path $targetDir)) {
    New-Item -ItemType Directory -Path $targetDir -Force | Out-Null
}

# Download Tomcat if not present
if (-not (Test-Path $tomcatDir)) {
    Write-Host "`nDownloading Tomcat $tomcatVersion..." -ForegroundColor Yellow
    Invoke-WebRequest -Uri $tomcatUrl -OutFile $tomcatZip
    Write-Host "Extracting..." -ForegroundColor Yellow
    Expand-Archive -Path $tomcatZip -DestinationPath $targetDir -Force
    Write-Host "Tomcat extracted!" -ForegroundColor Green
}

# Build project
Write-Host "`nBuilding project..." -ForegroundColor Yellow
& mvn clean package -DskipTests

# Copy WAR to webapps
$warFile = "$PSScriptRoot\target\lab08-jsf-validation.war"
if (Test-Path $warFile) {
    Write-Host "Deploying WAR..." -ForegroundColor Yellow
    Copy-Item $warFile "$webappsDir\" -Force
    Write-Host "WAR deployed!" -ForegroundColor Green
} else {
    Write-Host "ERROR: WAR not found" -ForegroundColor Red
    exit 1
}

# Start Tomcat
Write-Host "`nStarting Tomcat on port 8081..." -ForegroundColor Cyan
Write-Host "Access at: http://localhost:8081/lab08-jsf-validation/" -ForegroundColor Green
$env:CATALINA_HOME = $tomcatDir
$env:CATALINA_BASE = $tomcatDir
& cmd /c "$tomcatDir\bin\catalina.bat" run
