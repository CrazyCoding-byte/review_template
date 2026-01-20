package com.yzx.crazycodingbytecommon.entity;

/**
 * 库存模块常量类
 * 统一管理库存相关的业务类型、MQ主题、状态码等，避免硬编码
 */
public class InventoryConstant {

    // ====================== 库存业务类型（对应LocalMessage的businessType字段） ======================
    /** 库存锁定（下单时锁定库存） */
    public static final String BUSINESS_TYPE_INVENTORY_LOCK = "INVENTORY_LOCK";
    /** 库存解锁（订单取消/超时/锁定失败时解锁） */
    public static final String BUSINESS_TYPE_INVENTORY_UNLOCK = "INVENTORY_UNLOCK";
    /** 库存扣减（支付成功后实际扣减库存） */
    public static final String BUSINESS_TYPE_INVENTORY_DEDUCT = "INVENTORY_DEDUCT";
    /** 库存恢复（退款/退货时恢复库存） */
    public static final String BUSINESS_TYPE_INVENTORY_RECOVER = "INVENTORY_RECOVER";

    // ====================== 库存MQ主题常量 ======================
    /** 库存锁定消息主题 */
    public static final String TOPIC_INVENTORY_LOCK = "topic_inventory_lock";
    /** 库存解锁消息主题 */
    public static final String TOPIC_INVENTORY_UNLOCK = "topic_inventory_unlock";
    /** 库存扣减消息主题 */
    public static final String TOPIC_INVENTORY_DEDUCT = "topic_inventory_deduct";
    /** 库存恢复消息主题 */
    public static final String TOPIC_INVENTORY_RECOVER = "topic_inventory_recover";
    /** 库存操作结果回调主题 */
    public static final String TOPIC_INVENTORY_OP_RESULT = "topic_inventory_op_result";

    // ====================== 库存锁定状态常量 ======================
    /** 库存未锁定 */
    public static final Integer LOCK_STATUS_UNLOCKED = 0;
    /** 库存已锁定 */
    public static final Integer LOCK_STATUS_LOCKED = 1;
    /** 库存锁定中（中间状态） */
    public static final Integer LOCK_STATUS_LOCKING = 2;

    // ====================== 库存操作结果常量 ======================
    /** 操作成功 */
    public static final String OP_RESULT_SUCCESS = "SUCCESS";
    /** 操作失败 */
    public static final String OP_RESULT_FAIL = "FAIL";

    // ====================== 库存重试相关常量 ======================
    /** 库存操作默认最大重试次数 */
    public static final Integer DEFAULT_MAX_RETRY = 3;
    /** 库存操作重试间隔（毫秒） */
    public static final Long RETRY_INTERVAL_MS = 500L;
}