plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.kotlinKapt)
    alias(libs.plugins.hilt)
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    
    // Configuración para mejorar la estabilidad de Kapt
    kapt {
        correctErrorTypes = true
        useBuildCache = true
        javacOptions {
            option("-source", "8")
            option("-target", "8")
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
    kapt(libs.hilt.compiler)
    
    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.retrofit.gson)
    implementation(libs.gson)
    implementation(libs.okhttp.logging)
    
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}