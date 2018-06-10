package ttl.larku.service.ejb.interceptor;

import java.lang.reflect.Method;
import java.security.Principal;
import java.util.Date;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

import ttl.larku.domain.Student;
import ttl.larku.service.ejb.StudentService;

public class SecurityInterceptor {

	@Resource
	private SessionContext sessionContext;
	
	@EJB(beanName = "LocalStudentServiceImpl")
	private StudentService studentService;

	@AroundInvoke
	public Object gateKeeper(InvocationContext ic) throws Exception {
		Object result = null;
		Method method = ic.getMethod();
		if (method.getName().equals("getStudent")) {
			Object [] params = ic.getParameters();
			Integer id = (Integer)params[0];
			Student student = studentService.getStudent(id);
			if(student == null) {
				throw new RuntimeException("SecInt: No student found with id + " + id);
			}
			if (!sessionContext.isCallerInRole("admin")) {
				Principal p = sessionContext.getCallerPrincipal();
				String userName = p.getName();
				if (!student.getName().equalsIgnoreCase(userName)) {
					throw new RuntimeException(
							"Sorry, you can only get your own information");
				}
			}

		}
		try {
			result = ic.proceed();
		} catch (Exception e) {
			System.out.println("Interceptor caught Exception ");
			e.printStackTrace();
			throw e;
		}

		return result;
	}

}
