package shop;

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

public class StoreServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private final static Log _log = LogFactory.getLog(StoreServlet.class);
	private final Map<Integer, String> products = new Hashtable<>();
	public StoreServlet(){
		this.products.put(1, "Sandpaper");
		this.products.put(2, "Nails");
		this.products.put(3, "Glue");
		this.products.put(4, "Paint");
		this.products.put(5, "Tape");
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String action = request.getParameter("action");
		if(_log.isInfoEnabled()){
			_log.info("doGet method called with action: " + action);
		}
		if(action == null)
			action = "browse";
		switch(action){
		case "addToCart":
			this.addToCart(request, response);
			break;
		case "viewCart":
			this.viewCart(request, response);
			break;
		case "emtpyCart":
			this.emptyCart(request, response);
		case "browser":
		default:
			this.browse(request, response);
			break;

		}
	}

	private void emptyCart(HttpServletRequest request, HttpServletResponse response) throws IOException {
		if(_log.isInfoEnabled()){
			_log.info("emptyCart method called");
		}
		request.getSession().removeAttribute("cart");
		response.sendRedirect("shop?action=viewCart");
	}

	private void viewCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		if(_log.isInfoEnabled()){
			_log.info("viewCart method called");
		}
		request.setAttribute("products", this.products);
		request.getRequestDispatcher("/WEB-INF/shop/view/viewCart.jsp").forward(request, response);
	}

	private void browse(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		if(_log.isInfoEnabled()){
			_log.info("browse method called");
		}
		request.setAttribute("products", this.products);
		request.getRequestDispatcher("/WEB-INF/shop/view/browse.jsp")
		.forward(request, response);
	}

	private void addToCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int productId;
		if(_log.isInfoEnabled()){
			_log.info("addToCart method called");
		}
		try {
			productId = Integer.parseInt(request.getParameter("productId"));
			if(_log.isInfoEnabled()){
				_log.info("Product code is " + productId + " - item " + products.get(productId));
			}
		} catch(Exception e) {
			response.sendRedirect("shop");
			return;
		}
		HttpSession session = request.getSession();
		if(session.getAttribute("cart") == null){
			if(_log.isInfoEnabled()){
				_log.info("Creating new session for customer");
			}
			session.setAttribute("cart", new Hashtable<Integer, Integer>());
		}
		@SuppressWarnings("unchecked")
		Map<Integer, Integer> cart = (Map<Integer, Integer>)session.getAttribute("cart");
		if(!cart.containsKey(productId)){
			cart.put(productId, 0);
		}
		cart.put(productId, cart.get(productId) + 1);
		response.sendRedirect("shop?action=viewCart");
	}
}