package com.coates.helloservice.common;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 
 * <p>Title: AllBean.java </p>
 * <p>Package com.shenpinkj.common.utils </p>
 * <p>Description: TODO(为线程安全开通注入bean)  </p>
 * <p>Company: www.shenpinkj.cn</p> 
 * @author 牟超
 * @date	2017年12月18日上午11:13:51
 * @version 1.0
 */
@Component
public class ApplicationContextHolder implements ApplicationContextAware {

	private static ApplicationContext applicationContext;
	
	/**
	 * 
	 * 创 建 人：牟 超 
	 * 创建时间：2017年12月18日
	 * 方法描述： 获取ApplicationContext
	 * @return 返回一个bean对象
	 */
	public void setApplicationContext(ApplicationContext context) {
		ApplicationContextHolder.applicationContext = context;
	}

	/**
	 * 
	 * 创 建 人：牟 超 
	 * 创建时间：2017年12月18日
	 * 方法描述：这是一个便利的方法，帮助我们快速得到一个BEAN
	 * @param name beanName bean的名字
	 * @return 返回一个bean对象
	 */
	public static Object getBean(String name) {
		return applicationContext.getBean(name);
	}

	/**
	 * 
	 * 创 建 人：牟 超 
	 * 创建时间：2017年12月18日
	 * 方法描述：获取ApplicationContext
	 * @return
	 */
	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}
}