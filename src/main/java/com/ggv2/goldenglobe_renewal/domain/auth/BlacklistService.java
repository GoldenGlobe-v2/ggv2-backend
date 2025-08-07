package com.ggv2.goldenglobe_renewal.domain.auth;

import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Service
public class BlacklistService {
  private final Set<String> tokenBlacklist = Collections.synchronizedSet(new HashSet<>());

  public void addToBlacklist(String token) {
    tokenBlacklist.add(token);
    System.out.println("Token added to blacklist: " + token);
  }

  public boolean isBlacklisted(String token) {
    return tokenBlacklist.contains(token);
  }
}
