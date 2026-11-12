package com.kit.pay.models

import com.kit.pay.interfaces.LocalPurchaseVerifier
import org.junit.Assert.assertEquals
import org.junit.Test

class PayKitConfigurationTest {

    @Test
    fun `default acknowledgeMode is CLIENT_AUTO and verifier is LocalPurchaseVerifier`() {
        val config = PayKitConfiguration(subsProductIds = setOf("subs_pro"))
        assertEquals(AcknowledgeMode.CLIENT_AUTO, config.acknowledgeMode)
        assertEquals(LocalPurchaseVerifier, config.purchaseVerifier)
    }

    @Test
    fun `server managed acknowledgeMode can be configured`() {
        val config = PayKitConfiguration(
            subsProductIds = setOf("subs_pro"),
            acknowledgeMode = AcknowledgeMode.SERVER_MANAGED
        )
        assertEquals(AcknowledgeMode.SERVER_MANAGED, config.acknowledgeMode)
    }
}
