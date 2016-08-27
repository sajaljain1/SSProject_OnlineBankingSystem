package edu.asu.cse.softwaresecurity.group6.bosd.model.pki;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "USER_PUBLIC_KEY")
public class UserPublicKey {
	private String username;
	private Long keyId; 
	private String publicKey;
	
	@Column(name = "public_key")
	public String getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}

	@Id
    @Column(name="key_id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Long getKeyId() {
		return keyId;
	}

	public void setKeyId(Long keyId) {
		this.keyId = keyId;
	}

	@Column(name = "username", nullable = false)
	public String getUsername() {
		return this.username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}

}
