# Android CRUD Firebase

Aplicación simple con Android Studio aplicando operaciones CRUD usando [Firebase](https://console.firebase.google.com/).

## Descripción del proyecto

Añadir Firebase a una aplicación de Android

Nombre del paquete: `com.fernandocalmet.crudfirebase`

## Dependencias

### Firebase

Build.gradle - Project: 

```gradle
buildscript {
  repositories {
    // Check that you have the following line (if not, add it):
    google()  // Google's Maven repository
  }
  dependencies {
    ...
    // Add this line
    classpath 'com.google.gms:google-services:4.3.4'
  }
}
```

Build.gradle - Module: 

```gradle
apply plugin: 'com.android.application'
// Add this line
apply plugin: 'com.google.gms.google-services'

dependencies {
  // Import the Firebase BoM
  implementation platform('com.google.firebase:firebase-bom:25.12.0')

  // Add the dependency for the Firebase SDK for Google Analytics
  // When using the BoM, don't specify versions in Firebase dependencies
  implementation 'com.google.firebase:firebase-analytics'

  // Add the dependencies for any other desired Firebase products
  // https://firebase.google.com/docs/android/setup#available-libraries
}
```

### Otros

```gradle
implementation 'com.google.android.material:material:1.2.1'
implementation 'com.android.support:design:28.0.0'
implementation 'com.google.firebase:firebase-database:19.5.0'
```

## :page_facing_up: LICENCIA

Licencia MIT. Puedes verla en el [Archivo de Licencia](https://github.com/FernandoCalmet/android-crud-firebase/blob/main/LICENSE) para más información.

---

:octocat: [Puedes seguirme en Github.](https://github.com/FernandoCalmet)

[![ko-fi](https://www.ko-fi.com/img/githubbutton_sm.svg)](https://ko-fi.com/T6T41JKMI)
