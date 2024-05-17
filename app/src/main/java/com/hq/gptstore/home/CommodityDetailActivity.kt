package com.hq.gptstore.home

import android.content.DialogInterface
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.BmobUser
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.FindListener
import cn.bmob.v3.listener.SaveListener
import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.TimeUtils
import com.blankj.utilcode.util.ToastUtils
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.common.base.BaseActivity
import com.example.common.base.BaseModel
import com.example.login.UserInfo
import com.hq.gptstore.R
import com.hq.gptstore.bean.Comment
import com.hq.gptstore.bean.Commodity
import com.hq.gptstore.databinding.ActivityCommodityBinding

class CommodityDetailActivity : BaseActivity<ActivityCommodityBinding, BaseModel>() {

    lateinit var mCommodity: Commodity

    override fun initBinding(): ActivityCommodityBinding {
        return ActivityCommodityBinding.inflate(layoutInflater)
    }

    override fun initViewModel(): Class<BaseModel> {
        return BaseModel::class.java
    }

    override fun initView() {

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        BarUtils.addMarginTopEqualStatusBarHeight(binding.toolbar)

        mCommodity = intent.getSerializableExtra("bean") as Commodity

        Glide.with(this).load(mCommodity.imgUrl).into(binding.ivImage)
        binding.toolbar.title = mCommodity.name
        binding.collapsToolbar.title = mCommodity.name
        supportActionBar?.title = mCommodity.name

        binding.tvContent.text = mCommodity.introduce
        binding.tvCpu.text = "CPU:${mCommodity.cpu}"
        binding.tvRunningMemory.text = "运行内存:${mCommodity.runningMemory}"
        binding.tvStorage.text = "储存大小:${mCommodity.storage}"

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
     * 评论弹框
     */
    private fun showCommentDialog(){

        val etContent = EditText(this)
        val inputDialog = AlertDialog.Builder(this)
        inputDialog.setCancelable(false)
        inputDialog.setTitle("回复").setView(etContent)
        inputDialog.setPositiveButton("确定", DialogInterface.OnClickListener { dialog, _ ->
            val text = etContent.text.toString()
            if (TextUtils.isEmpty(text)){
                ToastUtils.showShort("内容不能为空")
                return@OnClickListener
            }else {
                //提交评论
                Comment().apply {
                    content = text
                    sender = BmobUser.getCurrentUser(UserInfo::class.java)
                    commodity = mCommodity
                }.save(object : SaveListener<String>(){
                    override fun done(p0: String?, p1: BmobException?) {
                        if (p1 == null){
                            ToastUtils.showShort("评论成功")
                            getCommentsList()
                        }else {
                            ToastUtils.showShort("评论失败")
                        }
                        dialog.dismiss()
                    }
                })

            }
        })
        inputDialog.setNegativeButton("取消", DialogInterface.OnClickListener { dialog, _ ->
            dialog.dismiss()
        })
        inputDialog.show()
    }

    /**
     * 从数据库获取任务提交内容
     */
    private fun getCommentsList(){
        BmobQuery<Comment>().apply {
            addWhereEqualTo("commodity",mCommodity.objectId)
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_comment_create,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home){
            finish()
            return true
        }else if (item.itemId == R.id.action_comment_create){
            showCommentDialog()
        }
        return super.onOptionsItemSelected(item)
    }

}