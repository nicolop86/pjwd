package filtersChapter.beginner.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class NonAsyncServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private final static Log _log = LogFactory.getLog(NonAsyncServlet.class);
	
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
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException{
		if(_log.isInfoEnabled()){
			_log.info("I'm in doGet method of Servlet " + this.getServletName() + ".");
		}
		req.getRequestDispatcher("WEB-INF/jsp/view/nonAsync.jsp").forward(req, resp);
		if(_log.isInfoEnabled()){
			_log.info("Exiting doGet method of Servlet " + this.getServletName() + ".");
		}
	}
}
