plugins {
    id("com.android.application")
}

android {
    namespace = "io.github.emotionbug.openheadunitshortcut"
    compileSdk = 36

    defaultConfig {
        applicationId = "io.github.emotionbug.openheadunitshortcut"
        // Match Open Headunit's GitHub distribution, including older head units.
        minSdk = 16
        targetSdk = 36
        versionCode = 1
        versionName = "1.0.0"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    lint {
        abortOnError = true
        warningsAsErrors = true
        // Keep the build toolchain aligned with Open Headunit; upgrades are deliberate.
        disable += "AndroidGradlePluginVersion"
    }
}
