plugins {
    alias(libs.plugins.androidApplication)

    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.perfumeapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.perfumeapp"
        minSdk = 28
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
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(platform("com.google.firebase:firebase-bom:33.0.0"))
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-database")
    implementation ("com.github.denzcoskun:ImageSlideshow:0.1.2")
    implementation("com.tbuonomo:dotsindicator:5.0")
    implementation ("com.google.code.gson:gson:2.11.0")

    dependencies {
        implementation ("com.razorpay:checkout:1.6.38")
    }

    implementation ("com.github.bumptech.glide:glide:4.11.0")
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)
    annotationProcessor ("com.github.bumptech.glide:compiler:4.11.0")

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.car.ui.lib)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}