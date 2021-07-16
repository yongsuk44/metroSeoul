import org.gradle.api.artifacts.dsl.DependencyHandler

object Modules {
    const val app = ":app"
    const val data = ":data"
    const val dataremote = ":data-remote"
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

    const val gson = "com.google.code.gson:gson:${Versions.gson}"
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val retrofitGsonConverter = "com.squareup.retrofit2:converter-gson:${Versions.retrofitGson}"
    const val retrofitMoshiConverter = "com.squareup.retrofit2:converter-moshi:${Versions.retrofitGson}"
    const val httpLoggingInterceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.okHttp}"
    const val retrofitRxAdapter = "com.squareup.retrofit2:adapter-rxjava2:${Versions.rxjava2Adapter}"

    const val glide = "com.github.bumptech.glide:glide:${Versions.glide}"
    const val glideCompiler = "com.github.bumptech.glide:compiler:${Versions.glide}"

    const val coroutineAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.4.1"
    const val coroutineTest = "org.jetbrains.kotlinx:kotlinx-coroutines-test:1.4.1"
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
}

object JUnit {
    const val junit =
        "junit:junit:${Versions.junit}"
    const val junitPlatformRunner =
        "org.junit.platform:junit-platform-runner:${Versions.junitPlatformRunner}"
}

object AndroidLibraries {
    // ANDROID
    const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"

    const val appCompat = "androidx.appcompat:appcompat:${Versions.appCompat}"
    const val materialDesign = "com.google.android.material:material:${Versions.materialDesign}"
    const val coreKtx = "androidx.core:core-ktx:${Versions.coreKtx}"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"

    const val lifecycleViewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"
    const val lifecycleExtensions = "androidx.lifecycle:lifecycle-viewmodel-savedstate:${Versions.lifecycle}"
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
    const val pagingJetPack = "androidx.paging:paging-compose:${Versions.pagingJetpack}"

    // navigation
    const val navigationRuntimeKtx = "androidx.navigation:navigation-runtime-ktx:${Versions.navigation}"
    const val navigationFragmentKtx = "androidx.navigation:navigation-fragment-ktx:${Versions.navigation}"
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
}

object LibraryList {
    val appLibraries = arrayListOf<String>().apply {
        add(AndroidLibraries.kotlin)
        add(AndroidLibraries.appCompat)
        add(AndroidLibraries.materialDesign)
        add(AndroidLibraries.coreKtx)
        add(AndroidLibraries.constraintLayout)
        add(AndroidLibraries.lifecycleViewModel)
        add(AndroidLibraries.lifecycleExtensions)
        add(AndroidLibraries.lifecycleLiveData)
        add(AndroidLibraries.lifecycleRunTime)
        add(AndroidLibraries.viewPager)
        add(AndroidLibraries.fragment)
        add(AndroidLibraries.googleCore)
        add(AndroidLibraries.viewPager)
    }

    val exoLibrary = arrayListOf<String>().apply {
        add(AndroidLibraries.exoPlayerCore)
        add(AndroidLibraries.exoPlayerUI)
        add(AndroidLibraries.exoMediasession)
        add(AndroidLibraries.exoDash)
    }

    val cameraLibrary = arrayListOf<String>().apply {
        add(AndroidLibraries.cameraCore)
        add(AndroidLibraries.camera2)
        add(AndroidLibraries.cameraLifeCycle)
        add(AndroidLibraries.cameraView)
    }

    val RecyclerViewLibraries = arrayListOf<String>().apply {
        add(AndroidLibraries.recyclerView)
        add(AndroidLibraries.recyclerViewTracker)
        add(AndroidLibraries.paging)
        add(AndroidLibraries.pagingJetPack)
        add(AndroidLibraries.pagingRxjava)
    }

    val NavigationLibraries = arrayListOf<String>().apply {
        add(AndroidLibraries.navigationRuntimeKtx)
        add(AndroidLibraries.navigationFragmentKtx)
        add(AndroidLibraries.navigationUiKtx)
    }

    val HiltLibraries = arrayListOf<String>().apply {
        add(Libraries.hilt)
        add(Libraries.hiltCore)
        add(Libraries.hiltLifecycle)
    }

    val HiltLibraryKapt = arrayListOf<String>().apply {
        add(Libraries.hiltKapt)
        add(Libraries.hiltAndroidx)
    }

    val RetrofitLibraries = arrayListOf<String>().apply {
        add(Libraries.gson)
        add(Libraries.retrofit)
        add(Libraries.retrofitGsonConverter)
        add(Libraries.retrofitMoshiConverter)
        add(Libraries.httpLoggingInterceptor)
        add(Libraries.retrofitRxAdapter)
    }

    val Glide = arrayListOf<String>().apply {
        add(Libraries.glide)
        add(Libraries.glideCompiler)
    }

    val TestLibrary = arrayListOf<String>().apply {
        add(JUnit.junit)
        add(JUnit.junitPlatformRunner)
        add(AndroidX.coreTesting)
        add(AndroidX.pagingTesting)
        add(AndroidX.roomTest)
    }

    val AndroidTestLibrary = arrayListOf<String>().apply {
        add(AndroidX.core)
        add(AndroidX.coreKtx)
        add(AndroidX.runner)
        add(AndroidX.rules)
        add(AndroidX.espressoCore)
        add(AndroidX.espressoContrib)
        add(AndroidX.junit)
    }
}

fun DependencyHandler.kaptList(list: List<String>) {
    list.forEach { dependency ->
        add("kapt", dependency)
    }
}


fun DependencyHandler.implementationList(list: List<String>) {
    list.forEach { dependency ->
        add("implementation", dependency)
    }
}

fun DependencyHandler.androidTestImplementationList(list: List<String>) {
    list.forEach { dependency ->
        add("androidTestImplementation", dependency)
    }
}

fun DependencyHandler.testImplementationList(list: List<String>) {
    list.forEach { dependency ->
        add("testImplementation", dependency)
    }
}