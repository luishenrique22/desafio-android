plugins {
    id(AppConfiguration.Plugins.androidApplication)
    kotlin(AppConfiguration.Plugins.kotlinAndroid)
    kotlin(AppConfiguration.Plugins.kotlinAndroidExtensions)
    kotlin(AppConfiguration.Plugins.kapt)
    id(AppConfiguration.Plugins.kotlinKapt)
}

android {

    compileSdkVersion(AppConfiguration.compileSdkVersion)
    defaultConfig {
        applicationId = AppConfiguration.applicationId
        minSdkVersion(AppConfiguration.minSdkVersion)
        targetSdkVersion(AppConfiguration.targetSdkVersion)
        versionCode = AppConfiguration.versionCode
        versionName = AppConfiguration.versionName

        vectorDrawables.useSupportLibrary = true

        testInstrumentationRunner = AppConfiguration.testInstrumentationRunner
    }
    buildTypes {

        getByName(AppConfiguration.BuildType.Release.name) {
            isMinifyEnabled = AppConfiguration.BuildType.Release.isMinifyEnabled
            proguardFiles(
                getDefaultProguardFile(AppConfiguration.BuildType.Release.defaultProguardFile),
                AppConfiguration.BuildType.Release.defaultProguardRules
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions.apply {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }

    getTasksByName(AppConfiguration.Task.preBuild, true).firstOrNull()
        ?.dependsOn(getTasksByName(AppConfiguration.Task.ktlintCheck, true))

    testOptions {
        unitTests.isReturnDefaultValues = true
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
        implementation("androidx.room:room-runtime:2.2.5")
        implementation("androidx.room:room-compiler:2.2.5")
        kapt("androidx.room:room-ktx:2.2.5")
        testImplementation("io.mockk:mockk:1.10.0")

        testImplementation(kotlinx_coroutines_test)
        testImplementation(junit)
        testImplementation(mockito_core)
        testImplementation(mockito_kotlin)
        testImplementation(koin_test)
        testImplementation(core_testing)
        testImplementation(espresso_core)

        androidTestImplementation(core_testing)
        androidTestImplementation(androidx_test_runner)
        androidTestImplementation(espresso_core)
        androidTestImplementation(androidx_test_core_ktx)
    }
}
