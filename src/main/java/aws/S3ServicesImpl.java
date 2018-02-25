package aws;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;

@Service
public class S3ServicesImpl implements S3Services {
    static final int FULL = 10;
    static final int NORMAL = 5;
    static final int NONE = 0;


    int loggingLevel = NONE;

    @Autowired
    private AmazonS3 s3client;

    @Value("${jsa.s3.bucket}")
    private String bucket;

    private Logger logger = LoggerFactory.getLogger(S3ServicesImpl.class);


    @Override
    public void downloadFile(String fileName) {
        try{
            S3Object s3object = s3client.getObject(new GetObjectRequest(bucket, fileName));
            if(loggingLevel >= FULL){
                logger.info("file " + fileName + " has been downloaded successfully");
            }
        }catch(AmazonServiceException ase){
            if(loggingLevel >= NORMAL) {
                logger.info("Caught an AmazonServiceException from GET requests, rejected reasons:");
                logger.info("Error Message:    " + ase.getMessage());
            }
            if(loggingLevel  >= FULL) {
                logger.info("HTTP Status Code: " + ase.getStatusCode());
                logger.info("AWS Error Code:   " + ase.getErrorCode());
                logger.info("Error Type:       " + ase.getErrorType());
                logger.info("Request ID:       " + ase.getRequestId());
            }
            loggingLevel++;
        }catch(AmazonClientException ace){
            if(loggingLevel >= NORMAL) {
                logger.info("Caught an AmazonClientException:");
                logger.info("Error Message:    " + ace.getMessage());
            }
            if(loggingLevel  >= FULL) {
                logger.info("Reported Cause: " + ace.getCause());
            }
            loggingLevel++;
        }
    }

    @Override
    public void uploadFile(String fileName, String filePath) {
        try{
            File path = new File(filePath);
            s3client.putObject(new PutObjectRequest(bucket, fileName, path));
            if(loggingLevel >= FULL)
                logger.info("File " + fileName + " uploaded successfully.");
        }catch(AmazonServiceException ase){
            if(loggingLevel >= NORMAL) {
                logger.info("Caught an AmazonServiceException from GET requests, rejected reasons:");
                logger.info("Error Message:    " + ase.getMessage());
            }
            if(loggingLevel  >= FULL) {
                logger.info("HTTP Status Code: " + ase.getStatusCode());
                logger.info("AWS Error Code:   " + ase.getErrorCode());
                logger.info("Error Type:       " + ase.getErrorType());
                logger.info("Request ID:       " + ase.getRequestId());
            }
            loggingLevel++;
        }catch(AmazonClientException ace){
            if(loggingLevel >= NORMAL) {
                logger.info("Caught an AmazonClientException:");
                logger.info("Error Message:    " + ace.getMessage());
            }
            if(loggingLevel  >= FULL) {
                logger.info("Reported Cause: " + ace.getCause());
            }
            loggingLevel++;
        }

    }
}