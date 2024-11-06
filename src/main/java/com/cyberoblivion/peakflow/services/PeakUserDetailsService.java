package com.cyberoblivion.peakflow.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cyberoblivion.peakflow.data.UserAccount;
import com.cyberoblivion.peakflow.data.UserAccountRepository;

@Service
public class PeakUserDetailsService implements UserDetailsService {

    UserAccountRepository userAccountRepository;
    

    public PeakUserDetailsService(UserAccountRepository userAccountRepository) {
        this.userAccountRepository = userAccountRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {        

        UserAccount user = userAccountRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Convert roles from the database to granted authorities
        List<GrantedAuthority> authorities = user.getRoles().stream()
            .map(role -> new SimpleGrantedAuthority("ROLE_" + role.toString()))
            .collect(Collectors.toList());

            return new org.springframework.security.core.userdetails.User(user.getUsername(),
            user.getPassword(), user.getEnabled(), true, true, true, authorities);
        
    }
    
}
