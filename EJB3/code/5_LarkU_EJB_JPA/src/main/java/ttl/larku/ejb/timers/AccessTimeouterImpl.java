package ttl.larku.ejb.timers;

import javax.ejb.AccessTimeout;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Remote;
import javax.ejb.Singleton;
import javax.interceptor.Interceptors;

import ttl.cdi.interceptors.LogInterceptor;

@Singleton
@Remote
@Lock(LockType.READ)
@Interceptors({LogInterceptor.class})
public class AccessTimeouterImpl implements AccessTimeOuter {
	
	@Lock(LockType.WRITE)
	@Override
	@AccessTimeout(2000)
	public String doStuff(String stuff) {
		int sleepTime = 10000;
		System.out.printf("Timeouter going to sleep for $d ms%s", sleepTime);
		try {
			Thread.sleep(sleepTime);
		}
		catch(InterruptedException e) {
		}
		System.out.println("Timeouter finally woke up");
		
		return "Processed " + stuff;
	}
	
	public String doOtherStuff(String stuff) {
		return "Did something";
	}

}
