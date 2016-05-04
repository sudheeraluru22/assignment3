package com.sudheer.assignment3;

import java.io.IOException;

import javax.jdo.PersistenceManager;
import javax.servlet.RequestDispatcher;

import java.security.SecureRandom;
import java.util.ArrayList;
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

	@SuppressWarnings("unchecked")
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		

		PersistenceManager pm = PMF.get().getPersistenceManager();
		List<DirectoryJDO> directoriesList=new ArrayList<DirectoryJDO>();
		req.setAttribute("message", "");
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
			
			
			if (req.getParameter("id") != null) {
				String dirID = req.getParameter("id");
				String action = req.getParameter("action");
				req.setAttribute("action", action);
				List<DirectoryJDO> directories = null;
				
				Query q = pm.newQuery(DirectoryJDO.class);
				q.setFilter("id ==dirID");
				q.declareParameters("String dirID");
				directories = new ArrayList<DirectoryJDO>();
				directories = (List<DirectoryJDO>) q.execute(dirID);
				

				if (action.equals("delete")) {
					DirectoryJDO directoryJDO =directories.get(0);
					Query query=pm.newQuery(DirectoryJDO.class);
					query.setFilter("parentDirID ==parentID");
					query.declareParameters("String parentID");
					directoriesList = (List<DirectoryJDO>) query.execute(directoryJDO.getId());
					
					if(directoriesList.size()==0){
						pm.deletePersistent(directoryJDO);
					}else{
						req.setAttribute("message", "Please Delete childs first and then delete it.");
					}
					
				} else if(action.equals("load")) {
					
					// Load sub child directories
					/*Query query = pm.newQuery(DirectoryJDO.class);
					query.setFilter("parentDirID ==parentDirID");
					query.declareParameters("String parentDirID");
					directoriesList = new ArrayList<DirectoryJDO>();
					directoriesList = (List<DirectoryJDO>) q.execute(dirID);*/
					if(dirID!=null&&dirID.length()>0){
						DirectoryJDO directoryJDO = null;
						directoryJDO = directories.get(0);
						req.setAttribute("currentDirectory", directoryJDO);
					}
					
				}else if(action.equals("update")){
					if(dirID!=null&&dirID.length()>0){
						DirectoryJDO directoryJDO = null;
						directoryJDO = directories.get(0);
						req.setAttribute("currentDirectory", directoryJDO);
					}
				}

				Query query=pm.newQuery(DirectoryJDO.class);
				query.setFilter("parentDirID ==parentID");
				query.declareParameters("String parentID");
				directoriesList = (List<DirectoryJDO>) query.execute("");
				
				System.out.println("RootServlet.doGet()====directoriesList===="+directoriesList.size());
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
	
	@SuppressWarnings("unchecked")
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		List<DirectoryJDO> directoriesList=new ArrayList<DirectoryJDO>();
		req.setAttribute("message", "");
		try {

			String directoryName = req.getParameter("directoryName").trim();
			String btton = req.getParameter("button").trim();
			String parentDirID = req.getParameter("currentDirectoryID");
			UserService userService = UserServiceFactory.getUserService();
			User user = userService.getCurrentUser();
			String ID = user.getUserId();
			req.setAttribute("user", user);
			
			if(btton.equals("Update")){
				Query q = pm.newQuery(DirectoryJDO.class);
				q.setFilter("id ==dirID");
				q.declareParameters("String dirID");
				List<DirectoryJDO>	directories = new ArrayList<DirectoryJDO>();
				directories = (List<DirectoryJDO>) q.execute(parentDirID);
				DirectoryJDO directoryJDO=directories.get(0);
				directoryJDO.setDirName(directoryName);
			}else{
				Key user_key = KeyFactory.createKey("UserJDO", ID);
				UserJDO userJDO = pm.getObjectById(UserJDO.class, user_key);
				pm.makePersistent(userJDO);
				DirectoryJDO directory = new DirectoryJDO(directoryName,parentDirID);
				userJDO = pm.getObjectById(UserJDO.class, ID);
				directory.setId(randomString(7));
				directory.setUserJDO(userJDO);
				pm.makePersistent(directory);
			}
			
			
			if(parentDirID!=null&parentDirID.length()>0){
				// Load sub child directories
				Query q = pm.newQuery(DirectoryJDO.class);
				q.setFilter("parentDirID ==parentID");
				q.declareParameters("String parentID");
				directoriesList = new ArrayList<DirectoryJDO>();
				directoriesList = (List<DirectoryJDO>) q.execute(parentDirID);
				
				// Load Parent Directory
				List<DirectoryJDO> directories = null;
				Query query = pm.newQuery(DirectoryJDO.class);
				query.setFilter("id ==dirID");
				query.declareParameters("String dirID");
				directories = new ArrayList<DirectoryJDO>();
				directories = (List<DirectoryJDO>) q.execute(parentDirID);
				req.setAttribute("currentDirectory", directories.get(0));
				
			}else{
				
				Query query=pm.newQuery(DirectoryJDO.class);
				query.setFilter("parentDirID ==parentID");
				query.declareParameters("String parentID");
				directoriesList = (List<DirectoryJDO>) query.execute("");
			}
			
			req.setAttribute("directoriesList", directoriesList);
			RequestDispatcher rd = null;
			rd = req.getRequestDispatcher("/directory.jsp");
			rd.forward(req, resp);
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			pm.close();
		}
	}
	
	static final String AB = "123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	static SecureRandom rnd = new SecureRandom();

	String randomString( int len ){
	   StringBuilder sb = new StringBuilder( len );
	   for( int i = 0; i < len; i++ ) 
	      sb.append( AB.charAt( rnd.nextInt(AB.length()) ) );
	   return sb.toString();
	}
	
}
