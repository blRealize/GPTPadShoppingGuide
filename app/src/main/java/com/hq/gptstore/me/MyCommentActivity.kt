package com.hq.gptstore.me

import android.view.MenuItem
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.BmobUser
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.FindListener
import com.blankj.utilcode.util.TimeUtils
import com.blankj.utilcode.util.ToastUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.common.base.BaseActivity
import com.example.common.base.BaseModel
import com.hq.gptstore.R
import com.hq.gptstore.bean.Comment
import com.hq.gptstore.databinding.ActivityMyCommentBinding

class MyCommentActivity : BaseActivity<ActivityMyCommentBinding, BaseModel>() {

    override fun initBinding(): ActivityMyCommentBinding {
        return ActivityMyCommentBinding.inflate(layoutInflater)
    }

    override fun initViewModel(): Class<BaseModel> {
        return BaseModel::class.java
    }

    override fun initView() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        binding.rvList.adapter = commentsAdapter
        binding.refreshLayout.setEnableLoadMore(false)
        binding.refreshLayout.setOnRefreshListener {
            getCommentsList()
        }
        getCommentsList()
    }

    private val commentsAdapter = object : BaseQuickAdapter<Comment, BaseViewHolder>(R.layout.item_comment){
        override fun convert(holder: BaseViewHolder, item: Comment) {
            holder.setText(R.id.tv_user_name,item.sender.nickname)
            holder.setText(R.id.tv_comment_content,item.content)
            holder.setText(R.id.tv_time, TimeUtils.getFriendlyTimeSpanByNow(item.createdAt))
        }
    }

    /**
     * 从数据库获取任务提交内容
     */
    private fun getCommentsList(){
        BmobQuery<Comment>().apply {
            addWhereEqualTo("sender",BmobUser.getCurrentUser().objectId)
            include("sender.nickname")
            findObjects(object : FindListener<Comment>(){
                override fun done(p0: MutableList<Comment>?, p1: BmobException?) {
                    if (p1 == null){
                        commentsAdapter.setList(p0)
                    }else {
                        ToastUtils.showShort(p1?.message)
                    }
                    binding.refreshLayout.finishRefresh()
                }
            })
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