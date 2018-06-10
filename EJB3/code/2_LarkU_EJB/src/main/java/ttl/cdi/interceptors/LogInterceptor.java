package ttl.cdi.interceptors;

import java.io.Serializable;
import java.util.Date;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

@Logged @Interceptor
public class LogInterceptor implements Serializable {
    
    @AroundInvoke
    public Object logCall(InvocationContext ctx) {
	Object result = null;
	try {
	    result = ctx.proceed();
	    System.out.println("LogInterceptor: " + ctx.getMethod() + " finished at " + new Date() + " with result " + result);
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return result;
    }

}
