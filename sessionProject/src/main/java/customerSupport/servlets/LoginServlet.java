package customerSupport.servlets;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final Log _log = LogFactory.getLog(LoginServlet.class);

	private static final Map<String, String> userDatabase = new Hashtable<>();
	static {
		userDatabase.put("Nick", "Nick");
		userDatabase.put("Sara", "Sara");
		userDatabase.put("Miki", "Miki");
		userDatabase.put("coniglio", "coniglio");
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		if(_log.isInfoEnabled()){
			_log.info("doGet method called from " + this.getServletName());
		}
		HttpSession session = request.getSession();
		if(request.getParameter("logout") != null){
			session.invalidate();
			response.sendRedirect("login");
			return;
		} else if(session.getAttribute("username") != null){
			response.sendRedirect("tickets");
			return;
		}
		request.setAttribute("loginFailed", false);
		request.getRequestDispatcher("/WEB-INF/jsp/login/login.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		if(_log.isInfoEnabled()){
			_log.info("doPost method called from " + this.getServletName());
		}

		HttpSession session = request.getSession();
		if(session.getAttribute("username") != null){
			response.sendRedirect("tickets");
			return;
		}

		String username = request.getParameter("username");
		String password = request.getParameter("password");
		if(username == null || password == null ||
				!LoginServlet.userDatabase.containsKey(username) || !password.equals(LoginServlet.userDatabase.get(username))){
			request.setAttribute("loginFailed", true);
			request.getRequestDispatcher("/WEB-INF/jsp/login/login.jsp").forward(request, response);
		}else{
			session.setAttribute("username", username);
			request.changeSessionId();
			response.sendRedirect("tickets");
		}
	}
}
