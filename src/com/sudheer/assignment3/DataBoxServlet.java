package com.sudheer.assignment3;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.*;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

@SuppressWarnings("serial")
public class DataBoxServlet extends HttpServlet {
	@SuppressWarnings("unchecked")
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		

		PersistenceManager pm = PMF.get().getPersistenceManager();
		List<DirectoryJDO> directoriesList=new ArrayList<DirectoryJDO>();
		List<FileJDO> filesList=new ArrayList<FileJDO>();
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
				List<DirectoryJDO> directories = null;
				
				Query q = pm.newQuery(DirectoryJDO.class);
				q.setFilter("id ==dirID");
				q.declareParameters("String dirID");
				directories = new ArrayList<DirectoryJDO>();
				directories = (List<DirectoryJDO>) q.execute(dirID);
				

				if (action.equals("delete")) {
					pm.deletePersistent(directories.get(0));
				} else {
					
					// Load sub child directories
					Query query = pm.newQuery(DirectoryJDO.class);
					query.setFilter("parentDirID ==parentID");
					query.declareParameters("String parentID");
					directoriesList = new ArrayList<DirectoryJDO>();
					directoriesList = (List<DirectoryJDO>) query.execute(dirID);
					
					
					if(dirID!=null&&dirID.length()>0){
						DirectoryJDO directoryJDO = null;
						directoryJDO = directories.get(0);
						req.setAttribute("currentDirectory", directoryJDO);
						
						// Load file List
						Query fileQuery=pm.newQuery(FileJDO.class);
						fileQuery.setFilter("dirID==folderID");
						fileQuery.declareParameters("String folderID");
						filesList=(List<FileJDO>)fileQuery.execute(dirID);
						req.setAttribute("filesList", filesList);
					}
					
				}

				/* directoriesList = (List<DirectoryJDO>) pm
						.newQuery(DirectoryJDO.class).execute();*/
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
}
