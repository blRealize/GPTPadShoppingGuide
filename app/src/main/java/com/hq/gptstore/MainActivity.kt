package com.hq.gptstore

import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import cn.bmob.v3.BmobUser
import com.example.common.base.BaseActivity
import com.example.common.base.BaseModel
import com.example.common.router.AppRouter
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.hq.gptstore.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding, BaseModel>() {

    override fun initBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun initViewModel(): Class<BaseModel> {
        return BaseModel::class.java
    }

    override fun initView() {
        if (!BmobUser.isLogin()){
            AppRouter.launchLogin()
            finish()
            return
        }
        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main2)

        navController.setGraph(R.navigation.navigation_normal)

        navView.setupWithNavController(navController)
    }

}