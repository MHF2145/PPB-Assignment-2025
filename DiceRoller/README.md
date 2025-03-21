# Latihan Membuat Aplikasi Dice Roller Interaktif dengan Jetpack Compose

## 📌 Deskripsi
Repositori ini berisi latihan dalam membuat aplikasi "Dice Roller" interaktif menggunakan Jetpack Compose.
Aplikasi ini memungkinkan pengguna untuk melempar dadu secara acak dengan menekan tombol, serta menampilkan
hasil lemparan dalam bentuk gambar dadu yang berubah secara dinamis.

Fitur utama dari aplikasi ini meliputi:
- Menampilkan gambar dadu sesuai hasil lemparan.
- Menggunakan **State** untuk menyimpan hasil lemparan dadu secara dinamis.
- Tata letak berbasis **Column()**, dengan elemen **Image()**, **Spacer()**, dan **Button()**.
- Implementasi interaksi dengan **mutableStateOf()** dan pemutakhiran nilai menggunakan **(1..6).random()**.
- Penyimpanan string tombol "Roll" di **res/values/strings.xml** untuk pengelolaan teks yang lebih rapi.
- Memanfaatkan fitur **Preview** di Android Studio untuk melihat tampilan aplikasi sebelum dijalankan.

Aplikasi ini dibuat sebagai latihan untuk memahami dasar-dasar Jetpack Compose, khususnya dalam
menggunakan **State**, **Composable Functions**, **UI Layout**, dan interaksi pengguna dalam aplikasi Android.

Tutorial Lengkap: [Jetpack Compose Dice Roller](https://developer.android.com/codelabs/basic-android-kotlin-compose-build-a-dice-roller-app?hl=id&continue=https%3A%2F%2Fdeveloper.android.com%2Fcourses%2Fpathways%2Fandroid-basics-compose-unit-2-pathway-2%3Fhl%3Did%23codelab-https%3A%2F%2Fdeveloper.android.com%2Fcodelabs%2Fbasic-android-kotlin-compose-build-a-dice-roller-app#7)