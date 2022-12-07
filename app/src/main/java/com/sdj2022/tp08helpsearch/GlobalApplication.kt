package com.sdj2022.tp08helpsearch

import android.app.Application
import com.kakao.sdk.common.KakaoSdk

class GlobalApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        // 카카오 SDK 초기화 - 플랫폼에서 발급된 "네이티브앱키" 필요
        KakaoSdk.init(this, "8bc39357292f13b4fca5da3c380e4641")
    }
}