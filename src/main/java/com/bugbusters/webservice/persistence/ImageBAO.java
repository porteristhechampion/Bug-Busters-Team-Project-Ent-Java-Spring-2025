package com.bugbusters.webservice.persistence;

// Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: Apache-2.0
import com.amazonaws.AmazonServiceException;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

import java.io.File;
import java.nio.file.Paths;

/**
 * Upload a file to an Amazon S3 bucket.
 *
 * This code expects that you have AWS credentials set up per:
 * http://docs.aws.amazon.com/java-sdk/latest/developer-guide/setup-credentials.html
 */
public class ImageBAO {
    public String uploadImage(String filename) {

        // using my own S3 Bucket to test
        String bucket_name = "bugbustertrybucket";

        String key_name = Paths.get(filename).getFileName().toString();

        System.out.format("Uploading %s to S3 bucket %s...\n", filename, bucket_name);

        // Note that Region needs to change for bugbusters
        final AmazonS3 s3 = AmazonS3ClientBuilder.standard().withRegion(Regions.US_WEST_2).build();
        try {
            s3.putObject(bucket_name, key_name, new File(filename));
        } catch (AmazonServiceException e) {
            System.err.println(e.getErrorMessage());
            String error_code = e.getErrorCode();
            return error_code;
        }
        System.out.println("Done!");
        return "Success!";
    }
}