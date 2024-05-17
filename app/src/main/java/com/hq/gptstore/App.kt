package com.hq.gptstore

import android.app.Application
import cn.bmob.v3.Bmob
import cn.bmob.v3.ai.BmobAI
import com.example.common.CommonModule

class App : Application() {


     companion object {
         var bmobAI: BmobAI? = null
     }

    override fun onCreate() {
        super.onCreate()
        CommonModule.init(this)
        Bmob.initialize(this,"bb1889feed37ca0506ca259df6781a50")

        bmobAI = BmobAI()
        bmobAI?.Connect()
    }
}