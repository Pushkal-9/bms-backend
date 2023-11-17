package com.bms.backend.services;

import com.bms.backend.config.AwsProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

@Service
@RequiredArgsConstructor
@Slf4j
public class S3Service {


    private final AwsProperties awsProperties;
    private final BookingService bookingService;

    private final String bucketName = "movie-radar";
    private final String region = "us-east-2";

    public void uploadQRCodeToS3(Long bookingId) {
        AwsBasicCredentials awsCreds = AwsBasicCredentials.create(
                awsProperties.getAccessKey(),
                awsProperties.getSecretKey()
        );

        try (S3Client s3Client = S3Client.builder()
                .region(Region.of(region))
                .credentialsProvider(StaticCredentialsProvider.create(awsCreds))
                .build()) {
            ByteArrayOutputStream qrOutputStream = bookingService.generateQRCodeForBooking(bookingId);
            byte[] qrCodeImage = qrOutputStream.toByteArray();
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(qrCodeImage);
            String key = "qr_codes/" + bookingId + ".png";
            PutObjectRequest objectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .contentType("image/png")
                    .acl(ObjectCannedACL.PUBLIC_READ)
                    .build();

            s3Client.putObject(objectRequest, RequestBody.fromInputStream(byteArrayInputStream, qrCodeImage.length));
            bookingService.updateQRCodeUrl(bookingId,key);
            log.info("QR code uploaded to S3 successfully!");
        } catch (Exception e) {
            log.error("Error uploading QR code to S3: " + e.getMessage());
        }
    }
}
