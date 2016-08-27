package edu.asu.cse.softwaresecurity.group6.bosd.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import edu.asu.cse.softwaresecurity.group6.bosd.model.user.Account;
import edu.asu.cse.softwaresecurity.group6.bosd.model.user.DebitCreditModel;
import edu.asu.cse.softwaresecurity.group6.bosd.model.user.OTPInput;
import edu.asu.cse.softwaresecurity.group6.bosd.model.user.Request;
import edu.asu.cse.softwaresecurity.group6.bosd.model.user.Transaction;
import edu.asu.cse.softwaresecurity.group6.bosd.model.user.TransactionUpdateModel;
import edu.asu.cse.softwaresecurity.group6.bosd.model.user.TransferModel;
import edu.asu.cse.softwaresecurity.group6.bosd.model.user.UserData;
import edu.asu.cse.softwaresecurity.group6.bosd.service.AccountService;
import edu.asu.cse.softwaresecurity.group6.bosd.service.GenerateOTP;
import edu.asu.cse.softwaresecurity.group6.bosd.service.RequestService;
import edu.asu.cse.softwaresecurity.group6.bosd.service.TransactionService;
import edu.asu.cse.softwaresecurity.group6.bosd.service.UserDataService;
import edu.asu.cse.softwaresecurity.group6.bosd.service.UserService;
import edu.asu.cse.softwaresecurity.group6.bosd.utils.OTPStatus;
import edu.asu.cse.softwaresecurity.group6.bosd.utils.UtilMessages;

/**
 * Controller to handle debit and credit done by user It creates a new tuple in the transaction table
 * 
 * @author apchanda
 *
 */
@Controller
public class DebitCreditController {
	@Autowired
	private AccountService mAccountService;
	@Autowired
	private TransactionService mTransactionService;
	@Autowired
	private RequestService mRequestService;
	@Autowired
	private UserDataService mUserDataService;
	@Autowired
	private GenerateOTP generateOTP;
	
	@Autowired
	private UserService userService;
	
	// to be moved to a common constants file later
	private static String TRANS_DEBIT = "debit";
	private static String TRANS_CREDIT = "credit";
	private static String REQ_DEBIT = "debit";
	private static String REQ_CREDIT = "credit";

	private static final Logger logger = Logger.getLogger(HelloController.class);
	
	@RequestMapping(value = "/normalUser/PostDebit", method = RequestMethod.POST)
	public String UserOperation(@ModelAttribute("DebitCreditModel") DebitCreditModel debitModel, BindingResult result,
			Model model) {
		logger.info("In PostDebit page");
		try {
			double prevBal = Double.parseDouble(debitModel.getBalance());
			double amnt = Double.parseDouble(debitModel.getAmount());
			if (amnt < prevBal && amnt <= 5000 && amnt > 0) {
				User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				ArrayList<Account> acc = mAccountService.fetchUserAccounts(user.getUsername(), debitModel.getAcctype());
				Account account = acc.get(0);
				double balance = prevBal - amnt;
				mAccountService.updateBalance(account.getAccID(), balance);
				long transId = createTransaction(account.getAccID(), amnt, TRANS_DEBIT);
				// If transaction not inserted properly don't put the request
				if (transId != -1) {
					long reqId = createRequest(transId, REQ_DEBIT);
					// if request is not inserted properly then remove the transaction entry
					if (reqId == -1) {
						deleteTransactionFromDB(transId);
						model.addAttribute("Message", "Transaction not successful");
					} else
						model.addAttribute("Message", "Transaction will be completed after approval");
				}

			} else {
				model.addAttribute("Message", "Transaction not successful");
			}
		} catch (NumberFormatException e) {
			model.addAttribute("Message", "Transaction not successful");
		}
		return "NormalUser/TransactionSuccess";
	}
	
	@RequestMapping(value = "/normalUser/approveMerchantTransaction", method = RequestMethod.POST)
	private ModelAndView approveRequest(@ModelAttribute("requestId") Long requestId, @ModelAttribute("transactionId") Long transactionId){
		logger.info("In approve Merchant Transaction page");
		//mRequestService.getRequestForUser(userName);
		ModelAndView model = new ModelAndView();
		Transaction transaction = mTransactionService.getTransaction(transactionId);
		//if(transaction.getType().equals(TRANS_DEBIT)){
			double prevBal = mAccountService.getBalance(transaction.getAccount_from());
			if(prevBal < transaction.getAmount()){
				model.addObject("errorMessage","Your balance is less than that asked by merchant.");
				model.setViewName("RegularEmployee/messages");
			}
//			double balance = prevBal - transaction.getAmount();
//			mAccountService.updateBalance(transaction.getAccount_from(), balance);
		//}
	
		ArrayList<String> regEmpList = mUserDataService.getRegularEmpList();
		System.out.println(regEmpList.get(0).toString());
		String username = regEmpList.get(new Random().nextInt(regEmpList.size()));
		if(mRequestService.updateAssignedToRequest(requestId, username)){
			model.addObject("successMessage","Request was forwarded to Regular employee " + username + " for approval");
		}else{
			model.addObject("errorMessage","There was some problem and request was not acted upon, please contact bank representative for more information");
		}
				
		model.setViewName("RegularEmployee/messages");
		return model;
	}
	
	@RequestMapping(value = "/normalUser/denyMerchantTransaction", method = RequestMethod.POST)
	private ModelAndView denyRequest(@ModelAttribute("requestId") Long requestId, @ModelAttribute("transactionId") Long transactionId){
		logger.info("Deny Merchant Transaction");
		//mRequestService.getRequestForUser(userName);
		
		Transaction transaction = mTransactionService.getTransaction(transactionId);
		//if(transaction.getType().equals(TRANS_CREDIT)){
			double prevBal = mAccountService.getBalance(transaction.getAccount_from());
			double balance = prevBal + transaction.getAmount();
//			mAccountService.updateBalance(transaction.getAccount_from(), balance);
		//}
//		ArrayList<String> regEmpList = mUserDataService.getRegularEmpList();
//		System.out.println(regEmpList.get(0).toString());
//		String username = regEmpList.get(new Random().nextInt(regEmpList.size()));
//		mRequestService.updateAssignedToRequest(requestId, username);
		mRequestService.updateRequestStatus(requestId, 0, 0);
		ModelAndView model = new ModelAndView();
		model.addObject("successMessage","You denied the request from merchant");
		model.setViewName("RegularEmployee/messages");
		return model;
	}
	

	public AccountService getmAccountService() {
		return mAccountService;
	}

	public void setmAccountService(AccountService mAccountService) {
		this.mAccountService = mAccountService;
	}

	public TransactionService getmTransactionService() {
		return mTransactionService;
	}

	public void setmTransactionService(TransactionService mTransactionService) {
		this.mTransactionService = mTransactionService;
	}

	public RequestService getmRequestService() {
		return mRequestService;
	}

	public void setmRequestService(RequestService mRequestService) {
		this.mRequestService = mRequestService;
	}

	public UserDataService getmUserDataService() {
		return mUserDataService;
	}

	public void setmUserDataService(UserDataService mUserDataService) {
		this.mUserDataService = mUserDataService;
	}

	private long createTransaction(String accID, double amnt, String type) {
		Transaction transaction = new Transaction();
		transaction.setType(type);
		transaction.setCreated_at(new Timestamp(Calendar.getInstance().getTimeInMillis()));
		transaction.setAccount_from(accID);
		transaction.setPending(true);
		transaction.setApproved(false);
		transaction.setAmount(amnt);
		return mTransactionService.createTransaction(transaction);
	}

	private long createRequest(long transId, String reqType) {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Request request = new Request();
		request.setTransactionId(transId);
		request.setType(reqType);
		request.setCreated_at(new Timestamp(Calendar.getInstance().getTimeInMillis()));

		request.setUsername(user.getUsername());
		ArrayList<String> regEmpList = mUserDataService.getRegularEmpList();
		System.out.println(regEmpList.toString());
		if(regEmpList.size()==0)
			return -1;
		request.setApproved("0");
		request.setPending("1");
		request.setAssignedTo(regEmpList.get(new Random().nextInt(regEmpList.size())));
		return mRequestService.createDebitCreditRequest(request);
	}

	@RequestMapping(value = "/normalUser/Debit", method = RequestMethod.GET)
	public String listAccounts(Model model) {
		logger.info("Debit page");
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		model.addAttribute("listAccounts", this.mAccountService.fetchUserAccounts(user.getUsername()));
		return "NormalUser/Debit";
	}

	@RequestMapping(value = "/normalUser/PostCredit", method = RequestMethod.POST)
	public String UserOperationCredit(@ModelAttribute("DebitCreditModel") DebitCreditModel creditModel,
			BindingResult result, Model model) {
		logger.info("PostCredit page");
		try {
			double amnt = Double.parseDouble(creditModel.getAmount());
			if (amnt <= 5000 && amnt > 0) {
				User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				ArrayList<Account> acc = mAccountService
						.fetchUserAccounts(user.getUsername(), creditModel.getAcctype());
				Account account = acc.get(0);
				long transId = createTransaction(account.getAccID(), amnt, TRANS_CREDIT);
				if (transId != -1) {
					long reqId = createRequest(transId, REQ_CREDIT);
					// if request is not inserted properly then remove the transaction entry
					if (reqId == -1) {
						deleteTransactionFromDB(transId);
						model.addAttribute("Message", "Transaction not successful");
					} else
						model.addAttribute("Message", "Transaction will be completed after approval");
				}
			} else
				model.addAttribute("Message", "Transaction not successful");
		} catch (NumberFormatException e) {
			model.addAttribute("Message", "Transaction not successful");
		}
		return "NormalUser/TransactionSuccess";
	}

	@RequestMapping(value = "/normalUser/Credit", method = RequestMethod.GET)
	public String listAccountsCreditPage(Model model) {
		logger.info("Credit page");
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		model.addAttribute("listAccountsCreditPage", this.mAccountService.fetchUserAccounts(user.getUsername()));
		return "NormalUser/Credit";
	}

	/*@RequestMapping(value = "/normalUser/statements", method = RequestMethod.GET)
	public String getStatements(Model model) {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		model.addAttribute("listAccounts", this.mAccountService.fetchUserAccounts(user.getUsername()));
		return "NormalUser/statements";
	}*/

	@RequestMapping(value = "/normalUser/statements", method = RequestMethod.POST)
	public void downloadStatements(HttpServletRequest request, HttpServletResponse response) throws IOException,
			ParseException {

		String csvFileName = "statements" + new Timestamp(Calendar.getInstance().getTimeInMillis()).toString() + ".csv";
		response.setContentType("text/csv;charset=utf-8");

		// creates mock data
		String headerKey = "Content-Disposition";
		String headerValue = String.format("attachment; filename=\"%s\"", csvFileName);
		response.setHeader(headerKey, headerValue);

		String sdate = request.getParameter("startDate");
		String edate = request.getParameter("endDate");
		String accNo = request.getParameter("accType");
		if (sdate.equals("") || edate.equals("") || accNo.equals("")) {
			ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.EXCEL_PREFERENCE);
			String[] header = { "Amount", "Type", "Status", "Date" };

			csvWriter.writeHeader(header);
			csvWriter.close();

		} else {
			ArrayList<Transaction> transList = new ArrayList<Transaction>();

			User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			transList = mTransactionService.getTransactionList(user.getUsername(), accNo, sdate, edate);
			// uses the Super CSV API to generate CSV data from the model data
			ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.EXCEL_PREFERENCE);

			String[] header = { "Amount", "Type", "Status", "Date" , "Account From", "Account To"};

			csvWriter.writeHeader(header);

			Iterator itr = transList.iterator();
			while (itr.hasNext()) {
				Object[] obj = (Object[]) itr.next();
				String status = "Pending";
				if (obj[3].equals(true)) {
					status = "Approved";
				}
				// if (obj[6] != null && obj[6].equals(accNo)) {
				// 	if (obj[1].equals("DEBIT")) {
				// 		obj[1] = "CREDIT";
				// 	} else if(obj[1].equals("CREDIT")) {
				// 		obj[1] = "DEBIT";
				// 	}
				// }
				String[] row = { String.valueOf(obj[0]), String.valueOf(obj[1]), status, String.valueOf(obj[4]), String.valueOf(obj[5]), String.valueOf(obj[6]) };
				csvWriter.writeHeader(row);
			}

			csvWriter.close();
		}
	}

	@RequestMapping(value = "normalUser/TransactionDetail", method = RequestMethod.POST)
	public String transactionDetail(@ModelAttribute("transactionId") long trans, BindingResult result, Model model) {
		Transaction mTrans = mTransactionService.getTransaction(trans);
		model.addAttribute("transDetail", mTrans);
		return "NormalUser/TransactionDetail";
	}

	@RequestMapping(value = "normalUser/TransactionUpdate", method = RequestMethod.POST)
	public String updateTransaction(@ModelAttribute("TransactionUpdateModel") TransactionUpdateModel trans,
			BindingResult result, Model model) {
		updateTransactionDB(trans);
		model.addAttribute("Message", "Transaction will be completed after approval");
		return "NormalUser/TransactionSuccess";
	}

	private void updateTransactionDB(TransactionUpdateModel transUpdate) {
		try {
			double prevAmnt = transUpdate.getPrevAmount();
			double amnt = Double.parseDouble(transUpdate.getAmount());
			if (transUpdate.getType().equals(TRANS_DEBIT)) {
				double diff = prevAmnt - amnt;
				double balance = mAccountService.getBalance(transUpdate.getAccount_from().trim());
				balance = balance + diff;
				mAccountService.updateBalance(transUpdate.getAccount_from().trim(), balance);
			}
			Transaction transaction = new Transaction();
			transaction.setAccount_from(transUpdate.getAccount_from());
			transaction.setAccount_to(transUpdate.getAccount_to());
			transaction.setAmount(Double.parseDouble(transUpdate.getAmount()));
			transaction.setTransactionId(transUpdate.getTransactionId());
			transaction.setType(transUpdate.getType());
			transaction.setCreated_at(transUpdate.getUpdated_at());
			transaction.setUpdated_at(new Timestamp(Calendar.getInstance().getTimeInMillis()));
			transaction.setApproved(false);
			transaction.setPending(true);
			mTransactionService.updateTransaction(transaction);
		} catch (NumberFormatException e) {
		}

	}

	@RequestMapping(value = "normalUser/deleteTransaction", method = RequestMethod.POST)
	public String deleteDebitCreditTransaction(
			@ModelAttribute("TransactionUpdateModel") TransactionUpdateModel updateModel, BindingResult result,
			Model model) {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		// delete the transaction from transaction table
		deleteTransactionFromDB(updateModel.getTransactionId());
		mRequestService.deleteRequest(updateModel.getTransactionId(), user.getUsername());
		model.addAttribute("Message", "Transaction deleted successfuly");
		return "NormalUser/TransactionSuccess";

	}

	@RequestMapping(value="normalUser/generateOtpSt",method=RequestMethod.GET)
	public String getOtpRequest(){

		return "NormalUser/generateOtpSt";
	}
	@RequestMapping(value="normalUser/otpDetailsSt",method=RequestMethod.POST)
	public ModelAndView getOtpDetails(){
		
		ModelAndView mView = new ModelAndView();
			User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			generateOTP.generateOTPForUser(user.getUsername(), OTPStatus.STATEMENTS);	
		
			mView.addObject("oTPInput", new OTPInput());
			mView.setViewName("NormalUser/otpDetailsSt");

		return mView;
		
	}
	
	@RequestMapping(value="normalUser/otpdetail",method=RequestMethod.POST)
	public ModelAndView optDetails(@Valid OTPInput oTPInput, BindingResult result){
		
		ModelAndView mView = new ModelAndView();
		if(result.hasErrors()) {
			oTPInput.setFailureMessage(UtilMessages.OTP_INVALID);
			mView.setViewName("NormalUser/otpDetailsSt");
			mView.addObject("oTPInput",oTPInput);
		} else {
			User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if(userService.checkOTP(user.getUsername(), oTPInput.getOTP(), OTPStatus.STATEMENTS)){
				mView.addObject("listAccounts", this.mAccountService.fetchUserAccounts(user.getUsername()));
				mView.setViewName("NormalUser/statements");
			
			}
			else
				mView.setViewName( "redirect:/normalUser");
				
		}
		return mView;
	}
	private void deleteTransactionFromDB(long transId) {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Transaction trans = mTransactionService.getTransaction(transId);
		if (trans.getType().equals(TRANS_DEBIT)) {
			double balance = mAccountService.getBalance(trans.getAccount_from().trim());
			double amnt = balance + trans.getAmount();
			mAccountService.updateBalance(trans.getAccount_from().trim(), amnt);
		} else if (trans.getType().equals(TRANS_CREDIT)) {
			System.out.println("IN ELSE");
			double balance = mAccountService.getBalance(trans.getAccount_from().trim());
			double amnt = balance - trans.getAmount();
			mAccountService.updateBalance(trans.getAccount_from().trim(), amnt);
		}
		mTransactionService.deleteTransaction(transId);
	}
	
}
