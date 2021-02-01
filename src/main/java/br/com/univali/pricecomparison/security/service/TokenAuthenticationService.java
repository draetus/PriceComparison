package br.com.univali.pricecomparison.security.service;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import br.com.univali.pricecomparison.api.user.model.User;
import br.com.univali.pricecomparison.api.user.repository.UserRepository;
import br.com.univali.pricecomparison.security.exception.AuthException;
import br.com.univali.pricecomparison.security.model.PriceComparisonUserDetails;

@Service
public class TokenAuthenticationService {
	
	private static UserRepository userRepository;

	public static final String HEADER_STRING = "Authorization";

	private static final String TOKEN_SIGN_SECRET = "e8f001782cf6ffc07111a30237cea89c";
	private static final int SESSION_MINUTES = 60;

	private static final Algorithm TOKEN_SIGN_ALGORITHM = getTokenSignAlgorithm();
	private static final String TOKEN_ID = "id";
	public static final String USER_ID = "user_id";
	private static final String USERNAME = "cpf";

	private static final String CLAIM_ROLES = "ROLES";
	private static final String ISSUER = "br.com.univali.pricecomparison";
	private static final String ROLE_PREFIX = "ROLE_";
	
	public enum Auth{
		LOGIN, AUTH
	}
	
	@Autowired
	public TokenAuthenticationService(UserRepository userRepository) {
		TokenAuthenticationService.userRepository = userRepository;
	}

	private static Algorithm getTokenSignAlgorithm() {
		try {
			return Algorithm.HMAC256(TOKEN_SIGN_SECRET);
		} catch (IllegalArgumentException | UnsupportedEncodingException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	public static void addAuthentication(HttpServletResponse response, Authentication auth, boolean login) {
		Auth authEnum;
		String[] roles = extractRolesAsArray(auth);
		if(login) {
			authEnum = Auth.LOGIN;
		} else {
			authEnum =  Auth.AUTH;
		}
		String jwt = createDefaultSessionToken(auth, roles, authEnum);
		response.addHeader(HEADER_STRING, jwt);
	}

	private static String createDefaultSessionToken(Authentication auth, String[] roles, Auth authEnum) {
		return createToken(auth, roles, authEnum);
	}

	private static String createToken(Authentication auth, String[] roles, Auth authEnum) {
		Calendar exp = Calendar.getInstance();
		exp.setTime(new Date());
		exp.add(Calendar.MINUTE, SESSION_MINUTES);
		
		switch (authEnum) {
			case LOGIN: 
			return jwtLogin(auth, exp.getTime(), roles);
			case AUTH: 
			return jwtAuth(auth, exp.getTime(), roles);
		}
		return null;
	}
	
	private static String jwtLogin(Authentication auth, Date exp, String[] roles) {
		int id = getRandomNumberInRange(1000, 10000000);

		String username = auth.getName();
		Map<String, Object> detais = ((PriceComparisonUserDetails) auth.getPrincipal()).getExtraData();

		return JWT.create().withIssuer(ISSUER).withSubject(username)
				.withArrayClaim(CLAIM_ROLES, roles)
				.withClaim(TOKEN_ID, id)		
				.withClaim(USER_ID, (Long) detais.get(USER_ID))
				.withExpiresAt(exp).sign(TOKEN_SIGN_ALGORITHM);
	}
	
	private static String jwtAuth(Authentication auth, Date exp, String[] roles) {
		int id = getRandomNumberInRange(1000, 10000000);
		String username = auth.getName();
		User user = userRepository.findByCpf(username);
		Map<String, Object> detais = ((PriceComparisonUserDetails) auth).getExtraData();
		return JWT.create().withIssuer(ISSUER).withSubject(username)
				.withArrayClaim(CLAIM_ROLES, roles)
				.withClaim(TOKEN_ID, id)		
				.withClaim(USER_ID, (Long) detais.get(USER_ID))
				.withClaim(USERNAME, user.getCpf())
				.withExpiresAt(exp).sign(TOKEN_SIGN_ALGORITHM);	
	}
	

	public static Authentication getAuthentication(HttpServletRequest request) {
		String token = request.getHeader(HEADER_STRING);
		if (token != null) {
			try {
				JWTVerifier verifier = JWT.require(TOKEN_SIGN_ALGORITHM).withIssuer(ISSUER).build();

				DecodedJWT jwt = verifier.verify(token);

				String user = jwt.getSubject();
				Claim claimRoles = jwt.getClaims().get(CLAIM_ROLES);
				
				if (claimRoles != null) {
					List<String> rolesString = claimRoles.asList(String.class);
					List<GrantedAuthority> authorities = stringToGrantedAuthorities(rolesString);
					Hashtable<String, Object> extraData = new Hashtable<String, Object>();
				
					if (user != null) {
						Long idUser = jwt.getClaim(USER_ID).asLong();
						extraData.put(USER_ID, idUser);

						return new PriceComparisonUserDetails(user, null, authorities, extraData);
					}
				}
			} catch (TokenExpiredException e) {
				throw new AuthException("Token expirado, acesso negado.");
			} catch (JWTVerificationException e) {
				throw new AuthException("Acesso negado.");
			}
		}
		return null;
	}
	
	private static List<GrantedAuthority> stringToGrantedAuthorities(List<String> rolesString){
		List<GrantedAuthority> authorities = new ArrayList<>();
		for (String role : rolesString) {
			authorities.add(createRole(role));
		}
		return authorities;
	}

	private static SimpleGrantedAuthority createRole(String role) {
		return new SimpleGrantedAuthority(role.startsWith(ROLE_PREFIX) ? role : ROLE_PREFIX + role);
	}
	
	private static String[] extractRolesAsArray(Authentication auth) {
		String[] roles = new String[auth.getAuthorities().size()];
		int index = 0;
		for (Iterator<?> iterator = auth.getAuthorities().iterator(); iterator.hasNext();) {
			GrantedAuthority authority = (GrantedAuthority) iterator.next();
			roles[index] = authority.getAuthority();
			index++;
		}
		return roles;
	}
	
	private static int getRandomNumberInRange(int min, int max) {

		if (min >= max) {
			throw new IllegalArgumentException("max must be greater than min");
		}
		return new Random().nextInt((max - min) + 1) + min;
	}
}
