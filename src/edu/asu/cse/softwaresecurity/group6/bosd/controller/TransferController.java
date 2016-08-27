package edu.asu.cse.softwaresecurity.group6.bosd.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.asu.cse.softwaresecurity.group6.bosd.model.user.OTPRequestInfo;
import edu.asu.cse.softwaresecurity.group6.bosd.model.user.Request;
import edu.asu.cse.softwaresecurity.group6.bosd.model.user.Transaction;
import edu.asu.cse.softwaresecurity.group6.bosd.model.user.TransferModel;
import edu.asu.cse.softwaresecurity.group6.bosd.service.AccountService;
import edu.asu.cse.softwaresecurity.group6.bosd.service.GenerateOTP;
import edu.asu.cse.softwaresecurity.group6.bosd.service.RequestService;
import edu.asu.cse.softwaresecurity.group6.bosd.service.TransactionService;
import edu.asu.cse.softwaresecurity.group6.bosd.service.UserDataService;
import edu.asu.cse.softwaresecurity.group6.bosd.service.UserService;
import edu.asu.cse.softwaresecurity.group6.bosd.service.UserServiceImpl;
import edu.asu.cse.softwaresecurity.group6.bosd.utils.OTPStatus;

@Controller
public class TransferController {

	@Autowired
	private AccountService accountService;
	
	@Autowired
	private TransactionService transactionService;
	
	@Autowired
	private RequestService requestService;

	@Autowired
	private UserDataService userDataService;

	@Autowired
	private GenerateOTP generateOTP;
	
	@Autowired
	private UserService userService;
	
	private static final Logger logger = Logger.getLogger(HelloController.class);
	
	private static String REQ_TRANSFER = "transfer";
	private static String REQ_TYPE_C = "critical";
	private static String REQ_TYPE_R = "regular";

	@RequestMapping(value = "/normalUser/Transfer", method = RequestMethod.GET)
	public String listAccounts(Model model) {
		User user = (User) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		model.addAttribute("listSenderAccounts",
				accountService.fetchUserAccounts(user.getUsername()));

		return "NormalUser/Transfer";
	}

	@RequestMapping(value = "/normalUser/PostTransfer", method = RequestMethod.POST)
	public String Transfer(
			@ModelAttribute("TransferModel") TransferModel transferModel,
			BindingResult result, Model model) {

		try {
			double amount = Double.parseDouble(transferModel.getAmount());
			try {
				transferModel.setSenderAcc(accountService.fetchAccountDetail(transferModel.getSenderAccID()));
				transferModel.setReceiverAcc(accountService.fetchAccountDetail(transferModel.getReceiverAccID()));
			} catch (Exception e) {
				model.addAttribute("Message","One of the account numbers you entered was not found, please check");
				return "NormalUser/TransactionSuccess";
			}
				double newSenderBalance = transferModel.getSenderAcc()
						.getBalance() - amount;
				double newReceiverBalance = transferModel.getReceiverAcc()
						.getBalance() + amount;

				if (transferModel.getSenderAcc().getUsername().equalsIgnoreCase(transferModel.getReceiverAcc().getUsername())
						&& amount > 0 && amount <= transferModel.getSenderAcc().getBalance()) {
					boolean updated = accountService.transfer(transferModel.getSenderAccID(),transferModel.getReceiverAccID(), newSenderBalance,
							newReceiverBalance);
					logger.info("Inside self transfer");
					logger.info(transferModel);
					if (updated) {
						createTransaction(transferModel.getSenderAccID(),transferModel.getReceiverAccID(), amount,REQ_TRANSFER, false, true);
						model.addAttribute("Message", "Transfer successful");
						logger.info("Success: Transaction");
					} else {
						logger.info("Failed: Transaction");
						model.addAttribute("Message","Transfer not successful. Try again");
					}
				}

				else if (amount > 5000) {
						model.addAttribute("transferModel",transferModel);
						return "NormalUser/GenerateOTP";
					}else	if (amount < transferModel.getSenderAcc().getBalance() && amount <= 5000 && amount > 0) {
						logger.info("Inside external transfer");
						boolean updated = accountService.transfer(transferModel.getSenderAccID(),transferModel.getReceiverAccID(), newSenderBalance,
							newReceiverBalance);
						logger.info(transferModel);
						if (updated) {
							
						createTransaction(transferModel.getSenderAccID(),transferModel.getReceiverAccID(), amount,REQ_TRANSFER, false, true);
						model.addAttribute("Message", "Transfer successful");
						logger.info("Success: Transaction");
					} else {
						model.addAttribute("Message","Transfer not successful. Try again");
						logger.info("Failed: Transaction");
					}
				
					}
				else
					model.addAttribute("Message", "Transfer not successful");
					
			
		} catch (NumberFormatException en) {
			model.addAttribute("Message", "Transaction not successful!!");
		}

		return "NormalUser/TransactionSuccess";
	}

	@RequestMapping(value = "/normalUser/criticalTransfer", method = RequestMethod.POST)
	public String criticalTransfer(
			@ModelAttribute("TransferModel") @Valid TransferModel transferModel,
			BindingResult result, Model model) {
		logger.info("Inside Critical Transaction");
		if(result.hasErrors()){
			for (ObjectError error : result.getAllErrors()) {
				System.out.println(error.getDefaultMessage());
			}
		}
		
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		try {
			
			if(userService.checkOTP(user.getUsername(), transferModel.getOTP(), OTPStatus.CRITICAL_TRANSACTIONS)){
				double amount = Double.parseDouble(transferModel.getAmount());
				try {
					transferModel.setSenderAcc(accountService.fetchAccountDetail(transferModel.getSenderAccID()));
					transferModel.setReceiverAcc(accountService.fetchAccountDetail(transferModel.getReceiverAccID()));
				} catch (Exception e) {
					model.addAttribute("Message","One of the account numbers you entered was not found, please check");
					return "NormalUser/TransactionSuccess";
				}

				double newSenderBalance = transferModel.getSenderAcc().getBalance()- amount;
				double newReceiverBalance = transferModel.getReceiverAcc().getBalance() + amount;

			// if(amount<transferModel.getSenderAcc().getBalance() &&
			// amount>5000){
				logger.info(transferModel);
				
				accountService.updateBalance(transferModel.getSenderAccID(),newSenderBalance);
				long transId = createTransaction(transferModel.getSenderAccID(),transferModel.getReceiverAccID(), amount, REQ_TRANSFER,
					true, false);
				if (transId != -1) {
					long reqId = createRequest(transId, REQ_TYPE_C);
					if (reqId == -1) {
						logger.info("Failed: TransactionID:"+transId);
						deleteTransactionFromDB(transId);
						model.addAttribute("Message", "Transfer not successful");
					} else{
						logger.info("Success: TransactionID:"+transId);
						model.addAttribute("Message","Transfer will be completed after approval");
					}	
				}
			}else
				model.addAttribute("Message", "Transaction unsuccessful. OTP Invalidated.");

		} catch (NumberFormatException en) {
			model.addAttribute("Message", "Transaction not successful!!");
		}
		return "NormalUser/TransactionSuccess";
	}

	@RequestMapping(value = "/normalUser/otpDetails", method = RequestMethod.POST)
	public String generateOTP(
			@ModelAttribute("TransferModel") TransferModel transferModel,
			BindingResult result, Model model) {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		generateOTP.generateOTPForUser(user.getUsername(), OTPStatus.CRITICAL_TRANSACTIONS);
		model.addAttribute("transferModel",transferModel);
		return "NormalUser/otpDetails";
	}

	private long createTransaction(String senderaccID, String receiveraccID,
			double amnt, String type, boolean pending, boolean approved) {
		Transaction transaction = new Transaction();
		transaction.setType(type);
		transaction.setCreated_at(new Timestamp(Calendar.getInstance()
				.getTimeInMillis()));
		transaction.setAccount_from(senderaccID);
		transaction.setAccount_to(receiveraccID);
		transaction.setPending(pending);
		transaction.setApproved(approved);
		transaction.setAmount(amnt);
		return transactionService.createTransaction(transaction);
	}

	private long createRequest(long transId, String reqType) {
		User user = (User) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		Request request = new Request();
		request.setTransactionId(transId);
		request.setType(reqType);
		request.setCreated_at(new Timestamp(Calendar.getInstance()
				.getTimeInMillis()));
		request.setUsername(user.getUsername());

		if (reqType.equals(REQ_TYPE_R)) {
			ArrayList<String> regEmpList = userDataService.getRegularEmpList();
			request.setAssignedTo(regEmpList.get(new Random()
					.nextInt(regEmpList.size())));
		}
		if (reqType.equals(REQ_TYPE_C)) {
			List<String> sysManList = userDataService.getSystemManagerList();
			request.setAssignedTo(sysManList.get(new Random()
					.nextInt(sysManList.size())));
		}
		request.setApproved("0");
		request.setPending("1");
		return requestService.createDebitCreditRequest(request);
	}

	private void deleteTransactionFromDB(long transId) {
		User user = (User) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		Transaction trans = transactionService.getTransaction(transId);
		if (trans.getType().equals(REQ_TRANSFER)) {
			double balance = accountService.getBalance(trans.getAccount_from()
					.trim());
			double amnt = balance + trans.getAmount();
			accountService.updateBalance(trans.getAccount_from().trim(), amnt);
		}
		transactionService.deleteTransaction(transId);
	}

}