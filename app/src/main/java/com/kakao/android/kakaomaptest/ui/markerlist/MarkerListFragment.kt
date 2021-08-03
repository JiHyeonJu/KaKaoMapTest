package com.kakao.android.kakaomaptest.ui.markerlist

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kakao.android.kakaomaptest.R
import com.kakao.android.kakaomaptest.model.data.CategorySearchData
import com.kakao.android.kakaomaptest.model.repository.MapRepository
import com.kakao.android.kakaomaptest.ui.viewmodel.MapViewModel
import com.kakao.android.kakaomaptest.ui.viewmodel.MapViewModelFactory
import kotlinx.android.synthetic.main.fragment_list.*
import retrofit2.Response

class MarkerListFragment : Fragment() {
    companion object {
        private const val TAG = "KM/MarkerListFragment"
    }

    private lateinit var mContext: Context
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapter: MarkerListAdapter

    private val mViewModel: MapViewModel by activityViewModels {
        MapViewModelFactory(
            MapRepository()
        )
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        addObserver()
    }

    private fun initRecyclerView() {
        mAdapter = MarkerListAdapter(mContext, mViewModel)
        mRecyclerView = marker_list_view.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(mContext)
            adapter = mAdapter
        }
    }

    private fun addObserver() {
        val dataObserver: Observer<Response<CategorySearchData>> =
            Observer { liveData ->
                // liveData 변경시(api 호출로 데이터를 가져온 후에, place 정보 recyclerView 추가)
                if (liveData != null && liveData.isSuccessful) {
                    var isEnd = false
                    liveData.body()?.meta?.let {
                        isEnd = it.is_end
                    }
                    Log.i(TAG, "data :" + liveData.body().toString())

                    if (mViewModel.getDataList(mViewModel.getCategoryCode()).isEmpty()) {
                        setRecyclerViewVisibility(false)
                    } else {
                        setRecyclerViewVisibility(true)
                        mAdapter.setDataList(
                            mViewModel.getDataList(mViewModel.getCategoryCode()),
                            isEnd
                        )
                    }
                } else {
                    setRecyclerViewVisibility(false)
                }
            }
        mViewModel.liveData.observe(viewLifecycleOwner, dataObserver)

        mViewModel.liveMarkerItem.observe(viewLifecycleOwner, Observer {
            mAdapter.setSelectItem(it)
            mRecyclerView.smoothScrollToPosition(mAdapter.getSelectedPosition())
        })
    }

    private fun setRecyclerViewVisibility(isShow: Boolean) {
        if (isShow) {
            guide_text_view.visibility = View.GONE
            mRecyclerView.visibility = View.VISIBLE
        } else {
            guide_text_view.visibility = View.VISIBLE
            mRecyclerView.visibility = View.GONE
        }
    }
}