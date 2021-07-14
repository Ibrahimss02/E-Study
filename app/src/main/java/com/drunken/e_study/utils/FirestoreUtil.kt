package com.drunken.e_study.utils

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.drunken.e_study.R
import com.drunken.e_study.database.Course
import com.drunken.e_study.welcomeScreens.UserRegisterFragment
import com.drunken.e_study.database.User
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.*

class FirestoreUtil {

    private var db = Firebase.firestore
    private var auth = Firebase.auth
    private val _courses = MutableLiveData<ArrayList<Course>>()
    val courses: LiveData<ArrayList<Course>>
        get() = _courses


    fun registerUser(userInfo: User, fragment: Fragment) {
        db.collection("users").document(getCurrentUserId()).set(userInfo, SetOptions.merge())
            .addOnSuccessListener {
                (fragment as UserRegisterFragment).registerSuccess(userInfo)
            }.addOnFailureListener {
                Toast.makeText(fragment.requireActivity(), it.message, Toast.LENGTH_SHORT).show()
            }
    }

    fun getCurrentUserId(): String {
        val currentUser = auth.currentUser
        var currentUserID = ""
        if (currentUser != null) {
            currentUserID = currentUser.uid
        }

        return currentUserID
    }

    fun setupCourses() {
        val courseSd = arrayListOf(
            Course(
                "TMKV1",
                "Tematik Jilid I",
                "Penerapan Tematik pada Siswa Sekolah Dasar",
                "Budi, Ifadatul",
                "Kelas 2 SD",
                50000,
                3.0F,
                R.drawable.sample_course_img_sd,
                listOf(
                    "Pendahuluan",
                    "Konsep Pembelajaran Tematik - Keg. 1",
                    "Konsep Pembelajaran Tematik - Keg. 2",
                    "Perangkat Pembelajaran Tematik - Keg. 1",
                    "Perangkat Pembelajaran Tematik - Keg. 2",
                    "Penutup"
                ),
                listOf(
                    "Pendahuluan - Latar Belakang & Tujuan",
                    "Pendahuluan - Ruang Lingkup & Pemanfaatan Modul",
                    "Konsep Pembelajaran Tematik - Keg. 1",
                    "Konsep Pembelajaran Tematik - Keg. 2",
                    "Perangkat Pembelajaran Tematik - Keg. 1",
                    "Perangkat Pembelajaran Tematik - Keg. 2"
                ),
                listOf(
                    "Konsep Pembelajaran Tematik",
                    "Perangkat Pembelajaran Tematik",
                    "Tematik Akhir"
                ),
                56
            ),
            Course(
                "TMKV2",
                "Tematik Jilid II",
                "Penerapan Tematik pada Siswa Sekolah Dasar",
                "Budi, Ifadatul",
                "Kelas 2 SD",
                55000,
                3.5F,
                R.drawable.sample_course_img_sd
            ),
            Course(
                "TMKV3",
                "Tematik Jilid III",
                "Penerapan Tematik pada Siswa Sekolah Dasar",
                "Budi, Ifadatul",
                "Kelas 2 SD",
                60000,
                2.7F,
                R.drawable.sample_course_img_sd
            ),
            Course(
                "TMKV4",
                "Tematik Jilid IV",
                "Penerapan Tematik pada Siswa Sekolah Dasar",
                "Budi, Ifadatul",
                "Kelas 2 SD",
                55000,
                4F,
                R.drawable.sample_course_img_sd
            ),
            Course(
                "TMKV5",
                "Tematik Jilid V",
                "Penerapan Tematik pada Siswa Sekolah Dasar",
                "Budi, Ifadatul",
                "Kelas 2 SD",
                39000,
                3.5F,
                R.drawable.sample_course_img_sd
            ),
            Course(
                "BID",
                "Bahasa Indonesia",
                "Pengenalan Bahasa Indonesia yang Baik dan Benar",
                "Glen, Diego",
                "Kelas 3 SD",
                30000,
                4F,
                R.drawable.sample_bahasa_course
            ),
            Course(
                "BIDV1",
                "Bahasa Indonesia Jilid I",
                "Pengenalan Bahasa Indonesia yang Baik dan Benar",
                "Budi, Ifadatul",
                "Kelas 2 SD",
                35000,
                3F,
                R.drawable.sample_bahasa_course,
                listOf(
                    "Unit 1 - Tamasya",
                    "Unit 2 - Kegemaran",
                    "Unit 3 - Perpustakaan Sekolah",
                    "Unit 4 - Peristiwa",
                    "Unit 5 - Kehidupan",
                    "Unit 6 - Lingkungan",
                    "Unit 7 - Kenangan Masa Kecilku",
                    "Unit 8 - Budaya"
                ),
                listOf(
                    "Unit 1 - Menyebutkan kembali dengan kata",
                    "Unit 1 - Bertanya dengan sopan",
                    "Unit 2 - Bercerita",
                    "Unit 2 - Melengkapi cerita",
                    "Unit 2 - Menjelaskan isi puisi",
                    "Unit 3 - Membaca bersuara",
                    "Unit 3 - Menulis kalimat",
                    "Unit 4 - Mendeklamasikan isi puisi",
                    "Unit 4 - Menjelaskan isi puisi",
                    "Unit 5 - Menyampaikan pesan pendek",
                    "Unit 5 - Menjelaskan ciri khas tumbuhan dan binatang",
                    "Unit 6 - Membaca nyaring",
                    "Unit 6 - Mendeskripsikan gambar",
                    "Unit 7 - Mendengarkan dongeng",
                    "Unit 7  - Menyalin puisi",
                    "Unit 8 - Menceritakan cerita anak",
                    "Unit 8 - Membaca dalam hati"
                ),
                listOf("Uji kemampuanku semester I", "Uji kemampuanku semester 2", "Ujian Akhir"),
                103
            )
        )
        val courseSmp = arrayListOf(
            Course(
                classCategory =
                "Kelas 7 SMP",
                courseImg =
                R.drawable.sample_course_math,
                desc =
                "Intro ke aritmatika untuk siswa sekolah menengah pertama",
                id =
                "ATMKSMP1",
                mentor =
                "Dimas, Glen",
                price =
                65000,
                rating =
                4F,
                title =
                "Aritmatika",
                videos = listOf(
                    "Pengertian Aritmatika",
                    "Barisan Aritmatika",
                    "Deret Aritmatika",
                    "Pembahasan Soal"
                ),
                modules = listOf(
                    "Konsep Dasar Aritmatika",
                    "Konsep Barisan & Deret",
                    "Barisan Aritmatika",
                    "Deret Aritmatika"
                ),
                listQuiz = listOf(
                    "Latihan Konsep Barisan & Deret",
                    "Latihan Barisan Aritmatik",
                    "Latihan Deret Aritmatika"
                ),
                totalStudent = 32
            ),
            Course(
                classCategory =
                "Kelas 8 SMP",
                courseImg =
                R.drawable.sample_course_math,
                desc =
                "Intro ke geometri untuk siswa sekolah menengah pertama",
                id =
                "GMTSMP1",
                mentor =
                "Billy, Gabriel",
                price =
                60000,
                rating =
                4.5F,
                title =
                "Geometri",
                videos = listOf(
                    "Konsep dasar Geometri",
                    "Konsep baris & deret",
                    "Barisan Geometri",
                    "Deret Geometri",
                    "Pembahasan soal"
                ),
                modules = listOf(
                    "Konsep Geometri",
                    "Barisan Geometri",
                    "Deret Geometri",
                    "Outro",
                ),
                listQuiz = listOf(
                    "Soal Baris Geometri",
                    "Soal Deret Geometri",
                    "Ujian akhir Geometri"
                )
            ),
            Course(
                classCategory =
                "Kelas 7 SMP",
                courseImg =
                R.drawable.sample_course_math,
                desc =
                "Pengenalan konsep-konsep dasar Matematika bagi siswa sekolah menengah pertama",
                id =
                "LGKDSR1",
                mentor =
                "Paul, Veronica",
                price =
                68500,
                rating =
                5F,
                title =
                "Logika Dasar Matematika",
            ),
            Course(
                classCategory =
                "Kelas 7 SMP",
                courseImg =
                R.drawable.sample_course_math,
                desc =
                "Pengenalan aljabar dan garis singgung kepada siswa sekolah menengah pertama",
                id =
                "MTKSMP1",
                mentor =
                "Budi, Ani Wahyuni",
                price =
                75000,
                rating =
                4.3F,
                title =
                "Matematika",
            ),
            Course(
                classCategory =
                "Kelas 9 SMP",
                courseImg =
                R.drawable.sample_course_math,
                desc =
                "Kumpulan materi untuk menghadapi UNBK mata ujian Matematika",
                id =
                "MTKUNBK1",
                mentor =
                "Febry, John",
                price =
                70000,
                rating =
                4.8F,
                title =
                "Matematik UNBK"
            ),
            Course(
                classCategory =
                "Kelas 9 SMP",
                courseImg =
                R.drawable.sample_course_math,
                desc =
                "Kumpulan materi untuk menghadapi UNBK mata ujian Matematik (II)",
                id =
                "MTKUNBK2",
                mentor =
                "Febry, John",
                price =
                70000,
                rating =
                4.5F,
                title =
                "Matematik UNBK II"
            )

        )
        courseSd.forEach { course ->
            db.collection("courses").document(course.id).set(course, SetOptions.merge()).addOnSuccessListener {
                Log.i("upload", "${course.title} uploaded")
            }
            db.collection("SD_courses").document(course.id).set(course, SetOptions.merge()).addOnSuccessListener {
                Log.i("upload", "${course.title} uploaded to SD_courses") }
        }
        courseSmp.forEach { course ->
            db.collection("courses").document(course.id).set(course, SetOptions.merge()).addOnSuccessListener {
                Log.i("upload", "${course.title} uploaded")
            }
            db.collection("SMP_courses").document(course.id).set(course, SetOptions.merge()).addOnSuccessListener {
                Log.i("upload", "${course.title} uploaded to SMP_courses")
            }
        }
    }
}
