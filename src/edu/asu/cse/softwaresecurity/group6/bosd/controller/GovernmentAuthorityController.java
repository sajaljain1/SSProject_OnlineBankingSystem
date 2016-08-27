package edu.asu.cse.softwaresecurity.group6.bosd.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import edu.asu.cse.softwaresecurity.group6.bosd.model.pii.GovtInfoRequest;
import edu.asu.cse.softwaresecurity.group6.bosd.model.user.PIIForm;
import edu.asu.cse.softwaresecurity.group6.bosd.model.user.UserData;
import edu.asu.cse.softwaresecurity.group6.bosd.service.UserDataService;
import edu.asu.cse.softwaresecurity.group6.bosd.service.pii.GovtInfoReqService;

@Controller
public class GovernmentAuthorityController {
	@Autowired
	private UserDataService userDataService;

	public UserDataService getUserDataService() {
		return userDataService;
	}

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

	@RequestMapping(value = "/governmentAuthority", method = RequestMethod.GET)
	public ModelAndView adminPIIPage() {

		ModelAndView model = new ModelAndView();
		model.setViewName("govtHome");
		return model;
	}

	@RequestMapping(value = "/governmentAuthority/verificationRequests", method = RequestMethod.GET)
	public ModelAndView getVerificationRequests() {

		ModelAndView model = new ModelAndView("govtVerification");
		List<UserData> users = userDataService.getUsersAwaitingGovtPIIVerification();
		model.addObject("piiForm", new PIIForm(users));
		return model;
	}
	
	@RequestMapping(value = "/governmentAuthority/viewCompletedRequests", method = RequestMethod.GET)
	public ModelAndView viewCompletedRequests() {

		ModelAndView model = new ModelAndView("govtViewInfo");
		List<UserData> users = userDataService.getUsersWithCompletedInfoRequest();
		model.addObject("piiForm", new PIIForm(users));
		return model;
	}

	@RequestMapping(value = "/governmentAuthority/approve", method = RequestMethod.POST)
	public String sendForVerification(@ModelAttribute("piiForm") PIIForm piiForm, Model model, @RequestParam String action){ 
		if(piiForm != null && piiForm.getUsers() != null) {
			for(int i=0;i< piiForm.getUsers().size(); i++){
				if(piiForm.getCheckedUsers().get(i).isChecked()){
					if(action.equals("approve")){
						userDataService.setUserPIIVerified(piiForm.getUsers().get(i).getUsername());
					}else
						userDataService.setUserPIIRejected(piiForm.getUsers().get(i).getUsername());
				}
			}
		}
		return "redirect:/governmentAuthority";
	}
	
	@RequestMapping(value = "/governmentAuthority/requestPIIInfo", method = RequestMethod.GET)
	public ModelAndView requestPIIInfo() {
		ModelAndView model = new ModelAndView("govtRequestInfo");
		return model;
	}
	
	@RequestMapping(value = "/governmentAuthority/requestInfo", method = RequestMethod.POST)
	public String sendRequestInfo( @RequestParam String pii) {
		if(pii!=null && !pii.equals("")){
			try {
				GovtInfoRequest req = new GovtInfoRequest();
				req.setPii(pii);
				govtInfoReqService.addNewRequest(req);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "redirect:/governmentAuthority";
	}
}
