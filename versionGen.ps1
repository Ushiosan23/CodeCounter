<# Get current directory path #>
function GetCurrentPath
{
	return $PSScriptRoot
}

<# Get file target path #>
function GetTargetPathFilePath
{
	$basePath = GetCurrentPath
	return Join-Path $basePath "src" "fx" "codecounter" "program" "version.version"
}

<# Create version file with data #>
function CreateVersionFile
{
	<# save path #>
	$targetFile = GetTargetPathFilePath
	
	<# create file #>
	if (![System.IO.File]::Exists($targetFile))
	{
		New-Item -Path $targetFile -ItemType File
		UpdateVersion -location $targetFile -firstTime $true
	}
	else
	{
		UpdateVersion -location $targetFile -firstTime $false
	}
	
}

<# Get current file data #>
function GetFileData([String] $location)
{
	return Get-Content -Path $location
}

<# Write data in file location #>
function WriteFileData([String] $location, [String] $data)
{
	Set-Content -Path $location -Value $data
}

<# Update version file content #>
function UpdateVersion([String] $location, [Boolean] $firstTime)
{
	$timestamp = [Math]::Round((Get-Date).ToFileTime() / 10000)
	if ($firstTime)
	{
		$data = "0.0." + $timestamp
		WriteFileData -location $location -data $data
	}
	else
	{
		$data = GetFileData -location $location
		$split = $data.Split(".")
		
		$finalData = $split[0] + "." + $split[1] + "." + $timestamp
		
		WriteFileData -location $location -data $finalData
	}
	
}

<# call create version #>
CreateVersionFile