buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(BuildPlugins.android)
        classpath(BuildPlugins.kotlin)
        classpath(BuildPlugins.googleService)
        classpath(BuildPlugins.hilt)
        classpath(BuildPlugins.safeArgs)
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.10")
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}