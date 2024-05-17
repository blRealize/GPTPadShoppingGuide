package com.example.common.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.blankj.utilcode.util.BarUtils

/**
 * Activity基类
 */
abstract class BaseActivity<B : ViewBinding,V : ViewModel> : AppCompatActivity() {

    lateinit var binding:B
    lateinit var model:V

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = initBinding()
        model = ViewModelProvider(this)[initViewModel()]
        setContentView(binding.root)
        //沉浸式状态栏
        BarUtils.setStatusBarLightMode(this,true)
        BarUtils.transparentStatusBar(this)
        //初始化view
        initView()

    }
    //初始化binding
    abstract fun initBinding():B

    abstract fun initViewModel():Class<V>

    abstract fun initView()

}