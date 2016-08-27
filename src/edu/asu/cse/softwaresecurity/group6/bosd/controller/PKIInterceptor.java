package edu.asu.cse.softwaresecurity.group6.bosd.controller;
 
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import edu.asu.cse.softwaresecurity.group6.bosd.service.pki.UserPublicKeyService;
import edu.asu.cse.softwaresecurity.group6.bosd.utils.PKIHelper;
 
public class PKIInterceptor extends HandlerInterceptorAdapter {
	@Autowired
	private UserPublicKeyService mPublicKeyService;
	
    
    public UserPublicKeyService getmPublicKeyService() {
		return mPublicKeyService;
	}

	public void setmPublicKeyService(UserPublicKeyService mPublicKeyService) {
		this.mPublicKeyService = mPublicKeyService;
	}

	@Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		boolean match= false;
		try{
	        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	        String publicKey = mPublicKeyService.getPublicKeyForUser(user.getUsername()).getPublicKey();
	        String privateKey = request.getParameter("privateKey").trim();
	        match = PKIHelper.verifyKeys(Base64.decodeBase64(publicKey), Base64.decodeBase64(privateKey));
	    }catch(Exception e){
	    	response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			out.append("Opps!! Something is wrong. Please try again.");
			out.close();
	    	return false;
		}
		return match;
    }
 
    @Override
    public void postHandle(HttpServletRequest request,
            HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
        
    }
 
    @Override
    public void afterCompletion(HttpServletRequest request,
            HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        
    }
 
}