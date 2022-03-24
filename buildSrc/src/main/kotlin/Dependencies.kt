object BuildPlugins {
    val android by lazy { "com.android.tools.build:gradle:${Versions.gradlePlugin}" }
    val kotlin by lazy { "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}" }
    val googleService by lazy { "com.google.gms:google-services:${Versions.googleService}" }
    val safeArgs by lazy { "androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.safeArgs}" }
    val hilt by lazy { "com.google.dagger:hilt-android-gradle-plugin:${Versions.hiltVersion}" }
}

object Deps {
    val coreKtx by lazy { "androidx.core:core-ktx:${Versions.coreKtx}" }
    val appCompat by lazy { "androidx.appcompat:appcompat:${Versions.appCompat}" }
    val timber by lazy { "com.jakewharton.timber:timber:${Versions.timber}" }
    val materialDesign by lazy { "com.google.android.material:material:${Versions.material}" }
    val constraintsLayout by lazy { "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}" }

    val hilt by lazy { "com.google.dagger:hilt-android:${Versions.hiltVersion}" }
    val hiltAndroidCompiler by lazy { "com.google.dagger:hilt-android-compiler:${Versions.hiltVersion}" }
    val hiltCompiler by lazy { "androidx.hilt:hilt-compiler:${Versions.hiltCompilerVersion}" }
    val hiltNavigation by lazy { "androidx.hilt:hilt-navigation-fragment:${Versions.hiltCompilerVersion}" }
    val hiltLifecycle by lazy { "androidx.hilt:hilt-lifecycle-viewmodel:${Versions.hiltLifecycleVersion}" }

    val retrofit by lazy { "com.squareup.retrofit2:retrofit:${Versions.retrofit}" }
    val retrofitGsonConverter by lazy { "com.squareup.retrofit2:converter-gson:${Versions.retrofit}" }

    val okHttpLoggingInterceptor by lazy { "com.squareup.okhttp3:logging-interceptor:${Versions.okhttp}" }

    val coroutinesCore by lazy { "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}" }
    val coroutinesAndroid by lazy { "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}" }
    val coroutinesPlayService by lazy { "org.jetbrains.kotlinx:kotlinx-coroutines-play-services:${Versions.coroutines}" }

    val lifecycleRuntimeKtx by lazy { "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycle}" }
    val lifecycleViewModelKtx by lazy { "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}" }
    val lifecycleLivedataKtx by lazy { "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycle}" }
    val lifecycleExtensions by lazy { "androidx.lifecycle:lifecycle-extensions:${Versions.lifecycleExt}" }

    val gson by lazy { "com.google.code.gson:gson:${Versions.gson}" }

    val glide by lazy { "com.github.bumptech.glide:glide:${Versions.glide}" }
    val glideCompiler by lazy { "com.github.bumptech.glide:compiler:${Versions.glide}" }

    val navigationFragmentKtx by lazy { "androidx.navigation:navigation-fragment-ktx:${Versions.navigation}" }
    val navigationUiKtx by lazy { "androidx.navigation:navigation-ui-ktx:${Versions.navigation}" }

    val firebase by lazy { "com.google.firebase:firebase-bom:${Versions.firebase}" }
    val firebaseAuth by lazy { "com.google.firebase:firebase-auth-ktx" }
    val firebaseFirestore by lazy { "com.google.firebase:firebase-firestore-ktx" }
    val firebaseAnalytics by lazy { "com.google.firebase:firebase-analytics-ktx" }
    val firebaseAuthUi by lazy { "com.firebaseui:firebase-ui-auth:${Versions.firebaseAuthUi}" }

    val googleMaps by lazy { "com.google.android.gms:play-services-maps:${Versions.maps}" }
    val location by lazy { "com.google.android.gms:play-services-location:${Versions.location}" }

    val preferenceDataStore by lazy { "androidx.datastore:datastore-preferences:${Versions.preferences}" }

    val chuckEr by lazy { "com.github.chuckerteam.chucker:library:${Versions.chuckEr}" }

    val lottie by lazy { "com.airbnb.android:lottie:${Versions.lottieVersion}" }

    val shimmer by lazy { "com.facebook.shimmer:shimmer:${Versions.shimmer}" }

    val jUnit by lazy { "junit:junit:${Versions.jUnitVersion}" }
    val jUnitExt by lazy { "androidx.test.ext:junit:${Versions.jUnitExtVersion}" }
    val espressoCore by lazy { "androidx.test.espresso:espresso-core:${Versions.espresso}" }
    val googleTruth by lazy { "com.google.truth:truth:${Versions.googleTruth}" }
    val mockito by lazy { "org.mockito:mockito-core:${Versions.mockito}" }
}