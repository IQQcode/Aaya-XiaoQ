//引用插件
plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("android.extensions")
}

android {
    compileSdkVersion(AppConfig.compileSdkVersion)
    buildToolsVersion(AppConfig.buildToolsVersion)
    defaultConfig {
        minSdkVersion(AppConfig.minSdkVersion)
        targetSdkVersion(AppConfig.targetSdkVersion)
        versionCode = AppConfig.versionCode
        versionName = AppConfig.versionName
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    //依赖操作
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    api(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    //Kotlin基础库
    api(DependenciesConfig.STD_LIB)
    //Android标准库
    api(DependenciesConfig.APP_COMPAT)
    //Kotlin核心库
    api(DependenciesConfig.KTX_CORE)
    //EventBus
    api(DependenciesConfig.EVENT_BUS)
    //ARouter
    api(DependenciesConfig.AROUTER)
    //RecyclerView
    api(DependenciesConfig.RECYCLERVIEW)
    //AndPermissions
    api(DependenciesConfig.AND_PERMISSIONS)
    //ViewPager
    api(DependenciesConfig.VIEWPAGER)
    api(DependenciesConfig.MATERIAL)
    //Lottie
    api(DependenciesConfig.LOTTIE)
    //刷新
    api(DependenciesConfig.REFRESH_KERNEL)
    api(DependenciesConfig.REFRESH_HEADER)
    api(DependenciesConfig.REFRESH_FOOT)
    //图表
    api(DependenciesConfig.CHART)

    // GradientLayout
    api(DependenciesConfig.GRADIENT_LAYOUT)

    // ConstraintLayout
    api(DependenciesConfig.CONSTRAINT_LAYOUT)

    // ArcSeekBar(圆弧拖动条)
    api(DependenciesConfig.ARC_SEEKBAR)


    // 引入百度地图SDK
    api(files("libs/BaiduLBS_Android.jar"))
    api(files("libs/IndoorscapeAlbumPlugin.jar"))
    
    api(project(":lib_voice"))
    api(project(":lib_network"))
}
