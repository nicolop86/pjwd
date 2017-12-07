package sessionActivity;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ActivityServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private final static Log _log = LogFactory.getLog(ActivityServlet.class);

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		if(_log.isInfoEnabled()){
			_log.info("doGet method called");
		}
		this.recordSessionActivity(request);
		this.viewSessionActivity(request, response);
	}

	private void viewSessionActivity(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		if(_log.isInfoEnabled()){
			_log.info("viewSessionActivity method called");
		}
		request.getRequestDispatcher("/WEB-INF/sessionActivity/view.jsp")
		.forward(request, response);
	}

	private void recordSessionActivity(HttpServletRequest request){
		if(_log.isInfoEnabled()){
			_log.info("recordSessionActivity method called");
		}
		HttpSession session = request.getSession();
		if(session.getAttribute("activity") == null)
			session.setAttribute("activity", new Vector<PageVisit>());
		
		@SuppressWarnings("unchecked")
		Vector<PageVisit> visits = (Vector<PageVisit>)session.getAttribute("activity");
		if(!visits.isEmpty()){
			PageVisit last = visits.lastElement();
			last.setLeftTimestamp(System.currentTimeMillis());
		}
		PageVisit now = new PageVisit();
		now.setEnteredTimestamp(System.currentTimeMillis());
		if(request.getQueryString() == null)
			now.setRequest(request.getRequestURL().toString());
		else
			now.setRequest(request.getRequestURL()+"?"+request.getQueryString());
		try{
			now.setIpAddress(InetAddress.getByName(request.getRemoteAddr()));
		}catch (UnknownHostException e){
			e.printStackTrace();
		}
		visits.add(now);
	}

}