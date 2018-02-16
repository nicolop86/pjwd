package customerSupport.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import customerSupport.chatUtils.ChatEndpoint;

public class ChatServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final Log _log = LogFactory.getLog(ChatServlet.class);
	
	@Override
	public void init() {
		if(_log.isInfoEnabled()){
			_log.info("Servlet " + this.getServletName() + " has started.");
		}
	}

	@Override
	public void destroy() {
		if(_log.isInfoEnabled()){
			_log.info("Servlet " + this.getServletName() + " has stopped.");
		}
    }
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException{
		if(_log.isInfoEnabled()){
			_log.info("Servlet " + this.getServletName() + " calling doGet method");
		}
		String action = req.getParameter("action");
        if("list".equals(action)) {
        	if(_log.isInfoEnabled()){
    			_log.info("Available chat sessions");
    		}
            req.setAttribute("sessions", ChatEndpoint.pendingSessions);
            req.getRequestDispatcher("/WEB-INF/jsp/view/chat/list.jsp").forward(req, resp);
        } else {
        	if(_log.isInfoEnabled()){
    			_log.info("No chat sessions available");
    		}
            resp.sendRedirect("tickets");
        }
	}
	
	@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
		if(_log.isInfoEnabled()){
			_log.info("Servlet " + this.getServletName() + " calling doPost method");
		}
        response.setHeader("Expires", "Thu, 1 Jan 1970 12:00:00 GMT");
        response.setHeader("Cache-Control","max-age=0, must-revalidate, no-cache");

        String action = request.getParameter("action");
        String view = null;
        switch(action){
            case "new":
            	if(_log.isInfoEnabled()){
        			_log.info("New chat session is starting");
        		}
                request.setAttribute("chatSessionId", 0);
                view = "chat";
                break;
            case "join":
                String id = request.getParameter("chatSessionId");
                if(_log.isInfoEnabled()){
        			_log.info("Joining chat session with id: " + id);
        		}
                if(id == null || !NumberUtils.isDigits(id)){
                    response.sendRedirect("chat?list");
                }else{
                    request.setAttribute("chatSessionId", Long.parseLong(id));
                    view = "chat";
                }
                break;
            default:
            	if(_log.isInfoEnabled()){
        			_log.info("Default case, redirecting to /tickets");
        		}
                response.sendRedirect("tickets");
                break;
        }

        if(view != null)
        	if(_log.isInfoEnabled()){
    			_log.info("Post is forwarding with view: " + view);
    		}
            request.getRequestDispatcher("/WEB-INF/jsp/view/chat/" + view + ".jsp").forward(request, response);
    }
}
