package usingJsp;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class HelloWorld extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Log _log = LogFactory.getLog(HelloWorld.class);

	public void init(){
		if(_log.isInfoEnabled()){
			_log.info("Servlet " + this.getServletName() + " has started.");
		}
	}
	
//	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
//		String ctx = request.getContextPath();
//		request.getRequestDispatcher(ctx+request.getPathInfo()).forward(request, response);
//	}
//	
	public void destroy(){
		if(_log.isInfoEnabled()){
			_log.info("Servlet " + this.getServletName() + " has stopped.");
		}
	}
}
