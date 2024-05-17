package com.hq.gptstore.home

import android.util.Log
import android.view.MenuItem
import android.view.View
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.BmobUser
import cn.bmob.v3.ai.BmobAI
import cn.bmob.v3.ai.ChatMessageListener
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.FindListener
import cn.jiguang.imui.chatinput.listener.OnMenuClickListener
import cn.jiguang.imui.chatinput.model.FileItem
import com.blankj.utilcode.util.GsonUtils
import com.blankj.utilcode.util.KeyboardUtils
import com.example.common.base.BaseActivity
import com.example.common.base.BaseModel
import com.hq.gptstore.App
import com.hq.gptstore.bean.Commodity
import com.hq.gptstore.databinding.ActivityChatBinding

class ChatActivity : BaseActivity<ActivityChatBinding, BaseModel>() {

//    lateinit var bmobAI: BmobAI

    override fun initBinding(): ActivityChatBinding {
        return ActivityChatBinding.inflate(layoutInflater)
    }

    override fun initViewModel(): Class<BaseModel> {
        return BaseModel::class.java
    }

    override fun initView() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

//        bmobAI = BmobAI()
//        bmobAI.Connect()

        binding.chatInput.setMenuClickListener(object : OnMenuClickListener {
            override fun onSendTextMessage(p0: CharSequence?): Boolean {
                binding.llMy.visibility = View.VISIBLE
                binding.tvMyContent.text = p0

                chatAi(p0.toString())

                return true
            }

            override fun onSendFiles(p0: MutableList<FileItem>?) {

            }

            override fun switchToMicrophoneMode(): Boolean {
                return true
            }

            override fun switchToGalleryMode(): Boolean {
                return true
            }

            override fun switchToCameraMode(): Boolean {
                return true
            }

            override fun switchToEmojiMode(): Boolean {
                return true
            }
        })

        getAllData()
    }

    private fun chatAi(context:String) {
        App.Companion.bmobAI?.Chat(context,BmobUser.getCurrentUser().objectId,object : ChatMessageListener {
            override fun onMessage(p0: String?) {
                Log.d("onMessage",p0.toString())
                runOnUiThread {
                    binding.llAi.visibility = View.VISIBLE
//                    binding.tvAiContent.text = "加载中~~~"
                }
            }

            override fun onFinish(p0: String?) {
                runOnUiThread {
                    binding.llAi.visibility = View.VISIBLE
                    binding.tvAiContent.text = p0.toString()
                }
                Log.d("onFinish",p0.toString())
            }

            override fun onError(p0: String?) {
                runOnUiThread {
                    binding.llAi.visibility = View.VISIBLE
                    binding.tvAiContent.text = p0.toString()
                }
            }

            override fun onClose() {

            }
        })
    }

    private fun getAllData() {
        BmobQuery<Commodity>().findObjects(object : FindListener<Commodity>() {
            override fun done(p0: MutableList<Commodity>?, p1: BmobException?) {
                p0?.let {
                    val list = mutableListOf<MutableMap<String,String>>()
                    for (commodity in it) {
                        list.add(mutableMapOf<String, String>().apply {
                            put("cpu",commodity.cpu)
                            put("name",commodity.name)
                            put("price",commodity.price)
                            put("introduce",commodity.introduce)
                            put("runningMemory",commodity.runningMemory)
                            put("storage",commodity.storage)
                        })
                    }
//                    chatAi("接下来每个回复都要参考这个条件 ${GsonUtils.toJson(list)} 请根据这些条件给出购买建议 ")
                    App.bmobAI?.setPrompt("${GsonUtils.toJson(list)} 请根据这些条件给出购买建议 ")
                }
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home){
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

}