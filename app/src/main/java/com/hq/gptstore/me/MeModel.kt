package com.hq.gptstore.me

import androidx.lifecycle.MutableLiveData
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.BmobUser
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.FetchUserInfoListener
import cn.bmob.v3.listener.FindListener
import com.example.common.base.BaseModel
import com.example.login.UserInfo
import com.hq.gptstore.bean.Comment

class MeModel : BaseModel() {

    var userEvent = MutableLiveData<UserInfo>()
    var NumEvent = MutableLiveData<List<Comment>>()
//    var WarnEvent = MutableLiveData<List<WarningBean>>()


    fun getUserInfo(){
        BmobUser.fetchUserInfo(object : FetchUserInfoListener<UserInfo>(){
            override fun done(
                p0: UserInfo?,
                p1: BmobException?
            ) {
                if (p1 == null && p0 !=null) {
                    userEvent.postValue(p0!!)
                }else {
                    userEvent.postValue(BmobUser.getCurrentUser(UserInfo::class.java))
                }
            }
        })
    }

    /**
     */
    fun getClassCount(){
        val bmobQuery = BmobQuery<Comment>()
        bmobQuery.addWhereEqualTo("sender",BmobUser.getCurrentUser())
        bmobQuery.findObjects(object :FindListener<Comment>(){
            override fun done(p0: MutableList<Comment>?, p1: BmobException?) {
                NumEvent.postValue(p0!!)
            }
        })
    }

    /**
    */
    fun getWarningCount(){
//        val bmobQuery = BmobQuery<WarningBean>()
////        bmobQuery.addWhereEqualTo("updateUser",BmobUser.getCurrentUser())
//        bmobQuery.findObjects(object :FindListener<WarningBean>(){
//            override fun done(p0: MutableList<WarningBean>?, p1: BmobException?) {
//                WarnEvent.postValue(p0!!)
//            }
//        })
    }

}