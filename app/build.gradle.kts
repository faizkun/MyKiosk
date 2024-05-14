plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id ("com.google.devtools.ksp") version "1.9.0-1.0.13"
    id("com.google.dagger.hilt.android")
    kotlin("kapt")
    id("org.jetbrains.kotlin.plugin.serialization") version "2.0.0-Beta5"
}

android {
    namespace = "com.faizdev.mykiosk"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.faizdev.mykiosk"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation(platform("androidx.compose:compose-bom:2023.08.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.08.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    //Destination
    implementation("io.github.raamcosta.compose-destinations:core:1.9.54")
    ksp("io.github.raamcosta.compose-destinations:ksp:1.9.54")


    //Dagger-Hilt Injection
    implementation("com.google.dagger:hilt-android:2.49")
    ksp("com.google.dagger:hilt-android-compiler:2.49")
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")

    //Room Database
    val room_version = "2.5.0"
    implementation("androidx.room:room-runtime:$room_version")
    ksp("androidx.room:room-compiler:$room_version")
    implementation("androidx.room:room-ktx:$room_version")

    //Material Icons
    implementation("androidx.compose.material:material-icons-extended-android:1.6.4")

    //Supabase
    implementation(platform("io.github.jan-tennert.supabase:bom:2.4.1"))
    //SQL
    implementation("io.github.jan-tennert.supabase:postgrest-kt")
    //RDB
    implementation("io.github.jan-tennert.supabase:realtime-kt")
    //AUTH
    implementation("io.github.jan-tennert.supabase:gotrue-kt")

    //KotPref
    implementation ("com.chibatching.kotpref:kotpref:2.13.1")
    implementation ("com.chibatching.kotpref:initializer:2.13.1")

    //KTOR Client
    implementation("io.ktor:ktor-client-okhttp:2.3.9")

    //CollectAsState with Lifecycle
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.7.0")

    // Api Wrapper
    implementation("com.github.rmaprojects:apiresponsewrapper:1.4")
}

kapt{
    correctErrorTypes; true
}