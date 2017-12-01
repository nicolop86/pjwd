package simpleServlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ServletInitParamServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final Log _log = LogFactory.getLog(SimpleServlet.class);

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if(_log.isInfoEnabled()){
			_log.info("Servlet " + this.getServletName() + " is responding to doGet.");
		}
		ServletConfig ctx = this.getServletConfig();
		PrintWriter writer = response.getWriter();
		writer.append("database: ").append(ctx.getInitParameter("db.name"))
		.append(", server: ").append(ctx.getInitParameter("db.ip"));
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException{
		if(_log.isInfoEnabled()){
			_log.info("Servlet " + this.getServletName() + " is responding to doPost.");
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
