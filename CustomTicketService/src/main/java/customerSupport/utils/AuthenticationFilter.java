package customerSupport.utils;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class AuthenticationFilter implements Filter {

	private final static Log _log = LogFactory.getLog(AuthenticationFilter.class);
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
		HttpSession session = ((HttpServletRequest)request).getSession(false);
        if(session == null || session.getAttribute("username") == null)
            ((HttpServletResponse)response).sendRedirect("login");
        else
            chain.doFilter(request, response);
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
