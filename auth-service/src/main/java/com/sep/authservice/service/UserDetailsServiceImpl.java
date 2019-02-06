package com.sep.authservice.service;

import com.sep.authservice.entity.SecurityUser;
import com.sep.authservice.entity.AppUser;
import com.sep.authservice.repository.UserRepository;
import com.sep.authservice.utility.Utility;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {


    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            String token = Utility.readToken(username);
            String clientUsername = "";

            // check if company is logged
            if (token.split("-")[1].equals("company")) {
                clientUsername = token.split("-")[0];       // if company is logged, get first token param
            } else {
                clientUsername = token.split("-")[2];       // if payment process is started, get journal name
            }

            AppUser result = userRepository.findByUsername(clientUsername);
            if (result == null)
                throw new UsernameNotFoundException("Client: " + clientUsername + " not found");
            //ovde iscitati role i privilegije
            //			List<GrantedAuthority> grantedAuthorities = AuthorityUtils
            //					.commaSeparatedStringToAuthorityList("ROLE_" + appUser.getRole());
            List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
            return new SecurityUser(result);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }


}