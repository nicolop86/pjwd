package customerSupport.servlets;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import customerSupport.items.Attachment;
import customerSupport.items.Ticket;




public class TicketServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private volatile int TICKET_ID_SEQUENCE = 1;
	private static final Log _log = LogFactory.getLog(TicketServlet.class);
	private Map<Integer, Ticket> ticketDatabase = new LinkedHashMap<>();

	protected void doInit(){
		if(_log.isInfoEnabled()){
			_log.info("Servlet " + this.getServletName() + " has started.");
		}
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		if(_log.isInfoEnabled()){
			_log.info("Servlet " + this.getServletName() + " is responding to doGet.");
		}
		if(request.getSession().getAttribute("username") == null){
			response.sendRedirect("login");
			return;
		}
		String action = request.getParameter("action");
		if(action == null)
			action = "list";
		if(_log.isInfoEnabled()){
			_log.info("action is " + action);
		}
		switch(action){
		case "create":
			this.showTicketForm(request, response);
			break;
		case "view":
			this.viewTicket(request, response);
			break;
		case "download":
			this.downloadAttachment(request, response);
			break;
		case "list":
		default:
			this.listTickets(request, response);
			break;
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		if(_log.isInfoEnabled()){
			_log.info("Servlet " + this.getServletName() + " is responding to doPost.");
		}
		if(request.getSession().getAttribute("username") == null){
			response.sendRedirect("login");
			return;
		}
		String action = request.getParameter("action");
		if(action == null)
			action = "list";
		if(_log.isInfoEnabled()){
			_log.info("action is " + action);
		}
		switch(action){
		case "create":
			this.createTicket(request, response);
			break;
		case "list":
		default:
			response.sendRedirect("tickets");
			break;
		}
	}

	@Override
	public void destroy(){
		if(_log.isInfoEnabled()){
			_log.info("Servlet " + this.getServletName() + " has stopped.");
		}
	}

	private void showTicketForm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		if(_log.isInfoEnabled()){
			_log.info("showTicketForm method has been called.");
		}
		request.getRequestDispatcher("/WEB-INF/jsp/view/ticketForm.jsp").forward(request, response);

	}

	private void viewTicket(HttpServletRequest request,
			HttpServletResponse response)
					throws ServletException, IOException{
		if(_log.isInfoEnabled()){
			_log.info("viewTicket method has been called.");
		}
		String idString = request.getParameter("ticketId");
		Ticket ticket = this.getTicket(idString, response);
		if(ticket == null)
			return;
		request.setAttribute("ticketId", idString);
		request.setAttribute("ticket", ticket);
		request.getRequestDispatcher("/WEB-INF/jsp/view/viewTicket.jsp").forward(request, response);
	}

	private void listTickets(HttpServletRequest request,
			HttpServletResponse response)
					throws ServletException, IOException{
		if(_log.isInfoEnabled()){
			_log.info("listTickets method has been called.");
		}
		request.setAttribute("ticketDatabase", this.ticketDatabase);
		request.getRequestDispatcher("/WEB-INF/jsp/view/listTickets.jsp").forward(request, response);
	}

	private Ticket getTicket(String idString, HttpServletResponse response)
			throws ServletException, IOException{
		if(_log.isInfoEnabled()){
			_log.info("getTicket method has been called.");
		}

		if(idString == null || idString.length() == 0){
			response.sendRedirect("tickets");
			return null;
		}

		try{
			Ticket ticket = this.ticketDatabase.get(Integer.parseInt(idString));
			if(ticket == null){
				response.sendRedirect("tickets");
				return null;
			}
			return ticket;
		}catch(Exception e){
			response.sendRedirect("tickets");
			return null;
		}
	}

	private void downloadAttachment(HttpServletRequest request,
			HttpServletResponse response)
					throws ServletException, IOException{
		if(_log.isInfoEnabled()){
			_log.info("downloadAttachment method has been called.");
		}
		String idString = request.getParameter("ticketId");
		Ticket ticket = this.getTicket(idString, response);
		if(ticket == null)
			return;

		String name = request.getParameter("attachment");
		if(name == null){
			response.sendRedirect("tickets?action=view&ticketId=" + idString);
			return;
		}

		Attachment attachment = ticket.getAttachment(name);
		if(attachment == null){
			response.sendRedirect("tickets?action=view&ticketId=" + idString);
			return;
		}

		response.setHeader("Content-Disposition",
				"attachment; filename=" + attachment.getName());
		response.setContentType("application/octet-stream");

		byte[] buffer = new byte[4096];
		InputStream in = new ByteArrayInputStream(attachment.getContents());
		int length;
		ServletOutputStream stream = response.getOutputStream();
		while((length=in.read(buffer))>0){
			stream.write(buffer, 0, length);
		}
		in.close();
		stream.flush();
	}

	private void createTicket(HttpServletRequest request,
			HttpServletResponse response)
					throws ServletException, IOException{
		if(_log.isInfoEnabled()){
			_log.info("createTicket method has been called.");
		}
		Ticket ticket = new Ticket();
		ticket.setCustomerName((String)request.getSession().getAttribute("username"));
		ticket.setSubject(request.getParameter("subject"));
		ticket.setBody(request.getParameter("body"));

		Part filePart = request.getPart("file1");
		if(filePart != null && filePart.getSize() > 0)
		{
			Attachment attachment = this.processAttachment(filePart);
			if(attachment != null)
				ticket.addAttachment(attachment);
		}

		int id;
		synchronized(this)
		{
			id = this.TICKET_ID_SEQUENCE++;
			this.ticketDatabase.put(id, ticket);
		}

		response.sendRedirect("tickets?action=view&ticketId=" + id);
	}

	private Attachment processAttachment(Part filePart)
			throws IOException{
		if(_log.isInfoEnabled()){
			_log.info("processAttachment method has been called.");
		}
		InputStream inputStream = filePart.getInputStream();
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

		int read;
		final byte[] bytes = new byte[1024];

		while((read = inputStream.read(bytes)) != -1){
			outputStream.write(bytes, 0, read);
		}

		Attachment attachment = new Attachment();
		attachment.setName(filePart.getSubmittedFileName());
		attachment.setContents(outputStream.toByteArray());

		return attachment;
	}
}
