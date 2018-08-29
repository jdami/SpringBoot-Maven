package com.somnus.springboot.commons.base.result;

import com.alibaba.fastjson.JSONObject;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

import java.io.Serializable;

import org.springframework.util.StringUtils;

/**
 * @ClassName: LogicResult
 * @Description: 响应结果类
 * @author Somnus
 * @date 2018年8月24日
 * @param <T>
 */
@Data
@Builder
public class LogicResult implements Serializable {
	
    private static final long serialVersionUID = 5472588435205050617L;
    
    @Tolerate
    public LogicResult() {}

    /**
     * 响应码
     */
    @Builder.Default
    private String resCode = LogicResult.FAIL_CODE;
    /**
     * 响应消息
     */
    private String resMsg;
    /**
     * 处理是否成功
     */
    private boolean success;
    /**
     * 响应数据
     */
    private Object data;
    /**
     * 成功
     */
    public static final String SUCCESS_CODE = "1";
    /**
     * 失败
     */
    public static final String FAIL_CODE = "0";
    /**
     * 异常
     */
    public static final String ERROR_CODE = "-1";

    /**
     * 成功结果响应
     */
    public LogicResult complete() {
        this.success = true;
        this.resCode = SUCCESS_CODE;
        return this;
    }

    /**
     * 成功结果响应
     *
     * @param data
     */
    public LogicResult complete(Object data) {
        this.success = true;
        this.resCode = SUCCESS_CODE;
        this.data = data;
        return this;
    }
    
    /**
     * 成功结果响应
     *
     * @param resultEnum
     */
    public LogicResult complete(ResultEnum resultEnum) {
        this.success = true;
        this.resCode = SUCCESS_CODE;
        this.data = resultEnum.getMsg();
        return this;
    }

    /**
     * 失败结果响应
     *
     * @param failMsg
     */
    public LogicResult fail(String failMsg) {
        this.success = false;
        this.resCode = FAIL_CODE;
        this.resMsg = StringUtils.isEmpty(failMsg) ? "请求处理失败!" : failMsg;
        return this;
    }

    /**
     * 异常结果响应
     *
     * @param errorMsg
     */
    public LogicResult error(String errorMsg) {
        this.success = false;
        this.resCode = ERROR_CODE;
        this.resMsg = StringUtils.isEmpty(errorMsg) ? "请求处理异常!" : errorMsg;
        return this;
    }

    /**
     * 未登录响应
     */
	public LogicResult noLogin(Object... redirectUrl) {
        this.success = false;
        this.resCode = "NO-LOGIN";
        this.resMsg = "登录已超时或尚未登录!";
        this.data = redirectUrl != null && redirectUrl.length == 1 ? redirectUrl[0] : null;
        return this;
    }

    /**
     * 异常结果响应
     */
    public String toJsonString() {
        return JSONObject.toJSONString(this);
    }
}
