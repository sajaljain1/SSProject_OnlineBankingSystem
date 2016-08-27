package edu.asu.cse.softwaresecurity.group6.bosd.controller;

import java.security.KeyPair;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.asu.cse.softwaresecurity.group6.bosd.model.pki.UserPublicKey;
import edu.asu.cse.softwaresecurity.group6.bosd.model.user.Account;
import edu.asu.cse.softwaresecurity.group6.bosd.model.user.Account;
import edu.asu.cse.softwaresecurity.group6.bosd.model.user.Request;
import edu.asu.cse.softwaresecurity.group6.bosd.model.user.Transaction;
import edu.asu.cse.softwaresecurity.group6.bosd.model.user.User;
import edu.asu.cse.softwaresecurity.group6.bosd.model.user.UserData;
import edu.asu.cse.softwaresecurity.group6.bosd.model.user.UserRegistration;
import edu.asu.cse.softwaresecurity.group6.bosd.model.user.UserRole;
import edu.asu.cse.softwaresecurity.group6.bosd.service.AccountService;
import edu.asu.cse.softwaresecurity.group6.bosd.service.RequestService;
import edu.asu.cse.softwaresecurity.group6.bosd.service.SystemManagerService;
import edu.asu.cse.softwaresecurity.group6.bosd.service.SystemManagerService;
import edu.asu.cse.softwaresecurity.group6.bosd.service.TransactionService;
import edu.asu.cse.softwaresecurity.group6.bosd.service.TransactionService;
import edu.asu.cse.softwaresecurity.group6.bosd.service.UserDataService;
import edu.asu.cse.softwaresecurity.group6.bosd.service.UserService;
import edu.asu.cse.softwaresecurity.group6.bosd.service.pki.UserPublicKeyService;
import edu.asu.cse.softwaresecurity.group6.bosd.utils.AccountStatus;
import edu.asu.cse.softwaresecurity.group6.bosd.utils.AccountType;
import edu.asu.cse.softwaresecurity.group6.bosd.utils.DateAndTime;
import edu.asu.cse.softwaresecurity.group6.bosd.utils.GenerateRandomKey;
import edu.asu.cse.softwaresecurity.group6.bosd.utils.PKIHelper;
import edu.asu.cse.softwaresecurity.group6.bosd.utils.SendMail;
import edu.asu.cse.softwaresecurity.group6.bosd.utils.UtilMessages;
@Controller
public class SystemManagerController {
	
	@Autowired
	private UserDataService mUserDataService;
	@Autowired
	private RequestService mRequestService;
	@Autowired
	private SystemManagerService msysManagerService;
	@Autowired
	private UserService muserService;
	@Autowired
	private AccountService mAccountService;
	@Autowired
	private TransactionService mTransactionService;
	@Autowired
	private UserPublicKeyService pkiService;
	
	public UserService getMuserService() {
		return muserService;
	}
	@Autowired(required=true)
	@Qualifier(value = "userServiceImpl")
	public void setMuserService(UserService muserService) {
		this.muserService = muserService;
	}
	public UserPublicKeyService getPkiService() {
		return pkiService;
	}
	@Autowired(required=true)
	@Qualifier(value="userPublicKeyService")
	public void setPkiService(UserPublicKeyService pkiService) {
		this.pkiService = pkiService;
	}

	private static final Logger logger = Logger.getLogger(SystemManagerController.class);
	
	public SystemManagerService getMsysManagerService() {
		return msysManagerService;
	}
	@Autowired(required = true)
	@Qualifier(value = "systemManagerService")
	public void setMsysManagerService(SystemManagerService msysManagerService) {
		this.msysManagerService = msysManagerService;
	}

	@RequestMapping(value = { "/systemManager/delegateTask" }, method = RequestMethod.POST)
	private ModelAndView delegateJob(@RequestParam("requestId") String requestId){
		System.out.println("the request id is " + requestId);
		List<String> regEmployees = mUserDataService.getRegularEmpList();
		String selectedRegEmployee = regEmployees.get(new Random().nextInt(regEmployees.size()));
		mRequestService.updateAssignedToRequest(Long.parseLong(requestId), selectedRegEmployee);
		ModelAndView model = new ModelAndView();
		model.addObject("assignedTo",selectedRegEmployee);
		model.setViewName("RegularEmployee/messages");
		return model;
	}
	
	public UserDataService getmUserDataService() {
		return mUserDataService;
	}

	@Autowired(required =true)
	@Qualifier(value = "userDataServiceImpl")
	public void setmUserDataService(UserDataService mUserDataService) {
		this.mUserDataService = mUserDataService;
	}


	public RequestService getmRequestService() {
		return mRequestService;
	}

	@Autowired(required =true)
	@Qualifier(value = "requestServiceImpl")
	public void setmRequestService(RequestService mRequestService) {
		this.mRequestService = mRequestService;
	}
	
	@RequestMapping(value = "/sysManager/registeruser", method = RequestMethod.GET)
	public ModelAndView showUserRegistration() {
		
		ModelAndView mView = new ModelAndView();
		UserRegistration userRegistration = new UserRegistration();
		mView.addObject("userRegistration", userRegistration);
		mView.setViewName("SystemManager/CreateUserAccount");
		return mView;
	}

	@RequestMapping(value = "/createUserAccount", method = RequestMethod.POST)
	public ModelAndView CreateUserAccount(@Valid UserRegistration userRegistration, BindingResult result,
			Model model) {
		
		if(result.hasErrors()) {
			ModelAndView mView = new ModelAndView();
			mView.addObject("userRegistration", userRegistration);
			mView.setViewName("SystemManager/CreateUserAccount");
			return mView;
		}
		//Email already exists
		boolean emailExists = mUserDataService.doesEmailExist(userRegistration.getEmail());
		if(emailExists) {
			userRegistration.setFailureMessage(UtilMessages.ACCOUNT_CREATION_FAIL_EMAIL);
			ModelAndView mView = new ModelAndView();
			mView.addObject(userRegistration);
			mView.setViewName("SystemManager/CreateUserAccount");
			return mView;
		}
		
		logger.info(userRegistration);
		String accountnum = mAccountService.getLatestAccount();
		Long checkingAc = Long.valueOf(accountnum) + 1;
		Long savingAc = checkingAc + 1;
		String username = UtilMessages.EMPTY;
		do 
		{
			username = GenerateRandomKey.createUserName(userRegistration.getFirstname(), userRegistration.getLastname());
			//Check if username exists
		}while((mAccountService.fetchAccounts(username)).size() != 0);
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
		if(StringUtils.equalsIgnoreCase(userRegistration.getUserType(),UtilMessages.USER_TYPE_MERCHANT)) {
			userdata.setMerchantid("M"+checkingAc);
		}
	
		//Account Creation
		Account account = new Account();
		account.setUsername(username);
		account.setBalance(50);
		account.setAccID(checkingAc.toString());
		account.setAccstatus(AccountStatus.OPEN.getAccountStatus());
		account.setAcctype(AccountType.CHECKING.getAccountType());
		
		UserRole userRole = new UserRole();
		userRole.setRole(UtilMessages.ROLE_NORMAL_USER);
		userRole.setUsername(username);
		
		//Create UserData, UserRole, Checking Account
		boolean status = createUser(userdata,null,userRole,account);
		if(status) {
			account.setAccID(savingAc.toString());
			account.setAccstatus(AccountStatus.OPEN.getAccountStatus());
			account.setAcctype(AccountType.Savings.getAccountType());
			//Create Checking Account
			status = createUser(null,null,null,account);
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
				Content =  "Dear " + userRegistration.getFirstname()+" "+userRegistration.getLastname()
						+ ",\n\nWelcome to the Bank Of Sun Devils.\n\nYour Account has been created Successfully.\n\nPlease use the username "
						+username+" to sign up to the account\n\nPlease feel free to reach to the Bank Of Sun Devils and your private key for Critical Transactions is :"
								+ privateKey;
				sendMail.sendMailTo(userRegistration.getEmail(), Subject, Content);
				userRegistration = new UserRegistration();
				userRegistration.setSuccessMessage(UtilMessages.ACCOUNT_CREATION_SUCCESS);
			} else {
				userRegistration.setFailureMessage(UtilMessages.ACCOUNT_CREATION_FAIL);
			}
		} else {
			userRegistration.setFailureMessage(UtilMessages.ACCOUNT_CREATION_FAIL);
		}
		
		logger.info("Account Creation Status: " + status);
		logger.info(userdata);
		logger.info(userRole);
		
		ModelAndView mView = new ModelAndView();
		mView.addObject(userRegistration);
		mView.setViewName("SystemManager/CreateUserAccount");
		return mView;
	}

	@RequestMapping(value = "/systemManager/accountDelete", method = RequestMethod.POST)
	public ModelAndView DeleteUserAccount(@RequestParam("username") String username, @RequestParam("requestId") String requestId
			, @RequestParam("action") String action) {
		ModelAndView model = new ModelAndView();
		if(action.equals("approve")){
			if(mRequestService.updateRequestStatus(Long.parseLong(requestId), 1, 0)){
				mAccountService.deleteAccount(username);
				mRequestService.updateRequestStatus(Long.parseLong(requestId), 1, 0);
				model.addObject("successMessage","Account deleted successfully");
			}else{
				model.addObject("errorMessage","Account already deleted");
			}
		}else{
			
			if(mRequestService.updateRequestStatus(Long.parseLong(requestId), 0, 0)){
				muserService.makeAccountActive(username);
				model.addObject("successMessage","Account deletion denied");
			}else{
				model.addObject("successMessage","Request already denied");
			}
		}
		model.setViewName("errorMessage/messages");
		return model;
	}
	
	public AccountService getmAccountService() {
		return mAccountService;
	}

	@Autowired(required =true)
	@Qualifier(value = "accountServiceImpl")
	public void setmAccountService(AccountService mAccountService) {
		this.mAccountService = mAccountService;
	}

	public TransactionService getmTransactionService() {
		return mTransactionService;
	}

	@Autowired(required =true)
	@Qualifier(value = "transactionServiceImpl")
	public void setmTransactionService(TransactionService mTransactionService) {
		this.mTransactionService = mTransactionService;
	}
	


	@RequestMapping(value = "/updateProfile", method = RequestMethod.GET)
	private ModelAndView profileUpdate(){
		ModelAndView model = new ModelAndView();
		org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<UserData> userData = mUserDataService.getUserProfileDetails(user.getUsername());
		model.addObject("getUserProfile",userData);
		model.setViewName("SystemManager/updateProfile");
		return model;
	}

	@RequestMapping(value = "/systemManager/updateProfile", method = RequestMethod.POST)
	public ModelAndView updateUserProfile(@RequestParam("firstname") String firstname,@RequestParam("lastname") String lastname,
			@RequestParam("contact") String contact, @RequestParam("address") String address){

		ModelAndView model = new ModelAndView();
		if(firstname.equals("") || lastname.equals("") || contact.equals("") || address.equals("")  ) {
				model.addObject("errorMessage","Opps!! Something is wrong. Please try again.");
		}else{
			long reqId = createRequest(firstname, lastname, contact, address);
			model.addObject("successMessage","Your profile will be updated after approval");
		}
		
		model.setViewName("SystemManager/profileUpdateStatus");
		return model;
	}

	
	
	private long createRequest(String firstname, String lastname, String contact, String address) {
		Request request = new Request();

		org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		request.setUsername(user.getUsername());
		request.setTransactionId((long) 0);
		request.setType("Profile Update");
		List<String> admins = mUserDataService.getSystemAdminsList();
		request.setAssignedTo(admins.get(new Random().nextInt(admins.size())));	
		request.setUpdatedFirstName(firstname);
		request.setUpdatedLastName(lastname);
		request.setUpdatedPhone(contact);
		request.setUpdatedAddress(address);
		//request.setUpdatedEmail(email);
		request.setCreated_at(new Timestamp(Calendar.getInstance().getTimeInMillis()));		
		request.setApproved("0");
		request.setPending("1");
		return mRequestService.createprofileUpdateRequest(request);
	}
	
	
	@Transactional
	private boolean createUser(UserData userData, User user, UserRole userRole, Account account) {
		
		boolean flag = true;
		
		if(userData != null) {
			flag = flag && createUserData(userData);
		}
		if(userRole != null) {
			flag = flag && addRole(userRole);
		}
		if(account != null) {
			flag = flag && createAccount(account);
		}
		return flag;
	}
	
	private boolean createAccount(Account account){
		return mAccountService.createAccount(account);
	}
	
	private boolean createUserData(UserData userData) {
		return mUserDataService.createAccount(userData);
	}
	
	private boolean addRole(UserRole userRole) {
		return mUserDataService.addRole(userRole);
	}
	
	@RequestMapping(value = { "/workOnRequest"}, method = RequestMethod.POST)
	private ModelAndView WorkOnRequest(@RequestParam("requestId") String requestId){
		System.out.println("the request id is " + requestId);
		if(mRequestService==null) System.out.println("***************************************8requestService is null");
		Request req = mRequestService.getRequest(Long.parseLong(requestId));
		ModelAndView model = new ModelAndView();
		System.out.println("updated address in request is " + req.getUpdatedAddress());
		model.addObject("Request",req);
		UserData userData = mUserDataService.getUserDetails(req.getUsername());
		model.addObject("UserData",userData);
		model.setViewName("SystemManager/profileRequest");
		return model;
	}
	
	@RequestMapping(value = { "/systemManager/denyProfileChange"}, method = RequestMethod.POST)
	private ModelAndView denyProfileRequest(@RequestParam("requestId") String requestId){
		mRequestService.updateRequestStatus(Long.parseLong(requestId), 0, 0);	
		ModelAndView model = new ModelAndView();
		if(mRequestService.updateRequestStatus(Long.parseLong(requestId), 0, 0)){
			model.addObject("successMessageRequest","you denied the request " + requestId);
		}else{
			model.addObject("successMessageRequest","you already denied the request " + requestId);
		}
		model.setViewName("RegularEmployee/messages");
		return model;
	}
	
	@RequestMapping(value = "/systemManager/approveCriticalTransaction", method = RequestMethod.POST)
	private ModelAndView approveTransaction(@RequestParam("transactionId") String transactionId,@RequestParam("type") String requestType,
			@RequestParam("transactionType") String transactionType, @RequestParam("amount") String amount, @RequestParam("requestId") String requestId, @RequestParam("userName") String userName,
			@RequestParam("accountTo") String accountTo, @RequestParam("accountFrom") String accountFrom){
		ModelAndView model = new ModelAndView();
		if(requestType.equals("critical") && transactionType.equals("credit")){
			System.out.println("money to be credited "+accountTo);
			Transaction trans = mTransactionService.getTransaction(Long.parseLong(transactionId));
			System.out.println("money related to transaction is"+trans.getAmount());
			Double balance = mAccountService.getBalance(accountTo);
			System.out.println("balance in controller is " + balance);
			balance += Double.parseDouble(amount);
			
				if(mRequestService.updateRequestStatus(Long.parseLong(requestId), 1, 0)){
					if(mAccountService.updateBalance(accountTo, balance)){
					model.addObject("successMessage","Request " + requestId + " approved successfully");
				}
			}else{
				model.addObject("errorMessage","Request " + requestId + " was not approved. Please contact admin");
			}
		}else if(requestType.equals("critical") && transactionType.equals("debit")){
			if(mRequestService.updateRequestStatus(Long.parseLong(requestId), 1, 0)){
				model.addObject("successMessage","Request " + requestId + " approved successfully");
			}else{
				model.addObject("successMessage","Request " + requestId + " already approved successfully");
			}
		}
		else if(requestType.equals("critical") && transactionType.equals("transfer")){
			System.out.println("money to be credited "+accountTo);
			Transaction trans = mTransactionService.getTransaction(Long.parseLong(transactionId));
			System.out.println("money related to transaction is"+trans.getAmount());
			Double balance = mAccountService.getBalance(accountTo);
			System.out.println("balance in controller is " + balance);
			balance += Double.parseDouble(amount);
			
				if(mRequestService.updateRequestStatus(Long.parseLong(requestId), 1, 0)){
					if(mAccountService.updateBalance(accountTo, balance)){
						mTransactionService.updateTransactionStatus(Long.parseLong(transactionId), true, false);
						model.addObject("successMessage","Request " + requestId + " approved successfully");
				}else{
					model.addObject("successMessage","Request " + requestId + " already approved successfully");
				}
				
			}else{
				model.addObject("errorMessage","Request " + requestId + " was not approved. Please contact admin");
			}
		}
		model.setViewName("RegularEmployee/messages");
		return model;
	}
	
	@RequestMapping(value = "/systemManager/denyCriticalTransaction", method = RequestMethod.POST)
	private ModelAndView denyTransaction(@RequestParam("transactionId") String transactionId,@RequestParam("type") String requestType,
			@RequestParam("transactionType") String transactionType,@RequestParam("amount") String amount, @RequestParam("requestId") String requestId, @RequestParam("userName") String userName,
			@RequestParam("accountTo") String accountTo, @RequestParam("accountFrom") String accountFrom,
			RedirectAttributes redir){
		ModelAndView model = new ModelAndView();
		if(requestType.equals("critical") && transactionType.equals("debit")){
			System.out.println("money to be credited "+accountFrom);
			Transaction trans = mTransactionService.getTransaction(Long.parseLong(transactionId));
			System.out.println("money related to transaction is"+trans.getAmount());
			Double balance = mAccountService.getBalance(accountFrom);
			System.out.println("balance in controller is " + balance);
			balance += Double.parseDouble(amount);
			if(mRequestService.updateRequestStatus(Long.parseLong(requestId), 1, 0)){
				if(mAccountService.updateBalance(accountFrom, balance)){
					model.addObject("successMessage","Request " + requestId + " denied successfully");
				}else{
					model.addObject("errorMessage","Request " + requestId + " already denied successfully");
				}
			}else{
				model.addObject("errorMessage","Request " + requestId + " was not denied. Please contact admin");
			}
		}else if(requestType.equals("critical") && transactionType.equals("credit")){
			mRequestService.updateRequestStatus(Long.parseLong(requestId), 0, 0);
			model.addObject("successMessage","Request " + requestId + " denied successfully");
		}
		else if(requestType.equals("critical") && transactionType.equals("transfer")){
			System.out.println("money to be credited "+accountFrom);
			Transaction trans = mTransactionService.getTransaction(Long.parseLong(transactionId));
			System.out.println("money related to transaction is"+trans.getAmount());
			Double balance = mAccountService.getBalance(accountFrom);
			System.out.println("balance in controller is " + balance);
			balance += Double.parseDouble(amount);
			if(mRequestService.updateRequestStatus(Long.parseLong(requestId), 1, 0)){
				if(mAccountService.updateBalance(accountFrom, balance)){
					mTransactionService.updateTransactionStatus(Long.parseLong(transactionId), false, false);
					model.addObject("successMessage","Request " + requestId + " denied successfully");
				}else{
					model.addObject("successMessage","Request " + requestId + " already denied successfully");
				}
			}else{
				model.addObject("errorMessage","Request " + requestId + " was not denied. Please contact admin");
			}
		}
		model.setViewName("RegularEmployee/messages");
		return model;
	}
	
	@RequestMapping(value = { "/systemManager/approveProfileChange"}, method = RequestMethod.POST)
	private ModelAndView approveProfileChange(@RequestParam("requestId") String requestId){
		ModelAndView model = new ModelAndView();
		if(mRequestService != null){
			
		}
		Request req = mRequestService.getRequest(Long.parseLong(requestId));
		UserData userData = mUserDataService.getUserDetails(req.getUsername());
		if(req.getUpdatedFirstName() != null){
			userData.setFirstname(req.getUpdatedFirstName());
		}if(req.getUpdatedLastName() != null){
			userData.setLastname(req.getUpdatedLastName());
		}if(req.getUpdatedAddress() != null){
			userData.setAddress(req.getUpdatedAddress());
		}if(req.getUpdatedPhone() != null){
			userData.setContact(req.getUpdatedPhone());
		}if(req.getUpdatedEmail() != null){
			userData.setEmail(req.getUpdatedEmail());
		}
		mUserDataService.updateUserProfile(userData);
		model.addObject("successMessageRequest", "Request " + requestId + " was approved successfully");
		mRequestService.updateRequestStatus(Long.parseLong(requestId), 1,0);
		model.setViewName("RegularEmployee/messages");
		return model;
	}
}
