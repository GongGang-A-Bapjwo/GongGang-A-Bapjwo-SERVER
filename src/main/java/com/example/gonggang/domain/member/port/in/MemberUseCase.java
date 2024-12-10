package com.example.gonggang.domain.member.port.in;

import com.example.gonggang.domain.member.domain.Member;
import com.example.gonggang.domain.member.domain.SocialType;

public interface MemberUseCase {
	com.example.gonggang.domain.member.domain.Member findMemberByMemberId(Long memberId);

	boolean checkMemberExistsBySocialIdAndSocialType(Long socialId, SocialType socialType);

	Member findMemberBySocialIdAndSocialType(Long socialId, SocialType socialType);

	void deleteUser(Long id);
}
