package com.hcl.auth;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
@Component
public class GetAuthImpl implements GetAuth {

	public Authentication getAuth() {

		return SecurityContextHolder.getContext().getAuthentication();

	}

}
