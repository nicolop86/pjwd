package customerSupport.servlets;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import customerSupport.items.Ticket;
import simpleServlet.ServletInitParamServlet;

public class TicketServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final Log _log = LogFactory.getLog(TicketServlet.class);
	private volatile int TICKET_ID_SEQUENCE = 1;
	private Map<Integer, Ticket> ticketDatabase = new LinkedHashMap<>();

	protected void doInit(){
		if(_log.isInfoEnabled()){
			_log.info("Servlet " + this.getServletName() + " has started.");
		}
	}

//	@Override
//	protected void doGet(HttpServletRequest request, HttpServletResponse response)
//			throws ServletException, IOException{
//		if(_log.isInfoEnabled()){
//			_log.info("Servlet " + this.getServletName() + " is responding to doGet.");
//		}
//	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		String action = request.getParameter("action");
		if(action == null)
			action = "list";
		switch(action)
		{
		case "create":
			this.showTicketForm(response);
			break;
		case "view":
			this.viewTicket(request, response);
			break;
		case "download":
			this.downloadAttachment(request, response);
			break;
		case "list":
		default:
			this.listTickets(response);
			break;
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		String action = request.getParameter("action");
		if(action == null)
			action = "list";
		switch(action)
		{
		case "create":
			this.createTicket(request, response);
			break;
		case "list":
		default:
			response.sendRedirect("tickets");
			break;
		}
	}
}