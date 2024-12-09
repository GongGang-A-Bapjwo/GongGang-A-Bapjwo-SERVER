package com.example.gonggang.global.auth.client.application;

import com.example.gonggang.global.auth.client.dto.MemberInfoResponse;
import com.example.gonggang.global.auth.client.dto.MemberLoginRequest;

public interface SocialService {
	MemberInfoResponse login(final String authorizationToken, final MemberLoginRequest loginRequest);
}
