package com.drunken.e_study

import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.drunken.e_study.ui.mainScreens.account.AccountCourseAdapter
import com.drunken.e_study.ui.mainScreens.browseCourse.BrowseCourseAdapter
import com.drunken.e_study.dto.Course
import com.drunken.e_study.ui.mainScreens.cart.CartAdapter
import com.drunken.e_study.ui.mainScreens.courseDetail.CourseDetailFragment
import com.drunken.e_study.ui.mainScreens.courseDetail.CourseDetailVideosAdapter
import com.drunken.e_study.ui.mainScreens.courseDetail.courseProgress.CourseDetailModulesAdapter
import com.drunken.e_study.ui.mainScreens.courseDetail.courseProgress.CourseDetailQuizAdapter
import com.drunken.e_study.ui.mainScreens.courseDetailShop.CourseDetailShopVideosAdapter
import com.drunken.e_study.ui.mainScreens.payment.confirmPayment.ConfirmPaymentAdapter

@BindingAdapter("listDataAccount")
fun bindAccountItems(recyclerView : RecyclerView, data : ArrayList<Course>?){
    val adapter = recyclerView.adapter as AccountCourseAdapter
    adapter.submitList(data)
}

@BindingAdapter("listDataBrowse")
fun bindBrowseRecyclerView(recyclerView: RecyclerView, data : ArrayList<Course>?){
    val adapter = recyclerView.adapter as BrowseCourseAdapter
    adapter.submitList(data)
}

@BindingAdapter("listDataCourseDetailShopVideos")
fun bindCourseDetailShopVideos(recyclerView: RecyclerView, data : List<String>?){
    val adapter = recyclerView.adapter as CourseDetailShopVideosAdapter
    adapter.submitList(data)
}

@BindingAdapter("listDataCourseDetailVideos")
fun bindCourseDetailVideos(recyclerView: RecyclerView, data : List<String>?){
    val adapter = recyclerView.adapter as CourseDetailVideosAdapter
    adapter.submitList(data)
}

@BindingAdapter(value = ["data", "type"], requireAll = true)
fun bindCourseDetailProcessItems(recyclerView : RecyclerView, data: List<String>?, type : Int){
    val adapter = when(type){
        CourseDetailFragment.VIDEOS_ITEM_TYPE -> {
            recyclerView.adapter as CourseDetailVideosAdapter
        }
        CourseDetailFragment.MODULES_ITEM_TYPE -> {
            recyclerView.adapter as CourseDetailModulesAdapter
        }
        CourseDetailFragment.QUIZ_ITEM_TYPE -> {
            recyclerView.adapter as CourseDetailQuizAdapter
        }
        else -> null
    }
    adapter?.submitList(data)
}

@BindingAdapter("listDataCart")
fun bindCartItems(recyclerView : RecyclerView, data : ArrayList<Course>?){
    val adapter = recyclerView.adapter as CartAdapter
    adapter.submitList(data)
}

@BindingAdapter("listDataPayment")
fun bindPaymentItems(recyclerView: RecyclerView, data : ArrayList<Course>?){
    val adapter = recyclerView.adapter as ConfirmPaymentAdapter
    adapter.submitList(data)
}

@BindingAdapter("imgLoad")
fun loadImgToView(imgView: ImageView, imgSrc: Int) {
    Glide.with(imgView.context).load(imgSrc).into(imgView)
}

@BindingAdapter("loadPrice")
fun convertPrice(textView: TextView, price : Long) {
    val price = "%,d".format(price)
    textView.text = "Rp " + price
}

@BindingAdapter("initMetodePembayaran")
fun setMetodePembayaran(textView: TextView, metode : String?){
    if (metode == null){
        textView.text = textView.resources.getString(R.string.pilih_metode)
    } else {
        textView.text = metode
    }
}