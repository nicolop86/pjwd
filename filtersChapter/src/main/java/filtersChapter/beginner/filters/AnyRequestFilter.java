package filtersChapter.beginner.filters;

import java.io.IOException;

import javax.servlet.AsyncContext;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class AnyRequestFilter implements Filter {

	private final static Log _log = LogFactory.getLog(AnyRequestFilter.class);
	private String name;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.name = filterConfig.getFilterName();
		if(_log.isInfoEnabled()){
			_log.info("Filter " + this.name + " is starting ...");
		}
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		if(_log.isInfoEnabled()){
			_log.info("Starting doFilter method of " + this.name + ".");
		}
		chain.doFilter(new HttpServletRequestWrapper((HttpServletRequest)request),
				new HttpServletResponseWrapper((HttpServletResponse)response));
		if(request.isAsyncSupported() && request.isAsyncStarted()){
			AsyncContext ctx = request.getAsyncContext();
			if(_log.isInfoEnabled()){
				_log.info("Exiting " + this.name + ".doFilter(), async context holds wrapped request/response = " +
						!ctx.hasOriginalRequestAndResponse());
			}	
		} else {
			if(_log.isInfoEnabled()){
				_log.info("Exiting " + this.name + ".doFilter().");
			}
		}
	}

	@Override
	public void destroy() {
		if(_log.isInfoEnabled()){
			_log.info("... destroying filter " + this.name + ".");
		}
	}

}
