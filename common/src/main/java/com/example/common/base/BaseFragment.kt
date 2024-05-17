package com.example.common.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<B : ViewBinding,V : ViewModel>() : Fragment() {

    lateinit var binding:B
    lateinit var model:V

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = initBinding()
        model = ViewModelProvider(this)[initViewModel()]
        initView()
        return binding.root
    }

    abstract fun initBinding():B
    abstract fun initViewModel():Class<V>
    abstract fun initView()

}