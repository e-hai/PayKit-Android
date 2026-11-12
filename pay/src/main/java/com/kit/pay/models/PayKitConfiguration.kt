package com.kit.pay.models

import com.kit.pay.interfaces.LocalPurchaseVerifier
import com.kit.pay.interfaces.PurchaseVerifier

/**
 * 订阅与非消耗品订单的确认（acknowledge）策略。
 */
enum class AcknowledgeMode {
    /** 默认：验单通过后由 SDK 自动调用 Google Play acknowledgePurchase */
    CLIENT_AUTO,

    /** 由宿主服务端调用 Play Developer API 确认；SDK 仅负责上报验单，不重复 acknowledge */
    SERVER_MANAGED
}

/**
 * SDK 初始化配置。
 *
 * 商品类型（订阅 / 消耗 / 非消耗）必须由宿主声明，补单时才能正确
 * 选择 acknowledge 或 consume。
 *
 * @param isOfferPersonalizedDefault 欧盟个性化报价默认值。
 * @param purchaseVerifier 验单扩展；默认 [LocalPurchaseVerifier]（不联网）。
 * 接入服务端时传入自定义实现即可，无需改 SDK 发货主流程。
 * @param acknowledgeMode 非消耗品与订阅的确认策略；默认 [AcknowledgeMode.CLIENT_AUTO]。
 */
data class PayKitConfiguration(
    val subsProductIds: Set<String> = emptySet(),
    val consumableProductIds: Set<String> = emptySet(),
    val nonConsumableProductIds: Set<String> = emptySet(),
    val isOfferPersonalizedDefault: Boolean = false,
    val purchaseVerifier: PurchaseVerifier = LocalPurchaseVerifier,
    val acknowledgeMode: AcknowledgeMode = AcknowledgeMode.CLIENT_AUTO
)
