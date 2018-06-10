package ttl.larku.service.ejb.interceptor;

import java.lang.reflect.Method;
import java.util.Date;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

public class LoggingInterceptor {

	@AroundInvoke
	public Object logCall(InvocationContext ic) throws Exception {
		Object result = null;

		try {
			result = ic.proceed();
		} catch (Exception e) {
			System.out.println("Interceptor caught Exception ");
			e.printStackTrace();
			throw e;
		}

		Method method = ic.getMethod();
		System.out.println("Method " + method + " run at " + new Date());

		return result;
	}

}
