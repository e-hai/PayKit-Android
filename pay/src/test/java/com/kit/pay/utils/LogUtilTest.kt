package com.kit.pay.utils

import com.kit.pay.PayKit
import com.kit.pay.interfaces.PayKitLogLevel
import com.kit.pay.interfaces.PayKitLogger
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class LogUtilTest {

    @After
    fun tearDown() {
        PayKit.setLogger(null)
        LogUtil.enabled = true
    }

    @Test
    fun `custom logger intercepts all log levels`() {
        val captured = mutableListOf<Triple<PayKitLogLevel, String, String>>()
        PayKit.setLogger(PayKitLogger { level, tag, message ->
            captured.add(Triple(level, tag, message))
        })

        LogUtil.d("debug msg")
        LogUtil.i(LogUtil.TAG_BILLING, "info msg")
        LogUtil.w("warn msg")
        LogUtil.e("error msg")

        assertEquals(4, captured.size)
        assertEquals(Triple(PayKitLogLevel.DEBUG, LogUtil.TAG, "debug msg"), captured[0])
        assertEquals(Triple(PayKitLogLevel.INFO, LogUtil.TAG_BILLING, "info msg"), captured[1])
        assertEquals(Triple(PayKitLogLevel.WARN, LogUtil.TAG, "warn msg"), captured[2])
        assertEquals(Triple(PayKitLogLevel.ERROR, LogUtil.TAG, "error msg"), captured[3])
    }

    @Test
    fun `disabled LogUtil suppresses custom logger callbacks`() {
        val captured = mutableListOf<String>()
        PayKit.setLogger { _, _, message -> captured.add(message) }
        LogUtil.enabled = false

        LogUtil.d("should not log")
        LogUtil.e("should not log error")

        assertTrue(captured.isEmpty())
    }

    @Test
    fun `throwing custom logger does not crash caller`() {
        PayKit.setLogger { _, _, _ -> throw IllegalStateException("logger failure") }

        LogUtil.i("safe log")
    }
}
