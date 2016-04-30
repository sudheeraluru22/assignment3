package com.sudheer.assignment3;

import java.io.IOException;

import javax.jdo.PersistenceManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class RootServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		
		try {

			// we are outputting html
			resp.setContentType("text/html");
			// we need to get access to the google user service
			UserService userService = UserServiceFactory.getUserService();
			User user = userService.getCurrentUser();
			String login_url = userService.createLoginURL("/");
			String logout_url = userService.createLogoutURL("/");
			// persistence manager and a user we declare them like this because
			// each time we close we need a new persistence manager for the following
			// query
			PersistenceManager pm = null;
			
			// get access to the user. if they do not exist in the datastore then
			// store a default version of them. of course we have to check that a user has
			// logged in first
			if (user != null) {
				
				
			}
			
			// attach a few things to the request such that we can access them in the jsp
			req.setAttribute("user", user);
			req.setAttribute("login_url", login_url);
			req.setAttribute("logout_url", logout_url);
			
			// get access to a request dispatcher and forward onto the root.jsp file
			RequestDispatcher rd = req.getRequestDispatcher("/root.jsp");
			rd.forward(req, resp);
		
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	
}
