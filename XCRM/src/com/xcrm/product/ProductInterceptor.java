package com.xcrm.product;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;

/**
 * 
 * @author jzhang12
 *
 */
public class ProductInterceptor implements Interceptor {
	
	public void intercept(Invocation inv) {
		System.out.println("Before invoking " + inv.getActionKey());
		inv.invoke();
		System.out.println("After invoking " + inv.getActionKey());
	}
}
