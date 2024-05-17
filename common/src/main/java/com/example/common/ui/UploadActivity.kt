package com.example.common.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.MenuItem
import android.view.TextureView
import android.webkit.WebView
import android.widget.LinearLayout
import com.blankj.utilcode.util.ToastUtils
import com.example.common.base.BaseActivity
import com.example.common.base.BaseModel
import com.example.common.databinding.ActivityUploadBinding
import com.just.agentweb.AgentWeb
import com.just.agentweb.WebChromeClient
import com.just.agentweb.WebViewClient

class UploadActivity : BaseActivity<ActivityUploadBinding,BaseModel>() {

    lateinit var agentWeb: AgentWeb

    companion object {
        const val REQUEST_CODE_UPLOAD_IMG = 10086
        const val RESULT_IMG_URL = "result_img_url"

        fun launch(context: Activity){
            val intent = Intent(context,UploadActivity::class.java)
            context.startActivityForResult(intent, REQUEST_CODE_UPLOAD_IMG)
        }
    }

    override fun initBinding(): ActivityUploadBinding {
        return ActivityUploadBinding.inflate(layoutInflater)
    }

    override fun initViewModel(): Class<BaseModel> {
        return BaseModel::class.java
    }

    override fun initView() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.title = "上传图片"

        /**
         * 初始化webview
         */
        agentWeb = AgentWeb.with(this)
            .setAgentWebParent(binding.webContainer, LinearLayout.LayoutParams(-1, -1))
            .useDefaultIndicator()
            .setWebChromeClient(object : WebChromeClient() {
                override fun onReceivedTitle(view: WebView?, title: String?) {
                    super.onReceivedTitle(view, title)
                    //加载网页后把title显示在toolbar
                }
            })
            .setWebViewClient(object : WebViewClient(){
                override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                    return super.shouldOverrideUrlLoading(view, url)
                }
            })
            .createAgentWeb()
            .ready()
            .go("http://tuchuang.org/")

        binding.btnConfirm.setOnClickListener {
            val url = binding.etUrl.text.toString().trim()
            if (TextUtils.isEmpty(url)){
                ToastUtils.showShort("请填写图片链接")
                return@setOnClickListener
            }
            val intent = Intent()
            intent.putExtra(RESULT_IMG_URL,url)
            setResult(RESULT_OK,intent)
            finish()
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home){
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    /**
     * 跟随生命周期
     */
    override fun onPause() {
        agentWeb.webLifeCycle.onPause()
        super.onPause()
    }

    override fun onResume() {
        agentWeb.webLifeCycle.onResume()
        super.onResume()
    }

    override fun onDestroy() {
        agentWeb.webLifeCycle.onDestroy()
        super.onDestroy()
    }

}