package com.drunken.e_study.ui.mainScreens.payment.paymentMethod

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import com.drunken.e_study.R
import com.drunken.e_study.databinding.ElvChildItemLayoutBinding

class MethodsListAdapter(private val context: Context, private val titleList : List<String>, private val childList : HashMap<String, List<Pair<Int, String>>>, private val clickClickListener : MethodClickListener) : BaseExpandableListAdapter() {

    private lateinit var binding : ElvChildItemLayoutBinding

    override fun getGroupCount(): Int {
        return titleList.size
    }

    override fun getChildrenCount(groupPosition: Int): Int {
        return childList[titleList[groupPosition]]!!.size
    }

    override fun getGroup(groupPosition: Int): Any {
        return titleList[groupPosition]
    }

    override fun getChild(groupPosition: Int, childPosition: Int): Any {
        return childList[titleList[groupPosition]]!![childPosition]
    }

    override fun getGroupId(groupPosition: Int): Long {
        return groupPosition.toLong()
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return childPosition.toLong()
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun getGroupView(
        groupPosition: Int,
        isExpanded: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View {
        var convertView = convertView
        val listTitle = getGroup(groupPosition) as String
        if (convertView == null){
            val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = layoutInflater.inflate(R.layout.elv_group_layout, null)
        }
        val listTitleView = convertView!!.findViewById<TextView>(R.id.listTitle)
        listTitleView.text = listTitle

        val indicatorImageView = convertView.findViewById<ImageView>(R.id.group_indicator)
        indicatorImageView.apply {
            if (isExpanded){
                setImageResource(R.drawable.ic_eva_arrow_ios_back_outline_up)
            } else {
                setImageResource(R.drawable.ic_eva_arrow_ios_back_outline)
            }
        }

        return convertView
    }

    override fun getChildView(
        groupPosition: Int,
        childPosition: Int,
        isLastChild: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View {
        var convertView = convertView
        val childItemText = getChild(groupPosition, childPosition) as Pair<*, *>
        if (convertView == null){
            val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            binding = DataBindingUtil.inflate(layoutInflater, R.layout.elv_child_item_layout, null, false)
            convertView = binding.root
        }

        val childListTv = convertView.findViewById<TextView>(R.id.expandedListItem)
        val childImageView = convertView.findViewById<ImageView>(R.id.child_item_iv)
        (childItemText.second as String).apply {
            childListTv.text = this
            binding.method = this
        }
        childImageView.setImageResource(childItemText.first as Int)
        binding.clickListener = clickClickListener

        return convertView
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return true
    }
}

class MethodClickListener(val clickListener: (metode: String) -> Unit) {
    fun onClick(metode: String) = clickListener(metode)
}