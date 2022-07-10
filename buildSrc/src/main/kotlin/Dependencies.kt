object Modules {
    const val app = ":app"
    const val cache = ":cache"
    const val data = ":data"
    const val remote = ":remote"
    const val domain = ":domain"
    const val presentation = ":presentation"
    const val base = ":base"
}

object Libraries {
    const val hilt = "com.google.dagger:hilt-android:${Versions.hiltCore}"
    const val hiltLifecycle = "androidx.hilt:hilt-lifecycle-viewmodel:${Versions.hiltAndroidX}"
    const val hiltCore = "com.google.dagger:hilt-android:${Versions.hiltCore}"
    const val hiltKapt = "com.google.dagger:hilt-android-compiler:${Versions.hiltCore}"
    const val hiltAndroidx = "androidx.hilt:hilt-compiler:${Versions.hiltAndroidX}"
    const val hiltTest = "com.google.dagger:hilt-android-testing:${Versions.hiltCore}"

    const val gson = "com.google.code.gson:gson:${Versions.gson}"
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val retrofitGsonConverter =
        "com.squareup.retrofit2:converter-gson:${Versions.retrofitGson}"
    const val retrofitMoshiConverter =
        "com.squareup.retrofit2:converter-moshi:${Versions.retrofitGson}"
    const val httpLoggingInterceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.okHttp}"
    const val retrofitRxAdapter =
        "com.squareup.retrofit2:adapter-rxjava2:${Versions.rxjava2Adapter}"

    const val glide = "com.github.bumptech.glide:glide:${Versions.glide}"
    const val glideCompiler = "com.github.bumptech.glide:compiler:${Versions.glide}"

    const val coroutineAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.4.1"
    const val coroutineTest = "org.jetbrains.kotlinx:kotlinx-coroutines-test:1.4.1"

    const val lottie = "com.airbnb.android:lottie:${Versions.lottie}"
    const val photoView = "com.github.chrisbanes:PhotoView:${Versions.photoView}"
}

object AndroidX {
    const val core =
        "androidx.test:core:${Versions.androidxTest}"
    const val coreKtx =
        "androidx.test:core-ktx:${Versions.androidxTest}"
    const val runner =
        "androidx.test:runner:${Versions.androidxTest}"
    const val rules =
        "androidx.test:rules:${Versions.androidxTest}"
    const val espressoCore =
        "androidx.test.espresso:espresso-core:${Versions.espresso}"
    const val espressoContrib =
        "androidx.test.espresso:espresso-contrib:${Versions.espresso}"
    const val junit =
        "androidx.test.ext:junit:${Versions.androidxJunit}"
    const val coreTesting =
        "androidx.arch.core:core-testing:${Versions.androidxArchCoreTest}"
    const val pagingTesting =
        "androidx.paging:paging-common:${Versions.paging}"
    const val roomTest =
        "androidx.room:room-testing:${Versions.room}"
    const val navigationTest =
        "androidx.navigation:navigation-testing:${Versions.navigationTest}"
    const val fragmentTest =
        "androidx.fragment:fragment-testing:${Versions.fragmentTest}"

    const val robolectric = "org.robolectric:robolectric:4.5.1"
}

object Mockito {
    const val mockitoCore =
        "org.mockito:mockito-core:${Versions.mockito}"
    const val mockitoInline =
        "org.mockito:mockito-inline:${Versions.mockito}"
    const val mockitoKotlin =
        "com.nhaarman:mockito-kotlin:${Versions.mockitoKotlin}"

    const val mockWebServer = "com.squareup.okhttp3:mockwebserver:${Versions.mockWebserver}"
}

object AndroidLibraries {
    // ANDROID
    const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"

    const val appCompat = "androidx.appcompat:appcompat:${Versions.appCompat}"
    const val materialDesign = "com.google.android.material:material:${Versions.materialDesign}"
    const val coreKtx = "androidx.core:core-ktx:${Versions.coreKtx}"
    const val constraintLayout =
        "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"

    const val lifecycleViewModel =
        "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"
    const val lifecycleExtensions =
        "androidx.lifecycle:lifecycle-viewmodel-savedstate:${Versions.lifecycle}"
    const val lifecycleLiveData = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycle}"
    const val lifecycleRunTime = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycle}"

    const val viewPager = "androidx.viewpager2:viewpager2:${Versions.viewPager}"
    const val fragment = "androidx.fragment:fragment-ktx:${Versions.fragmentVersion}"
    const val googleCore = "com.google.android.play:core:${Versions.googleCore}"
    const val kotlinReflection = "org.jetbrains.kotlin:kotlin-reflect:${Versions.kotlin}"

    // recyclerview
    const val recyclerView = "androidx.recyclerview:recyclerview:${Versions.recyclerview}"
    const val recyclerViewTracker =
        "androidx.recyclerview:recyclerview-selection:${Versions.recyclerviewSelection}"
    const val paging = "androidx.paging:paging-runtime:${Versions.paging}"
    const val pagingRxjava = "androidx.paging:paging-rxjava2:${Versions.paging}"

    // navigation
    const val navigationRuntimeKtx =
        "androidx.navigation:navigation-runtime-ktx:${Versions.navigation}"
    const val navigationFragmentKtx =
        "androidx.navigation:navigation-fragment-ktx:${Versions.navigation}"
    const val navigationUiKtx = "androidx.navigation:navigation-ui-ktx:${Versions.navigation}"

    // Room
    const val roomKtx = "androidx.room:room-ktx:${Versions.room}"

    // RoomKapt
    const val roomKapt = "androidx.room:room-compiler:${Versions.room}"

    // Room api
    const val roomRuntime = "androidx.room:room-runtime:${Versions.room}"

    // legacy
    const val legacyCore = "androidx.legacy:legacy-support-core-utils:${Versions.legacy}"
    const val browser = "androidx.browser:browser:${Versions.browser}"
    const val legacySupport = "androidx.legacy:legacy-support-v4:${Versions.legacy}"
    const val customtabs = "com.android.support:customtabs:${Versions.coreKtx}"

    // camera
    const val cameraCore = "androidx.camera:camera-core:${Versions.camera}"
    const val camera2 = "androidx.camera:camera-camera2:${Versions.camera}"
    const val cameraLifeCycle = "androidx.camera:camera-lifecycle:${Versions.camera}"
    const val cameraView = "androidx.camera:camera-view:1.0.0-alpha10"

    // player
    const val exoPlayerCore = "com.google.android.exoplayer:exoplayer-core:${Versions.exoPlayer}"
    var exoPlayerUI = "com.google.android.exoplayer:exoplayer-ui:${Versions.exoPlayer}"
    var exoDash = "com.google.android.exoplayer:exoplayer-dash:${Versions.exoPlayer}"
    var exoMediasession =
        "com.google.android.exoplayer:extension-mediasession:${Versions.exoPlayer}"

    // Timber
    val timber = "com.jakewharton.timber:timber:${Versions.timber}"
}

object googleCloudeService {
    const val googleService =
        "com.google.android.gms:play-services-location:${Versions.googleLocationService}"
    const val googleBom = "com.google.firebase:firebase-bom:${Versions.firebaseBom}"
    const val analytics = "com.google.firebase:firebase-analytics"
    const val database = "com.google.firebase:firebase-database-ktx"

}

object LibraryList {
    val firebaseLibrary = arrayOf<String>(
        googleCloudeService.googleService,
        googleCloudeService.analytics,
        googleCloudeService.database
    )

    val appLibraries = arrayOf<String>(
        AndroidLibraries.kotlin,
        AndroidLibraries.appCompat,
        AndroidLibraries.materialDesign,
        AndroidLibraries.coreKtx,
        AndroidLibraries.constraintLayout,
        AndroidLibraries.lifecycleViewModel,
        AndroidLibraries.lifecycleExtensions,
        AndroidLibraries.lifecycleLiveData,
        AndroidLibraries.lifecycleRunTime,
        AndroidLibraries.viewPager,
        AndroidLibraries.fragment,
        AndroidLibraries.googleCore,
        AndroidLibraries.viewPager
    )

    val exoLibrary = arrayOf<String>(
        AndroidLibraries.exoPlayerCore,
        AndroidLibraries.exoPlayerUI,
        AndroidLibraries.exoMediasession,
        AndroidLibraries.exoDash
    )

    val cameraLibrary = arrayOf<String>(
        AndroidLibraries.cameraCore,
        AndroidLibraries.camera2,
        AndroidLibraries.cameraLifeCycle,
        AndroidLibraries.cameraView
    )

    val RecyclerViewLibraries = arrayOf<String>(
        AndroidLibraries.recyclerView,
        AndroidLibraries.recyclerViewTracker,
        AndroidLibraries.paging,
        AndroidLibraries.pagingRxjava
    )

    val NavigationLibraries = arrayOf(
        AndroidLibraries.navigationRuntimeKtx,
        AndroidLibraries.navigationFragmentKtx,
        AndroidLibraries.navigationUiKtx
    )

    val HiltLibraries = arrayOf(
        Libraries.hilt,
        Libraries.hiltCore,
        Libraries.hiltLifecycle
    )

    val HiltLibraryKapt = arrayOf(
        Libraries.hiltKapt,
        Libraries.hiltAndroidx
    )

    val RetrofitLibraries = arrayOf<String>(
        Libraries.gson,
        Libraries.retrofit,
        Libraries.retrofitGsonConverter,
        Libraries.retrofitMoshiConverter,
        Libraries.httpLoggingInterceptor,
        Libraries.retrofitRxAdapter
    )


    val Glide = arrayOf(
        Libraries.glide,
        Libraries.glideCompiler,
        Libraries.photoView
    )

    val AndroidTestLibrary = arrayOf(
        AndroidX.core,
        AndroidX.coreKtx,
        AndroidX.runner,
        AndroidX.rules,
        AndroidX.espressoCore,
        AndroidX.espressoContrib,
        AndroidX.junit,
        AndroidX.coreTesting,
        AndroidX.pagingTesting,
        AndroidX.roomTest,
//        AndroidX.robolectric,
        AndroidX.navigationTest,
        Libraries.hiltTest
    )

    val mockitoLibrary = arrayOf<String>(
        Mockito.mockitoCore,
        Mockito.mockitoInline,
        Mockito.mockitoKotlin,
        Mockito.mockWebServer
    )
}