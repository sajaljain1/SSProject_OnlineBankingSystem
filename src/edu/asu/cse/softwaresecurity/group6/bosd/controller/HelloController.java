package edu.asu.cse.softwaresecurity.group6.bosd.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import edu.asu.cse.softwaresecurity.group6.bosd.model.user.Account;
import edu.asu.cse.softwaresecurity.group6.bosd.model.user.ChangePassword;
import edu.asu.cse.softwaresecurity.group6.bosd.model.user.OTPRequestInfo;
import edu.asu.cse.softwaresecurity.group6.bosd.model.user.Request;
import edu.asu.cse.softwaresecurity.group6.bosd.model.user.SecurityQuestions;
import edu.asu.cse.softwaresecurity.group6.bosd.model.user.UserData;
import edu.asu.cse.softwaresecurity.group6.bosd.model.user.UserOTPSessionInfo;
import edu.asu.cse.softwaresecurity.group6.bosd.model.user.UserSignUp;
import edu.asu.cse.softwaresecurity.group6.bosd.service.AccountService;
import edu.asu.cse.softwaresecurity.group6.bosd.model.user.Account;
import edu.asu.cse.softwaresecurity.group6.bosd.model.user.UserData;
import edu.asu.cse.softwaresecurity.group6.bosd.service.AccountService;
import edu.asu.cse.softwaresecurity.group6.bosd.service.RequestService;
import edu.asu.cse.softwaresecurity.group6.bosd.service.TransactionService;
import edu.asu.cse.softwaresecurity.group6.bosd.service.UserDataService;
import edu.asu.cse.softwaresecurity.group6.bosd.service.UserService;
import edu.asu.cse.softwaresecurity.group6.bosd.service.GenerateOTP;
import edu.asu.cse.softwaresecurity.group6.bosd.utils.OTPStatus;
import edu.asu.cse.softwaresecurity.group6.bosd.utils.UtilMessages;

@Controller
@SessionAttributes("userOTPSessionInfo")
public class HelloController {
	private AccountService mAccountService;
	private TransactionService mTransctionService;
	@Autowired
	private UserService mUserService;
	@Autowired
	private UserDataService mUserDataService;
	@Autowired
	private RequestService mRequestService;
	@Autowired
	private GenerateOTP generateOTP;

	private static final Logger logger = Logger
			.getLogger(HelloController.class);

	public UserDataService getmUserDataService() {
		return mUserDataService;
	}

	public void setmUserDataService(UserDataService mUserDataService) {
		this.mUserDataService = mUserDataService;
	}

	@RequestMapping(value = { "/", "/welcome**" }, method = RequestMethod.GET)
	public ModelAndView welcomePage() {

		logger.info("In welcome page");
		ModelAndView model = new ModelAndView();
		model.setViewName("index");
		return model;
	}

	@RequestMapping(value = "/admin**", method = RequestMethod.GET)
	public ModelAndView adminPage() {
		logger.info("In admin page");
		ModelAndView model = new ModelAndView();
		model.addObject("title", "Spring Security Custom Login Form");
		model.addObject("message", "This is protected page!");
		model.setViewName("systemAdminHome");
		return model;
	}

	@RequestMapping(value = { "/regEmployee" }, method = RequestMethod.GET)
	public ModelAndView RegularEmployeePage() {
		logger.info("In regular employee page");
		ModelAndView model = new ModelAndView();
		model.addObject("title", "Spring Security Custom Login Form");
		model.addObject("message", "This is protected page!");
		model.setViewName("RegularEmployee/Home");

		return model;
	}

	@RequestMapping(value = { "/normalUser**" }, method = RequestMethod.GET)
	public ModelAndView NormalUserPage() {
		logger.info("In normal user page");
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		ModelAndView model = new ModelAndView();
//		model.addObject("title", "Spring Security Custom Login Form");
//		model.addObject("message", "This is protected page!");
		model.addObject("listAccounts", this.mAccountService.fetchUserAccounts(user.getUsername()));
		UserData userData = mUserDataService.getUserDetails(user.getUsername());
		model.addObject("userDetail", userData);
		model.setViewName("normalUserHome");
		return model;
	}

	@RequestMapping(value = { "/systemManager" }, method = RequestMethod.GET)
	public ModelAndView SystemManagerPage() {
		logger.info("In System Manager page");
		ModelAndView model = new ModelAndView();
//		model.addObject("title", "Spring Security Custom Login Form");
//		model.addObject("message", "This is protected page!");
		model.setViewName("SystemManager/Home");

		return model;
	}



	// Spring Security see this :
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login(@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout, HttpServletRequest request) {
		logger.info("In Login page");
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Object myUser = (authentication != null) ? authentication.getPrincipal() :  null;
		ModelAndView model = new ModelAndView();
		if (myUser instanceof UserDetails) {
			model.setViewName("redirect:/userHome");
		}else{
			if (error != null) {
				model.addObject("error", getErrorMessage(request, "SPRING_SECURITY_LAST_EXCEPTION"));
			}

			if (logout != null) {
				model.addObject("msg", "You've been logged out successfully.");
			}
			model.setViewName("Authentication/login");
		}
		return model;
	}
	
	private String getErrorMessage(HttpServletRequest request, String key){
		logger.info("In error message page");
		Exception exception = 
                   (Exception) request.getSession().getAttribute(key);
		
		String error = "";
		if (exception instanceof BadCredentialsException) {
			error = "Invalid username and password!";
		}else if(exception instanceof LockedException) {
			error = exception.getMessage();
		}else{
			error = "Invalid username and password!";
		}
		
		return error;
	}

	@RequestMapping(value = "normalUser/viewTrans/{acctype}", method = RequestMethod.GET)
	public String listTransactions(@PathVariable("acctype") String acctype, Model model) {
		logger.info("In normal user transaction page");
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		ArrayList<Account> accounts = mAccountService.fetchUserAccounts(user.getUsername());
		String accId = null;
		for (Account acc : accounts) {
			if (acc.getAcctype().equals(acctype)) {
				accId = acc.getAccID();
				break;
			}
		}
		if (accId != null)
			model.addAttribute("listTrans", this.mTransctionService.getTransactionList(accId));
		return "NormalUser/TransactionsList";
	}

	/************************** Reset Password ********************************/
	/**
	 * GenerateOTP view as Step-1 to reset password.
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/forgotpwd**", method = RequestMethod.GET)
	public ModelAndView generateOTPForPWDReset() {
		logger.info("In forgot password page");
		ModelAndView mview = new ModelAndView();
		mview.addObject("OTPRequestInfo", new OTPRequestInfo());
		mview.setViewName("Authentication/generateOTP");
		return mview;
	}
	
	@RequestMapping(value = "/new_password", method = RequestMethod.POST)
	public ModelAndView newPasswordPage(Model model, @Valid OTPRequestInfo OTPRequestInfo, BindingResult result) {
		logger.info("In new password page");
		ModelAndView mview = new ModelAndView();
		String usernameLowercase = UtilMessages.EMPTY;
		
		// 1. Username validity check
		if (result.hasErrors()) {
			logger.info("In generateOTP page");
			mview.addObject("OTPRequestInfo", OTPRequestInfo);
			mview.setViewName("Authentication/generateOTP");
		} else {
			//In case the user does not exist, we check using security question of the user.
			usernameLowercase = OTPRequestInfo.getUsername().toLowerCase();
			if(!mUserService.getIsUserAccountEnabled(usernameLowercase)) {
				logger.info("User Doesn't exist in Signup " + usernameLowercase + DateTime.now().toString());
				OTPRequestInfo.setUsername(null);
				OTPRequestInfo.setFailureMessage(UtilMessages.WRONG_USERNAME);
				mview.addObject("OTPRequestInfo", OTPRequestInfo);
				mview.setViewName("Authentication/generateOTP");
			} else { // 2. Move to Reset Password
				
				String securityQuestion = mUserService.getSecurityQuestionOfUser(usernameLowercase);
				if (StringUtils.equalsIgnoreCase( securityQuestion, UtilMessages.NONE)) {
					OTPRequestInfo.setUsername(null);
					OTPRequestInfo.setFailureMessage(UtilMessages.WRONG_USERNAME);
					mview.addObject("OTPRequestInfo", OTPRequestInfo);
					mview.setViewName("Authentication/generateOTP");
				} else {
					//Generate OTP
					generateOTP.generateOTPForUser(usernameLowercase, OTPStatus.FORGOT_PWD);
					
					UserOTPSessionInfo userOTPSessionInfo = new UserOTPSessionInfo();
					userOTPSessionInfo.setUsername(OTPRequestInfo.getUsername());
					userOTPSessionInfo.setSecurityQuestion(securityQuestion);
					model.addAttribute("userOTPSessionInfo", userOTPSessionInfo);
					ChangePassword changePassword = new ChangePassword();
					changePassword.setUsername(usernameLowercase);
					changePassword.setChosenSecurityQuestion(securityQuestion);
					mview.addObject("changePassword", changePassword);
					mview.setViewName("Authentication/new_password");
				}
				
			}
		}
		return mview;
	}

	@RequestMapping(value = "/reset_pwd", method = RequestMethod.POST)
	public String resetPassword(Model model, @Valid ChangePassword changePassword, BindingResult result, @ModelAttribute UserOTPSessionInfo userOTPSessionInfo) {
		logger.info("In reset password page");
		boolean status = true;
		 String usernameLowercase = userOTPSessionInfo.getUsername().toLowerCase();
		 
		changePassword.setUsername(usernameLowercase);
		changePassword.setChosenSecurityQuestion(userOTPSessionInfo.getSecurityQuestion());
		
		if (result.hasErrors()) {
			changePassword.setOTP(null);
			changePassword.setSuccessMessage(null);
			changePassword.setFailureMessage(null);
			model.addAttribute("changePassword", changePassword);
			return "Authentication/new_password";
		}
		//Reset Password
		status = mUserService.updateUserPassword(changePassword,OTPStatus.FORGOT_PWD);
		if (status) {
			String successMessage = UtilMessages.PWD_REST_SUCCESSFUL + " for username: " + changePassword.getUsername();
			changePassword = new ChangePassword();
			changePassword.setSuccessMessage(successMessage);
		} else {
			changePassword = new ChangePassword();
			changePassword.setFailureMessage(UtilMessages.PWD_REST_FAIL);
		}
		changePassword.setUsername(usernameLowercase);
		changePassword.setChosenSecurityQuestion(userOTPSessionInfo.getSecurityQuestion());
		model.addAttribute("securityQuestions", SecurityQuestions.getSecurityQuestions());
		model.addAttribute("changePassword", changePassword);
		return "Authentication/new_password";
		
	}
	/************************** Reset Password ********************************/

	/************************** User Sign Up ********************************/
	@RequestMapping(value = "/usersignupOTP", method = RequestMethod.GET)
	public ModelAndView generateOTPForUserSignup() {
		logger.info("In Sign up page");
		ModelAndView mview = new ModelAndView();
		mview.addObject("OTPRequestInfo", new OTPRequestInfo());
		mview.setViewName("Authentication/usersignupgenerateOTP");
		return mview;
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ModelAndView registerPage(Model model, @Valid OTPRequestInfo OTPRequestInfo, BindingResult result) {
		logger.info("In register page");
		ModelAndView mview = new ModelAndView();
		if (result.hasErrors()) {
			mview.addObject("OTPRequestInfo", OTPRequestInfo);
			mview.setViewName("Authentication/usersignupgenerateOTP");
		} else {
			//In case the user does not exist, we check using security question of the user.
			if (!mUserService.isThisValidUsername(OTPRequestInfo.getUsername())) {
				OTPRequestInfo.setUsername(null);
				OTPRequestInfo.setFailureMessage(UtilMessages.WRONG_USERNAME);
				mview.addObject("OTPRequestInfo", OTPRequestInfo);
				mview.setViewName("Authentication/usersignupgenerateOTP");
			} else if(!StringUtils.isEmpty(mUserService.getPIIInformationOfUser(OTPRequestInfo.getUsername()))) {
				OTPRequestInfo.setUsername(null);
				OTPRequestInfo.setFailureMessage(UtilMessages.USER_EXISTS);
				mview.addObject("OTPRequestInfo", OTPRequestInfo);
				mview.setViewName("Authentication/usersignupgenerateOTP");
			} else { // 2. Move to User Signup
				
				//Generate OTP
				generateOTP.generateOTPForUser(OTPRequestInfo.getUsername(), OTPStatus.SIGN_UP);
				
				UserOTPSessionInfo userOTPSessionInfo = new UserOTPSessionInfo();
				userOTPSessionInfo.setUsername(OTPRequestInfo.getUsername());
				model.addAttribute("userOTPSessionInfo", userOTPSessionInfo);
				UserSignUp userSignUp = new UserSignUp();
				userSignUp.setUsername(OTPRequestInfo.getUsername());
				mview.addObject("userSignUp", userSignUp);
				mview.addObject("securityQuestions", SecurityQuestions.getSecurityQuestions());
				mview.setViewName("Authentication/register");
			}
		}
		
		return mview;
	}

	@RequestMapping(value = "/usersignup", method = RequestMethod.POST)
	public String userSignUp(Model model, @Valid UserSignUp userSignUp, BindingResult result, @ModelAttribute UserOTPSessionInfo userOTPSessionInfo) {
		logger.info("In usersignup page");
		userSignUp.setFailureMessage(null);
		userSignUp.setSuccessMessage(null);
		userSignUp.setUsername(userOTPSessionInfo.getUsername());
		
		if (result.hasErrors()) {
//			System.out.println();
			userSignUp.setOTP(null);
		} else if(!StringUtils.equals(userSignUp.getPassword(), userSignUp.getRepassword())) {
			userSignUp.setPassword(null);
			userSignUp.setRepassword(null);
			userSignUp.setOTP(null);
			userSignUp.setFailureMessage(UtilMessages.NEW_PWD_RE_PWD_DONT_MATCH);
		} else if(!StringUtils.isEmpty(mUserService.getPIIInformationOfUser(userSignUp.getUsername()))) {
			userSignUp = new UserSignUp();
			userSignUp.setFailureMessage(UtilMessages.USER_EXISTS);
		} else {
				boolean status = mUserService.processUserSignUp(userSignUp);
				userSignUp = new UserSignUp();
				if(status) {
					userSignUp.setSuccessMessage(UtilMessages.USER_SIGNUP_SUCCESS);
				} else {
					userSignUp.setFailureMessage(UtilMessages.USER_SIGNUP_FAIL);
				}
		}
		userSignUp.setUsername(userOTPSessionInfo.getUsername());
		model.addAttribute("userSignUp", userSignUp);
		model.addAttribute("securityQuestions", SecurityQuestions.getSecurityQuestions());
		return "Authentication/register";
	}

	/************************** User Sign Up ********************************/

	public AccountService getmAccountService() {
		return mAccountService;
	}

	@Autowired(required = true)
	@Qualifier(value = "accountServiceImpl")
	public void setmAccountService(AccountService mAccountService) {
		this.mAccountService = mAccountService;
	}

	public TransactionService getmTransctionService() {
		return mTransctionService;
	}

	@Autowired(required = true)
	@Qualifier(value = "transactionServiceImpl")
	public void setmTransctionService(TransactionService mTransctionService) {
		this.mTransctionService = mTransctionService;
	}

	public RequestService getmRequestService() {
		return mRequestService;
	}

	public void setmRequestService(RequestService mRequestService) {
		this.mRequestService = mRequestService;
	}
	
	public UserService getmUserService() {
		return mUserService;
	}
	
	@Autowired(required = true)
	@Qualifier(value = "userServiceImpl")
	public void setmUserService(UserService mUserService) {
		this.mUserService = mUserService;
	}
	
	@RequestMapping(value = { "/userHome" }, method = RequestMethod.GET)
	public String UserHome(HttpSession session) {
		logger.info("In welcome page");
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		for (GrantedAuthority grantedAuthority : authorities) {
            if (grantedAuthority.getAuthority().equals("ROLE_NORMAL_USER")) {
            	return "redirect:/normalUser";
            } 
            else if (grantedAuthority.getAuthority().equals("ROLE_MERCHANT")) {
            	return "redirect:/merchant";
            }  
            else if (grantedAuthority.getAuthority().equals("ROLE_ADMIN")) {
            	return "redirect:/admin";
            }            
            else if (grantedAuthority.getAuthority().equals("ROLE_REGULAR_EMP")) {
            	return "redirect:/regEmployee";
            }else if (grantedAuthority.getAuthority().equals("ROLE_SYS_MANAGER")) {
            	return "redirect:/systemManager";
            }
        }
		return "redirect:/welcome";
	}

	public GenerateOTP getGenerateOTP() {
		return generateOTP;
	}
	
	@Autowired(required = true)
	@Qualifier(value = "generateOTP")
	public void setGenerateOTP(GenerateOTP generateOTP) {
		this.generateOTP = generateOTP;
	}

	@RequestMapping(value = "/normalUser/DeleteAccount", method = RequestMethod.GET)
	public String deleteAccounts(Model model) {
		User user = (User) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		return "NormalUser/DeleteAccount";
	}
	
	@RequestMapping(value = "/regularEmployee/DeleteAccount", method = RequestMethod.GET)
	public ModelAndView deleteAccounts(@RequestParam("username") String username) {
		ModelAndView model = new ModelAndView();
		try{
		mAccountService.deleteAccount(username);
		model.addObject("successMessage","The account was deleted");
		}catch(Exception e){
			model.addObject("errorMessage","The account deletion had a problem");
		}
		model.setViewName("RegularEmployee/messages");
		return model;
	}
	

	@RequestMapping(value = {"/normalUser/DeleteAccountYes"}, method = RequestMethod.GET)
	public String deleteAccountsYes(Model model) {
		User user = (User) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();

		if (mUserService.makeAccountInactive(user.getUsername())) {
			Request request = new Request();
			request.setTransactionId(0l);
			request.setType("deleteAccount");
			request.setCreated_at(new Timestamp(Calendar.getInstance()
					.getTimeInMillis()));

			request.setUsername(user.getUsername());
			ArrayList<String> sysManagerList = (ArrayList<String>) mUserDataService
					.getSystemManagerList();
			request.setApproved("0");
			request.setPending("1");
			request.setAssignedTo(sysManagerList.get(new Random()
					.nextInt(sysManagerList.size())));
			mRequestService.createDebitCreditRequest(request);
			model.addAttribute("Message",
					"Account will be deleted after approval");

		} else
			model.addAttribute("Message", "Account already deleted");
		return "NormalUser/TransactionSuccess";
	}


}
