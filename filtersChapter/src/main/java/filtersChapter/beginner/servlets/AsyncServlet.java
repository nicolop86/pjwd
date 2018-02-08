package filtersChapter.beginner.servlets;

import java.io.IOException;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class AsyncServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private final static Log _log = LogFactory.getLog(NonAsyncServlet.class);
	private static volatile int ID = 1;

	@Override
	public void init(){
		if(_log.isInfoEnabled()){
			_log.info("Servlet " + this.getServletName() + " is starting.");
		}
	}

	@Override
	public void destroy(){
		if(_log.isInfoEnabled()){
			_log.info("Servlet " + this.getServletName() + " is going to be destroyed.");
		}
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(_log.isInfoEnabled()){
			_log.info("I'm in doGet method of Servlet " + this.getServletName() + ".");
		}

		final int id;

		synchronized(AsyncServlet.class) {
			id = ID++;
		}

		long timeout = request.getParameter("timeout") == null ? 10_000L : Long.parseLong(request.getParameter("timeout"));

		if(_log.isInfoEnabled()){
			_log.info("Entering " + this.getServletName() +".doGet(). Request ID = " + id + ", isAsyncStarted = " + request.isAsyncStarted());
		}

		final AsyncContext context = request.getParameter("unwrap") != null ? (AsyncContext) request.startAsync() : 
			request.startAsync(request, response);
		context.setTimeout(timeout);

		if(_log.isInfoEnabled()){
			_log.info("Starting asynchronous thread. Request ID = " + id + ".");
		}

		AsyncThread thread = new AsyncThread(id, context);
		context.start(thread::doWork);

		if(_log.isInfoEnabled()){
			_log.info("Leaving " + this.getServletName() +".doGet(). Request ID = " + id + ", isAsyncStarted = " + request.isAsyncStarted());
		}
	}

	private static class AsyncThread {
		
		private final int id;
		private final AsyncContext context;
		private final static Log _logAsyncThread = LogFactory.getLog(AsyncThread.class);

		public AsyncThread(int id, AsyncContext context) {
			this.id = id;
			this.context = context;
		}

		public void doWork() {
			if(_logAsyncThread.isInfoEnabled()){
				_logAsyncThread.info("Asynchronous thread started. Request ID = " + this.id + ".");
			}

			try {
				Thread.sleep(5_000L);
			} catch (Exception e) {
				e.printStackTrace();
			}

			HttpServletRequest request = (HttpServletRequest)this.context.getRequest();
			
			if(_logAsyncThread.isInfoEnabled()){
				_logAsyncThread.info("Done sleeping. Request ID = " + this.id + ", URL = " + request.getRequestURL() + ".");
			}

			this.context.dispatch("/WEB-INF/jsp/view/async.jsp");

			if(_logAsyncThread.isInfoEnabled()){
				_logAsyncThread.info("Asynchronous thread completed. Request ID = " + this.id + ".");
			}

		}
	}

}