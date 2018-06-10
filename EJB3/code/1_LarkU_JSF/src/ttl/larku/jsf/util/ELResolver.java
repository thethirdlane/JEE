package ttl.larku.jsf.util;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import javax.faces.application.Application;
import javax.faces.context.FacesContext;

public class ELResolver {

	public static <T> T findObject(String expression, Class<T> type) {
		FacesContext fCtx = FacesContext.getCurrentInstance();
		ELContext eCtx = fCtx.getELContext();

		Application a = fCtx.getApplication();
		ExpressionFactory ef = a.getExpressionFactory();
		ValueExpression va = ef.createValueExpression(eCtx, "#{" + expression
				+ "}", type);

		@SuppressWarnings("unchecked")
		T result = (T) va.getValue(eCtx);

		return result;
	}

}