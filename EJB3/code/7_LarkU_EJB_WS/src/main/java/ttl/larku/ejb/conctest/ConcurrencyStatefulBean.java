/**
 *
 * @author kelby
 * @version 1.0
 * @since Dec 30, 2008
 *
 */
package ttl.larku.ejb.conctest;

import javax.ejb.Stateful;
import javax.ejb.Remote;

@Remote
@Stateful
public class ConcurrencyStatefulBean implements ConcurrencyTester {

    private static int nextIndex;
    private int hitCount;
    private String message;

    public ConcurrencyStatefulBean() {
        message = "Stateful: " + ++nextIndex;
    }

    public String getMessage() {
        return message + " hitCount = "  + hitCount++;
    }

    public int getHitCount() {
    	return hitCount;
    }
}
