package com.stodger.common;

import sun.security.util.Password;

/**
 * @author Stodger
 * @version V1.0
 * @date 2019-06-29 16:39
 */
public class Const {

    public enum VoteUserEnum {

        /**
         * 验证 消息
         */
        /* ADMIN(-1,"admin"),*/
        ORDINARY(-1, "普通用户"),
        CODE_ERROR(0, "验证码错误"),
        USER_ERROR(1, "用户名不存在或密码错误"),
        USER_SUCCESS(2, "ok"),
        REGISTER_ERROR(3, "注册失败"),
        USERNAME_ERROR(4, "用户名已存在"),
        PASSWORD_SUCCESS(5, "SUCCESS"),
        VOTE_YES(6, "能投票"),
        VOTE_NO(7, "不能投票"),
        INIT_SUCCESS(8,"初始成功"),
        INIT_ERROR(9,"初始失败");

        private int code;
        private String msg;

        VoteUserEnum(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
    }
}
