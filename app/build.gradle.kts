plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("androidx.navigation.safeargs.kotlin")
    id("com.google.devtools.ksp")
    id("kotlin-parcelize")
    id("kotlin-android")
    id("com.google.gms.google-services")
}

android {
    namespace = "app.id.technicaltest"
    compileSdk = 34

    defaultConfig {
        applicationId = "app.id.technicaltest"
        minSdk = 25
        targetSdk = 34
        versionCode = 1
        versionName = "1.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables.useSupportLibrary = true
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
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.10.1")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    implementation("androidx.activity:activity-ktx:1.7.2") //Android KTX
    implementation("androidx.fragment:fragment-ktx:1.6.1")
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.1") //Navigation UI
    implementation("androidx.navigation:navigation-ui-ktx:2.7.1")
    implementation("androidx.datastore:datastore-preferences-core:1.0.0") //datastore

    api("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.2") // Kotlin Coroutines Components
    api("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.2")

    implementation("io.insert-koin:koin-core:3.4.3") //Koin DI
    implementation("io.insert-koin:koin-android:3.4.3")
    implementation("io.insert-koin:koin-androidx-navigation:3.4.3")

    implementation("com.google.firebase:firebase-auth-ktx:22.1.1")

    implementation ("androidx.work:work-runtime-ktx:2.8.1")
}
