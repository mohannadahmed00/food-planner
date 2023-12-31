plugins {
    id("com.android.application")
    id("androidx.navigation.safeargs")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.giraffe.foodplannerapplication"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.giraffe.foodplannerapplication"
        minSdk = 24
        targetSdk = 33
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
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    //firebase
    implementation(platform("com.google.firebase:firebase-bom:32.3.1"))
    implementation("com.google.firebase:firebase-analytics-ktx")
    implementation("com.google.firebase:firebase-auth")
    implementation("com.google.firebase:firebase-firestore")
    implementation("com.google.android.gms:play-services-auth:20.7.0")

    //navigation
    implementation("androidx.navigation:navigation-fragment:2.5.3")
    implementation("androidx.navigation:navigation-ui:2.5.3")

    //room
    implementation("androidx.room:room-runtime:2.5.2")
    annotationProcessor("androidx.room:room-compiler:2.5.2")

    //retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    //glide
    implementation("com.github.bumptech.glide:glide:4.12.0")

    //circle imageview
    implementation("de.hdodenhof:circleimageview:3.1.0")

    //lottie
    implementation("com.airbnb.android:lottie:6.1.0")

    //rxJava
    implementation ("io.reactivex.rxjava3:rxandroid:3.0.2")
    implementation ("io.reactivex.rxjava3:rxjava:3.1.6")
    implementation("com.github.akarnokd:rxjava3-retrofit-adapter:3.0.0")
    implementation("androidx.room:room-rxjava3:2.5.2")

    //youtube player
    implementation ("com.pierfrancescosoffritti.androidyoutubeplayer:core:12.0.0")
}