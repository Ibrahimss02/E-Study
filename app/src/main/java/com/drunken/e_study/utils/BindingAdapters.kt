package com.drunken.e_study

import android.annotation.SuppressLint
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.drunken.e_study.dto.Course
import com.drunken.e_study.dto.User
import com.drunken.e_study.ui.mainScreens.account.AccountCourseAdapter
import com.drunken.e_study.ui.mainScreens.browseCourse.BrowseCourseAdapter
import com.drunken.e_study.ui.mainScreens.cart.CartAdapter
import com.drunken.e_study.ui.mainScreens.courseDetail.CourseDetailFragment
import com.drunken.e_study.ui.mainScreens.courseDetail.CourseDetailVideosAdapter
import com.drunken.e_study.ui.mainScreens.courseDetail.courseProgress.CourseDetailModulesAdapter
import com.drunken.e_study.ui.mainScreens.courseDetail.courseProgress.CourseDetailQuizAdapter
import com.drunken.e_study.ui.mainScreens.courseDetailShop.CourseDetailShopVideosAdapter
import com.drunken.e_study.ui.mainScreens.newsFeed.NewsFeedAdapter
import com.drunken.e_study.ui.mainScreens.payment.confirmPayment.ConfirmPaymentAdapter
import com.kwabenaberko.newsapilib.models.Article
import java.text.SimpleDateFormat
import java.util.*

@BindingAdapter("listDataAccount")
fun bindAccountItems(recyclerView: RecyclerView, data: ArrayList<Course>?) {
    val adapter = recyclerView.adapter as AccountCourseAdapter
    adapter.submitList(data)
}

@BindingAdapter("listDataBrowse")
fun bindBrowseRecyclerView(recyclerView: RecyclerView, data: ArrayList<Course>?) {
    val adapter = recyclerView.adapter as BrowseCourseAdapter
    adapter.submitList(data)
}

@BindingAdapter("listDataCourseDetailShopVideos")
fun bindCourseDetailShopVideos(recyclerView: RecyclerView, data: List<Map<String, String>>?) {
    val adapter = recyclerView.adapter as CourseDetailShopVideosAdapter
    adapter.submitList(data)
}

@BindingAdapter("listDataCourseDetailVideos")
fun bindCourseDetailVideos(recyclerView: RecyclerView, data: List<Map<String, String>>?) {
    val adapter = recyclerView.adapter as CourseDetailVideosAdapter
    adapter.submitList(data)
}

@BindingAdapter("listDataNews")
fun bindListNews(recyclerView: RecyclerView, data : ArrayList<Article>?){
    val adapter = recyclerView.adapter as NewsFeedAdapter
    adapter.submitList(data)
}

@BindingAdapter(value = ["dataVideos", "dataTitles", "type"], requireAll = false)
fun bindCourseDetailProcessItems(recyclerView: RecyclerView, dataVideos : List<Map<String, String>>?, dataTitles : List<String>?, type: Int) {
    when (type) {
        CourseDetailFragment.VIDEOS_ITEM_TYPE -> {
            val adapter = recyclerView.adapter as CourseDetailVideosAdapter
            adapter.submitList(dataVideos)
        }
        CourseDetailFragment.MODULES_ITEM_TYPE -> {
            val adapter = recyclerView.adapter as CourseDetailModulesAdapter
            adapter.submitList(dataTitles)
        }
        CourseDetailFragment.QUIZ_ITEM_TYPE -> {
            val adapter = recyclerView.adapter as CourseDetailQuizAdapter
            adapter.submitList(dataTitles)
        }
    }
}

@BindingAdapter("listDataCart")
fun bindCartItems(recyclerView: RecyclerView, data: ArrayList<Course>?) {
    val adapter = recyclerView.adapter as CartAdapter
    adapter.submitList(data)
}

@BindingAdapter("listDataPayment")
fun bindPaymentItems(recyclerView: RecyclerView, data: ArrayList<Course>?) {
    val adapter = recyclerView.adapter as ConfirmPaymentAdapter
    adapter.submitList(data)
}

@BindingAdapter("imgLoad")
fun loadImgToView(imgView: ImageView, imgSrc: Int) {
    Glide.with(imgView.context).load(imgSrc).into(imgView)
}

@BindingAdapter("loadImgProfile")
fun loadImgWithGlide(imgView: ImageView, imgSrc: User?){
    if (imgSrc != null){
        Glide.with(imgView.context).load(imgSrc.imageProfile).centerCrop().placeholder(R.drawable.avatar_place_holder).error(R.drawable.avatar_place_holder).into(imgView)
    }
}

@BindingAdapter("loadImg")
fun loadImg(imgView: ImageView, url : String?){
    url?.let {
        Glide.with(imgView.context).load(url).centerCrop().error(R.drawable.img_error_placeholder).into(imgView)
    }
}

@BindingAdapter("loadPrice")
fun convertPrice(textView: TextView, price: Long) {
    val price = "%,d".format(price)
    textView.text = "Rp " + price
}

@BindingAdapter("initMetodePembayaran")
fun setMetodePembayaran(textView: TextView, metode: String?) {
    if (metode == null) {
        textView.text = textView.resources.getString(R.string.pilih_metode)
    } else {
        textView.text = metode
    }
}

@SuppressLint("SimpleDateFormat")
@BindingAdapter("setDate")
fun setDate(textView: TextView, date : String){
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
    val dates = inputFormat.parse(date)
    val diff = Calendar.getInstance().time.time - dates!!.time
    val formattedDate = ((diff / (1000 * 60 * 60)) % 24)
    textView.text = "$formattedDate jam yang lalu"
}