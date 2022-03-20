# Sistem Penilaian Mahasiswa
Aplikasi berbasis Android yang dibangun menggunakan bahasa pemrograman Kotlin. Dibuat untuk memenuhi salah satu tugas Ujian Tengah Semester dari mata kuliah Konstruksi Perangkat Lunak (RL212). 


## Deskripsi Aplikasi
Aplikasi Sistem Penilaian Mahasiswa ini bertujuan untuk menyimpan informasi nilai mahasiswa berdasarkan mata kuliah, pengguna utamanya ditunjukan kepada dosen. Sistem penilaian dibagi kedalam empat kategori nilai, yaitu nilai Tugas, Quiz, Ujian Tengah Semester, dan Ujian Akhir Semester dengan komposisi sebagai berikut.
- Nilai tugas sebanyak 10%
- Nilai Quiz sebanyak 20%
- Nilai Ujian Tengah Semester sebanyak 30%
- Nilai Ujian Akhir Semester sebanyak 40%

Setiap mahasiswa memiliki 2 macam penilaian
1. Nilai Keseluruhan
Nilai keseluruhan diambil dari nilai rata-rata keseluruhan mata kuliah dari keempat kategori yang ada, kemudian dijumlahkan sesuai dengan komposisinya.
2. Nilai per Mata Kuliah
Nilai per mata kuliah diambil dari setiap mata kuliah, kemudian dijumlahkan sesuai dengan komposisi yang ada

Format penilaian terdiri dari dua jenis yaitu angka yang terdiri dari 1-100, dan huruf yang terdiri dari a, b+, b, b-, dan C. Ketentuan penilaian

| Nilai | Range |
|-------|-------------|
| A     | > 90        |
| A-    | 85 - 90     |
| B+    | 80 - 84     |
| B     | 75-79       |
| B-    | 70-74       |
| C     | < 70        |

## Design Architecture

Aplikasi ini dibangun dengan menerapkan [Clean Architecture](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html) dengan gambaran sebagai berikut.
<p align="center">
  <img src="https://blog.cleancoder.com/uncle-bob/images/2012-08-13-the-clean-architecture/CleanArchitecture.jpg" height="400px" />
</p>

Struktur modul yang digunakan mengikuti panduan dari clean architecture dengan membagi ke dalam beberapa layer dengan rincian sebagai berikut
1. Presentasion Layer
Berisi kelas UI Controller dan juga View Model, UI Controller digunakan untuk mengontrol aktivitas pada UI termasuk manajemen View dan View Group yang terdapat pada file layout. Kemudian View Model yang bertugas untuk menyediakan data bagi UI Controller
2. Domain Layer
Penyambung antara data dan UI, terdiri dari komponen ```Use Case```, ```Model```, dan ```Repositories Interface``` 
3. Data Layer
Mengimplementasi interface pada domain, bertugas untuk berkomunikasi dengan pihak luar. Dalam case ini berarti Firebase Firestore

## Tech Stack
1. [100% Kotlin](https://kotlinlang.org/) + [Coroutines](https://developer.android.com/kotlin/coroutines) - Menggunakan background proccess
2. Networking dengan [Retrofit](https://square.github.io/retrofit/)
3. [Jetpack](https://developer.android.com/jetpack)
    * [Navigation Components](https://developer.android.com/guide/navigation/navigation-getting-started) - Navigasi utama di dalam aplikasi
    * [Livedata](https://developer.android.com/topic/libraries/architecture/livedata) - Memberitahu ketika data mengalami update
    * [Viewmodel](https://developer.android.com/topic/libraries/architecture/viewmodel) - Memisahkan data dari UI Controller dan bertahan dari configuration changes
    * [Lifecycle](https://developer.android.com/topic/libraries/architecture/lifecycle) - Membuat event jika lifecycle mengalami perubahan
    * [Preferences Datastore](https://developer.android.com/topic/libraries/architecture/datastore) - Menyimpan data dengan bentuk key-value menggunakan protocol buffer dan dapat diakses secara asynchronous
4. [Firebase](https://firebase.google.com/) - Menyediakan berbagai macam tools powerfull secara gratis 
    * [Firestore](https://firebase.google.com/docs/firestore) - Database online dengan sinkronisasi data secara realtime
    * [Firebase Auth](https://firebase.google.com/docs/auth) - Menyediakan berbagai macam metode autentikasi tanpa melakukan konfigurasi yang rumit
5. [Material Design](https://material.io/develop/android)
6. [Timber](https://github.com/JakeWharton/timber)
7. [Gradle Kotlin DSL](https://docs.gradle.org/current/userguide/kotlin_dsl.html)
