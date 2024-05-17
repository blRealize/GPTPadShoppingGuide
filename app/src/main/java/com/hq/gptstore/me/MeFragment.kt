package com.hq.gptstore.me

import android.view.View
import android.view.View.OnClickListener
import cn.bmob.v3.BmobUser
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.ClickUtils
import com.blankj.utilcode.util.TimeUtils
import com.bumptech.glide.Glide
import com.example.common.base.BaseFragment
import com.example.common.router.AppRouter
import com.hq.gptstore.R
import com.hq.gptstore.databinding.FragmentMeBinding


class MeFragment : BaseFragment<FragmentMeBinding, MeModel>() ,OnClickListener{

    override fun initBinding(): FragmentMeBinding {
        return FragmentMeBinding.inflate(layoutInflater)
    }

    override fun initViewModel(): Class<MeModel> {
        return MeModel::class.java
    }

    override fun initView() {

        ClickUtils.applyGlobalDebouncing(binding.itemLogout,this)
        ClickUtils.applyGlobalDebouncing(binding.ivAvatar,this)
        ClickUtils.applyGlobalDebouncing(binding.itemComment,this)
        //个人信息回调
        model.userEvent.observe(this){

            Glide.with(this).load(it.avatar).error(R.mipmap.ic_default_avatar).into(binding.ivAvatar)
            binding.tvNickname.text = it.nickname
            binding.tvId.text = "ID:"+it.objectId
            binding.tvNowTime.text = TimeUtils.getFriendlyTimeSpanByNow(System.currentTimeMillis())
        }
        model.NumEvent.observe(this){
            binding.tvCommentCount.text = it.size.toString()
        }

        binding.refreshLayout.setOnRefreshListener {
            it.finishRefresh()
            //获取个人信息
            model.getUserInfo()
            model.getClassCount()
        }
        //获取个人信息
        model.getUserInfo()
        model.getClassCount()
        model.getWarningCount()
    }

    override fun onClick(view: View) {
        when(view){

            binding.itemLogout -> {
                //退出登录
                BmobUser.logOut()
                AppRouter.launchLogin()
                activity?.finish()
            }
            binding.itemComment -> {
                ActivityUtils.startActivity(MyCommentActivity::class.java)
            }
        }
    }

}