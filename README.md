# Compose Exercise: Room Basics
- Add, show, edit, and delete data from a Room database using Jetpack Compose.
- TextField background color change.

<br />

- [ ] BUG: Deleting a word while multiple edit forms are open shows stale data on the edit forms.
- [ ] TODO: Close all open edit forms on clicking any Update or Delete button.

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

<img src="https://user-images.githubusercontent.com/67064997/129222172-8d336785-9dce-446a-a388-65b176ada993.png" width="50%" />
