/**
 * 
 */
package com.sudheer.assignment3;

import java.io.IOException;
import java.io.InputStream;
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

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;
import org.apache.commons.io.IOUtils;

import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

/**
 * Servlet to handle File upload request from Client
 */
@SuppressWarnings("serial")
public class FileUploadServet extends HttpServlet {
  
    @SuppressWarnings("unchecked")
	@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	 PersistenceManager pm = PMF.get().getPersistenceManager();
    	List<DirectoryJDO> directoriesList=new ArrayList<DirectoryJDO>();
    	try {
    		 
    		String parentDirID = request.getParameter("currentDirectoryID");
			UserService userService = UserServiceFactory.getUserService();
			User user = userService.getCurrentUser();
			String ID = user.getUserId();
			request.setAttribute("user", user);
			InputStream inputStream =null;
			FileJDO fileJDO = new FileJDO();
			ServletFileUpload upload = new ServletFileUpload();
            response.setContentType("text/plain"); 


            FileItemIterator iterator = upload.getItemIterator(request);

            while (iterator.hasNext()) {
                FileItemStream item = iterator.next();

                InputStream stream = item.openStream();

                if (item.isFormField()) {
	            	if(item.getFieldName().equals("currentDirectoryID")){
	            		parentDirID = Streams.asString(stream);
	            		  fileJDO.setDirID(parentDirID);
	            	}
	            	
	            	
	            }else{
	            //	inputStream = item.getInputStream();
	            	Blob blob = new Blob(IOUtils.toByteArray(stream));
	                fileJDO.setFileName(item.getName());
	                fileJDO.setFileContent(blob);
	              
	                fileJDO.setId(randomString(7));
	            }
	        }
        	/*// Get the image representation
            ServletFileUpload upload = new ServletFileUpload();
            FileItemIterator iter = upload.getItemIterator(request);
            FileItemStream  fileItemStream = iter.next();
            InputStream inputStream = fileItemStream.openStream();*/

            // construct our entity objects
            
            // persist image
           
            pm.makePersistent(fileJDO);
          

          /*  // respond to query
            response.setContentType("text/plain");
            response.getOutputStream().write("OK!".getBytes());*/
            
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
				request.setAttribute("currentDirectory", directories.get(0));
				
			}else{
				
				Query query=pm.newQuery(DirectoryJDO.class);
				query.setFilter("parentDirID ==parentID");
				query.declareParameters("String parentID");
				directoriesList = (List<DirectoryJDO>) query.execute("");
			}
			
            request.setAttribute("directoriesList", directoriesList);
			RequestDispatcher rd = null;
			rd = request.getRequestDispatcher("/directory.jsp");
			rd.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
    	finally{
    		  pm.close();
    	}
    }
    
    static final String AB = "123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	static SecureRandom rnd = new SecureRandom();

	String randomString(int len) {
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++)
			sb.append(AB.charAt(rnd.nextInt(AB.length())));
		return sb.toString();
	}
  
}
