plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.kotlinKapt)
    alias(libs.plugins.hilt)
    alias(libs.plugins.navigation.safeargs)
}

android {
    namespace = "com.example.dai_android_grupo_4"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.dai_android_grupo_4"
        minSdk = 34
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
    kotlinOptions {
        jvmTarget = "17"
    }
    
    // Habilitar Navigation Safe Args
    buildFeatures {
        dataBinding = true
        viewBinding = true
    }
    
    // Configuración para mejorar la estabilidad de Kapt
    kapt {
        correctErrorTypes = true
        useBuildCache = true
        javacOptions {
            option("-source", "17")
            option("-target", "17")
            option("-Xmaxerrs", 500)
            option("--add-opens=jdk.compiler/com.sun.tools.javac.api=ALL-UNNAMED")
            option("--add-opens=jdk.compiler/com.sun.tools.javac.code=ALL-UNNAMED")
            option("--add-opens=jdk.compiler/com.sun.tools.javac.comp=ALL-UNNAMED")
            option("--add-opens=jdk.compiler/com.sun.tools.javac.file=ALL-UNNAMED")
            option("--add-opens=jdk.compiler/com.sun.tools.javac.main=ALL-UNNAMED")
            option("--add-opens=jdk.compiler/com.sun.tools.javac.model=ALL-UNNAMED")
            option("--add-opens=jdk.compiler/com.sun.tools.javac.parser=ALL-UNNAMED")
            option("--add-opens=jdk.compiler/com.sun.tools.javac.processing=ALL-UNNAMED")
            option("--add-opens=jdk.compiler/com.sun.tools.javac.tree=ALL-UNNAMED")
            option("--add-opens=jdk.compiler/com.sun.tools.javac.util=ALL-UNNAMED")
        }
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    
    // Hilt
    implementation(libs.hilt.android)
    implementation(libs.recyclerview)
    kapt(libs.hilt.compiler)
    
    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.retrofit.gson)
    implementation(libs.gson)
    implementation(libs.okhttp.logging)

    // Navigation Component
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)

    // Security Crypto para EncryptedSharedPreferences
    implementation(libs.security.crypto)

    // Biometric
    implementation(libs.biometric)
    
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}