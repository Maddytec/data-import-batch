package br.com.maddytec.service;

import br.com.maddytec.domain.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.util.List;

@Service
public class TransferFileService {

    @Autowired
    private TransferFTPService transferFTPService;

    @Autowired
    private FileService fileService;

    @Autowired
    private SendFileKafkaService sendFileKafkaService;

    @Scheduled(fixedRate = 100*600)
    public void execute() throws FileNotFoundException {
        List<FileTransfer> files = transferFTPService.execute();

        for(FileTransfer fileTransfer : files){
         String uuid = fileService.execute(
                    File.builder()
                    .path(fileTransfer.getPath())
                    .build()
            );

            fileTransfer.setUuid(uuid);
            sendFileKafkaService.execute(fileTransfer);
        }

    }
}
