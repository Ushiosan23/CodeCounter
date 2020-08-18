// Plugins section
plugins {
	application
	kotlin("jvm") version "1.3.72"
	kotlin("plugin.serialization") version "1.3.72"
}

// Global variables
val appMainClass = "fx.soft.codecounter.Main"
val appVendor = "Ushiosan23"

// Repositories section
repositories {
	mavenCentral()
	jcenter()
}

// Configuration app
application {
	group = "fx.soft.codecounter"
	version = "0.0.1"
	mainClassName = appMainClass
}

// Configure java source
configure<JavaPluginConvention> {
	sourceCompatibility = JavaVersion.VERSION_11
}

// Configure tasks
tasks {
	
	// Kotlin configuration
	compileKotlin {
		kotlinOptions.jvmTarget = "11"
	}
	
	// Kotlin test configuration
	compileTestKotlin {
		kotlinOptions.jvmTarget = "11"
	}
	
	// Jar configuration
	jar {
		// Manifest configuration
		manifest {
			attributes(
				"Main-Class" to appMainClass,
				"Specification-Title" to rootProject.name,
				"Specification-Version" to archiveVersion.get(),
				"Specification-Vendor" to appVendor
			)
		}
		
		// Include files
		from(configurations.compileClasspath.map { config ->
			config.map { item ->
				if (item.isDirectory) item else zipTree(item)
			}
		})
	}
}

// Dependencies section
dependencies {
	// Kotlin implementations
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("org.jetbrains.kotlin:kotlin-serialization:1.3.72")
	implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime:0.20.0")
	implementation("org.jetbrains.kotlin:kotlin-reflect:1.3.72")
	
	// Test implementation
	implementation("junit:junit:4.12")
}
