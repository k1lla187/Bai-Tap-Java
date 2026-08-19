# Download and setup Tomcat 10.1.17 for Lab 8
$tomcatVersion = "10.1.17"
$tomcatUrl = "https://archive.apache.org/dist/tomcat/tomcat-10/v${tomcatVersion}/bin/apache-tomcat-${tomcatVersion}.zip"
$targetDir = "$PSScriptRoot\tomcat"
$tomcatDir = "$targetDir\apache-tomcat-${tomcatVersion}"
$tomcatZip = "$targetDir\apache-tomcat-${tomcatVersion}.zip"

Write-Host "=== Download Tomcat $tomcatVersion ===" -ForegroundColor Cyan

# Create directory
if (-not (Test-Path $targetDir)) {
    New-Item -ItemType Directory -Path $targetDir -Force | Out-Null
}

# Download
Write-Host "Downloading from $tomcatUrl ..." -ForegroundColor Yellow
Invoke-WebRequest -Uri $tomcatUrl -OutFile $tomcatZip

# Extract
Write-Host "Extracting..." -ForegroundColor Yellow
Expand-Archive -Path $tomcatZip -DestinationPath $targetDir -Force

Write-Host "`n=== SUCCESS ===" -ForegroundColor Green
Write-Host "Tomcat path: $tomcatDir" -ForegroundColor Cyan
Write-Host "Bin folder:  $tomcatDir\bin" -ForegroundColor Cyan
Write-Host ""
Write-Host "Add to Windows PATH:" -ForegroundColor Yellow
Write-Host "  $tomcatDir\bin" -ForegroundColor White
