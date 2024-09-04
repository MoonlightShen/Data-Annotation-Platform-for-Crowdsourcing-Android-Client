buildscript {
    repositories {
        google()
        mavenCentral()
        maven("https://jitpack.io")
        maven("https://maven.aliyun.com/repository/google")
        maven("https://maven.aliyun.com/repository/gradle-plugin")
        maven("https://maven.aliyun.com/repository/public")
        maven("https://maven.aliyun.com/repository/jcenter")
    }
    dependencies {
        //Gradle 插件及使用版本
        classpath("com.android.tools.build:gradle:8.0.2")
        classpath("org.greenrobot:greendao-gradle-plugin:3.3.1")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.0")
    }
}

plugins {
    id("com.android.application") version "8.1.2" apply false
}
