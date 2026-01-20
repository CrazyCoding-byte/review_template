// order/src/main/java/com/demo/order/constant/OrderConstant.java
package com.yzx.crazycodingbytecommon.entity;

public class OrderConstant {
    // 订单号前缀
    public static final String ORDER_NO_PREFIX = "ORD";

    // 订单锁定库存过期时间（30分钟）
    public static final long ORDER_LOCK_EXPIRE_MINUTES = 30;

    // 订单支付超时时间（30分钟）
    public static final long ORDER_PAY_TIMEOUT_MINUTES = 30;

    // 最大重试次数
    public static final int MAX_RETRY_COUNT = 3;
    /**
     * 订单创建
     */
    public static final String CREATE = "CREATE";

    /**
     * 订单取消
     */
    public static final String CANCEL = "CANCEL";

    /**
     * 订单支付
     */
    public static final String PAY = "PAY";

    /**
     * 订单发货
     */
    public static final String DELIVER = "DELIVER";

    /**
     * 订单确认收货
     */
    public static final String CONFIRM_RECEIVE = "CONFIRM_RECEIVE";

    /**
     * 订单退款
     */
    public static final String REFUND = "REFUND";

    /**
     * 订单状态强制修改（如客服操作）
     */
    public static final String FORCE_UPDATE_STATUS = "FORCE_UPDATE_STATUS";

    /**
     * 订单超时自动关闭
     */
    public static final String TOPIC_ORDER_CREATE = "ORDER_CREATE_TOPIC";
    public static final String TOPIC_ORDER_PAY = "ORDER_PAY_TOPIC";
    public static final String TOPIC_ORDER_CANCEL = "ORDER_CANCEL_TOPIC";
    // RocketMQ Tag
    public static final String TAG_ORDER_CREATED = "ORDER_CREATED";
}