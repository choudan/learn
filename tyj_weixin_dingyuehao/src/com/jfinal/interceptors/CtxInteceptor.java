package com.jfinal.interceptors;
import com.jfinal.aop.Interceptor;
import com.jfinal.core.ActionInvocation;

public class CtxInteceptor implements Interceptor {

	@Override
	public void intercept(ActionInvocation ai) {
		String ctx = ai.getController().getRequest().getSession().getServletContext().getContextPath();
		ai.getController().setAttr("ctx", ctx);
		System.out.println("ctx="+ctx);
		ai.invoke();
	}

}

