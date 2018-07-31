package pku.ss.news.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import pku.ss.news.model.User;
import pku.ss.news.model.UserManager;

public class AutoFilter implements Filter{

	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	public void doFilter(ServletRequest request, ServletResponse arg1, FilterChain arg2) throws IOException, ServletException {
		 HttpSession session = ((HttpServletRequest) request).getSession(true);  
		User u=(User) session.getAttribute("user");
		//????????cookie
		if(u==null){
			//??cookie????
			String usernameCookie = null;  
			String passwordCookie = null;  
			Cookie[] cookies = ((HttpServletRequest)request).getCookies();  
			if (cookies != null) {  
			  for (Cookie cookie : cookies) {  
			    if ("SESSION_LOGIN_USERNAME".equals(cookie.getName())) {  
			      usernameCookie = cookie.getValue(); // ??cookie????  
			    }  
			    if ("SESSION_LOGIN_PASSWORD".equals(cookie.getName())) {  
			      passwordCookie = cookie.getValue(); // ??cookie???  
			    }  
			  }  
			  if (usernameCookie != null && passwordCookie != null) { // ????  
				  UserManager um = new UserManager();
				  if(um.login(usernameCookie,passwordCookie)==1){
						User user = new User(usernameCookie);
						session.setAttribute("user",user);
						
						
						
			    }  
			  }  
			}  
		
		}
		arg2.doFilter(request,arg1);
	}

	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}

}
