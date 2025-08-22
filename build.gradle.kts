import org.gradle.api.publish.maven.MavenPublication
import org.gradle.kotlin.dsl.create

plugins {
    id("java")
    id("maven-publish")
}

group = project.group
version = project.version

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    compileOnly("dev.folia:folia-api:1.20.4-R0.1-SNAPSHOT")
    compileOnly("com.j256.ormlite:ormlite-core:6.1")
}

tasks {
    java {
        toolchain.languageVersion.set(JavaLanguageVersion.of(17))
        withSourcesJar()
        withJavadocJar()
    }

    compileJava {
        options.encoding = "UTF-8"
        options.release.set(17)
    }

    javadoc {
        (options as StandardJavadocDocletOptions).apply {
            encoding = Charsets.UTF_8.name()
            use()
            tags("apiNote:a:API Note:")
        }
    }

    sourceSets {
        main {
            java.setSrcDirs(listOf("src"))
            resources.setSrcDirs(emptyList<String>())
        }
        test {
            java.setSrcDirs(emptyList<String>())
            resources.setSrcDirs(emptyList<String>())
        }
    }
}

publishing {
    repositories {
        maven {
            url = uri("https://repo.bxteam.org/releases/")

            if (project.version.toString().endsWith("-SNAPSHOT")) {
                url = uri("https://repo.bxteam.org/snapshots/")
            }

            credentials.username = System.getenv("REPO_USERNAME")
            credentials.password = System.getenv("REPO_PASSWORD")
        }
    }
    publications {
        create<MavenPublication>("maven") {
            from(components.getByName("java"))
        }
    }
}
