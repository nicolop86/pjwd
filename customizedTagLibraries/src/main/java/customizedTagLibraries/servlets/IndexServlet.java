package customizedTagLibraries.servlets;

import java.io.IOException;
import java.time.Instant;
import java.time.chrono.JapaneseEra;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class IndexServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final Log _log = LogFactory.getLog(IndexServlet.class);

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		if(_log.isInfoEnabled()){
			_log.info("Servlet " + this.getServletName() + " calling doGet method");
		}
		String greeting = "greeting";

		if(req.getParameter("dates") != null){
			req.setAttribute("date", new Date());
			req.setAttribute("calendar", Calendar.getInstance());
			req.setAttribute("instant", Instant.now());
			req.setAttribute("japaneseEra", JapaneseEra.TAISHO);
			greeting = "dates";
		} else if (req.getParameter("text")!= null) {
			req.setAttribute("shortText", "This is short text.");
			req.setAttribute("longText", "This is really long text that should get cut off at 32 chars.");
			greeting = "text";
		}

		req.getRequestDispatcher("/WEB-INF/jsp/view/" + greeting + ".jsp").forward(req, res);
	}

	public void init(){
		if(_log.isInfoEnabled()){
			_log.info("Servlet " + this.getServletName() + " has started.");
		}
	}
}