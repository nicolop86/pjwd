package filtersChapter.beginner.filters;

import java.io.IOException;
import java.time.Instant;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.time.StopWatch;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class RequestLogFilter implements Filter {

	private final static Log _log = LogFactory.getLog(RequestLogFilter.class);
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
		Instant time = Instant.now();
		StopWatch timer = new StopWatch();
		try {
			timer.start();
			chain.doFilter(request, response);
		} finally {
			timer.stop();
			HttpServletRequest in = (HttpServletRequest)request;
			HttpServletResponse out = (HttpServletResponse)response;
			String length = out.getHeader("Content-Length");
			if(length == null || length.length() == 0)
				length = "-";
			if(_log.isInfoEnabled()){
				_log.info(in.getRemoteAddr() + " - - [" + time + "] \"" + in.getMethod() + " " + in.getRequestURI() + " " +
						in.getProtocol() + "\" " + out.getStatus() + " " + length +	" " + timer);
			}
		}
			if(_log.isInfoEnabled()){
				_log.info("Exiting " + this.name + ".doFilter().");
			}
		}

		@Override
		public void destroy() {
			if(_log.isInfoEnabled()){
				_log.info("... destroying filter " + this.name + ".");
			}
		}

	}
