package org.corodiak.sangsang.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JWTUtil {
	public static final String key = "TESTKEY";

	public static String createToken(Map<String, Object> payload) {
		Map<String, Object> header = new HashMap<String, Object>();
		header.put("typ", "JWT");
		header.put("alg", "HS256");

		Long expiredTime = 1000 * 60l * 60l * 24l * 365l;
		Date now = new Date();
		now.setTime(now.getTime() + expiredTime);

		String jwt = Jwts.builder().setHeader(header).setClaims(payload).setExpiration(now)
				.signWith(SignatureAlgorithm.HS256, key.getBytes()).compact();

		return jwt;
	}

	public static Map<String, Object> validateToken(String token) {
		try {
			Claims claims = Jwts.parser().setSigningKey(key.getBytes()).parseClaimsJws(token).getBody();
			return claims;
		} catch (Exception e) {
			return null;
		}
	}
}
