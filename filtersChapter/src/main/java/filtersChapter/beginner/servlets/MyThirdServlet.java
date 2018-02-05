package filtersChapter.beginner.servlets;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MyThirdServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private final static Log _log = LogFactory.getLog(MyThirdServlet.class);
	
	@Override
	public void init(){
		if(_log.isInfoEnabled()){
			_log.info("Servlet " + this.getServletName() + " is starting.");
		}
	}
	
	@Override
	public void destroy(){
		if(_log.isInfoEnabled()){
			_log.info("Servlet " + this.getServletName() + " is going to be destroyed.");
		}
	}

	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		if(_log.isInfoEnabled()){
			_log.info("I'm in doGet method of Servlet " + this.getServletName() + ".");
		}
		resp.getWriter().write(this.getServletName());
		if(_log.isInfoEnabled()){
			_log.info("Exiting doGet method of Servlet " + this.getServletName() + ".");
		}
	}
}