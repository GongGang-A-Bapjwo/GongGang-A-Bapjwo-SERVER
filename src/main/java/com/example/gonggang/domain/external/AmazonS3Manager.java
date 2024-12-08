package com.example.gonggang.domain.external;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.example.gonggang.domain.external.exception.FileProcessingFailException;
import com.example.gonggang.domain.external.exception.S3UploadFailException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

@Slf4j
@Component
@RequiredArgsConstructor
public class AmazonS3Manager {

	@Value("${cloud.aws.s3.bucket}")
	private String bucket;

	@Value("${cloud.aws.region.static}")
	private String region;

	@Value("${cloud.aws.credentials.accessKey}")
	private String accessKey;

	@Value("${cloud.aws.credentials.secretKey}")
	private String secretKey;

	@Value("${cloud.aws.cloudfront.url}")
	private String cloudfrontUrl;

	private S3Client getS3Client() {
		return S3Client.builder()
			.region(Region.of(region))
			.credentialsProvider(
				StaticCredentialsProvider.create(
					AwsBasicCredentials.create(accessKey, secretKey)
				)
			)
			.build();
	}

	public String upload(MultipartFile multipartFile, String dirName) {
		String fileName = generateFileName(dirName, multipartFile.getOriginalFilename());
		try (InputStream inputStream = multipartFile.getInputStream()) {
			return putS3(inputStream, fileName, multipartFile.getContentType());
		} catch (IOException e) {
			log.error("S3 파일 업로드 중 오류 발생: {}", e.getMessage());
			throw new S3UploadFailException();
		}
	}

	private String putS3(InputStream inputStream, String fileName, String contentType) {
		S3Client s3Client = getS3Client();

		try {
			s3Client.putObject(
				PutObjectRequest.builder()
					.bucket(bucket)
					.key(fileName)
					.contentType(contentType)
					.build(),
				RequestBody.fromInputStream(inputStream, inputStream.available())
			);

			return generatePublicUrl(fileName);
		} catch (S3Exception e) {
			log.error("S3 업로드 실패: {}", e.awsErrorDetails().errorMessage());
			throw new S3UploadFailException();
		} catch (IOException e) {
			log.error("파일 읽기 중 오류 발생: {}", e.getMessage());
			throw new FileProcessingFailException();
		}
	}

	private String generatePublicUrl(String fileName) {
		return cloudfrontUrl + "/" + fileName;
	}

	private String generateFileName(String dirName, String originalFilename) {
		return dirName + "/" + Objects.requireNonNull(originalFilename);
	}
}
