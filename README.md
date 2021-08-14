# Compose Exercise: Room Basics
- Add, show, edit, and delete data from a Room database using Jetpack Compose.
- TextField background color change.
- Keep only one edit form open at a time.

<br />

## Dependencies
```kt
ext {
    compose_version = '1.0.1'
    room_version = '2.3.0'
}

dependencies {
    classpath "com.android.tools.build:gradle:7.0.0"
    classpath "org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.21"
}
```

```kt
plugins {
    id 'com.android.application'
    id 'kotlin-android'
    id 'kotlin-kapt'
}

// Room
implementation "androidx.room:room-runtime:$room_version"
kapt "androidx.room:room-compiler:$room_version"

// Optional - Kotlin Extensions and Coroutines support for Room
implementation "androidx.room:room-ktx:$room_version"

// Compose ViewModel
implementation "androidx.lifecycle:lifecycle-viewmodel-compose:1.0.0-alpha07"

// Compose LiveData (StackOverflow)
implementation "androidx.compose.runtime:runtime-livedata:$compose_version"

// LiveData (Flow to LiveData: asLiveData)
implementation "androidx.lifecycle:lifecycle-livedata-ktx:2.3.1"
```

<br />

<img src="https://user-images.githubusercontent.com/67064997/129380620-aedce818-05e1-4c6d-9b9d-66e85bf072de.png" width="50%" />
