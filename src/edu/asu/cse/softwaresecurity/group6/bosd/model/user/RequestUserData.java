package edu.asu.cse.softwaresecurity.group6.bosd.model.user;

public class RequestUserData {
	private Request request;
	private UserData userdata;
	public Request getRequest() {
		return request;
	}
	public void setRequest(Request request) {
		this.request = request;
	}
	public UserData getUserdata() {
		return userdata;
	}
	public void setUserdata(UserData userdata) {
		this.userdata = userdata;
	}
}
