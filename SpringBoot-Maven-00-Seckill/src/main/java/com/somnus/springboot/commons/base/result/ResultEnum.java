package com.somnus.springboot.commons.base.result;

/**
 * @ClassName: ResultEnum
 * @Description: 异常消息响应枚举
 * @author Somnus
 * @date 2018年8月27日
 */
public enum ResultEnum {
	
	SEC_KILL_MUCH("2","哎呦喂，人也太多了，请稍后！"),
    SEC_KILL_SUCCESS("1","秒杀成功"),
    SEC_KILL_END("0","秒杀结束"),
    REPEAT_KILL("-1","重复秒杀"),
    INNER_ERROR("-2","系统异常"),
    DATE_REWRITE("-3","数据篡改"),

	SUCCESS_000000("000000", "处理成功"),
	ERROR_999999("999999","系统异常"),
	ERROR_300001("300001","系统错误：系统配置文件{0}不存在！"),
	ERROR_300003("300003","系统错误: 报文内容签名与签名字段不符合，报文被篡改！"),
	ERROR_300004("300004","系统错误: 报文签名验证异常！"),
	ERROR_300007("300007","系统错误:解密失败!"),
	ERROR_300008("300008","系统错误:{0}获取密钥失败!"),
	ERROR_301001("301001","报文中，关键字段{0}不可为空！"),
	ERROR_301002("301002","报文中，关键字段{0}必须为数字或金额！"),
	ERROR_301003("301003","报文中，关键字段{0}格式必须为{1}！"),
	ERROR_301004("301004","报文中，{0}元素个数超过限制，最多{1}个！"),
	ERROR_301005("301005","报文中，{0}元素长度不正确！"),
	ERROR_301006("301006","报文中，当前报文交易代码未指定！"),
	ERROR_301007("301007","数据错误: 当前交易未开通使用权限！"),
	ERROR_301008("301008","报文中，字段{0}无效！"),
	ERROR_302002("302002","交易异常:{0}账户信息无效"),
	ERROR_302003("302003","交易异常:记账服务调用异常"),
	ERROR_302004("302004","交易异常：{0}交易流水已经生成台帐");

	public final String code;
    
    public final String msg;

    private ResultEnum(String code,String msg) {
        this.code = code;
        this.msg = msg;
    }
    
    public String getCode() {
        return code;
    }
    
    public String getMsg() {
        return msg;
    }
    
    /**
     * 通过枚举<code>code</code>获得枚举
     * 
     * @param code
     * @return
     */
    public static ResultEnum getByCode(String code) {
        for (ResultEnum genum : values()) {
            if (genum.getCode().equals(code)) {
                return genum;
            }
        }
        return null;
    }
    
    public static ResultEnum nameOf(String name){
    	ResultEnum genum = null;
		if (name != null){
			for (ResultEnum type : ResultEnum.values()) {
				if (type.name().equalsIgnoreCase(name))
					genum = type;
			}
		}
		return genum;
	}
    
    public static ResultEnum msgOf(String msg){
    	ResultEnum genum = null;
		if (msg != null){
			for (ResultEnum type : ResultEnum.values()) {
				if (type.msg.equalsIgnoreCase(msg))
					genum = type;
			}
		}
		return genum;
	}

}
