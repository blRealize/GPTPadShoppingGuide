package com.hq.gptstore.home

import android.content.Intent
import android.widget.ImageView
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.FindListener
import cn.bmob.v3.listener.SaveListener
import com.blankj.utilcode.util.ActivityUtils
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.common.base.BaseFragment
import com.example.common.base.BaseModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.hq.gptstore.R
import com.hq.gptstore.bean.Classification
import com.hq.gptstore.bean.Commodity
import com.hq.gptstore.databinding.FragmentHomeBinding

class HomeFragment : BaseFragment<FragmentHomeBinding, BaseModel>() {

    private val classificationList = arrayListOf<Classification>()

    override fun initBinding(): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(layoutInflater)
    }

    override fun initViewModel(): Class<BaseModel> {
        return BaseModel::class.java
    }

    override fun initView() {

        binding.toolbar.setOnMenuItemClickListener {
            if (it.itemId == R.id.item_ai) {
                ActivityUtils.startActivity(ChatActivity::class.java)
            }
            true
        }
        val searchItem = binding.toolbar.menu.findItem(R.id.item_search)
        val searchView = searchItem.actionView as SearchView
        searchView.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query.isNullOrEmpty())return false
                startActivity(Intent().apply {
                    setClass(requireActivity(),SearchResultActivity::class.java)
                    putExtra("keyword",query)
                })
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
        binding.rvList.adapter = commodityAdapter
        commodityAdapter.setOnItemClickListener { _, _, position ->
            val item = commodityAdapter.getItem(position)
            startActivity(Intent().apply {
                setClass(requireActivity(),CommodityDetailActivity::class.java)
                putExtra("bean",item)
            })
        }
        binding.refreshLayout.setEnableLoadMore(false)
        binding.refreshLayout.setOnRefreshListener {
            refreshData()
        }
        binding.tab.setOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(p0: TabLayout.Tab?) {
                refreshData()
            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {

            }

            override fun onTabReselected(p0: TabLayout.Tab?) {

            }
        })
        getClassificationData()
//        refreshData()
    }

    private fun refreshData() {
        BmobQuery<Commodity>().apply {
            addWhereEqualTo("classification", classificationList[binding.tab.selectedTabPosition].objectId)
        }.findObjects(object : FindListener<Commodity> () {
            override fun done(p0: MutableList<Commodity>?, p1: BmobException?) {
                commodityAdapter.setList(p0)
                binding.refreshLayout.finishRefresh()
            }
        })
    }

    private val commodityAdapter = object : BaseQuickAdapter<Commodity,BaseViewHolder>(R.layout.item_commodity) {
        override fun convert(holder: BaseViewHolder, item: Commodity) {
            val image = holder.getView<ImageView>(R.id.iv_image)
            Glide.with(context).load(item.imgUrl).into(image)
            holder.setText(R.id.tv_name,item.name)
            holder.setText(R.id.tv_content,item.introduce)
            holder.setText(R.id.tv_price,"￥${item.price}")
        }

    }

    private fun getClassificationData() {
        BmobQuery<Classification>().findObjects(object : FindListener<Classification> () {
            override fun done(p0: MutableList<Classification>?, p1: BmobException?) {
                classificationList.clear()

                p0?.let {
                    classificationList.addAll(it)
                    for (classification in classificationList) {
                        binding.tab.addTab(binding.tab.newTab().apply {
                            text = classification.name
                        })
                    }
                    refreshData()
                }
            }
        })
    }

}