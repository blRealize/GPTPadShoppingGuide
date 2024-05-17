package com.hq.gptstore.home

import android.content.Intent
import android.view.MenuItem
import android.widget.ImageView
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.FindListener
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.common.base.BaseActivity
import com.example.common.base.BaseModel
import com.hq.gptstore.R
import com.hq.gptstore.bean.Commodity
import com.hq.gptstore.databinding.ActivitySearchResultBinding

class SearchResultActivity : BaseActivity<ActivitySearchResultBinding,BaseModel>(){

    private var keyword:String = ""

    override fun initBinding(): ActivitySearchResultBinding {
        return ActivitySearchResultBinding.inflate(layoutInflater)
    }

    override fun initViewModel(): Class<BaseModel> {
        return BaseModel::class.java
    }

    override fun initView() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        keyword = intent.getStringExtra("keyword").toString()
        supportActionBar?.title = keyword

        binding.rvList.adapter = commodityAdapter
        commodityAdapter.setOnItemClickListener { _, _, position ->
            val item = commodityAdapter.getItem(position)
            startActivity(Intent().apply {
                setClass(this@SearchResultActivity,CommodityDetailActivity::class.java)
                putExtra("bean",item)
            })
        }
        getAllData()
    }

    private fun getAllData() {
        BmobQuery<Commodity>().findObjects(object : FindListener<Commodity>() {
            override fun done(p0: MutableList<Commodity>?, p1: BmobException?) {

                p0?.let {
                    for (commodity in it) {
                        if (commodity.name.contains(keyword) || commodity.introduce.contains(keyword)) {
                            commodityAdapter.addData(commodity)
                        }
                    }
                }
            }
        })
    }

    private val commodityAdapter = object : BaseQuickAdapter<Commodity, BaseViewHolder>(R.layout.item_commodity) {
        override fun convert(holder: BaseViewHolder, item: Commodity) {
            val image = holder.getView<ImageView>(R.id.iv_image)
            Glide.with(context).load(item.imgUrl).into(image)
            holder.setText(R.id.tv_name,item.name)
            holder.setText(R.id.tv_content,item.introduce)
            holder.setText(R.id.tv_price,"￥${item.price}")
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home){
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

}