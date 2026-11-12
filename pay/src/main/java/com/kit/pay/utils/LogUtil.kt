package com.kit.pay.utils

import android.util.Log
import com.kit.pay.interfaces.PayKitLogLevel
import com.kit.pay.interfaces.PayKitLogger

/**
 * SDK 统一日志。
 *
 * Logcat 过滤建议：
 * - `PayKit`：查看全部 SDK 日志（子 tag 均以 PayKit 开头）
 * - `PayKit-Billing`：仅 Billing / 商店交互
 * - `PayKit-Sample`：示例 App（Demo 使用）
 *
 * 消息约定：`action key=value key=value`，便于全文检索。
 */
object LogUtil {

    const val TAG = "PayKit"
    const val TAG_BILLING = "PayKit-Billing"

    /** 运行时开关；默认开启，宿主可在 release 关闭 */
    @JvmField
    var enabled: Boolean = true

    /** 外部日志拦截器（接管模式）；非空时不再输出到系统 Logcat */
    @Volatile
    @JvmField
    var logger: PayKitLogger? = null

    fun d(msg: String) = d(TAG, msg)

    fun d(tag: String, msg: String) {
        dispatch(PayKitLogLevel.DEBUG, tag, msg) { Log.d(tag, msg) }
    }

    fun i(msg: String) = i(TAG, msg)

    fun i(tag: String, msg: String) {
        dispatch(PayKitLogLevel.INFO, tag, msg) { Log.i(tag, msg) }
    }

    fun w(msg: String) = w(TAG, msg)

    fun w(tag: String, msg: String) {
        dispatch(PayKitLogLevel.WARN, tag, msg) { Log.w(tag, msg) }
    }

    fun e(msg: String) = e(TAG, msg)

    fun e(tag: String, msg: String) {
        dispatch(PayKitLogLevel.ERROR, tag, msg) { Log.e(tag, msg) }
    }

    private inline fun dispatch(
        level: PayKitLogLevel,
        tag: String,
        msg: String,
        fallback: () -> Unit
    ) {
        if (!enabled) return
        val custom = logger
        if (custom != null) {
            runCatching { custom.log(level, tag, msg) }
        } else {
            fallback()
        }
    }
}
