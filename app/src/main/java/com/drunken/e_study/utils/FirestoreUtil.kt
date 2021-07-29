package com.drunken.e_study.utils

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.drunken.e_study.R
import com.drunken.e_study.dto.Course
import com.drunken.e_study.dao.CourseDatabaseDao
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FirestoreUtil {

    private var db = Firebase.firestore
    private var auth = Firebase.auth
    private val _courses = MutableLiveData<ArrayList<Course>>()
    val courses: LiveData<ArrayList<Course>>
        get() = _courses


//    fun registerUser(userInfo: User, fragment: Fragment) {
//        db.collection("users").document(getCurrentUserId()).set(userInfo, SetOptions.merge())
//            .addOnSuccessListener {
//                (fragment as UserRegisterFragment).registerSuccess(userInfo)
//            }.addOnFailureListener {
//                Toast.makeText(fragment.requireActivity(), it.message, Toast.LENGTH_SHORT).show()
//            }
//    }

    fun getCurrentUserId(): String {
        val currentUser = auth.currentUser
        var currentUserID = ""
        if (currentUser != null) {
            currentUserID = currentUser.uid
        }

        return currentUserID
    }

    suspend fun setupCourses(dao: CourseDatabaseDao) {
        val courseSd = arrayListOf(
            Course(
                "7eabac85-909f-47f0-9dad-72b4ec427fd3",
                "Tematik Jilid I",
                "Penerapan Tematik pada Siswa Sekolah Dasar",
                "Budi, Ifadatul",
                "Kelas 2 SD",
                50000,
                3.0F,
                "https://firebasestorage.googleapis.com/v0/b/e-study-2c48f.appspot.com/o/images%2FcourseImg%2Fsample_course_img_sd.png?alt=media&token=a7f52a39-30ba-4e45-a958-d49f63f6c4b3",
                listOf(
                    mapOf("Pendahuluan" to "https://www.youtube.com/watch?v=0dsBFFzPylE"),
                    mapOf("Konsep Pembelajaran Tematik - Keg. 1"  to "https://www.youtube.com/watch?v=0dsBFFzPylE"),
                    mapOf("Konsep Pembelajaran Tematik - Keg. 2"  to "https://www.youtube.com/watch?v=0dsBFFzPylE"),
                    mapOf("Perangkat Pembelajaran Tematik - Keg. 1"  to "https://www.youtube.com/watch?v=0dsBFFzPylE"),
                    mapOf("Perangkat Pembelajaran Tematik - Keg. 2"  to "https://www.youtube.com/watch?v=0dsBFFzPylE"),
                    mapOf("Penutup"  to "https://www.youtube.com/watch?v=0dsBFFzPylE")
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
                "795e0cde-fa11-4294-9ec6-f93e4aec2e62",
                "Tematik Jilid II",
                "Penerapan Tematik pada Siswa Sekolah Dasar",
                "Budi, Ifadatul",
                "Kelas 2 SD",
                55000,
                3.5F,
                "https://firebasestorage.googleapis.com/v0/b/e-study-2c48f.appspot.com/o/images%2FcourseImg%2Fsample_course_img_sd.png?alt=media&token=a7f52a39-30ba-4e45-a958-d49f63f6c4b3"
            ),
            Course(
                "56a2f4bd-d6c1-42c0-b774-1462ab79e03b",
                "Tematik Jilid III",
                "Penerapan Tematik pada Siswa Sekolah Dasar",
                "Budi, Ifadatul",
                "Kelas 2 SD",
                60000,
                2.7F,
                "https://firebasestorage.googleapis.com/v0/b/e-study-2c48f.appspot.com/o/images%2FcourseImg%2Fsample_course_img_sd.png?alt=media&token=a7f52a39-30ba-4e45-a958-d49f63f6c4b3"
            ),
            Course(
                "52ba7f4a-4057-4ac5-8d7e-9ad3a7e796f0",
                "Tematik Jilid IV",
                "Penerapan Tematik pada Siswa Sekolah Dasar",
                "Budi, Ifadatul",
                "Kelas 2 SD",
                55000,
                4F,
                "https://firebasestorage.googleapis.com/v0/b/e-study-2c48f.appspot.com/o/images%2FcourseImg%2Fsample_course_img_sd.png?alt=media&token=a7f52a39-30ba-4e45-a958-d49f63f6c4b3"
            ),
            Course(
                "ab553ef3-f6b1-4a3b-b522-c976c2cb853b",
                "Tematik Jilid V",
                "Penerapan Tematik pada Siswa Sekolah Dasar",
                "Budi, Ifadatul",
                "Kelas 2 SD",
                39000,
                3.5F,
                "https://firebasestorage.googleapis.com/v0/b/e-study-2c48f.appspot.com/o/images%2FcourseImg%2Fsample_course_img_sd.png?alt=media&token=a7f52a39-30ba-4e45-a958-d49f63f6c4b3"
            ),
            Course(
                "ef5f2576-f8f5-4c4c-a9ad-b4990232edee",
                "Bahasa Indonesia",
                "Pengenalan Bahasa Indonesia yang Baik dan Benar",
                "Glen, Diego",
                "Kelas 3 SD",
                30000,
                4F,
                "https://firebasestorage.googleapis.com/v0/b/e-study-2c48f.appspot.com/o/images%2FcourseImg%2Fsample_bahasa_course.jpg?alt=media&token=9b9dd1ec-9ac4-4989-a0fb-3bf44570fbdb"
            ),
            Course(
                "de3241cc-30f0-48fd-9305-000fd9660c53",
                "Bahasa Indonesia Jilid I",
                "Pengenalan Bahasa Indonesia yang Baik dan Benar",
                "Budi, Ifadatul",
                "Kelas 2 SD",
                35000,
                3F,
                "https://firebasestorage.googleapis.com/v0/b/e-study-2c48f.appspot.com/o/images%2FcourseImg%2Fsample_bahasa_course.jpg?alt=media&token=9b9dd1ec-9ac4-4989-a0fb-3bf44570fbdb",
                listOf(
                    mapOf("Unit 1 - Tamasya"  to "https://www.youtube.com/watch?v=0dsBFFzPylE"),
                    mapOf("Unit 2 - Kegemaran"  to "https://www.youtube.com/watch?v=0dsBFFzPylE"),
                    mapOf("Unit 3 - Perpustakaan Sekolah"  to "https://www.youtube.com/watch?v=0dsBFFzPylE"),
                    mapOf("Unit 4 - Peristiwa"  to "https://www.youtube.com/watch?v=0dsBFFzPylE"),
                    mapOf("Unit 5 - Kehidupan"  to "https://www.youtube.com/watch?v=0dsBFFzPylE"),
                    mapOf("Unit 6 - Lingkungan"  to "https://www.youtube.com/watch?v=0dsBFFzPylE"),
                    mapOf("Unit 7 - Kenangan Masa Kecilku"  to "https://www.youtube.com/watch?v=0dsBFFzPylE"),
                    mapOf("Unit 8 - Budaya"  to "https://www.youtube.com/watch?v=0dsBFFzPylE")
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
                "https://firebasestorage.googleapis.com/v0/b/e-study-2c48f.appspot.com/o/images%2FcourseImg%2Fsample_course_math.png?alt=media&token=a2656dd2-7777-4bb5-8db6-adf90b6ac8ca",
                desc =
                "Intro ke aritmatika untuk siswa sekolah menengah pertama",
                id =
                "92e575e5-19d2-461a-8a84-bc17a70be377",
                mentor =
                "Dimas, Glen",
                price =
                65000,
                rating =
                4F,
                title =
                "Aritmatika",
                videos = listOf(
                    mapOf("Pengertian Aritmatika"  to "https://www.youtube.com/watch?v=0dsBFFzPylE"),
                    mapOf("Barisan Aritmatika"  to "https://www.youtube.com/watch?v=0dsBFFzPylE"),
                    mapOf("Deret Aritmatika"  to "https://www.youtube.com/watch?v=0dsBFFzPylE"),
                    mapOf("Pembahasan Soal"  to "https://www.youtube.com/watch?v=0dsBFFzPylE")
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
                "https://firebasestorage.googleapis.com/v0/b/e-study-2c48f.appspot.com/o/images%2FcourseImg%2Fsample_course_math.png?alt=media&token=a2656dd2-7777-4bb5-8db6-adf90b6ac8ca",
                desc =
                "Intro ke geometri untuk siswa sekolah menengah pertama",
                id =
                "1f3d9dc8-f5fe-4f08-8217-2ef10b76dd83",
                mentor =
                "Billy, Gabriel",
                price =
                60000,
                rating =
                4.5F,
                title =
                "Geometri",
                videos = listOf(
                    mapOf("Konsep dasar Geometri"  to "https://www.youtube.com/watch?v=0dsBFFzPylE"),
                    mapOf("Konsep baris & deret"  to "https://www.youtube.com/watch?v=0dsBFFzPylE"),
                    mapOf("Barisan Geometri"  to "https://www.youtube.com/watch?v=0dsBFFzPylE"),
                    mapOf("Deret Geometri"  to "https://www.youtube.com/watch?v=0dsBFFzPylE"),
                    mapOf("Pembahasan soal"  to "https://www.youtube.com/watch?v=0dsBFFzPylE")
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
                "https://firebasestorage.googleapis.com/v0/b/e-study-2c48f.appspot.com/o/images%2FcourseImg%2Fsample_course_math.png?alt=media&token=a2656dd2-7777-4bb5-8db6-adf90b6ac8ca",
                desc =
                "Pengenalan konsep-konsep dasar Matematika bagi siswa sekolah menengah pertama",
                id =
                "2e092f45-58e0-436d-95a8-ce47d9397b71",
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
                "https://firebasestorage.googleapis.com/v0/b/e-study-2c48f.appspot.com/o/images%2FcourseImg%2Fsample_course_math.png?alt=media&token=a2656dd2-7777-4bb5-8db6-adf90b6ac8ca",
                desc =
                "Pengenalan aljabar dan garis singgung kepada siswa sekolah menengah pertama",
                id =
                "3a51e7cc-f96e-4fb0-9679-2a9f40384654",
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
                "https://firebasestorage.googleapis.com/v0/b/e-study-2c48f.appspot.com/o/images%2FcourseImg%2Fsample_course_math.png?alt=media&token=a2656dd2-7777-4bb5-8db6-adf90b6ac8ca",
                desc =
                "Kumpulan materi untuk menghadapi UNBK mata ujian Matematika",
                id =
                "21acdb98-c5db-42f1-975a-201dd2c9d942",
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
                "https://firebasestorage.googleapis.com/v0/b/e-study-2c48f.appspot.com/o/images%2FcourseImg%2Fsample_course_math.png?alt=media&token=a2656dd2-7777-4bb5-8db6-adf90b6ac8ca",
                desc =
                "Kumpulan materi untuk menghadapi UNBK mata ujian Matematik (II)",
                id =
                "95b84d5f-8660-4f77-8809-9f34a1487973",
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
        withContext(Dispatchers.IO){
            courseSd.forEach { course ->
                dao.insert(course)
                db.collection("courses").document(course.id).set(course, SetOptions.merge())
                db.collection("SD_courses").document(course.id).set(course, SetOptions.merge())
            }
            courseSmp.forEach { course ->
                dao.insert(course)
                db.collection("courses").document(course.id).set(course, SetOptions.merge())
                db.collection("SMP_courses").document(course.id).set(course, SetOptions.merge())
            }
        }
    }
}
