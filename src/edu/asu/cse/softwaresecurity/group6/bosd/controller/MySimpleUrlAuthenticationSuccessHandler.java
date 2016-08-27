package edu.asu.cse.softwaresecurity.group6.bosd.controller;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

public class MySimpleUrlAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
	protected Log logger = LogFactory.getLog(this.getClass());
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		handle(request, response, authentication);
        clearAuthenticationAttributes(request);
		// TODO Auto-generated method stub
	}
	
	protected void handle(HttpServletRequest request, 
		      HttpServletResponse response, Authentication authentication) throws IOException {
		        String targetUrl = determineTargetUrl(authentication);
		 
		        if (response.isCommitted()) {
		            logger.debug("Response has already been committed. Unable to redirect to " + targetUrl);
		            return;
		        }
		 
		        redirectStrategy.sendRedirect(request, response, targetUrl);
		    }
	
	/** Builds the target URL according to the logic defined in the main class Javadoc. */
    protected String determineTargetUrl(Authentication authentication) {
        boolean isNormalUser = false;
        boolean isMerchant = false;
        boolean isAdmin = false;
        boolean isRegularEmp = false;
        boolean isSystemManager = false;
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (GrantedAuthority grantedAuthority : authorities) {
            if (grantedAuthority.getAuthority().equals("ROLE_NORMAL_USER")) {
            	isNormalUser = true;
                break;
            } 
            else if (grantedAuthority.getAuthority().equals("ROLE_MERCHANT")) {
            	isMerchant = true;
                break;
            }  
            else if (grantedAuthority.getAuthority().equals("ROLE_ADMIN")) {
                isAdmin = true;
                break;
            }            
            else if (grantedAuthority.getAuthority().equals("ROLE_REGULAR_EMP")) {
            	isRegularEmp = true;
                break;
            }else if (grantedAuthority.getAuthority().equals("ROLE_SYS_MANAGER")) {
            	isSystemManager = true;
                break;
            }
        }
 


        if (isNormalUser) {
            return "/normalUser";
        }else if (isMerchant) {
            return "/merchant";
        }else if (isAdmin) {
            return "/admin";
        } else if(isRegularEmp){
        	return "/regEmployee";
        } else if(isSystemManager){
        	return "/systemManager";
        } else {
            throw new IllegalStateException();
        }
    }
    
    protected void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return;
        }
        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }
 
    public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
        this.redirectStrategy = redirectStrategy;
    }
    protected RedirectStrategy getRedirectStrategy() {
        return redirectStrategy;
    }

}
