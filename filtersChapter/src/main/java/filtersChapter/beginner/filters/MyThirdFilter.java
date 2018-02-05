package filtersChapter.beginner.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MyThirdFilter implements Filter {

private final static Log _log = LogFactory.getLog(MyThirdFilter.class);
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		if(_log.isInfoEnabled()){
			_log.info("Filter " + this.getClass().getName() + " is starting ...");
		}
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		if(_log.isInfoEnabled()){
			_log.info("Starting doFilter method of " + this.getClass().getName() + ".");
		}
		chain.doFilter(request, response);
		if(_log.isInfoEnabled()){
			_log.info("Exiting doFilter method of " + this.getClass().getName() + ".");
		}
	}

	@Override
	public void destroy() {
		if(_log.isInfoEnabled()){
			_log.info("... destroying filter " + this.getClass().getName() + ".");
		}
	}

}
