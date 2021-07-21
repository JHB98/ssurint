package listview.androidtown.org.ssuprinter;

import android.util.Log;

import java.io.*;
import java.net.*;


public class TcpClient {
    Socket socket;
    ObjectOutputStream outputStream;
    boolean isConnected = false;
    FileEvent fileEvent;
    BufferedReader in;
    PrintWriter out;
    String srcPath, destPath, ip;
    int port = 8000;

    public TcpClient(String ip, int port, String srcPath, String destPath) throws Exception{

        this.ip = ip;
        this.port = port;
        this.srcPath = srcPath;
        this.destPath = destPath;

        while (!isConnected) {
                Log.d("status","ip : " + ip + " port : " + port);
                socket = new Socket(ip, port);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
                outputStream = new ObjectOutputStream(socket.getOutputStream());
                //서버 소켓에 스트림을 연결
                printInfo();
                isConnected = true;
        }
    }

    public void receive() {
        try {
            //서버 소켓으로부터 받은 메시지를 화면에 출력
            Log.d("status","[서버] " + in.readLine());
        } catch (IOException e) {
            Log.d("status",e.toString());
        }
    }

    public void send(String msg) {
        //서버 소켓에 메시지 전송
        out.println(msg);
        out.flush();
        Log.d("status","[클라이언트] " + msg);
    }

    public void sendFile() {

        FileEvent fileEvent = new FileEvent();

        String fileName = srcPath.substring(srcPath.lastIndexOf("/") + 1, srcPath.length());
        //String path = srcPath.substring(0, srcPath.lastIndexOf("/") + 1);

        fileEvent.setDestDir(destPath);
        fileEvent.setFilename(fileName);
        fileEvent.setSrcDir(srcPath);

        File file = new File(srcPath);

        if (file.isFile()) {
            try {
                DataInputStream diStream = new DataInputStream(new FileInputStream(file));
                long len = (int) file.length();
                byte[] fileBytes = new byte[(int) len];

                int read = 0;
                int numRead = 0;
                while (read < fileBytes.length && (numRead = diStream.read(fileBytes, read, fileBytes.length - read)) >= 0) {
                    read = read + numRead;
                }

                fileEvent.setFileSize(len);
                fileEvent.setFileData(fileBytes);
                fileEvent.setStatus("Success");
                diStream.close();
            } catch (Exception e) {
                e.printStackTrace();
                fileEvent.setStatus("Error");
            }
        } else {
            Log.d("status","path is not pointing to a file");
            fileEvent.setStatus("Error");
            Log.d("status","TCP client를 종료합니다.");
            System.exit(0);
        }

        // write file
        try {
            outputStream.writeObject(fileEvent);
            Log.d("status","파일 전송 완료");
            Thread.sleep(3000);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void sendFile(FileEvent target) {

        FileEvent fileEvent = target;

        String fileName = srcPath.substring(srcPath.lastIndexOf("/") + 1, srcPath.length());
        //String path = srcPath.substring(0, srcPath.lastIndexOf("/") + 1);

        fileEvent.setDestDir(destPath);
        fileEvent.setFilename(fileName);
        fileEvent.setSrcDir(srcPath);

        File file = new File(srcPath);

        if (file.isFile()) {
            try {
                DataInputStream diStream = new DataInputStream(new FileInputStream(file));
                long len = (int) file.length();
                byte[] fileBytes = new byte[(int) len];

                int read = 0;
                int numRead = 0;
                while (read < fileBytes.length && (numRead = diStream.read(fileBytes, read, fileBytes.length - read)) >= 0) {
                    read = read + numRead;
                }

                fileEvent.setFileSize(len);
                fileEvent.setFileData(fileBytes);
                fileEvent.setStatus("Success");
                diStream.close();
            } catch (Exception e) {
                e.printStackTrace();
                fileEvent.setStatus("Error");
            }
        } else {
            Log.d("status","path is not pointing to a file");
            fileEvent.setStatus("Error");
            Log.d("status","TCP client를 종료합니다.");
            System.exit(0);
        }

        // write file
        try {
            outputStream.writeObject(fileEvent);
            Log.d("status","파일 전송 완료");
            Thread.sleep(3000);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            // 클라이언트 소켓 종료
            socket.close();
        } catch (IOException e) {
            Log.d("status",e.toString());
        }
    }

    public void printInfo() {
        Log.d("status",">> 서버 접속에 성공했습니다.");
        //서비스 포트 번호와 클라이언트 주소와 포트번호 출력
        Log.d("status","     서버 주소: " + socket.getInetAddress());
        Log.d("status","     서버 포트번호: " + socket.getPort());
        Log.d("status","     클라이인트 포트번호: " + socket.getLocalPort() + '\n');
    }
}
