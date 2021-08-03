package com.kakao.android.kakaomaptest.ui.markerlist

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.marker_list_footer.view.*
import kotlinx.android.synthetic.main.marker_list_item.view.*

class MarkerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    fun setText(category: String, place: String, address: String) {
        with(itemView) {
            category_name.text = category
            place_name.text = place
            address_name.text = address
        }
    }

    fun setMoreInfoButton(hasToRemove: Boolean, onClickListener: View.OnClickListener) {
        with(itemView) {
            if (hasToRemove) {
                more_info_btn.visibility = View.GONE
            } else {
                more_info_btn.visibility = View.VISIBLE
                more_info_btn.setOnClickListener(onClickListener)
            }
        }
    }
}