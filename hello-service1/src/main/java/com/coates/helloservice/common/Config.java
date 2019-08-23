package com.coates.helloservice.common;

/**
 * 积分系统通用常量配置
 *
 * @author huang_kangjie
 * @create 2018-06-09 10:08
 **/
public class Config {


    /**
     * 的缓存队列。 接收到的消息后首先被放到该队列里然后等待被上层业务代码处理
     */
    public static final int _CACHE_SIZE = 10000 * 5;

    /**
     * 存放队列最大等待时间
     */
    public static final int WAIT_SECONDS = 3;

    /**
     * 超时时间间隔
     */
    public static final long TIMEOUT_INTERVAL = 3 * 60 * 1000;




    /**
     * 应用缓存
     */
    public static final String SCORE_APPS = "CREDIT_CENTER_APPS";

    /**
     * 缓存时间
     */
    public static final int SCORE_APPS_EXPIRE_DAY = 30;

    /**
     * 消费要检查余额，同步操作
     */
    public static final int CONSUME_CHECK = 0;

    /**
     * 消费不检查余额，异步操作
     */
    public static final int CONSUME_NO_CHECK = 1;

    /**
     * 内部
     */
    public static final int SOURCE_INNER = 0;

    /**
     * 外部
     */
    public static final int SOURCE_THIRD = 1;

    /**
     * 非商城操作积分
     */
    public static final int NOT_SOURCE_MALL = 0;

    /**
     * 是商城操作积分
     */
    public static final int IS_SOURCE_MALL = 1;

    /**
     * 用户消费锁
     */
    public static final String USER_CONSUME_LOCK_PREFIX = "CREDIT_CENTER_USER_CONSUME_LOCK_";

    /**
     * 用户消费锁 锁的过期时间， 单位秒
     */
    public static final int USER_CONSUME_LOCK_EXPIRE = 10 * 1000;

    /**
     * 用户消费锁 如果锁被占有，则间隔该时间再次重新去获取锁
     */
    public static final int TRY_GET_LOCK_INTERVAL = 100;

    /**
     * 积分token缓存key
     */
    public static final String TOKEN_PREFIX = "CREDIT_CENTER_TOKEN_";

    /**
     * 积分token缓存key , 过期时间为一个月
     */
    public static final int TOKEN_TIMEOUT = 30;

    /**
     * 用户调整积分权限
     */
    public static final String USER_ROLE_CACHE_KEY_PREFIX = "user_role_cache_";
    /**
     * 合作商ID
     */
    public static final String appId = "DE9AC16589964B12BBE78E35C1F18439";

    /**
     * 积分权限判断
     */
    public static final Long ROLEID = 99999999L;
    /**
     * 合作商私钥
     */
    public static final String appPrivateSecret = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBALqLZw4IwLngEt2/4k9lCrkUdvNj4gCZvdgtZ76J/Yqb4Y2ZnxsvbVe0Cmz1z9tUXBr/8efrwaEQRxtooYHKKchpOJbBUFiGN99AvukNl7TKxz7yAiW2+cni2B6ESjX+9z9Mf0RrUokNyhyMRpgLEy6ZqmDU3qEAp1trQWFnSw7TAgMBAAECgYAb/Rh8QZHxEY6jGMwyz105gbmLMRxSrL6QNdfP/dI9vr+ryDXYFViZhRjfWk5SgSjEoExCWNwXd2LD2QIKgDOebZ5ZjRZDWjYZc86LTxtYdA++th0TdpptlYwFLc/31+ScZj5vJt3+B4mwi1FfR6b5ePYJXIuYYxRZMgymN/iY6QJBAN0wry3Ox7mIzb1wO1YRmZGxCsCdIziMqdNod42dTngVCSCfELvF9QXb0WzR4hMgNUPfLxqzBw262ZL411cx4q0CQQDX5uoDGJT6kkhe4oBE7YB82aB5rDMpRPLdVFlOYfw6aFx46sL/1i5nRws7u1qIpNB2Uu5i2c1FngFTqo2juGd/AkBS7ZzQJNW21vakDr8AXurft6vbWU7KNdq6G1Gvc4nm9aQNafXBIM/jmW2kSfBj0VCHGNc1QGflmudV9hvuYhKlAkEAoa1NLnZZEe87fLqXHYbRQi1ia9sVEOGOEUpZ+kWMMVz3aqE614TXiQV9lwRs/oiVhxYBaEr72hdBnRrKKIEuFwJAQV7xJMwMVG6AQZYGEaxdPKYPUp7NLIlbY+PbM/3buGV2PrYMh9Wt2oqmqkDoQY1+aOjT2iDoW3U/jnBGH6yzlg==";

    /**
     * 合作商公钥
     */
    public static final String appSecretKey = "GS20180731165738337QKpP206h";

    /**
     * 开放平台公钥
     */
    public static final String openPlatformPublickey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCMfUp+MDaEmOQOGKOUWgRXs/2ryS9Q/YQyH+CIjBO1EIz1P3C9jOMA5Q9x5P0xHB/NOl+GGuUtcQaYIkEsk4h3ZkZ/uBXpQmS3DLpdNsdWb0G64PLf33GhAvPKc+yRkp8av8pz+X2WbfjtVZfavw5k8TPRMXKgWCPdgznm7jbVewIDAQAB";

    /**
     * 渠道ID
     */
    public static final String channelId = "100";

    /**
     * 渠道状态为1代表组织易
     */
    public static final String status = "1";

    /**
     * token缓存前缀
     */
    public static final String TOKEN_REDIS_CACHE_KEY_PREFIX = "Token_redis_cache_";

    /**
     * 积分商城消费接口编号
     */
    public static final Long CONSUME_ID = 1L;
    /**
     * 积分商城退款接口编号
     */
    public static final Long REFUND_ID = 2L;
    /**
     * 验证扣减是否合法接口编号
     */
    public static final Long VERIFICATION_ID = 3L;
    /**
     * 积分商城订单结果通知接口编号
     */
    public static final Long NOTIFICATION_ID = 4L;
    /**
     * 查询用户积分总额接口编号
     */
    public static final Long USERSCORE_ID = 5L;

    /**
     * 查询积分兑换接口编号
     */
    public static final Long CONVERSION_ADD_ID = 6L;

    /**
     * 查询积分兑换接口编号
     */
    public static final Long CONVERSION_deduct_ID = 7L;

    /** 预扣款账户状态 启用 */
    public static final Byte SOCRE_APP_STATUS_Enabled = 0;
    /** 预扣款账户状态 禁用*/
    public static final Byte SOCRE_APP_STATUS_FORBIDDEN = 1;
    /** 预扣款账户 没有限制*/
    public static final Long SOCRE_APP_WITHHOLD= -1L;

    /** 第三方应用消费无限制*/
    public static final Integer SOCRE_APP_NON_LIMIT= -1;

}
