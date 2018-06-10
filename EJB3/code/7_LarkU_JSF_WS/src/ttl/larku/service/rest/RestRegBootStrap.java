package ttl.larku.service.rest;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.ManagedBean;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import ttl.larku.service.ejb.RegistrationFacade;

@ApplicationPath("/restreg")
public class RestRegBootStrap extends Application{

	@Override
	public Set<Object> getSingletons() {
		
		Set<Object> set = new HashSet<Object>();
		try {
			Context ctx = new InitialContext();
			//For glassfish
			RegistrationFacade regFacade = (RegistrationFacade) ctx.lookup("java:global/7_LarkU_EAR/7_LarkU_EJB_WS/RegistrationFacadeImpl!ttl.larku.service.ejb.RegistrationFacade");

			//For WebLogic/IntelliJ - different names just because of the artifact builder
			//RegistrationFacade regFacade = (RegistrationFacade) ctx.lookup("java:global/7_LarkU_EE/7/RegistrationFacadeImpl!ttl.larku.service.ejb.RegistrationFacade");

			
			set.add(new RestRegistration(regFacade));
		}
		catch(Exception e) {e.printStackTrace(); }
		
		return set;

	}

}
