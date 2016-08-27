package edu.asu.cse.softwaresecurity.group6.bosd.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import edu.asu.cse.softwaresecurity.group6.bosd.model.user.Account;
import edu.asu.cse.softwaresecurity.group6.bosd.model.user.Request;
import edu.asu.cse.softwaresecurity.group6.bosd.model.user.Transaction;
import edu.asu.cse.softwaresecurity.group6.bosd.model.user.UserData;
import edu.asu.cse.softwaresecurity.group6.bosd.service.AccountService;
import edu.asu.cse.softwaresecurity.group6.bosd.service.RequestService;
import edu.asu.cse.softwaresecurity.group6.bosd.service.TransactionService;
import edu.asu.cse.softwaresecurity.group6.bosd.service.UserDataService;
import freemarker.core.ParseException;

@Controller
@RequestMapping(value = "/normalUser**")
public class ProfileSettingsController {
	@Autowired
	private UserDataService mUserDataService;
	@Autowired
	private AccountService mAccountService;
	@Autowired
	private RequestService mRequestService;
	@Autowired
	private TransactionService mTransactionService;
	
	
	@RequestMapping(value = "/profile_settings", method = RequestMethod.POST)
	public String updateUserProfile(@ModelAttribute("UserData") UserData userDataModel, BindingResult result,
			Model model) {

		try {
			// mAccountService.debitAmount(debitModel.getAccID(), balance);
			//updateProfile(userDataModel);
			if (userDataModel.getAddress().equals("") || userDataModel.getContact().equals("") || userDataModel.getFirstname().equals("") || userDataModel.getLastname().equals("")  ) {
				model.addAttribute("Message", "Opps!! Something is wrong. Please try again.");
			} else {
				long reqId = createRequest(userDataModel);
				model.addAttribute("Message", "Your profile will be updated after approval");
			}
		} catch(Exception e){
			model.addAttribute("Message", "Opps!! Something is wrong. Please try again.");
		} 
		
		return "NormalUser/TransactionSuccess";
	}

	
//	private int updateProfile(UserData uData) {		
//		return mUserDataService.updateUserProfile(uData);
//	}
	
	private long createRequest(UserData uData) {
		Request request = new Request();
		request.setUsername(uData.getUsername());
		request.setTransactionId((long) 0);
		request.setType("Profile Update");
		ArrayList<String> regEmpList = mUserDataService.getRegularEmpList();
		request.setAssignedTo(regEmpList.get(new Random().nextInt(regEmpList.size())));
		request.setUpdatedFirstName(uData.getFirstname());
		request.setUpdatedLastName(uData.getLastname());
		request.setUpdatedPhone(uData.getContact());
		request.setUpdatedAddress(uData.getAddress());
		// request.setUpdatedEmail(uData.getEmail());
		// request.setUpdatedSecurityQuestion(uData.getSecurityquestion());
		// request.setUpdatedSecurityAnswer(uData.getSecurityanswer());
		request.setCreated_at(new Timestamp(Calendar.getInstance().getTimeInMillis()));		
		request.setApproved("0");
		request.setPending("1");
		return mRequestService.createprofileUpdateRequest(request);
	}

	
	@RequestMapping(value = "/profile_settings", method = RequestMethod.GET)
	public String getUserProfile(Model model) {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		model.addAttribute("getUserProfile", this.mUserDataService.getUserProfileDetails(user.getUsername()));
		return "NormalUser/profileSettings";
	}
	
	@RequestMapping(value = "/reqpayment", method = RequestMethod.GET)
	public String getPaymentRequest(Model model) {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		model.addAttribute("fromlistAccounts", this.mAccountService.fetchUserAccounts(user.getUsername()));
		model.addAttribute("listAccounts", this.mAccountService.fetchOtherAccounts(user.getUsername()));
		return "NormalUser/requestPayment";
	}


	@RequestMapping(value = "/requestPayment", method = RequestMethod.POST)
	public void processPaymentRequest(HttpServletRequest request, HttpServletResponse response) throws IOException,ParseException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		try {
			String accNo = request.getParameter("accID");
			String accType = request.getParameter("accType");
			String transType = "CREDIT";//request.getParameter("transType");
			String amount = request.getParameter("amount");
			if(accNo.equals("") || transType.equals("") || amount.equals("") ) {
				out.append("Opps!! Something is wrong. Please try again.");
			}else {
				double amt = Double.parseDouble(amount);
				User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				String username = user.getUsername();
				List<Account>accts = this.mAccountService.fetchAccounts(username, accType);
				long transId = createTransaction(amt,accts.get(0).getAccID(), accNo, transType);
				long reqId = createRequest(transId, username, accNo );

				out.append("Your request will be processed after approval");
			} 
		} catch (Exception e) {
			out.append("Opps!! Something is wrong. Please try again.");
		}
		out.close();
	}
	
	private long createTransaction(double amt, String faccID ,String accID, String transType) {
		Transaction transaction = new Transaction();
		transaction.setType(transType);
		transaction.setCreated_at(new Timestamp(Calendar.getInstance().getTimeInMillis()));
		transaction.setAccount_from(faccID);
		transaction.setPending(true);
		transaction.setApproved(false);
		transaction.setAmount(amt);
		transaction.setAccount_to(accID);
		return mTransactionService.createTransaction(transaction);
	}

	private long createRequest(long transId, String username, String accID) {
		Request request = new Request();
		request.setTransactionId(transId);
		request.setType("Merchant");
		request.setCreated_at(new Timestamp(Calendar.getInstance().getTimeInMillis()));
		request.setUsername(username);
		request.setPending("1");
		request.setApproved("0");
		request.setAssignedTo(mAccountService.getUsername(accID));
		return mRequestService.createDebitCreditRequest(request);
	}

	
}
