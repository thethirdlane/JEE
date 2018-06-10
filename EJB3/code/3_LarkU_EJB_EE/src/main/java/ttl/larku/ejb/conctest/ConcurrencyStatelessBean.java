
package ttl.larku.ejb.conctest;

import javax.ejb.Remote;
import javax.ejb.Stateless;

//@Stateless(name="HelloWorldStatelessBean", mappedName="ejb3/HelloWorldStatelessBean")
@Remote
@Stateless
public class ConcurrencyStatelessBean implements ConcurrencyTester { //HelloWorld {
    private static int nextIndex;
    private String message;
    private int hitCount;

    public ConcurrencyStatelessBean() {
        message = "ConcurrencyStateless "+ ++nextIndex;
    }

    public String getMessage() {
        return message + " hitCount = " + hitCount++;
    }
    
    public int getHitCount() {
    	return hitCount;
    }
}
