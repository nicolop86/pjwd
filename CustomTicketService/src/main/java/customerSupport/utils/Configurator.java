package customerSupport.utils;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Configurator implements ServletContextListener {

	private final static Log _log = LogFactory.getLog(Configurator.class);
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		if(_log.isInfoEnabled()){
			_log.info("Initializing context through " + this.getClass().getName() + ".");
		}
		ServletContext context = sce.getServletContext();
		FilterRegistration.Dynamic registration = context.addFilter("authenticationFilter", new AuthenticationFilter());
		registration.setAsyncSupported(true);
		registration.addMappingForUrlPatterns(null, false, "/sessions", "/tickets");
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		if(_log.isInfoEnabled()){
			_log.info("Destroying " + this.getClass().getName() + ".");
		}
	}

}
