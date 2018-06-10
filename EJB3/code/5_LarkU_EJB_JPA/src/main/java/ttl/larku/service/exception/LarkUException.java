package ttl.larku.service.exception;

import javax.ejb.ApplicationException;

@SuppressWarnings("serial")
@ApplicationException(rollback=true)
public class LarkUException extends Exception {

	public LarkUException(String message) {
		super(message);
	}
}
