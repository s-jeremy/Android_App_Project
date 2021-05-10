package com.android_app_project.app.data.models

data class PingResult(val ping: String) {
    fun isSuccess(): Boolean {
        return this.ping == "1"
    }
}