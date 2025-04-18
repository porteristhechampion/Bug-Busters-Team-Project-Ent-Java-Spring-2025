package com.bugbusters.service;

import com.bugbusters.util.AwsAuthUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * A utility class to read and write images from/to an S3 bucket using the AWS SDK v2.
 *
 * @author Alison Fait
 * @author Justin Gritton-Bell
 */
public class S3ImageService {

    private static final Logger logger = LogManager.getLogger(S3ImageService.class);

    private final S3Client s3;
    private final String bucketName;

    /**
     * Constructor for S3ImageService that builds an S3 client.
     *
     * @param bucketName the name of the S3 bucket
     * @param region     the AWS region where the bucket is hosted
     */
    public S3ImageService(String bucketName, Region region) {
        this.bucketName = bucketName;
        this.s3 = S3Client.builder()
                .region(AwsAuthUtil.getRegion())
                .credentialsProvider(AwsAuthUtil.getCredentialsProvider())
                .build();
    }

    /**
     * Downloads an image from the specified S3 bucket and returns it as a BufferedImage.
     *
     * @param key the object key in the S3 bucket
     * @return a BufferedImage representing the downloaded image
     * @throws IOException if download or image parsing fails
     */
    public BufferedImage downloadImage(String key) throws IOException {
        try {
            GetObjectRequest getRequest = GetObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .build();

            ResponseBytes<GetObjectResponse> objectBytes = s3.getObjectAsBytes(getRequest);

            return ImageIO.read(new ByteArrayInputStream(objectBytes.asByteArray()));

        } catch (S3Exception | IOException e) {
            logger.error("Error downloading image from S3: " + key, e);
            throw new IOException("Failed to download image from S3", e);
        }
    }

    /**
     * Uploads a BufferedImage to the specified S3 bucket.
     *
     * @param key   the object key to use when saving the image
     * @param image the BufferedImage to upload
     * @throws IOException if the upload fails
     */
    public void uploadImage(String key, BufferedImage image) throws IOException {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(image, "png", outputStream);
            byte[] bytes = outputStream.toByteArray();

            PutObjectRequest putRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .contentType("image/png")
                    .build();

            s3.putObject(putRequest, RequestBody.fromBytes(bytes));
            logger.info("Uploaded image to S3: " + key);
        } catch (S3Exception | IOException e) {
            logger.error("Error uploading image to S3: " + key, e);
            throw new IOException("Failed to upload image to S3", e);
        }
    }
}
