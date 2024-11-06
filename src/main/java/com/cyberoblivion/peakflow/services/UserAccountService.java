package com.cyberoblivion.peakflow.services;

import org.springframework.stereotype.Service;

import com.cyberoblivion.peakflow.data.UserAccount;
import com.cyberoblivion.peakflow.data.UserAccountRepository;

@Service
public class UserAccountService {
    private final UserAccountRepository userAccountRepository;

    
    public UserAccountService(UserAccountRepository userAccountRepository) {
        this.userAccountRepository = userAccountRepository;
    }

    public UserAccount createUserAccount(String username, String email, 
            String firstName, String lastName) {
        UserAccount userAccount = new UserAccount(username, email);
        userAccount.setFirstName(firstName);
        userAccount.setLastName(lastName);
        userAccount.setEnabled(true);
        userAccount.setRoles(null);
        userAccount.setPassword("not a valid password");
        return userAccountRepository.save(userAccount);
    }

}
