package com.kit.pay.interfaces

/**
 * PayKit 内部日志级别。
 */
enum class PayKitLogLevel {
    DEBUG,
    INFO,
    WARN,
    ERROR
}

/**
 * 外部日志拦截器（接管模式）。
 *
 * 通过 `PayKit.setLogger(...)` 注册后，SDK 内部日志将全部转发给该拦截器，
 * 不再重复调用系统 `android.util.Log`，便于桥接到 LogKit、Timber 或文件日志系统。
 */
fun interface PayKitLogger {
    fun log(level: PayKitLogLevel, tag: String, message: String)
}
