plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-parcelize")
    id("kotlin-kapt")
    id("com.google.gms.google-services")
    id("dagger.hilt.android.plugin")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    compileSdk = ConfigData.compileSdkVersion

    defaultConfig {
        applicationId = ConfigData.packageName
        minSdk = ConfigData.minSdkVersion
        targetSdk = ConfigData.targetSdk
        versionCode = ConfigData.versionCode
        versionName = ConfigData.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
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

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions {
            jvmTarget = "1.8"
        }
    }

    buildFeatures {
        viewBinding = true
        dataBinding = true
    }
}

dependencies {
    implementation(Deps.coreKtx)
    implementation(Deps.appCompat)
    implementation(Deps.materialDesign)
    implementation(Deps.constraintsLayout)
    implementation(Deps.timber)

    // Firebase
    implementation(platform(Deps.firebase))
    implementation(Deps.firebaseAuth)
    implementation(Deps.firebaseFirestore)
    implementation(Deps.firebaseAuthUi)
    implementation(Deps.firebaseAnalytics)

    // Maps
    implementation(Deps.googleMaps)
    implementation(Deps.location)

    // Hilt
    implementation(Deps.hilt)
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    kapt(Deps.hiltCompiler)
    kapt(Deps.hiltAndroidCompiler)
    implementation(Deps.hiltLifecycle)
    implementation(Deps.hiltNavigation)

    // Retrofit
    implementation(Deps.retrofit)
    implementation(Deps.retrofitGsonConverter)

    // Okhttp
    implementation(Deps.okHttpLoggingInterceptor)

    // Coroutines
    implementation(Deps.coroutinesCore)
    implementation(Deps.coroutinesAndroid)
    implementation(Deps.coroutinesPlayService)

    // Lifecycle
    implementation(Deps.lifecycleRuntimeKtx)
    implementation(Deps.lifecycleViewModelKtx)
    implementation(Deps.lifecycleLivedataKtx)
    implementation(Deps.lifecycleExtensions)

    // GSON
    implementation(Deps.gson)

    // Navigation Component
    implementation(Deps.navigationUiKtx)
    implementation(Deps.navigationFragmentKtx)

    // Glide
    implementation(Deps.glide)
    annotationProcessor(Deps.glideCompiler)

    // Preference
    implementation(Deps.preferenceDataStore)

    // Chucked
    debugImplementation(Deps.chuckEr)

    // Lottie
    implementation(Deps.lottie)

    // Shimmer
    implementation(Deps.shimmer)

    // Testing
    testImplementation(Deps.jUnit)
    androidTestImplementation(Deps.jUnitExt)
    androidTestImplementation(Deps.espressoCore)
    implementation(Deps.googleTruth)
    testImplementation(Deps.mockito)
    implementation("androidx.test:runner:1.4.0")
}