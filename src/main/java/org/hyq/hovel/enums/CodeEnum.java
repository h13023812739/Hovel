package org.hyq.hovel.enums;


public enum CodeEnum {
    /*
    0000  成功
    1000  请求参数错误
    2002  数据库入库异常
    2003  删除失败

    2101  请求参数为空

    9999  其它，自定义错误
    */
    OK("0", "成功"),
    PARAMS_ERROR("1000", "请求参数错误"),
    DATABASE_ERROR("2002", "数据库入库异常"),
    DELETE_ERROR("2003", "删除失败"),
    PARAMS_EMPTY("2101", "请求参数为空"),
    CUSTOM_ERROR("9999", " 其它，自定义错误"),

    SUCCESS ("200", " 请求成功 " ),
    FAILED ("400", "请求失败"),
    REQUEST_METHOD_NOT_SUPPORT("405", "请求方法不支持"),
    NOT_FOUND ("404", "接口不存在"),
    SERVER_ERROR ("500","服务器内部出错"),
    UNAUTHORIZED("401", "暂未登录或token已经过期"),
    VALIDATE_FAILED("4011", "参数检验失败"),
    FORBIDDEN("403", "没有相关权限"),
    BEFORE_EXIST("1010", "前置任务存在"),
    NO_NULL("1001", "口令不能为空"),
    NO_LENGTH_LEGITIMATE("1002", "口令不符合长度要求，长度必须大于等于8小于等于20"),
    CONTAIN_THREE_CLASS("1003", "口令应包括数字、小写字母、大写字母、特殊符号4类中至少3类"),
    CONTAIN_FOUR_CLASS("1004", "口令应包括数字、小写字母、大写字母、特殊符号4类"),
    NO_REPEAT("1005", "口令不能出现3位以上连续重复字母（数字、符号）或连续键盘的字母、数字、特殊字符"),
    NO_COMMONLY_USED("1006", "口令不能使用常用词库"),
    NO_USERNAME_REPEAT("1007", "口令应与用户名无相关性"),
    NO_HISTORY_REPEAT("1008", "口令应与近5次修改历史密码不能重复"),

    CAPTCHA_EXPIRED_ERROR("4001", "验证码已过期,请重新获取验证码"),
    CAPTCHA_ERROR("4002", "验证码不正确"),
    USER_LOCKED("4003", "该账号已被锁定4H"),
    USER_DISABLE("4004", "该账号已被禁用，请联系管理员"),
    ERROR_FOUR("4005", "您的密码已连续输入错误4次，输错5次将锁定账号4小时"),
    PASSWD_ERROR("4006", "用户名或密码错误"),
    USER_CHANGE_ERROR("4007", "切换用户，请刷新页面重新登录"),
    PASSWD_TIME_OVER("4008", "密码已失效，请修改密码");

    private String code;
    private String msg;

    private CodeEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}
