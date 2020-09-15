object AppConfiguration {

    object Plugins {
        const val androidApplication = "com.android.application"
        const val kotlinAndroid = "android"
        const val kotlinAndroidExtensions = "android.extensions"
        const val kapt = "kapt"
    }

    const val compileSdkVersion = 29
    const val applicationId = "com.picpay.desafio.android"
    const val minSdkVersion = 21
    const val targetSdkVersion = 29
    const val versionCode = 1
    const val versionName = "1.0"
    const val testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

    sealed class BuildType(
        val name: String,
        val isMinifyEnabled: Boolean = false,
        val defaultProguardFile: String = "",
        val defaultProguardRules: String = ""
    ) {
        object Release : BuildType(
            name = "release",
            isMinifyEnabled = true,
            defaultProguardFile = "proguard-android-optimize.txt",
            defaultProguardRules = "proguard-rules.pro"
        )
    }

    object Task {
        const val preBuild = "preBuild"
        const val ktlintCheck = "ktlintCheck"
    }
}