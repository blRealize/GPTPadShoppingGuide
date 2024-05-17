package com.example.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.SaveListener
import com.example.common.base.BaseModel

class LoginModel : BaseModel() {

    val loginSuccess = MutableLiveData<UserInfo>()
    val registerSuccess = MutableLiveData<Any>()
    val error = MutableLiveData<String>()

    /**
     * 登录
     */
    fun login(email:String,password:String){
        Log.d(javaClass.simpleName, "email：$email||password:$password")
        val user = UserInfo()
        user.username = email
        user.setPassword(password)
        user.login(object : SaveListener<UserInfo>(){
            override fun done(userInfo: UserInfo?, p1: BmobException?) {
                if (p1 == null){
                    loginSuccess.postValue(userInfo!!)
                }else {
                    error.postValue("登录失败${p1.message}")
                    Log.d("错误",p1.message.toString()+"||"+p1.printStackTrace())
                }
            }
        })
    }

    /**
     * 注册
     */
    fun signup(username:String, password:String, email:String){
        val user = UserInfo()
        user.username = email
        user.setPassword(password)
        user.nickname = username
        user.signUp(object : SaveListener<UserInfo>(){
            override fun done(userInfo: UserInfo?, exception: BmobException?) {
                if (exception == null){
                    registerSuccess.postValue("")
                }else {
                    error.postValue("注册失败")
                }
            }
        })
    }

}