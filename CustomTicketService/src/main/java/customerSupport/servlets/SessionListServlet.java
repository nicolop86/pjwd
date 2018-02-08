package customerSupport.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import customerSupport.utils.SessionRegistry;

public class SessionListServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	private static final Log _log = LogFactory.getLog(SessionListServlet.class);
	
	protected void doInit(){
		if(_log.isInfoEnabled()){
			_log.info("Servlet " + this.getServletName() + " has started.");
		}
	}
	
	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
		if(_log.isInfoEnabled()){
			_log.info("Servlet " + this.getServletName() + " is responding to doGet.");
		}

        request.setAttribute("timestamp", System.currentTimeMillis());
        request.setAttribute("numberOfSessions", SessionRegistry.getNumberOfSessions());
        request.setAttribute("sessionList", SessionRegistry.getAllSessions());
        request.setAttribute("timestamp", System.currentTimeMillis());
        request.getRequestDispatcher("/WEB-INF/jsp/view/sessions.jsp").forward(request, response);
    }
	
	@Override
	public void destroy(){
		if(_log.isInfoEnabled()){
			_log.info("Servlet " + this.getServletName() + " has stopped.");
		}
	}

}