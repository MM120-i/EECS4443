# Memory Retention Game

***Technical Description:***

The memory retention game is a full stack mobile application that uses interesting game-like exercises to assist users improve their cognitive abilities. The application will be created in Android Studio using the Java programming language. This application is based on Human-Computer Interaction (HCI) concepts to deliver the best possible experience to the user. 

***Topic Description:***

The app focuses on memory retention and improvement, mainly speed, accuracy, flexibility, and problem-solving. The user interface is planned to have a grid pattern with twelve squares, making it easy to use and suitable for one and/or two-handed usage. The game’s difficulty grows gradually because it forces users to tap the correct squares in the correct order while improving their cognitive skills. 
## Tech Stack

* Java
* Kotlin
* Android Studio
* XML
* Android SDK
* Gradle
* Git
* SQLite

## Setup/Installation Requirements
To run this project, you need to have Android Studio installed on your machine. If you haven't already installed Android Studio, you can download it from the official Android Studio website.

#### Clone the Repository
* Open Android Studio.
* In the Welcome screen, click on "Check out project from Version Control" and select "Git."
* Enter the repository URL, and choose a directory for the local repository.
* Click "Clone" to clone the repository to your local machine.

#### Open the Project in Android Studio
* After cloning the repository, navigate to the directory where it was cloned.
* Double-click on the build.gradle file in the root directory of the project.
* Android Studio will import the project and build the necessary dependencies.

#### Run the Project
* Connect an Android device to your computer or use an Android Virtual Device (AVD) in Android Studio (A physical device is better imo).
* In Android Studio, click on the "Run" button or press Shift + F10 to build and run the project.
* Select the connected device or AVD to install and run the application.

Note: The game APK file is available for download. It can be located in the `debug` folder in the root directory.

### Configuration Overview: Build Gradle File

<details>
<summary><code>build.gradle.kts(:app)</code></summary>

```
plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.example.memorygame"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.memorygame"
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
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.core:core-ktx:1.12.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}
```
</details>

## App Demo
<div align="left">
   Memory Game tutorial <a href="https://www.youtube.com/watch?v=XjdJMXmrb8c&ab_channel=MahimMarufuzzaman" target="_blank"><b>Youtube Link</b></a>.
</div>

[![Memory Game Demo](https://img.youtube.com/vi/XjdJMXmrb8c/0.jpg)](https://www.youtube.com/watch?v=XjdJMXmrb8c)
