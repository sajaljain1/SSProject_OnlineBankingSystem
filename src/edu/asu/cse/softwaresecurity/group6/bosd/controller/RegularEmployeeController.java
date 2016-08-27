package edu.asu.cse.softwaresecurity.group6.bosd.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import edu.asu.cse.softwaresecurity.group6.bosd.model.user.SearchUserData;
import edu.asu.cse.softwaresecurity.group6.bosd.model.user.Account;
import edu.asu.cse.softwaresecurity.group6.bosd.model.user.Request;
import edu.asu.cse.softwaresecurity.group6.bosd.model.user.RequestTransaction;
import edu.asu.cse.softwaresecurity.group6.bosd.model.user.RequestUserData;
import edu.asu.cse.softwaresecurity.group6.bosd.model.user.Transaction;
import edu.asu.cse.softwaresecurity.group6.bosd.model.user.UserData;
import edu.asu.cse.softwaresecurity.group6.bosd.service.AccountService;
import edu.asu.cse.softwaresecurity.group6.bosd.service.RequestService;
import edu.asu.cse.softwaresecurity.group6.bosd.service.TransactionService;
import edu.asu.cse.softwaresecurity.group6.bosd.service.UserDataService;
import edu.asu.cse.softwaresecurity.group6.bosd.utils.UtilMessages;

@Controller
public class RegularEmployeeController {
	
	private RequestService mRequestService;
	private TransactionService mTransactionService;
	private AccountService mAccountService;
	private UserDataService mUserDataService;
	
	private static final Logger logger = Logger.getLogger(RegularEmployeeController.class);
	@RequestMapping(value = "/requests", method = RequestMethod.GET)
	public ModelAndView routeToTransactions(){
		logger.info("In Requests page");
		ModelAndView model = new ModelAndView();
		////System.out.println(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Collection<GrantedAuthority> grantedAuth = user.getAuthorities();
		//System.out.println(grantedAuth.size());
		for (GrantedAuthority grantedAuthority : grantedAuth) {
			 //System.out.println(grantedAuthority.getAuthority());
			 if(grantedAuthority.getAuthority().equals("ROLE_REGULAR_EMP")){
				 	//System.out.println("in reg emplyee controller");
					List<RequestTransaction> requestTransactions = new ArrayList<RequestTransaction>();
					List<Request> requests= mRequestService.getRequestForUser(getCurrentUserName());
					List<RequestUserData> requestUserDatas = new ArrayList<RequestUserData>();
					for(int i=0;i<requests.size();i++){
						if(requests.get(i).getType().equalsIgnoreCase("credit") || requests.get(i).getType().equalsIgnoreCase("debit")){
							Long transactionId = requests.get(i).getTransactionId();
							Transaction trans = getTransactionById(transactionId);
							if(trans == null){
								model.addObject("transactionNotFoundMessage","There was no transaction associated with Request ID " + requests.get(i).getRequetId());
							}
							//System.out.println(trans.getCreated_at());
							RequestTransaction rt = new RequestTransaction();
							rt.setRequest(requests.get(i));
							rt.setTransaction(trans);
							requestTransactions.add(rt);
							model.addObject("requestTransactions", requestTransactions);
						}else if(requests.get(i).getType().equalsIgnoreCase("Profile Update")) {
							 Request req = requests.get(i);
							 UserData usr = mUserDataService.getUserDetails(req.getUsername());
							 RequestUserData reqUserData = new RequestUserData();
							 reqUserData.setRequest(req);
							 reqUserData.setUserdata(usr);
							 requestUserDatas.add(reqUserData);
							 if(mRequestService.permittedOn(req.getUsername(),getCurrentUserName(),"Profile Permission")){
								 model.addObject("permitted","yes");
							 }else{
								 model.addObject("permitted","no");
							 }
							 model.addObject("requestUserDataRegEmployee",requestUserDatas);
						}
					}
					 List<Request> merchantRequests = mRequestService.getMerchantRequestForUser(getCurrentUserName());
					 List<RequestTransaction> reqTrans = new ArrayList<RequestTransaction>();
					 //System.out.println("number of requests for "+getCurrentUserName()+" is" + requests.size());
					 for(int i=0;i<merchantRequests.size(); i++){
						 Transaction trans = mTransactionService.getTransaction(merchantRequests.get(i).getTransactionId());
						 RequestTransaction reqTrans1 = new RequestTransaction();
						 reqTrans1.setRequest(merchantRequests.get(i));
						 reqTrans1.setTransaction(trans);
						 reqTrans.add(reqTrans1);
					 }
					 model.addObject("merchantRequests",reqTrans);
			 }else if(grantedAuthority.getAuthority().equals("ROLE_NORMAL_USER")){
				 //Getting all the merchant permissions
				 //System.out.println("in regular customer");
				 List<Request> requests = mRequestService.getMerchantRequestForUser(getCurrentUserName());
				 List<RequestTransaction> reqTrans = new ArrayList<RequestTransaction>();
				 //System.out.println("number of requests for "+getCurrentUserName()+" is" + requests.size());
				 for(int i=0;i<requests.size(); i++){
					 Transaction trans = mTransactionService.getTransaction(requests.get(i).getTransactionId());
					 RequestTransaction reqTrans1 = new RequestTransaction();
					 reqTrans1.setRequest(requests.get(i));
					 reqTrans1.setTransaction(trans);
					 reqTrans.add(reqTrans1);
				 }
				 model.addObject("merchantRequests",reqTrans);
				 //Getting the requests for profile permissions
				 //System.out.println("in system admin requests");
				 List<Request> profilePermissionRequests = mRequestService.getRequestOfTypeForUser("Profile Permission",getCurrentUserName());
				 if(profilePermissionRequests != null){
					 model.addObject("profilePermissionRequests",profilePermissionRequests);
				 }
				 List<Request> transactionPermissionRequests = mRequestService.getRequestOfTypeForUser("Transaction Permission",getCurrentUserName());
				 ////System.out.println("transaction requests for " + getCurrentUserName() + " are " + transactionPermissionRequests.size());
				 if(transactionPermissionRequests != null){
					 model.addObject("transactionPermissionRequests",transactionPermissionRequests);
				 }
			 }else if(grantedAuthority.getAuthority().equals("ROLE_SYS_MANAGER")){
				 //System.out.println("in system manager");
				 List<Request> profileRequests = mRequestService.getAccountRequestForUser(getCurrentUserName());
				 List<RequestUserData> requestUserDatas = new ArrayList<RequestUserData>();
				 for(int i=0;i<profileRequests.size();i++){
					 Request req = profileRequests.get(i);
					 UserData usr = mUserDataService.getUserDetails(req.getUsername());
					 RequestUserData reqUserData = new RequestUserData();
					 reqUserData.setRequest(req);
					 reqUserData.setUserdata(usr);
					 requestUserDatas.add(reqUserData);
				 }
				 model.addObject("requestUserData",requestUserDatas);
				 List<RequestTransaction> reqTrans = new ArrayList<RequestTransaction>();
				 List<Request> criticalPermissionRequests = mRequestService.getRequestOfTypeForUser("critical",getCurrentUserName());
				 if(criticalPermissionRequests!=null){
					 for(int i=0;i<criticalPermissionRequests.size();i++){
						 Transaction tr = mTransactionService.getTransaction(criticalPermissionRequests.get(i).getTransactionId());
						 RequestTransaction reqTransa = new RequestTransaction();
						 reqTransa.setRequest(criticalPermissionRequests.get(i));
						 reqTransa.setTransaction(tr);
						 reqTrans.add(reqTransa);
					 }
					 model.addObject("criticalTansactionRequests",reqTrans);
				 }
				 List<Request> deletionRequests = mRequestService.getRequestOfTypeForUser("deleteAccount", getCurrentUserName());
				 model.addObject("deletionRequests",deletionRequests);
			 }else if(grantedAuthority.getAuthority().equals("ROLE_ADMIN")){
				 //System.out.println("in system admin requests");
				 //List<Request> profilePermissionRequests = mRequestService.getRequestOfTypeForUser("Profile Permission",getCurrentUserName());
				 List<Request> profilePermissionRequests = new ArrayList<Request>();
				 List<Request> transactionPermissionRequests = new ArrayList<Request>();
				 List<Request> requests= mRequestService.getRequestForUser(getCurrentUserName());
				 List<RequestUserData> requestUserDatas = new ArrayList<RequestUserData>();
//				 List<RequestUserData> resUserData = new ArrayList<RequestUserData>();
				 for(int i=0;i<requests.size();i++){
					 if(requests.get(i).getType().equals("Profile Permission")){
						 profilePermissionRequests.add(requests.get(i));
//						 UserData usr = mUserDataService.getUserDetails(requests.get(i).getUsername());
//						 RequestUserData reqUser = new RequestUserData();
//						 reqUser.setUserdata(usr);
//						 reqUser.setRequest(requests.get(i));
//						 resUserData.add(reqUser);
					 }else if(requests.get(i).getType().equals("Transaction Permission")){
						 transactionPermissionRequests.add(requests.get(i));
					 }else if(requests.get(i).getType().equals("Profile Update")) {
						 Request req = requests.get(i);
						 UserData usr = mUserDataService.getUserDetails(req.getUsername());
						 RequestUserData reqUserData = new RequestUserData();
						 reqUserData.setRequest(req);
						 reqUserData.setUserdata(usr);
						 requestUserDatas.add(reqUserData);
						 if(mRequestService.permittedOn(req.getUsername(),getCurrentUserName(),"Profile Permission")){
							 model.addObject("permitted","yes");
						 }else{
							 model.addObject("permitted","no");
						 }
						// model.addObject("requestUserDataRegEmployee",requestUserDatas);
					}
				 }
//				 if(resUserData!=null){
//					 model.addObject("requestUserDataRegEmployee",resUserData);
//				 }
				 if(profilePermissionRequests != null){
					 model.addObject("profilePermissionRequests",profilePermissionRequests);
				 }if(transactionPermissionRequests != null){
					 model.addObject("transactionPermissionRequests",transactionPermissionRequests);
				 }if(profilePermissionRequests != null){
					 model.addObject("requestUserDataRegEmployee",requestUserDatas);
				 }
				 model.addObject("permitted","yes");
			 }
			 model.addObject("roleOfUser",grantedAuthority.getAuthority());
		 }
		model.setViewName("RegularEmployee/requests");
		return model;
	}
	
	
	@RequestMapping(value = "/regEmployee/raiseArrovalRequest", method = RequestMethod.POST)
	private ModelAndView approveRequest(@RequestParam("requestId") String requestId,@RequestParam("approver") String approver,
			@RequestParam("desc") String des, @RequestParam("username") String username){
		logger.info("In raise approval request page");
		ModelAndView model = new ModelAndView();
		List<String> admins = mUserDataService.getSystemAdminsList();
		Request req = new Request();
		req.setUsername(username);
		req.setType("Profile Permission");
		String assignedTo = "";
		if(approver.equals("systemAdmin")){
			assignedTo = admins.get(new Random().nextInt(admins.size()));
		}else{
			assignedTo = username;
		}
		req.setRemarks(des);
		req.setAssignedTo(assignedTo);
		req.setApproved("0");
		req.setPending("1");
		req.setPermissionto(getCurrentUserName());
		
			if(mRequestService.updateRequestStatus(Long.parseLong(requestId), 0, 1)){
				if(mRequestService.createRequest(req)){
				model.addObject("assignedTo",assignedTo);
				model.addObject("username",username);
				model.addObject("successMessage","request raised for permission on account");
			}else{
				model.addObject("errorMessage","request already raised for the account");
			}
		}else{
			model.addObject("errorMessage","There was some problem, please contact admin");
		}
		model.setViewName("RegularEmployee/messages");
		return model;
	}
	
	@RequestMapping(value = "/regEmployee/approveTransaction", method = RequestMethod.POST)
	private ModelAndView approveTransaction(@RequestParam("transactionId") String transactionId,@RequestParam("requestType") String requestType,
			@RequestParam("transactionType") String transactionType, @RequestParam("amount") String amount, @RequestParam("requestId") String requestId, @RequestParam("userName") String userName,
			@RequestParam("accountTo") String accountTo, @RequestParam("accountFrom") String accountFrom){
		logger.info("In approve transaction page");
		ModelAndView model = new ModelAndView();
		if(requestType.equals("credit")){
			//System.out.println("money to be credited "+accountFrom);
			Transaction trans = getTransactionById(Long.parseLong(transactionId));
			//System.out.println("money related to transaction is"+trans.getAmount());
			Double balance = mAccountService.getBalance(accountFrom);
			//System.out.println("balance in controller is " + balance);
			balance += Double.parseDouble(amount);
			if(mRequestService.updateRequestStatus(Long.parseLong(requestId), 1, 0)){
			if(mAccountService.updateBalance(accountFrom, balance)){
				
					mTransactionService.updateTransactionStatus(Long.parseLong(transactionId),true,false);
					model.addObject("successMessage","Request " + requestId + " approved successfully");
				}
				else{
					model.addObject("errorMessage","Request " + requestId + " was already approved.");
				}
			}else{
				model.addObject("errorMessage","Request " + requestId + " was not approved. Please contact admin");
			}
		}else if(requestType.equals("debit")){
			if(mRequestService.updateRequestStatus(Long.parseLong(requestId), 1, 0)){
				mTransactionService.updateTransactionStatus(Long.parseLong(transactionId),true,false);
				model.addObject("successMessage","Request " + requestId + " approved successfully");
			}else{
				model.addObject("errorMessage","Request " + requestId + " already approved successfully");
			}
		}else if(requestType.equalsIgnoreCase("merchant")){
			double toAccountBalance = mAccountService.getBalance(accountTo);
			double fromAccountBalance = mAccountService.getBalance(accountFrom);
			toAccountBalance = toAccountBalance + Double.parseDouble(amount);
			if(mRequestService.updateRequestStatus(Long.parseLong(requestId), 1, 0)){
				mAccountService.updateBalance(accountTo, toAccountBalance);
				model.addObject("successMessage","Request " + requestId + " approved successfully");
			}else{
				model.addObject("errorMessage","Request " + requestId + " approved successfully");
			}
		}
		model.setViewName("RegularEmployee/messages");
		return model;
	}
	
	
	@RequestMapping(value = "/regularEmployee/approveMerchantTransaction", method = RequestMethod.POST)
	private ModelAndView approveMerchantTransaction(@RequestParam("requestId") String requestId, @RequestParam("transactionId") String transactionId){
		ModelAndView model = new ModelAndView();
		Transaction trans = mTransactionService.getTransaction(Long.parseLong(transactionId));
		double amount = trans.getAmount();
		double balance = mAccountService.getBalance(trans.getAccount_to());
		if(balance<amount){
			mRequestService.updateRequestStatus(Long.parseLong(requestId), 0, 0);
			model.addObject("errorMessage","Amount is more than balance");
			mTransactionService.updateTransactionStatus(Long.parseLong(transactionId), false,false);
			model.setViewName("RegularEmployee/messages");
			return model;
		}else{
			
			mRequestService.updateRequestStatus(Long.parseLong(requestId), 1, 0);
			mTransactionService.updateTransactionStatus(Long.parseLong(transactionId), true,false);
			double merAmt = mAccountService.getBalance(trans.getAccount_from());
			double cusAmt = mAccountService.getBalance(trans.getAccount_to());
			double totAmnt = merAmt + amount;
			//System.out.println(merAmt+"**************************"+amount+"....."+totAmnt);
			mAccountService.updateBalance(trans.getAccount_from(),totAmnt);
			mAccountService.updateBalance(trans.getAccount_to(), (cusAmt-amount));
			model.addObject("successMessage","Request approved");
		}
		model.setViewName("RegularEmployee/messages");
		return model;
	}
			
	@RequestMapping(value = "/regularEmployee/askManager", method = RequestMethod.POST)
	private ModelAndView askManager(@RequestParam("requestId") String requestId){
		ModelAndView model = new ModelAndView();
		List<String> managers = mUserDataService.getSystemManagerList();
		if(managers.size() > 0){
			String assignedTo = managers.get(new Random().nextInt(managers.size()));
			mRequestService.updateAssignedToRequest(Long.parseLong(requestId), assignedTo);
			model.addObject("successMessage","The request was assigned to " + assignedTo + " ");
		}else{
			model.addObject("errorMessage","there was no manager found in the system");
		}
		model.setViewName("RegularEmployee/messages");
		return model;
	}
	
	@RequestMapping(value = "/regEmployee/denyTransaction", method = RequestMethod.POST)
	private ModelAndView denyTransaction(@RequestParam("transactionId") String transactionId,@RequestParam("requestType") String requestType,
			@RequestParam("transactionType") String transactionType,@RequestParam("amount") String amount, @RequestParam("requestId") String requestId, @RequestParam("userName") String userName,
			@RequestParam("accountTo") String accountTo, @RequestParam("accountFrom") String accountFrom,
			RedirectAttributes redir){
		logger.info("In deny transaction page");
		ModelAndView model = new ModelAndView();
		if(requestType.equals("debit")){
			//System.out.println("money to be credited "+accountTo);
			Transaction trans = getTransactionById(Long.parseLong(transactionId));
			//System.out.println("money related to transaction is"+trans.getAmount());
			Double balance = mAccountService.getBalance(accountFrom);
			//System.out.println("balance in controller is " + balance);
			balance += Double.parseDouble(amount);
			if(mRequestService.updateRequestStatus(Long.parseLong(requestId), 0, 0)){
			if(mAccountService.updateBalance(accountFrom, balance)){
				
					mTransactionService.updateTransactionStatus(Long.parseLong(transactionId),false,false);
					model.addObject("successMessage","Request " + requestId + " denied successfully");
				}else{
					model.addObject("successMessage","Request " + requestId + " already denied successfully");
				}
			}else{
				model.addObject("errorMessage","Request " + requestId + " was not denied. Please contact admin");
			}
		}else if(requestType.equals("credit")){
			if(mRequestService.updateRequestStatus(Long.parseLong(requestId), 0, 0)){
				mTransactionService.updateTransactionStatus(Long.parseLong(transactionId),false,false);
				model.addObject("successMessage","Request " + requestId + " denied successfully");
			}else{
				model.addObject("successMessage","Request " + requestId + " already denied successfully");
			}
		}else if(requestType.equals("merchant")){
			//double toAccountBalance = mAccountService.getBalance(accountTo);
			double fromAccountBalance = mAccountService.getBalance(accountFrom);
			fromAccountBalance = fromAccountBalance + Double.parseDouble(amount);
			if(mRequestService.updateRequestStatus(Long.parseLong(requestId), 1, 0)){
				mAccountService.updateBalance(accountFrom, fromAccountBalance);
				model.addObject("successMessage","Request " + requestId + " denied successfully");
				redir.addFlashAttribute("successMessage","Request " + requestId + " denied successfully");
			}else{
				model.addObject("successMessage","Request " + requestId + " already denied successfully");
			}
		}
		model.setViewName("RegularEmployee/messages");
		return model;
	}
	
	@RequestMapping(value = "/regularEmployee/askPermission", method = RequestMethod.POST)
	private ModelAndView askPermission(@RequestParam("requestId") String requestId,
			@RequestParam("username") String username){
		logger.info("In ask permission page");
		ModelAndView model = new ModelAndView();
		//System.out.println("asking permission to access");
		
		Request req = mRequestService.getRequest(Long.parseLong(requestId));
		model.addObject("request",req);
		model.addObject("requestDesc","Raising a request to access account of " + username + 
				" as per the requestID " + requestId);
		model.addObject("username",username);
		model.setViewName("RegularEmployee/requestPermission");
		return model;
	}
	
	@RequestMapping(value = "/approveAccountPermission", method = RequestMethod.POST)
	private ModelAndView approveAccountPermission(@RequestParam("requestId") String requestId, @RequestParam("permissionto") String permissionto,
			@RequestParam("username") String username){
		ModelAndView model = new ModelAndView();
		Request req = mRequestService.getRequest(Long.parseLong(requestId));
		if(mRequestService.updateRequestStatus(Long.parseLong(requestId), 1, 0)){
			//mRequestService.updateAssignedToRequest(Long.parseLong(requestId), permissionto);
			model.addObject("successMessage","Permission was given to " + req.getPermissionto() + 
					" on username " + username);
			//mRequestService.updateRequestStatus(Long.parseLong(requestId), 1, 0);
		}else{
			model.addObject("errorMessage","unable to give permission to " + permissionto + 
					" on username " + username + ". Please contact admin");
		}
		model.setViewName("RegularEmployee/messages");
		return model;
	}
	
	@RequestMapping(value = "/denyAccountPermission", method = RequestMethod.POST)
	private ModelAndView denyAccountPermission(@RequestParam("requestId") String requestId, @RequestParam("permissionto") String permissionto,
			@RequestParam("username") String username){
		logger.info("In deny account permission page");
		ModelAndView model = new ModelAndView();
		model.addObject("successMessage","Permission was denied to " + permissionto + 
					" on username " + username);
		model.setViewName("RegularEmployee/messages");
		return model;
	}
	
	@RequestMapping(value = "/accountTransaction", method = RequestMethod.GET)
	private ModelAndView routeToAccountTransactions(){
		logger.info("In account transaction page");
		ModelAndView model = new ModelAndView();
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Collection<GrantedAuthority> grantedAuth = user.getAuthorities();
		String role = UtilMessages.EMPTY;
		for (GrantedAuthority grantedAuthority : grantedAuth) {
				role = grantedAuthority.getAuthority();
		}
		model.addObject("role",role);
		model.setViewName("RegularEmployee/accountTransaction");
		return model;
	}
	
	@RequestMapping(value = "/regEmployee/searchUser", method = RequestMethod.POST)
	private ModelAndView searchUser(@RequestParam("username") String username){
		logger.info("In search user page");
		ModelAndView model = new ModelAndView();
		if(username.equals("")){
			model.addObject("errorMessage","Please enter a user name to search for");
			model.setViewName("RegularEmployee/messages");
			return model;
		}else{
			User user1 = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Collection<GrantedAuthority> grantedAuth = user1.getAuthorities();
			String role = "";
			//System.out.println(grantedAuth.size());
			for (GrantedAuthority grantedAuthority : grantedAuth) {
					role = grantedAuthority.getAuthority();
			}
			UserData user = mUserDataService.getUserDetails(username);
			if(user == null){
				model.addObject("errorMessage","username not found");
				model.setViewName("RegularEmployee/messages");
				return model;
			}
			model.addObject("firstname",user.getFirstname());
			model.addObject("lastname",user.getLastname());
			model.addObject("username",username);
			model.addObject("role",role);
			List<Account> accounts = mAccountService.fetchAccounts(username);
			List<String> accountNumbers = new ArrayList<String>();
			if(accounts == null){
				model.addObject("errorMessage","No Accounts were found for this user");
				model.setViewName("RegularEmployee/userDetails");
				return model;
			}else{
				for(int i=0;i<accounts.size();i++){
					//System.out.println("account number :" + accounts.get(i).getAccID());
					accountNumbers.add(accounts.get(i).getAccID());
				}
				model.addObject("accounts",accountNumbers);
			}
			if(mRequestService.permittedOn(username,getCurrentUserName(),"Profile Permission")){
				model.addObject("accountPermission","yes");
			}else{
				model.addObject("accountPermission","no");
			}
			if(mRequestService.permittedOn(username,getCurrentUserName(),"Transaction Permission")){
				model.addObject("transactionPermission","yes");
			}else{
				model.addObject("transactionPermission","no");
			}
			model.setViewName("RegularEmployee/userDetails");
			return model;
		}
	}
	
	@RequestMapping(value = "/regEmployee/searchUserByName", method = RequestMethod.POST)
	private ModelAndView searchByName(@RequestParam("firstname") String firstname, @RequestParam("lastname") String lastname){
		ModelAndView model = new ModelAndView();
		if(firstname.equals("") || lastname.equals("")){
			model.addObject("errorMessage","One of the details for the search was missing");
			model.setViewName("RegularEmployee/messages");
			return model;
		}
		List<String> usernames = mUserDataService.getUsernameByFirstAndLast(firstname,lastname);
		if(usernames.size() == 0){
			model.addObject("errorMessage","There was no user found by that name");
			model.setViewName("RegularEmployee/messages");
			return model;
		}
		List<SearchUserData> searchUserData = new ArrayList<SearchUserData>();
		for(int i=0;i<usernames.size();i++){
			SearchUserData sud = new SearchUserData();
			sud.setFirstName(firstname);
			sud.setLastName(lastname);
			UserData usr = mUserDataService.getUserDetails(usernames.get(i));
			sud.setCreationTime(usr.getUpdated_at().toString());
			List<Account> accounts = mAccountService.fetchActiveAccounts(usernames.get(i));
			if(accounts != null && accounts.size() !=0 ) {
				sud.setAccount1(accounts.get(0).getAccID());
				sud.setAccount2(accounts.get(1).getAccID());
			}
			boolean accountPermitted = mRequestService.permittedOn(usernames.get(i), getCurrentUserName(), "Profile Permission");
			//System.out.println(accountPermitted);
			if(accountPermitted){
				sud.setAccountPermission("yes");
			}else{
				sud.setAccountPermission("no");
			}
			boolean transactionPermitted = mRequestService.permittedOn(usernames.get(i), getCurrentUserName(), "Transaction Permission");
			if(transactionPermitted){
				sud.setTransactionPermission("yes");
			}else{
				sud.setTransactionPermission("no");
			}
			sud.setUsername(usernames.get(i));
			searchUserData.add(sud);
		}
		model.addObject("searchUserData",searchUserData);
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Collection<GrantedAuthority> grantedAuth = user.getAuthorities();
		//System.out.println(grantedAuth.size());
		for (GrantedAuthority grantedAuthority : grantedAuth) {
			model.addObject("role",grantedAuthority.getAuthority());
		}
		model.setViewName("RegularEmployee/searchResults");
		return model;
	}
	
	@RequestMapping(value = "/regularEmployee/viewAccountDetails", method = RequestMethod.POST)
	private ModelAndView viewAccountDetails(@RequestParam("username") String username){
		logger.info("In account details page");
		ModelAndView model = new ModelAndView();
		UserData usr = mUserDataService.getUserDetails(username);
		model.addObject("UserData",usr);
		model.setViewName("RegularEmployee/accountDetails");
		return model;
	}
	
	@RequestMapping(value = "/regularEmployee/viewTransactionDetails", method = RequestMethod.POST)
	private ModelAndView viewTransactionDetails(@RequestParam("username") String username){
		logger.info("In view transaction page");
		ModelAndView model = new ModelAndView();
		List<Account> accounts = mAccountService.fetchAccounts(username);
		List<Transaction> transactions = new ArrayList<Transaction>();
		for(int i=0;i<accounts.size();i++){
			transactions.addAll(mTransactionService.getTransactionList(accounts.get(i).getAccID()));
		}
		model.addObject("transactions",transactions);
		model.setViewName("RegularEmployee/transactionDetails");
		return model;
	}
	
	@RequestMapping(value = "/regularEmployee/askAccountPermission", method = RequestMethod.POST)
	private ModelAndView viewAccountDetailsPermission(@RequestParam("username") String username, @RequestParam("type") String type){
		logger.info("In ask account permission page");
		ModelAndView model = new ModelAndView();
		model.addObject("Title","Raising request for Account viewing permission");
		model.addObject("username",username);
		model.addObject("desc","Request for viewing permission on account " + username + " to " + getCurrentUserName());
		model.addObject("type",type);
		model.setViewName("RegularEmployee/reqPermission");
		return model;
	}
	
	@RequestMapping(value = "/regularEmployee/askTransactionPermission", method = RequestMethod.POST)
	private ModelAndView viewTransactionDetailsPermission(@RequestParam("username") String username, @RequestParam("type") String type){
		ModelAndView model = new ModelAndView();
		model.addObject("Title","Raising request for Account viewing permission");
		model.addObject("desc","Request for viewing permission on account " + username + " to " + getCurrentUserName());
		model.addObject("username",username);
		model.addObject("type",type);
		model.setViewName("RegularEmployee/reqPermission");
		return model;
	}
	
	@RequestMapping(value = "/regEmployee/raiseATArrovalRequest", method = RequestMethod.POST)
	private ModelAndView raiseATRequest(@RequestParam("type") String type,@RequestParam("approver") String approver,
			@RequestParam("desc") String des, @RequestParam("username") String username){
		ModelAndView model = new ModelAndView();
		List<String> admins = mUserDataService.getSystemAdminsList();
		Request req = new Request();
		req.setUsername(username);
		req.setType(type);
		String assignedTo = "";
		if(approver.equals("systemAdmin")){
			assignedTo = admins.get(new Random().nextInt(admins.size()));
		}else{
			assignedTo = username;
		}
		req.setRemarks(des);
		req.setAssignedTo(assignedTo);
		req.setApproved("0");
		req.setPending("1");
		req.setPermissionto(getCurrentUserName());
		if(mRequestService.createRequest(req)){
			model.addObject("successMessage","A request was raised to " + assignedTo + " for " + des);
		}else{
			model.addObject("errorMessage","An error occured, please contact admin");
		}
		model.setViewName("RegularEmployee/messages");
		return model;
	}
	
	@RequestMapping(value = "/transactionRequest", method = RequestMethod.POST)
	private ModelAndView transactionRequest(@RequestParam("requestId") String requestId, @RequestParam("action") String action){
		ModelAndView model = new ModelAndView();
		int approved = 0;
		if(action.equals("approved")){
			approved = 1;
		}
		if(mRequestService.updateRequestStatus(Long.parseLong(requestId), approved, 0)){
			model.addObject("successMessage","Request " + action + " successfully");
		}else{
			model.addObject("errorMessage","There was some problem, please contact administrator");
		}
		model.setViewName("RegularEmployee/messages");
		return model;
	}
	
	@RequestMapping(value = "/createTransactionForExternal", method = RequestMethod.POST)
	private ModelAndView createTransactionForExternalUser(@RequestParam("username") String username){
		ModelAndView model = new ModelAndView();
		List<Account> accountDetails = mAccountService.fetchAccounts(username);
		List<String> accounts = new ArrayList<String>();
		for(int i=0;i<accountDetails.size();i++){
			accounts.add(accountDetails.get(i).getAccID());
		}
		model.addObject("username",username);
		model.addObject("userAccounts",accounts);
		model.setViewName("RegularEmployee/createTransaction");
		return model;
	}
	
	@RequestMapping(value = "/regularEmployee/raiseTransaction", method = RequestMethod.POST)
	private ModelAndView raiseTransaction(@RequestParam("type") String type, @RequestParam("selectedAccount") String selectedAccount,
			@RequestParam("account_to") String account_to,@RequestParam("amount") String amount,
			@RequestParam("username") String username){
		ModelAndView model = new ModelAndView();
		String errorMessage = "";
		String successMessage = "";
		if(amount == null){
			errorMessage = "Please enter the amount to transfer money";
		}else if(selectedAccount == null){
			errorMessage="please select one of the user accounts";
		}else{
			if(type.equalsIgnoreCase("debit")){
				if(Double.parseDouble(amount) >= 5000){
					errorMessage="Debit can't be greater than 5000";
				}else{
					Double balance = mAccountService.getBalance(selectedAccount);
					if(balance < Double.parseDouble(amount)){
						errorMessage="Customer doesn't has sufficient balance in this account";
					}else{
						mAccountService.updateBalance(selectedAccount, balance - Double.parseDouble(amount));
						successMessage="Transaction created successfully";
					}
				}
			}else if(type.equalsIgnoreCase("credit")){
				if(Double.parseDouble(amount) >= 5000){
					errorMessage="Debit can't be greater than 5000";
				}else{
					Double balance = mAccountService.getBalance(selectedAccount);
					mAccountService.updateBalance(selectedAccount, balance + Double.parseDouble(amount));
					successMessage="Transaction created successfully";
				}
			}else if(type.equalsIgnoreCase("transfer")){
				Double balance = mAccountService.getBalance(selectedAccount);
				Double toBalance = 0D;
				try{
					Account toAccount = mAccountService.fetchAccountDetail(account_to);
					if(toAccount == null) {
						errorMessage="Bad Account, Please check the details entered.";
					}
					else {
						toBalance = toAccount.getBalance();
						if(balance < Double.parseDouble(amount)){
							errorMessage="Customer doesn't has sufficient balance in this account";
						}else{
							mAccountService.updateBalance(selectedAccount, balance - Double.parseDouble(amount));
							//mAccountService.updateBalance(account_to,toBalance + Double.parseDouble(amount));
							Transaction trans = new Transaction();
							trans.setAccount_from(selectedAccount);
							trans.setAccount_to(account_to);
							trans.setAmount(Double.parseDouble(amount));
							trans.setApproved(true);
							trans.setPending(false);
							trans.setType(type);
							Long transactionid = mTransactionService.createTransaction(trans);
							
							Request req = new Request();
							req.setApproved("0");
							req.setPending("1");
							List<String> sysManList = mUserDataService.getSystemManagerList();
							req.setAssignedTo(sysManList.get(new Random().nextInt(sysManList.size())));
							req.setTransactionId(transactionid);
							req.setType("critical");
							req.setUsername(username);
							
							mRequestService.createRequest(req);
							successMessage="As it is a critical transaction, it will be approved after request approval, \n"
									+ "transaction Id " + transactionid + " generated for this transaction" ;
						}
					}
				}catch(Exception e){
					errorMessage="Entered account doesn't exists, please check and fill again";
				}
			}
		}
		
		if(!errorMessage.equals("")){
			
			model.addObject("errorMessage",errorMessage);
			List<Account> accountDetails = mAccountService.fetchAccounts(username);
			List<String> accounts = new ArrayList<String>();
			for(int i=0;i<accountDetails.size();i++){
				accounts.add(accountDetails.get(i).getAccID());
			}
			model.addObject("type",type);
			model.addObject("userAccounts",accounts);
			model.addObject("account_to", account_to);
			model.addObject("selectedAccount", selectedAccount);
			model.addObject("amount",amount);
			
		}else{
			
		}
		model.addObject("errorMessage",errorMessage);
		model.addObject("successMessage",successMessage);
		model.setViewName("RegularEmployee/messages");
		return model;
	}
	

	@RequestMapping(value = "/requestStatus", method = RequestMethod.GET)
	private ModelAndView requestStatus(){
		ModelAndView model = new ModelAndView();
		List<Request> requests = mRequestService.getAllRequestForUser(getCurrentUserName());
		model.addObject("requests",requests);
		model.setViewName("RegularEmployee/requestStatus");
		return model;
	}
			
	@RequestMapping(value = "/regEmployee/raiseProfileRequest", method = RequestMethod.POST)
	private ModelAndView raiseProfileRequest(@RequestParam("firstname") String firstname, @RequestParam("lastname") String lastname, @RequestParam("emailid") String emailid,
			@RequestParam("address") String address, @RequestParam("contact") String contact,
			@RequestParam("username") String username){
		ModelAndView model = new ModelAndView();
		
		Request req = new Request();
		req.setUpdatedFirstName(firstname);
		req.setUpdatedLastName(lastname);
		req.setUpdatedEmail(emailid);
		req.setUpdatedPhone(contact);
		req.setUpdatedAddress(address);
		req.setPending("1");
		req.setApproved("0");
		req.setUsername(username);
		List<String> admins = mUserDataService.getSystemAdminsList();
		req.setAssignedTo(admins.get(new Random().nextInt(admins.size())));
		req.setType("Profile Update");
		if(mRequestService.createRequest(req)){
			model.addObject("successMessage","Your requests were submitted successfully");
		}else{
			model.addObject("errorMessage","There was an error, Please contact administrator");
		}
		model.setViewName("RegularEmployee/messages");
		return model;
	}
	
	@RequestMapping(value = "/customer/approveTransaction", method = RequestMethod.POST)
	private ModelAndView approveCustomerRequest(){
		return null;
	}
	
	@RequestMapping(value = "/customer/denyTransaction", method = RequestMethod.POST)
	private ModelAndView denyCustomerRequest(){
		return null;
	}
	
	private Transaction getTransactionById(Long transactionId) {
		return mTransactionService.getTransaction(transactionId);
	}

	private String getCurrentUserName() {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return user.getUsername();
	}

	public RequestService getmRequestService() {
		return mRequestService;
	}
	
	@Autowired(required =true)
	@Qualifier(value = "requestServiceImpl")
	public void setmRequestService(RequestService mRequestService) {
		this.mRequestService = mRequestService;
	}

	public TransactionService getmTransactionService() {
		return mTransactionService;
	}

	
	@Autowired(required =true)
	@Qualifier(value = "transactionServiceImpl")
	public void setmTransactionService(TransactionService mTransactionService) {
		this.mTransactionService = mTransactionService;
	}

	public AccountService getmAccountService() {
		return mAccountService;
	}

	@Autowired(required =true)
	@Qualifier(value = "accountServiceImpl")
	public void setmAccountService(AccountService mAccountService) {
		this.mAccountService = mAccountService;
	}

	public UserDataService getmUserDataService() {
		return mUserDataService;
	}

	@Autowired(required =true)
	@Qualifier(value = "userDataServiceImpl")
	public void setmUserDataService(UserDataService mUserDataService) {
		this.mUserDataService = mUserDataService;
	}

	@RequestMapping(value = "/profileUpdate", method = RequestMethod.GET)
	private ModelAndView profileUpdate(){
		ModelAndView model = new ModelAndView();
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<UserData> userData = mUserDataService.getUserProfileDetails(user.getUsername());
		model.addObject("getUserProfile",userData);
		model.setViewName("RegularEmployee/profileUpdate");
		return model;
	}

	@RequestMapping(value = "/regEmployee/profileUpdate", method = RequestMethod.POST)
	public ModelAndView updateUserProfile(@RequestParam("firstname") String firstname,@RequestParam("lastname") String lastname,
			@RequestParam("contact") String contact, @RequestParam("address") String address){

		ModelAndView model = new ModelAndView();
		if(firstname.equals("") || lastname.equals("") || contact.equals("") || address.equals("")  ) {
				model.addObject("errorMessage","Opps!! Something is wrong. Please try again.");
		}else{
			long reqId = createRequest(firstname, lastname, contact, address);
			model.addObject("successMessage","Your profile will be updated after approval");
		}
		
		model.setViewName("RegularEmployee/profileUpdateStatus");
		return model;
	}

	
	
	private long createRequest(String firstname, String lastname, String contact, String address) {
		Request request = new Request();

		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		request.setUsername(user.getUsername());
		request.setTransactionId((long) 0);
		request.setType("Profile Update");
		List<String> admins = mUserDataService.getSystemAdminsList();
		request.setAssignedTo(admins.get(new Random().nextInt(admins.size())));	
		request.setUpdatedFirstName(firstname);
		request.setUpdatedLastName(lastname);
		request.setUpdatedPhone(contact);
		request.setUpdatedAddress(address);
		// request.setUpdatedEmail(email);
		// request.setUpdatedSecurityQuestion(uData.getSecurityquestion());
		// request.setUpdatedSecurityAnswer(uData.getSecurityanswer());
		request.setCreated_at(new Timestamp(Calendar.getInstance().getTimeInMillis()));		
		request.setApproved("0");
		request.setPending("1");
		return mRequestService.createprofileUpdateRequest(request);
	}

	@RequestMapping(value = "/systemManager/raiseATArrovalRequest", method = RequestMethod.POST)
	private ModelAndView raiseATManagerRequest(@RequestParam("type") String type,@RequestParam("approver") String approver,
			@RequestParam("desc") String des, @RequestParam("username") String username){
		ModelAndView model = new ModelAndView();
		List<String> admins = mUserDataService.getSystemAdminsList();
		Request req = new Request();
		req.setUsername(username);
		req.setType(type);
		String assignedTo = "";
		if(approver.equals("systemAdmin")){
			assignedTo = admins.get(new Random().nextInt(admins.size()));
		}else{
			assignedTo = username;
		}
		req.setRemarks(des);
		req.setAssignedTo(assignedTo);
		req.setApproved("0");
		req.setPending("1");
		req.setPermissionto(getCurrentUserName());
		if(mRequestService.createRequest(req)){
			model.addObject("successMessage","A request was raised to " + assignedTo + " for " + des);
		}else{
			model.addObject("errorMessage","An error occured, please contact admin");
		}
		model.setViewName("RegularEmployee/messages");
		return model;
	}
	
}
