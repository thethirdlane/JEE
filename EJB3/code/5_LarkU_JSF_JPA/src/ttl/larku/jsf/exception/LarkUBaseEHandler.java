package ttl.larku.jsf.exception;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import javax.ejb.EJBException;
import javax.el.ELException;
import javax.faces.FacesException;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ExceptionQueuedEvent;

import ttl.larku.service.exception.LarkUException;

public class LarkUBaseEHandler extends ExceptionHandlerWrapper {

	private final ExceptionHandler wrapped;

	public LarkUBaseEHandler(final ExceptionHandler wrapped) {
		this.wrapped = wrapped;
	}

	@Override
	public ExceptionHandler getWrapped() {
		return wrapped;
	}

	@Override
	public void handle() throws FacesException {
		Iterable<ExceptionQueuedEvent> exes = getUnhandledExceptionQueuedEvents();
		Iterator<ExceptionQueuedEvent> it = exes.iterator();
		StringBuffer buffer = new StringBuffer(100);
		final FacesContext facesContext = FacesContext.getCurrentInstance();
		final ExternalContext externalContext = facesContext
				.getExternalContext();
		final Map<String, Object> requestMap = externalContext.getRequestMap();
		while (it.hasNext()) {
			ExceptionQueuedEvent eqe = it.next();
			Throwable th = eqe.getContext().getException();
			while ((th instanceof FacesException || th instanceof EJBException || th instanceof ELException)
					&& th.getCause() != null) {
				th = th.getCause();
			}
			String message = th.getMessage();
			buffer.append("Exception of type " + th.getClass()
					+ ", errorMessage is "
					+ message + "<br/>");
			it.remove();
		}

		requestMap.put("ExceptionMessage", buffer.toString());

		try {
			if (buffer.length() > 0) {
				externalContext.dispatch("/unexpectedError.jsp");
				facesContext.responseComplete();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (RuntimeException e) {
			e.printStackTrace();
		}

	}

}
