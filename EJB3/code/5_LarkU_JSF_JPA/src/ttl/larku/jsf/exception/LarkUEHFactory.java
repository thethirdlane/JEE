package ttl.larku.jsf.exception;

import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerFactory;

public class LarkUEHFactory extends ExceptionHandlerFactory {

	private final ExceptionHandlerFactory parent;

	public LarkUEHFactory( final ExceptionHandlerFactory parent) {
		this.parent = parent;
	}

	@Override
	public ExceptionHandler getExceptionHandler() {
		return new LarkUBaseEHandler(this.parent.getExceptionHandler());
	}
}