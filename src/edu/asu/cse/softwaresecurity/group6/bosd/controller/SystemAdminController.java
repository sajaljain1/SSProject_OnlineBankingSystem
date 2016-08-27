package edu.asu.cse.softwaresecurity.group6.bosd.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyPair;
import java.sql.Date;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Appender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Logger;
import org.apache.tomcat.util.codec.binary.Base64;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import edu.asu.cse.softwaresecurity.group6.bosd.model.pki.UserPublicKey;
import edu.asu.cse.softwaresecurity.group6.bosd.model.user.PIIForm;
import edu.asu.cse.softwaresecurity.group6.bosd.model.user.UserData;
import edu.asu.cse.softwaresecurity.group6.bosd.model.user.UserRegistration;
import edu.asu.cse.softwaresecurity.group6.bosd.model.user.UserRole;
import edu.asu.cse.softwaresecurity.group6.bosd.service.AccountService;
import edu.asu.cse.softwaresecurity.group6.bosd.service.UserDataService;
import edu.asu.cse.softwaresecurity.group6.bosd.service.pii.GovtInfoReqService;
import edu.asu.cse.softwaresecurity.group6.bosd.service.pki.UserPublicKeyService;
import edu.asu.cse.softwaresecurity.group6.bosd.utils.DateAndTime;
import edu.asu.cse.softwaresecurity.group6.bosd.utils.GenerateRandomKey;
import edu.asu.cse.softwaresecurity.group6.bosd.utils.PKIHelper;
import edu.asu.cse.softwaresecurity.group6.bosd.utils.SendMail;
import edu.asu.cse.softwaresecurity.group6.bosd.utils.UtilMessages;
@Controller
@RequestMapping("/admin")
public class SystemAdminController {
	
	private static final Logger logger = Logger.getLogger(SystemAdminController.class);
	
	@Autowired
	private AccountService accountService;
	
	public AccountService getAccountService() {
		return accountService;
	}

	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}

	@Autowired
	private UserDataService userDataService;

	@Autowired
	private AccountService mAccountService;
	
	public UserDataService getUserDataService() {
		return userDataService;
	}

	public AccountService getmAccountService() {
		return mAccountService;
	}
	
	@Autowired(required =true)
	@Qualifier(value = "accountServiceImpl")
	public void setmAccountService(AccountService mAccountService) {
		this.mAccountService = mAccountService;
	}
	
	@Autowired(required =true)
	@Qualifier(value = "userDataServiceImpl")
	public void setUserDataService(UserDataService userDataService) {
		this.userDataService = userDataService;
	}

	@Autowired
	private GovtInfoReqService govtInfoReqService;

	public GovtInfoReqService getGovtInfoReqService() {
		return govtInfoReqService;
	}

	public void setGovtInfoReqService(GovtInfoReqService govtInfoReqService) {
		this.govtInfoReqService = govtInfoReqService;
	}
	
	@Autowired
	private UserPublicKeyService pkiService;
	
	public UserPublicKeyService getPkiService() {
		return pkiService;
	}
	@Autowired(required=true)
	@Qualifier(value="userPublicKeyService")
	public void setPkiService(UserPublicKeyService pkiService) {
		this.pkiService = pkiService;
	}

	@RequestMapping(value = "/pii", method = RequestMethod.GET)
	public ModelAndView adminPIIPage() {

		ModelAndView model = new ModelAndView();
		model.setViewName("systemAdminPII");
		return model;
	}

	@RequestMapping(value = "/pii/newAccounts", method = RequestMethod.GET)
	public ModelAndView newPIIPage() {

		ModelAndView model = new ModelAndView("newPII");
		List<UserData> users = userDataService.getNewUsersRequiringPIIVerification();
		model.addObject("piiForm", new PIIForm(users));
		return model;
	}

	@RequestMapping(value = "/pii/requests", method = RequestMethod.GET)
	public ModelAndView newRequestsPage() {
		ModelAndView model = new ModelAndView("newRequests");
		List<UserData> users = userDataService.getNewUsersWithPendingInfoRequest();
		model.addObject("piiForm", new PIIForm(users));
		return model;
	}

	@RequestMapping(value = "pii/sendForVerification", method = RequestMethod.POST)
	public String sendForVerification(@ModelAttribute("piiForm") PIIForm piiForm, Model model){ 
		if(piiForm != null && piiForm.getUsers() != null) {
			for(int i=0;i< piiForm.getUsers().size(); i++){

				if(piiForm.getCheckedUsers().get(i).isChecked()){
					userDataService.sendUserForPIIVerification(piiForm.getUsers().get(i).getUsername());
				}
			}
		}
		return "redirect:/admin/pii";
	}

	@RequestMapping(value = "pii/sendInfo", method = RequestMethod.POST)
	public String sendInfo(@ModelAttribute("piiForm") PIIForm piiForm, Model model){ 
		if(piiForm != null && piiForm.getUsers() != null) {
			for(int i=0;i< piiForm.getUsers().size(); i++){
				if(piiForm.getCheckedUsers().get(i).isChecked()){
					UserData user = userDataService.getUserDetails(piiForm.getUsers().get(i).getUsername());
					govtInfoReqService.completeRequest(user.getPii());
				}
			}
		}
		return "redirect:/admin/pii";
	}

	@RequestMapping(value = "/syslogs", method = RequestMethod.GET)
	private void readLog( HttpServletResponse response){
		 try {
			 Enumeration e = Logger.getRootLogger().getAllAppenders();
			    while ( e.hasMoreElements() ){
			      Appender app = (Appender)e.nextElement();
			      if ( app instanceof FileAppender ){
			    	  InputStream is = new FileInputStream(((FileAppender)app).getFile());
			    	  IOUtils.copy(is, response.getOutputStream());
//			        System.out.println("File: " + ((FileAppender)app).getFile());
			      }
			    }
		    
		      response.flushBuffer();
		    } catch (IOException ex) {
		    	ex.printStackTrace();
		    }
	}

	@RequestMapping(value = "/technicalAccountAccess", method = RequestMethod.GET)
	public String getrequestInfo() {

		return "techAccessSearchPage";
	}
	
	@RequestMapping(value = "/technicalAccountAccess", method = RequestMethod.POST)
	public ModelAndView findUsers( @RequestParam String firstName, @RequestParam String lastName) {
		List<UserData> users= userDataService.getUsersFromName(firstName, lastName);
		ModelAndView model = new ModelAndView("techAccessSearchResults");
		model.addObject("piiForm", new PIIForm(users));
		return model;
	}
	
	@RequestMapping(value = "/getMoreInfo", method = RequestMethod.POST)
	public ModelAndView getMoreInfo( @RequestParam String userId) {
		UserData user = userDataService.getUserDetails(userId);
		ModelAndView model = new ModelAndView("techUserDetails");
		model.addObject("user", user);
		return model;
	}
	
	@RequestMapping(value = "/deleteUser", method = RequestMethod.POST)
	public String deleteUser( @RequestParam String userId) {
//		UserData user = userDataService.daccountService.deleteAccount(userId);
		mAccountService.deleteAccount(userId);
		return "redirect:/admin";
	}
	 
	 @RequestMapping(value = "/registerinternaluser", method = RequestMethod.GET)
		public ModelAndView showUserRegistration() {
			
			ModelAndView mView = new ModelAndView();
			UserRegistration userRegistration = new UserRegistration();
			mView.addObject("userRegistration", userRegistration);
			mView.setViewName("SACreateAcc");
			return mView;
		}
	 
	 @RequestMapping(value = "/createUserAccount", method = RequestMethod.POST)
		public ModelAndView CreateUserAccount(@Valid UserRegistration userRegistration, BindingResult result,
				Model model) {
			
			if(result.hasErrors()) {
				ModelAndView mView = new ModelAndView();
				//userRegistration = new UserRegistration();
				mView.addObject("userRegistration", userRegistration);
				mView.setViewName("SACreateAcc");
				return mView;
			}
			logger.info(userRegistration); 
			//Email already exists
			boolean emailExists = userDataService.doesEmailExist(userRegistration.getEmail());
			if(emailExists) {
				userRegistration.setFailureMessage(UtilMessages.ACCOUNT_CREATION_FAIL_EMAIL);
				ModelAndView mView = new ModelAndView();
				mView.addObject(userRegistration);
				mView.setViewName("SACreateAcc");
				return mView;
			}
			
			String username = UtilMessages.EMPTY;
			do 
			{
				username = GenerateRandomKey.createUserName(userRegistration.getFirstname(), userRegistration.getLastname());
				//Check if username exists
			}while(userDataService.userExists(username));
			username = username.trim();
			//User Data.
			UserData userdata = new UserData();
			userdata.setUsername(username);
			userdata.setFirstname(userRegistration.getFirstname());
			userdata.setLastname(userRegistration.getLastname());
			userdata.setEmail(userRegistration.getEmail().toLowerCase());
			Date date = new Date(DateAndTime.getDOBfromString(userRegistration.getDob()).getMillis());
			userdata.setBirthdate(date);
			userdata.setPii(UtilMessages.EMPTY);
			userdata.setPii_status(UtilMessages.EMPTY);
			userdata.setSecurityquestion(UtilMessages.EMPTY);
			userdata.setSecurityanswer(UtilMessages.EMPTY);
			userdata.setContact(userRegistration.getContact());
			userdata.setAddress(userRegistration.getAddress());
			
			UserRole userRole = new UserRole();
			
			userRole.setRole(UtilMessages.ROLE_SYS_MANAGER);
			if(!StringUtils.equalsIgnoreCase(UtilMessages.ROLE_SYS_MANAGER, userRegistration.getUserType())) {
				userRole.setRole(UtilMessages.ROLE_REGUAL_EMP);
			}
			userRole.setUsername(username);

			//Create UserData, UserRole, Checking Account
			boolean status = createUser(userdata,userRole);
			String privateKey = UtilMessages.EMPTY;
			if(status) {
					try {
						KeyPair pair = PKIHelper.generateKeys();
						String publicKey = Base64.encodeBase64String(pair.getPublic().getEncoded());
						privateKey =  Base64.encodeBase64String(pair.getPrivate().getEncoded());
						UserPublicKey userKey = new UserPublicKey();
						userKey.setPublicKey(publicKey);
						userKey.setUsername(username);
						//add PKI
						pkiService.addUserPublicKey(userKey);
					} catch (Exception e) {
						logger.info(e.getMessage());
					}
					SendMail sendMail = new SendMail();
					String Subject;
					Subject = "Your username is "+username ;
					String Content ;
					Content = "Dear " + userRegistration.getFirstname()+" "+userRegistration.getLastname()
							+ ",\n\nWelcome to the Bank Of Sun Devils.\n\nYour Account has been created Successfully.\n\nPlease use the username "
							+ ""+username+" to sign up to the account\n\nPlease feel free to reach to the Bank Of Sun Devils"
									+ " and your private key to handle critical transactions is : " + privateKey;
					sendMail.sendMailTo(userRegistration.getEmail(), Subject, Content);
					userRegistration = new UserRegistration();
					userRegistration.setSuccessMessage(UtilMessages.ACCOUNT_CREATION_SUCCESS);
			} else {
				userRegistration.setFailureMessage(UtilMessages.ACCOUNT_CREATION_FAIL);
			}
			
			logger.info("Status : " + status);
			logger.info(userdata);
			logger.info(userRole);
			
			ModelAndView mView = new ModelAndView();
			mView.addObject(userRegistration);
			mView.setViewName("SACreateAcc");
			return mView;
		}
	 
	 @Transactional
		private boolean createUser(UserData userData, UserRole userRole) {
			
			boolean flag = true;
			
			if(userData != null) {
				flag = flag && createUserData(userData);
			}
			if(userRole != null) {
				flag = flag && addRole(userRole);
			}
			return flag;
		}
		
		private boolean createUserData(UserData userData) {
			return userDataService.createAccount(userData);
		}
		
		private boolean addRole(UserRole userRole) {
			return userDataService.addRole(userRole);
		}
}
