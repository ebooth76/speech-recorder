package aws;

public interface S3Services {
    public void downloadFile(String fileName);
    public void uploadFile(String fileName, String filePath);
}
