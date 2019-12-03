package com.hien.service.impl;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.hien.service.JwtService;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

@Service
public class JwtServiceImpl implements JwtService {

	// generate jwt from user information
	@Override
	public String generateToken(String userName) {
		String token = null;
		try {
			// Create HMAC signer
			JWSSigner signer = new MACSigner(generateShareSecret());
			JWTClaimsSet.Builder builder = new JWTClaimsSet.Builder();
			builder.claim(USER_NAME, userName);
			builder.expirationTime(generateExpirationDate());
			JWTClaimsSet claimsSet = builder.build();
			SignedJWT signnerJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claimsSet);
			// Apply the HMAC protection
			signnerJWT.sign(signer);
			// Serialize to compact form
			token = signnerJWT.serialize();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return token;
	}

	// get claims from token
	@Override
	public JWTClaimsSet getClaimsFromToken(String token) {
		JWTClaimsSet claims = null;
		try {
			SignedJWT signedJWT = SignedJWT.parse(token);
			JWSVerifier verifier = new MACVerifier(generateShareSecret());
			if (signedJWT.verify(verifier)) {
				claims = signedJWT.getJWTClaimsSet();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return claims;
	}

	// generate expration
	@Override
	public Date generateExpirationDate() {
		// int expire_time = Integer.parseInt(env.getProperty("expire_time"));
		return new Date(System.currentTimeMillis() + EXPIRE_TIME);
	}

	// get expiration from token
	@Override
	public Date getExpriceDateFromToken(String token) {
		Date exprication = null;
		JWTClaimsSet claims = getClaimsFromToken(token);
		exprication = claims.getExpirationTime();
		return exprication;
	}

	// get user_name from token
	@Override
	public String getUsernameFromToken(String token) {
		String userName = null;
		try {
			JWTClaimsSet claimsSet = getClaimsFromToken(token);
			userName = claimsSet.getStringClaim(USER_NAME);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userName;
	}

	// generate digital signatures
	@Override
	public byte[] generateShareSecret() {
		byte[] shareSecret = new byte[32];
		shareSecret = SECRET_KEY.getBytes();
		return shareSecret;
	}

	// check token expried
	@Override
	public boolean isTokenExpried(String token) {
		Date expration = getExpriceDateFromToken(token);
		return expration.before(new Date());
	}

	// validate token
	@Override
	public boolean validateTokenLogin(String token) {
		if (token == null || token.trim().length() == 0) {
			return false;
		}
		String userName = getUsernameFromToken(token);
		if (userName == null || userName.isEmpty()) {
			return false;
		}
		if (isTokenExpried(token)) {
			return false;
		}
		return true;
	}
}
