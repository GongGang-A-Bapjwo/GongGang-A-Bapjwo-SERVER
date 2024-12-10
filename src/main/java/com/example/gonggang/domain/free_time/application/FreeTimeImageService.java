package com.example.gonggang.domain.free_time.application;

import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.gonggang.domain.external.s3.application.S3Service;
import com.example.gonggang.domain.external.s3.exception.S3UploadFailException;
import com.example.gonggang.domain.free_time.dto.response.ImageUploadResponse;
import com.example.gonggang.domain.member.application.MemberService;
import com.example.gonggang.domain.member.domain.Member;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FreeTimeImageService {
	private static final String FREE_TIME_S3_UPLOAD_FOLER = "free-time/";

	private final MemberService memberService;
	private final S3Service s3Service;

	@Transactional
	public ImageUploadResponse uploadImage(Long memberId, MultipartFile file) {
		Member member = memberService.findMemberByMemberId(memberId);

		try {
			String imageUrl = s3Service.uploadImage(FREE_TIME_S3_UPLOAD_FOLER, file);
			member.updateTimeTableImageUrl(imageUrl);
			memberService.saveMember(member);
			return ImageUploadResponse.of(imageUrl, member.getId());
		} catch (RuntimeException | IOException e) {
			throw new S3UploadFailException();
		}
	}
}
