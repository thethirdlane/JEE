package ttl.larku.ejb.play;

import javax.ejb.AccessTimeout;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Remote;
import javax.ejb.Singleton;

@Singleton
@Remote
public class AccessTimeouterImpl implements AccessTimeOuter {
	
	@Lock(LockType.WRITE)
	@Override
	@AccessTimeout(2000)
	public String doStuff(String stuff) {
		System.out.println("Timeouter going to sleep for 1 minute");
		try {
			Thread.sleep(10000);
		}
		catch(InterruptedException e) {
		}
		System.out.println("Timeouter finally woke up");
		
		return "Processed " + stuff;
	}

}
