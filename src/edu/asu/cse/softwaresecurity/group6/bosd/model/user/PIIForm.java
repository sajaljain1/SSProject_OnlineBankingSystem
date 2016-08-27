package edu.asu.cse.softwaresecurity.group6.bosd.model.user;

import java.util.List;

public class PIIForm {
	private List<UserData> users;
	private List<FormChecked> checkedUsers;
	
	public PIIForm() {
		super();
	}
	
	public PIIForm(List<UserData> users) {
		super();
		this.users = users;
	}
	
	public List<FormChecked> getCheckedUsers() {
		return checkedUsers;
	}

	public void setCheckedUsers(List<FormChecked> checkedUsers) {
		this.checkedUsers = checkedUsers;
	}

	public List<UserData> getUsers() {
		return users;
	}

	public void setUsers(List<UserData> users) {
		this.users = users;
	}
}
