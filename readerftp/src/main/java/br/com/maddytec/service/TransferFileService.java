package br.com.maddytec.service;

import br.com.maddytec.domain.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransferFileService {

    @Autowired
    private TransferFTPService transferFTPService;

    @Autowired
    private FileService fileService;

    @Scheduled(fixedRate = 100*600)
    public void execute(){
        List<FileTransfer> files = transferFTPService.execute();

        for(FileTransfer fileTransfer : files){
         String uuid = fileService.execute(
                    File.builder()
                    .path(fileTransfer.getPath())
                    .build()
            );

            fileTransfer.setUuid(uuid);
        }

    }
}
