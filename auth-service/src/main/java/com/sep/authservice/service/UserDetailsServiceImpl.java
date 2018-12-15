package com.sep.authservice.service;

import com.sep.authservice.entity.Client;
import com.sep.authservice.repository.ClientRepository;
import com.sep.authservice.utility.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {


	private final ClientRepository clientRepository;

	public UserDetailsServiceImpl(ClientRepository clientRepository) {
		this.clientRepository = clientRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String clientId) throws UsernameNotFoundException {
		try {
			String client = Utility.readToken(clientId);
			List<Client> result = clientRepository.findByClient(client);
			if(result.size() == 0)
				throw new UsernameNotFoundException("Client: " + client + " not found");

			//ovde iscitati role i privilegije
//			List<GrantedAuthority> grantedAuthorities = AuthorityUtils
//					.commaSeparatedStringToAuthorityList("ROLE_" + appUser.getRole());
			List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
			return new AppUser(client);

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private static class AppUser implements  UserDetails {
	    	private String username;

		public AppUser(String username) {
	    		this.username = username;
	    	}

		@Override
		public String getUsername() {
			return username;
		}

		@Override
		public boolean isAccountNonExpired() {
			return false;
		}

		@Override
		public boolean isAccountNonLocked() {
			return false;
		}

		@Override
		public boolean isCredentialsNonExpired() {
			return false;
		}

		@Override
		public boolean isEnabled() {
			return false;
		}

		@Override
		public Collection<? extends GrantedAuthority> getAuthorities() {
			return null;
		}

		@Override
		public String getPassword() {
			return null;
		}
	}
}