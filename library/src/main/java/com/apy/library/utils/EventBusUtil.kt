package com.apy.library.utils

import com.apy.library.model.BusinessEvent
import org.greenrobot.eventbus.EventBus

object EventBusUtil {

    fun post (state:String){
        EventBus.getDefault().post(BusinessEvent(state))
    }

}