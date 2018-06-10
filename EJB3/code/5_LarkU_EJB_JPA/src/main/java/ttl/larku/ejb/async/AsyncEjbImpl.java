package ttl.larku.ejb.async;

import java.util.concurrent.Future;

import javax.ejb.AsyncResult;
import javax.ejb.Asynchronous;
import javax.ejb.Stateless;

@Stateless
public class AsyncEjbImpl implements AsyncEjb {
	
	/* (non-Javadoc)
	 * @see ttl.larku.ejb.async.AsyncEjb#longCalculation(int)
	 */
	@Override
	@Asynchronous
	public Future<Double> longCalculation(int i) {
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		return new AsyncResult<Double>(32.6);
	}

}
