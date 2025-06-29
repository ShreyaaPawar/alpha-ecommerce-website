package com.shreyy.billingsoftware.service.impl;

import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import com.shreyy.billingsoftware.service.FileUploadService;
import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;
import software.amazon.awssdk.core.sync.RequestBody;


@Service
@RequiredArgsConstructor
public class FileUploadServiceImpl implements FileUploadService{
	
	private final S3Client s3Client;

	@Value("${aws.bucket.name}")
	private String bucketName;
	
	@Override
	public String uploadFile(MultipartFile file) {
		String filenameExtension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")+1);
		String key = UUID.randomUUID().toString() + "." + filenameExtension;
		try{
			System.out.println("Uploading to bucket: " + bucketName + " in region: " + s3Client.serviceClientConfiguration().region());
			PutObjectRequest putObjectRequest = PutObjectRequest.builder()
																.bucket(bucketName.trim())
																.key(key)
																.acl("public-read")
																.contentType(file.getContentType())
																.build();
			PutObjectResponse response = s3Client.putObject(putObjectRequest, RequestBody.fromBytes(file.getBytes()));
			if(response.sdkHttpResponse().isSuccessful()) {
				return "https://" + bucketName.trim() + ".s3.amazonaws.com/" + key;
			}else {
				throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occured while uploading the image!");
			}
		}catch (IOException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occured while uploading the file!");
		}
	}

	@Override
	public boolean deleteFile(String imageUrl) {
		String filename = imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
		DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
																	 .bucket(bucketName)
																	 .key(filename)
																	 .build();
		s3Client.deleteObject(deleteObjectRequest);
		return true;
	}

}
