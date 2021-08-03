package com.kakao.android.kakaomaptest.ui.markerlist

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.kakao.android.kakaomaptest.R
import com.kakao.android.kakaomaptest.model.data.Place
import com.kakao.android.kakaomaptest.ui.viewmodel.MapViewModel
import java.util.*

class MarkerListAdapter(private val context: Context, private val viewModel: MapViewModel) :
    RecyclerView.Adapter<MarkerViewHolder>() {
    companion object {
        private const val TAG = "KM/MarkerListAdapter"

        const val VIEW_TYPE_ITEM = 1
        const val VIEW_TYPE_FOOTER = 0
        const val NOT_SELECTED = -1
    }

    //private var dataList: ArrayList<ArrayList<Place>> = ArrayList(4) => 이걸로 하면 dataList[0]이 비었다고 지랄지랄
    private var dataList = ArrayList<Place>()
    private var isNoNeedToMoreButton = false
    private var selectedPosition = NOT_SELECTED

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarkerViewHolder {

        return if (viewType == VIEW_TYPE_ITEM) {
            MarkerViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.marker_list_item,
                    parent,
                    false
                )
            );
        } else {
            MarkerViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.marker_list_footer,
                    parent,
                    false
                )
            );
        }
    }

    override fun onBindViewHolder(holder: MarkerViewHolder, position: Int) {
        holder.itemView.setBackgroundColor(
            if (selectedPosition === position)
                ContextCompat.getColor(context, R.color.yellow_selected) else Color.TRANSPARENT
        )

        if (position == itemCount - 1) {
            holder.setMoreInfoButton(
                isNoNeedToMoreButton,
                View.OnClickListener { viewModel.setNextPage() })
        } else {
            holder.setText(
                dataList[position].category_name,
                dataList[position].place_name,
                dataList[position].address_name
            )

            holder.itemView.setOnClickListener {
                viewModel.setMarkerItem(dataList[position].place_url)
                //(context as MainActivity).setDataAtListFragment(dataList[position].place_url)
                Log.d(TAG, "Click index : $position, data : $dataList")

                if (selectedPosition != NOT_SELECTED) {
                    notifyItemChanged(selectedPosition)
                }
                selectedPosition = position
                notifyItemChanged(selectedPosition)
                // Do your another stuff for your onClick
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            itemCount - 1 -> VIEW_TYPE_FOOTER;
            else -> VIEW_TYPE_ITEM;
        }
    }

    override fun getItemCount(): Int {
        var count = dataList.size

        return if (count > 0) {
            count + 1
        } else {
            count
        }
    }

    fun setDataList(list: ArrayList<Place>, isEnd: Boolean) {
        dataList = list

        isNoNeedToMoreButton = isEnd
        selectedPosition = NOT_SELECTED
        notifyDataSetChanged()
    }

    fun getSelectedPosition() : Int {
        return selectedPosition
    }

    fun setSelectItem(url: String) {
        if (selectedPosition != NOT_SELECTED) {
            notifyItemChanged(selectedPosition)
        }
        selectedPosition = findSelectedPosition(url)
        if (selectedPosition != NOT_SELECTED) {
            notifyItemChanged(selectedPosition)
        }
    }

    private fun findSelectedPosition(url: String): Int {
        var position = NOT_SELECTED
        for (data in dataList.withIndex()) {
            if (data.value.place_url == url) {
                return data.index
            }
        }
        return position
    }
}