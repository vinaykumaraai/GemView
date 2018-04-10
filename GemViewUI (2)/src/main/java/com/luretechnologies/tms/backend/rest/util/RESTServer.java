/**
 * 
 */
package com.luretechnologies.tms.backend.rest.util;

/**
 * @author sils
 *
 */
public class RESTServer {
	 private String user;
	    private String password;
	    private String host;


	    public RESTServer(String user, String password, String host)
	    {
	        this.user = user;
	        this.password = password;
	        this.host = host;
	    }

	    public String getUser()
	    {
	        return user;
	    }

	    public String getPassword()
	    {
	        return password;
	    }

	    public String getHost()
	    {
	        return host;
	    }

		@Override
		public String toString() {
			return "RESTServer [user=" + user + ", password=" + password + ", host=" + host + "]";
		}
	    
	    
}
