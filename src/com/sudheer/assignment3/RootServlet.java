package com.sudheer.assignment3;

import java.io.IOException;

import javax.jdo.PersistenceManager;
import javax.servlet.RequestDispatcher;
import java.security.SecureRandom;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class RootServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		

		PersistenceManager pm = PMF.get().getPersistenceManager();
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
			
			// get access to the user. if they do not exist in the datastore then
			// store a default version of them. of course we have to check that a user has
			// logged in first
			if (user != null) {

			}
			
			// attach a few things to the request such that we can access them in the jsp
			req.setAttribute("user", user);
			req.setAttribute("login_url", login_url);
			req.setAttribute("logout_url", logout_url);
			
			
			RequestDispatcher rd=null;
			
			
			if(req.getParameter("id")!=null){
				String dirID= req.getParameter("id");
				String action= req.getParameter("action");
				
				Query q = pm.newQuery(DirectoryJDO.class);
				q.setFilter("id =="+dirID);
				List<DirectoryJDO> directories=(List<DirectoryJDO>)  q.execute();
				
				if(action.equals("delete")){
					pm.deletePersistent(directories.get(0));	
				}else{
					DirectoryJDO directoryJDO=directories.get(0);
					req.setAttribute("currentDirectory", directoryJDO);
				}
				
				
				List<DirectoryJDO> directoriesList=(List<DirectoryJDO>) pm.newQuery(DirectoryJDO.class).execute();
				req.setAttribute("directoriesList", directoriesList);
				q.closeAll();
				rd = req.getRequestDispatcher("/directory.jsp");
			}else{
				// get access to a request dispatcher and forward onto the root.jsp file
				if(user!=null){
					 rd = req.getRequestDispatcher("/directory.jsp");
				}else{
					 rd = req.getRequestDispatcher("/root.jsp");
				}
			}
			
			rd.forward(req, resp);
			
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			pm.close();
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {

			String directoryName = req.getParameter("directoryName").trim();
			UserService userService = UserServiceFactory.getUserService();
			User user = userService.getCurrentUser();
			String ID = user.getUserId();
			req.setAttribute("user", user);
			Key user_key = KeyFactory.createKey("UserJDO", ID);
			UserJDO userJDO;
			userJDO=pm.getObjectById(UserJDO.class,user_key);
			pm.makePersistent(userJDO);
			DirectoryJDO directory=new DirectoryJDO(directoryName, null);
			userJDO=pm.getObjectById(UserJDO.class,ID);
	        directory.setId(randomString(7));
			directory.setUserJDO(userJDO);
			pm.makePersistent(directory);
			
			
		List<DirectoryJDO> directoriesList=(List<DirectoryJDO>) pm.newQuery(DirectoryJDO.class).execute();
		req.setAttribute("directoriesList", directoriesList);
		RequestDispatcher rd=null;
		rd = req.getRequestDispatcher("/directory.jsp");
		rd.forward(req, resp);
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			pm.close();
		}
	}
	
	static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	static SecureRandom rnd = new SecureRandom();

	String randomString( int len ){
	   StringBuilder sb = new StringBuilder( len );
	   for( int i = 0; i < len; i++ ) 
	      sb.append( AB.charAt( rnd.nextInt(AB.length()) ) );
	   return sb.toString();
	}
	
}
