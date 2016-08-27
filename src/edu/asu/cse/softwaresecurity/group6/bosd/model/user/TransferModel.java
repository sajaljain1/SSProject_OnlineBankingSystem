package edu.asu.cse.softwaresecurity.group6.bosd.model.user;

public class TransferModel {

	private String amount;
	private Account senderAcc;
	private Account receiverAcc;
	private String senderAccID;
	private String receiverAccID;
	private String type;
	private String OTP;
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAmount() {
		return amount;
	}
	
	public void setAmount(String amount) {
		this.amount = amount;
	}
	
	public Account getSenderAcc() {
		return senderAcc;
	}
	
	public void setSenderAcc(Account senderAcc) {
		this.senderAcc = senderAcc;
	}
	
	public Account getReceiverAcc() {
		return receiverAcc;
	}
	
	public void setReceiverAcc(Account recieverAcc) {
		this.receiverAcc = recieverAcc;
	}

	
	public String getSenderAccID() {
		return senderAccID;
	}

	public void setSenderAccID(String senderAccID) {
		this.senderAccID = senderAccID;
	}

	public String getReceiverAccID() {
		return receiverAccID;
	}

	public void setReceiverAccID(String receiverAccID) {
		this.receiverAccID = receiverAccID;
	}

	
	
	public String getOTP() {
		return OTP;
	}

	public void setOTP(String oTP) {
		OTP = oTP;
	}

	@Override
	public String toString() {
		return "TransferModel [amount=" + amount + ", senderAccID=" + senderAccID
				+ ", recieverAccID=" + receiverAccID + "]";
	}
	

}
