package com.drunken.e_study

import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.drunken.e_study.mainScreens.account.AccountCourseAdapter
import com.drunken.e_study.mainScreens.browseCourse.BrowseCourseAdapter
import com.drunken.e_study.database.Course

@BindingAdapter("listDataAccount")
fun bindRecyclerView(recyclerView: RecyclerView, data: ArrayList<Course>?) {
    val adapter = recyclerView.adapter as AccountCourseAdapter
    adapter.submitList(data)
}

@BindingAdapter("listDataBrowse")
fun bindBrowseRecyclerView(recyclerView: RecyclerView, data : ArrayList<Course>?){
    val adapter = recyclerView.adapter as BrowseCourseAdapter
    Log.i("binding", data.toString())
    adapter.submitList(data)
}

@BindingAdapter("imgLoad")
fun loadImgToView(imgView: ImageView, imgSrc: Int) {
    Glide.with(imgView.context).load(imgSrc).apply(
        RequestOptions().placeholder(R.drawable.loading_animation).error(R.drawable.ic_broken_image)
    ).into(imgView)
}

@BindingAdapter("loadPrice")
fun convertPrice(textView: TextView, price : Long) {
    val price = "%,d".format(price)
    textView.text = "Rp " + price
}