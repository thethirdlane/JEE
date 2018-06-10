package ttl.cdi.interceptors;

import java.io.Serializable;
import java.util.Date;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

@Logged @Interceptor
public class PerfInterceptor implements Serializable {
    
    @AroundInvoke
    public Object logCall(InvocationContext ctx) throws Exception {
	Object result = null;
	try {
		long start = System.currentTimeMillis();
	    result = ctx.proceed();
	    long end = System.currentTimeMillis();
	    System.out.println("PerfInterceptor: " + ctx.getMethod() + " took " + (end - start) + " ms. At " + new Date() + " with result " + result);
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return result;
    }

}
