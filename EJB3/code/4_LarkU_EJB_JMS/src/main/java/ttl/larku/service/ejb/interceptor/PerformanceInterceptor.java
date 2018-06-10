package ttl.larku.service.ejb.interceptor;

import java.lang.reflect.Method;
import java.util.Date;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

public class PerformanceInterceptor {

	@AroundInvoke
	public Object logCall(InvocationContext ic) throws Exception {
		
		long start = System.currentTimeMillis();
		
		Object result = null;

		try {
			result = ic.proceed();
		} catch (Exception e) {
			System.out.println("Interceptor caught Exception ");
			e.printStackTrace();
			throw e;
		}

		long end = System.currentTimeMillis();
		Method method = ic.getMethod();
		System.out.println("Method " + method + " tool " + (end - start) + " ms");

		return result;
	}

}
