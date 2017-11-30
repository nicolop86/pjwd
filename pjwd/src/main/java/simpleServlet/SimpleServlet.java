package simpleServlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SimpleServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	private static final Log _log = LogFactory.getLog(SimpleServlet.class);
	private static final String DEFAULT_USER = "Guest";

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException{
//		response.getWriter().print("Hello!");
		if(_log.isInfoEnabled()){
			_log.info("Servlet " + this.getServletName() + " is responding to doGet.");
		}
		String user = request.getParameter("user");
		if(user == null)
			user = DEFAULT_USER;
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		PrintWriter writer = response.getWriter();
		writer.append("<!DOCTYPE html>\r\n")
		.append("<html>\r\n")
		.append(" <head>\r\n")
		.append(" <title>Hello User Application</title>\r\n")
		.append(" </head>\r\n")
		.append(" <body>\r\n")
		.append(" Hello, ").append(user).append("!<br/><br/>\r\n")
		.append(" <form action=\"welcome\" method=\"POST\">\r\n")
		.append(" Enter your name:<br/>\r\n")
		.append(" <input type=\"text\" name=\"user\"/><br/>\r\n")
		.append(" <input type=\"submit\" value=\"Submit\"/>\r\n")
		.append(" </form>\r\n")
		.append(" </body>\r\n")
		.append("</html>\r\n");
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException{
		if(_log.isInfoEnabled()){
			_log.info("Servlet " + this.getServletName() + " is responding to doPost.");
		}
		this.doGet(request, response);
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