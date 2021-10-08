package com.vti.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.vti.entity.Account;
import com.vti.request.AccountRequest;
import com.vti.request.AccountUpdateRequest;

public interface IAccountService {

public Page<Account> getAllAccounts(Pageable pageable);
	
	public Account getAccountById(int id);
	
	public Account getAccountByUsername(String name);
	
	public Account getAccountByEmail(String email);
	
	public void createAccount(AccountRequest form);
	
	public void activeUser(String token);

	public void sendConfirmUserRegistrationViaEmail(String email);
	
	public void sendConfirmResetPasswordViaEmail(String email);
	
	public boolean existsByUsername(String name);

	public boolean existsByEmail(String email);
	
	public boolean existsByPhoneNumber(String phoneNumber);
	
	public void createCart(Account account);
	
	public void deleteAccount(int id);
	
	public void updateAccount(int id, AccountUpdateRequest request);
	
	public void resetPassword(String email);
	
	public void activeResetPassword(String token);

	/**
	 * Function gửi mail
	 */
	void sendConfirmUserRegistrationViaEmail(String email, String token);
	
}
