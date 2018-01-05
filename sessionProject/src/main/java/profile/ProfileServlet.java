package profile;

import java.io.IOException;
import java.util.Hashtable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ProfileServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		User user = new User();
		user.setUserId(19384L);
		user.setUsername("Coder314");
		user.setFirstName("John");
		user.setLastName("Smith");

		Hashtable<String, Boolean> permissions = new Hashtable<>();
		permissions.put("user", true);
		permissions.put("moderator", true);
		permissions.put("admin", false);
		user.setPermissions(permissions);

		request.setAttribute("user", user);
		request.getRequestDispatcher("/WEB-INF/jsp/profile/view//profile.jsp").forward(request, response);
	}

}