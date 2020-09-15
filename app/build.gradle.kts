plugins {
    id(AppConfiguration.Plugins.androidApplication)
    kotlin(AppConfiguration.Plugins.kotlinAndroid)
    kotlin(AppConfiguration.Plugins.kotlinAndroidExtensions)
    kotlin(AppConfiguration.Plugins.kapt)
}

android {

    compileSdkVersion(AppConfiguration.compileSdkVersion)
    defaultConfig {
        applicationId = AppConfiguration.applicationId
        minSdkVersion(AppConfiguration.minSdkVersion)
        targetSdkVersion(AppConfiguration.targetSdkVersion)
        versionCode= AppConfiguration.versionCode
        versionName = AppConfiguration.versionName

        vectorDrawables.useSupportLibrary = true

        testInstrumentationRunner = AppConfiguration.testInstrumentationRunner
    }
    buildTypes {

        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions.apply{
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
}

dependencies {
    Libs.run {
        implementation(kotlin_stdlib_jdk7)
        implementation(androidx_core_core_ktx)
        implementation(appcompat)
        implementation(constraintlayout)
        implementation(material)
        implementation(koin_android)
        implementation(koin_core)
        implementation(koin_androidx_viewmodel)
        implementation(dagger)
        kapt(dagger_compiler)
        implementation(lifecycle_livedata_ktx)
        implementation(lifecycle_runtime_ktx)
        implementation(lifecycle_viewmodel_ktx)
        implementation(kotlinx_coroutines_core)
        implementation(kotlinx_coroutines_android)
        implementation(rxjava)
        implementation(rxandroid)
        implementation(adapter_rxjava2)
        implementation(gson)
        implementation(retrofit)
        implementation(converter_gson)
        implementation(okhttp)
        implementation(mockwebserver)
        implementation(picasso)
        implementation(circleimageview)

        testImplementation(kotlinx_coroutines_test)
        testImplementation(junit)
        testImplementation(mockito_core)
        testImplementation(mockito_kotlin)
        testImplementation(core_testing)
        testImplementation(koin_test)

        androidTestImplementation(androidx_test_runner)
        androidTestImplementation(espresso_core)
        androidTestImplementation(androidx_test_core_ktx)
    }

}
