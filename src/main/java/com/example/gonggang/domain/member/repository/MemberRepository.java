package com.example.gonggang.domain.member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.gonggang.domain.member.domain.Member;
import com.example.gonggang.domain.member.domain.SocialType;

import feign.Param;

public interface MemberRepository extends JpaRepository<Member, Long> {

	@Query("SELECT u FROM Member u WHERE u.socialId = :socialId AND u.socialType = :socialType")
	Optional<Member> findBySocialTypeAndSocialId(@Param("socialId") Long socialId,
		@Param("socialType") SocialType socialType);

}
