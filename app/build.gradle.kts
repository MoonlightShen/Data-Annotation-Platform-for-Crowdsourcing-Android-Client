import com.android.build.api.dsl.Packaging

plugins {
    id("com.android.application")
    id("org.greenrobot.greendao")
    id("kotlin-android")
}

greendao {
    schemaVersion =1 //数据库版本号
    daoPackage = "com.echo.dapc.database"
    generateTests =false //设置为true以自动生成单元测试。
}

android {
    namespace = "com.echo.dapc"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.echo.dapc"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    dataBinding{
        enable = true
    }

    buildFeatures {
        viewBinding = true
    }

}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    implementation(platform("org.jetbrains.kotlin:kotlin-bom:1.8.0"))

    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.recyclerview:recyclerview:1.3.1")
    implementation("androidx.viewpager2:viewpager2:1.0.0")

    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.9.0")

    //数据库框架 GreenDAO
    implementation("org.greenrobot:greendao:3.3.0")
    implementation("org.greenrobot:greendao-generator:3.3.0")

    //序列化工具 Gson
    implementation("com.google.code.gson:gson:2.10.1")

    //网络请求框架
    implementation("com.squareup.okhttp3:okhttp:4.11.0")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")

    //图片加载框架
    implementation("com.github.bumptech.glide:glide:4.16.0")

    //权限请求框架
    implementation("com.github.getActivity:XXPermissions:18.62")

    //字体工具
    implementation("io.github.inflationx:calligraphy3:3.1.1")

    //多线程框架
//    implementation("com.squareup.retrofit2:retrofit:2.11.0")
//    implementation("com.squareup.retrofit2:converter-gson:2.11.0")
//    implementation("io.reactivex.rxjava3:rxandroid:3.0.0")

    //缓存框架
//    implementation("com.tencent:mmkv:1.3.4")

    implementation(fileTree("libs"))

    //
    implementation("com.github.javakam:file.core:3.9.8@aar")
    implementation("com.github.javakam:file.selector:3.9.8@aar")

    //编码检测
    implementation("com.github.albfernandez:juniversalchardet:2.4.0")

    /////////////////////////UI
    //Material风格输入框
    implementation("com.rengwuxian.materialedittext:library:2.1.4")

    //通用标题栏
    implementation("com.github.getActivity:TitleBar:10.5")

    //智能刷新
    implementation("io.github.scwang90:refresh-layout-kernel:2.1.0")
    implementation("io.github.scwang90:refresh-header-classics:2.1.0")
    implementation("io.github.scwang90:refresh-footer-classics:2.1.0")

    //圆角ImageVIew
//    implementation("com.github.xuxuliooo:RoundImageView:1.1.2")

    //底部导航栏
    implementation("com.github.ibrahimsn98:SmoothBottomBar:1.7.9")

    //对话框
    implementation("com.afollestad.material-dialogs:core:3.3.0")
    implementation("com.afollestad.material-dialogs:input:3.3.0")
    implementation("com.afollestad.material-dialogs:datetime:3.3.0")
    implementation("com.afollestad.material-dialogs:bottomsheets:3.3.0")

    //流式布局
    implementation("com.google.android.flexbox:flexbox:3.0.0")

    //加载布局
    implementation("com.billy.android:gloading:1.0.0")

    //搜索框
    implementation("com.github.arimorty:floatingsearchview:2.1.1")

    //多状态布局
    implementation("com.classic.common:multiple-status-view:1.7")

    //单项/数字、二三级联动、日期/时间等滚轮选择器
    implementation("com.github.gzu-liyujiang.AndroidPicker:WheelPicker:4.1.13")

    //图片选择
    implementation("io.github.lucksiege:pictureselector:v3.11.2")
    implementation("io.github.lucksiege:compress:v3.11.2")  //压缩
    implementation("io.github.lucksiege:ucrop:v3.11.2") //裁剪

    //进度条
    implementation("com.github.FPhoenixCorneaE:SmartProgressBar:1.0.3")

}

repositories {
    google()
    mavenCentral()
    maven("https://jitpack.io")
    maven("https://maven.aliyun.com/repository/google")
    maven("https://maven.aliyun.com/repository/gradle-plugin")
    maven("https://maven.aliyun.com/repository/public")
    maven("https://maven.aliyun.com/repository/jcenter")
}
