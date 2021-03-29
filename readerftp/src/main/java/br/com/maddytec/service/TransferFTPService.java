package br.com.maddytec.service;

import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

@Service
@Log4j2
public class TransferFTPService {

    @Value("${ftp.server}")
    private String server;

    @Value("${ftp.port}")
    private String port;

    @Value("${ftp.user}")
    private String user;

    @Value("${ftp.pass}")
    private String pass;

    @Autowired
    private UploadS3Service uploadS3Service;

    public List<FileTransfer> execute(){
        List<FileTransfer> listFileTransfer = new ArrayList<>() ;
        FTPClient ftpClient = new FTPClient();

        try {
            ftpClient.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out), true));
            ftpClient.connect(server, Integer.parseInt(port));
            ftpClient.login(user, pass);
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);

            String[] files = ftpClient.listNames();
            for (String file: files ) {
                File tempDownload = new File(file);

                OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(tempDownload));
                boolean success = ftpClient.retrieveFile(file, outputStream);
                outputStream.close();
                if(success){
                    log.info("File arrived with success, sending to S3...");
                    FileTransfer fileTransfer = uploadS3Service.execute(file, tempDownload);
                    fileTransfer.setPathLocal(tempDownload);
                    listFileTransfer.add(fileTransfer);
                    log.info("File sent to S3.");

                    ftpClient.deleteFile(file);
                    log.info("File deleted the server FTP.");
                }
            }
        } catch (Exception e) {
            log.error("Error: " + e.getMessage() + "Cause: " + e.getCause());
        } finally {
            try{
                if(ftpClient.isConnected()){
                    ftpClient.logout();
                    ftpClient.disconnect();
                }
            }catch (IOException e){
                log.error("FTP server was not disconnected");
            }
        }
        return listFileTransfer;
    }

}
