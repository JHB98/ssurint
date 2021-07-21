package listview.androidtown.org.ssuprinter;

import java.io.Serializable;

public class FileEvent implements Serializable {

    public FileEvent() {
    }

    private static final long serialVersionUID = 1L;

    private String destDir;
    private String userInfo;
    private String srcDir;
    private String filename;
    private long fileSize;
    private byte[] fileData;
    private String status;
    private int lower;
    private int upper;
    private int times;

    public String getDestDir() {
        return destDir;
    }

    public void setDestDir(String destDir) {
        this.destDir = destDir;
    }

    public String getSrcDir() {
        return srcDir;
    }

    public void setSrcDir(String srcDir) {
        this.srcDir = srcDir;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public byte[] getFileData() {
        return fileData;
    }

    public void setFileData(byte[] fileData) {
        this.fileData = fileData;
    }
    public void setUserInfo(String userInfo) {
        this.userInfo = userInfo;
    }

    public String getUserInfo() {
        return userInfo;
    }
    public void setUpper(int  upper) {
        this.upper = upper;
    }

    public int getUpper() {
        return upper;
    }

    public void setLower(int lower) {
        this.lower = lower;
    }

    public int getLower() {
        return lower;
    }

    public void setTimes(int times) {
        this.times = times;
    }

    public int getTimes() {
        return times;
    }
}