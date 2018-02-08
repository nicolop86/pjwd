package filtersChapter.beginner.listeners;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import filtersChapter.beginner.filters.CompressionFilter;
import filtersChapter.beginner.filters.RequestLogFilter;

public class Configurator implements ServletContextListener {

	private final static Log _log = LogFactory.getLog(Configurator.class);

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		if(_log.isInfoEnabled()){
			_log.info("Initializing context through " + this.getClass().getName() + ".");
		}
		ServletContext context = sce.getServletContext();
		FilterRegistration.Dynamic registration = context.addFilter("requestLogFilter", new RequestLogFilter());
		registration.addMappingForUrlPatterns(null, false, "/*");
		registration = context.addFilter("compressionFilter", new CompressionFilter());
		registration.setAsyncSupported(true);
		registration.addMappingForUrlPatterns(null, false, "/*");
		if(_log.isInfoEnabled()){
			_log.info("Context was initialized.");
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		if(_log.isInfoEnabled()){
			_log.info("Destroying " + this.getClass().getName() + ".");
		}
	}

}
