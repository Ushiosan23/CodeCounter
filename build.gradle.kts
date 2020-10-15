plugins {
	application
	id("org.jetbrains.dokka") version "1.4.10"
	kotlin("jvm") version "1.4.10"
	kotlin("plugin.serialization") version "1.4.10"
}

/* repositories */
repositories {
	jcenter()
	mavenCentral()
}

/* application config */
application {
	mainClassName = "com.github.ushiosan23.codecounter.CodeCounter"
}

/* task configurations */
tasks {

	/* dokka config task */
	dokkaHtml.configure {
		outputDirectory.set(buildDir.resolve("dokka"))
		/* configure documentation */
		dokkaSourceSets {
			named("main") {
				suppress.set(false)
				includeNonPublic.set(true)
				skipDeprecated.set(false)
				skipEmptyPackages.set(false)
				sourceRoots.from(file("src"))
				jdkVersion.set(11)
				noStdlibLink.set(false)
				noJdkLink.set(false)
			}
		}
	}

	/* kotlin configuration */
	compileKotlin {
		kotlinOptions.jvmTarget = "11"
	}

	/* kotlin test */
	compileTestKotlin {
		kotlinOptions.jvmTarget = "11"
	}

}

/* configure java */

/* dependencies */
dependencies {
	/* kotlin */
	implementation(kotlin("stdlib"))
	implementation(kotlin("reflect"))
	/* serialization */
	implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:1.0.0")
	/* test */
	implementation("junit", "junit", "4.12")
}
