plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    id ("io.realm.kotlin")
}

android {
    namespace = "com.weather.app"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.weather.app"
        minSdk = 24
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
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)

    //ADDING DEPENDENCY for Volley - used in API call
    implementation(libs.android.volley)

    //ADDING DEPENDENCY for fasterxml - used in WeatherData
    implementation(libs.jackson.databind)

    //ADDING DEPENDENCY for GSON - used for parsing JSON response from api
    //noinspection UseTomlInstead
    implementation("com.google.code.gson:gson:2.10.1")

    //ADDING DEPENDENCY for Picasso - used to get the PNG Image and set it into ImageView
    //noinspection UseTomlInstead
    implementation("com.squareup.picasso:picasso:2.8")

    // Used for Location Permission
    implementation(libs.play.services.location)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    //noinspection UseTomlInstead
    implementation("io.realm.kotlin:library-base:1.11.0")
    //noinspection UseTomlInstead
    implementation("io.realm.kotlin:library-sync:1.11.0")
    //noinspection UseTomlInstead
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:1.5.1")
    //noinspection UseTomlInstead
    implementation("org.mongodb:bson-kotlinx:4.11.0")
}