package com.somnus.springboot;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

public class BundleUtil {
	/**
	 * MESSAGE资源文件名
	 */
	private static final String CONFIG_FILE = "application";
	
	/**
	 * resource文件实际对象
	 */
	private static ResourceBundle bundle = null;

	/**
	 * 获取资源文件，只加载一次
	 */
	static {
		Locale locale = Locale.CHINA;

		bundle = ResourceBundle.getBundle(CONFIG_FILE, locale);
		
		if (bundle == null) {
			// 报错，系统文件不存在！
			throw new RuntimeException(String.format("系统错误：系统配置文件[%s]不存在", CONFIG_FILE));
		}
	}

    /**
     * @param key
     * @return
     */
	public static String getString(String key){
		
		if(bundle.containsKey(key)){
			
			return bundle.getString(key).trim();
			
		}
		return StringUtils.EMPTY;
	}

    /**
     * @param key
     * @param args
     * @return
     */
	public static String getString(String key, Object... args){
		
		if(bundle.containsKey(key)){
			
			String value =  bundle.getString(key).trim();
			
			return MessageFormat.format(value, args);
			
		}
		
		return StringUtils.EMPTY;
	}

    /**
     * @param key
     * @param param
     * @return
     */
	public static String getString(String key,String param){
		return getString(key, new Object[]{param});
	}
	
	// 根据key读取value
    @Test
	public void readValue(){
    	System.out.println(BundleUtil.getString("api.key"));
    	System.out.println(BundleUtil.getString("100001","sign"));
	}
    
	// 读取properties的全部信息
    @Test
	public void readAllProperties(){
    	for(String key:bundle.keySet()){
            String value = bundle.getString(key);
            System.out.println(key +"="+value);
        }
	}
}
