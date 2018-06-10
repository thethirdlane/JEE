/**
 *
 * @author kelby
 * @version 1.0
 * @since Dec 30, 2008
 *
 */
package ttl.larku.ejb.conctest;

import javax.ejb.Remote;
import javax.ejb.Singleton;

@Remote
@Singleton
public class ConcurrencySingletonBean implements ConcurrencyTester {

    private static int nextIndex;
    private int hitCount;
    private String message;

    public ConcurrencySingletonBean() {
        message = "Singleton: " + ++nextIndex;
    }

    public String getMessage() {
        return message + " hitCount = "  + hitCount++;
    }

    public int getHitCount() {
    	return hitCount;
    }
}
