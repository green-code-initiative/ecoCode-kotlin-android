
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java
    id("jacoco")
    id("io.spring.dependency-management") version "1.0.11.RELEASE" apply false
    id("org.sonarqube") version "3.3"
    id("org.jetbrains.kotlin.jvm") apply false
    id("com.diffplug.spotless") version "6.11.0"
    `maven-publish`
    signing
}

val projectTitle: String by project

configure(subprojects.filter { it.name != "kotlin-checks-test-sources"}) {
    apply(plugin = "com.diffplug.spotless")

    configure<com.diffplug.gradle.spotless.SpotlessExtension> {

        lineEndings = com.diffplug.spotless.LineEnding.UNIX

        fun SourceSet.findSourceFilesToTarget() = allJava.srcDirs.flatMap { srcDir ->
            project.fileTree(srcDir).filter { file ->
                file.name.endsWith(".kt") || (file.name.endsWith(".java") && file.name != "package-info.java")
            }
        }

        kotlin {
            licenseHeaderFile(rootProject.file("LICENSE_HEADER")).updateYearWithLatest(true)

            target(
                project.sourceSets.main.get().findSourceFilesToTarget(),
                project.sourceSets.test.get().findSourceFilesToTarget()
            )
        }
        kotlinGradle {
            target("*.gradle.kts")
            ktlint()
        }

        format("misc") {
            // define the files to apply `misc` to
            target("*.gradle", "*.md", ".gitignore")

            // define the steps to apply to those files
            trimTrailingWhitespace()
            indentWithSpaces()
            endWithNewline()
        }
    }
}

allprojects {
    apply<JavaPlugin>()
    apply(plugin = "jacoco")
    apply(plugin = "io.spring.dependency-management")
    apply(plugin = "maven-publish")
    apply(plugin = "signing")

    gradle.projectsEvaluated {
        tasks.withType<JavaCompile> {
            if (project.hasProperty("warn")) {
                options.compilerArgs = options.compilerArgs + "-Xlint:unchecked" + "-Xlint:deprecation"
            } else {
                options.compilerArgs = options.compilerArgs + "-Xlint:-unchecked" + "-Xlint:-deprecation"
            }
        }
    }

    val buildNumber: String? = System.getProperty("buildNumber")

    ext {
        set("buildNumber", buildNumber)
    }

    // Replaces the version defined in sources, usually x.y-SNAPSHOT, by a version identifying the build.
    if (project.version.toString().endsWith("-SNAPSHOT") && buildNumber != null) {
        val versionSuffix =
            if (project.version.toString().count { it == '.' } == 1) ".0.$buildNumber" else ".$buildNumber"
        project.version = project.version.toString().replace("-SNAPSHOT", versionSuffix)
    }

    val extraProperties = File(rootDir, "private/extraProperties.gradle")
    if (extraProperties.exists()) {
        apply(from = extraProperties)
    }

    repositories {
        mavenCentral()
    }
}

subprojects {
    val javadoc: Javadoc by tasks

    java.sourceCompatibility = JavaVersion.VERSION_11
    tasks.withType<JavaCompile> {
        options.encoding = "UTF-8"
        options.release.set(java.sourceCompatibility.majorVersion.toInt())
    }

    tasks.withType<KotlinCompile>().all {
        kotlinOptions.jvmTarget = java.sourceCompatibility.toString()
    }

    jacoco {
        toolVersion = "0.8.8"
    }

    tasks.jacocoTestReport {
        reports {
            xml.required.set(true)
            csv.required.set(false)
            html.required.set(false)
        }
    }

    // when subproject has Jacoco pLugin applied we want to generate XML report for coverage
    plugins.withType<JacocoPlugin> {
        tasks["test"].finalizedBy("jacocoTestReport")
    }

    configurations {
        // include compileOnly dependencies during test
        testImplementation {
            extendsFrom(configurations.compileOnly.get())
        }
    }

    if (!project.path.startsWith(":its") && !project.path.startsWith(":private:its")) {
        tasks.test {
            useJUnitPlatform()
        }
    }

    tasks.test {
        testLogging {
            exceptionFormat =
                org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL // log the full stack trace (default is the 1st line of the stack trace)
            events("skipped", "failed") // verbose log for failed and skipped tests (by default the name of the tests are not logged)
        }

        systemProperties = System.getProperties().filterKeys {
            it is String &&
                (it.startsWith("orchestrator") || it.startsWith("sonar") || it == "buildNumber" || it == "slangVersion")
        }.mapKeys { it.key as String }

        if (systemProperties.containsKey("buildNumber") && !systemProperties.containsKey("slangVersion")) {
            systemProperties["slangVersion"] = version
        }
    }

    val sourcesJar by tasks.creating(Jar::class) {
        dependsOn(JavaPlugin.CLASSES_TASK_NAME)
        classifier = "sources"
        from(sourceSets.main.get().allSource)
    }

    val javadocJar by tasks.creating(Jar::class) {
        dependsOn(javadoc)
        classifier = "javadoc"
        from(tasks["javadoc"])
    }

}

