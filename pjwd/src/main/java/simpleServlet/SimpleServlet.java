package simpleServlet;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SimpleServlet extends HttpServlet{
	
	private static final long serialVersionUID = 1L;
	private static final Log _log = LogFactory.getLog(SimpleServlet.class);
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException{
		response.getWriter().print("Hello!");
		if(_log.isInfoEnabled()){
			_log.info("Servlet " + this.getServletName() + " is responding to doGet.");
		}
	}
	
	@Override
	public void init(){
		if(_log.isInfoEnabled()){
			_log.info("Servlet " + this.getServletName() + " has started.");
		}
	}
	
	@Override
	public void destroy(){
		if(_log.isInfoEnabled()){
			_log.info("Servlet " + this.getServletName() + " has stopped.");
		}
	}

}