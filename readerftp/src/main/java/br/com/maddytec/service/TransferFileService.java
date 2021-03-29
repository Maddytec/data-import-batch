package br.com.maddytec.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransferFileService {

    @Autowired
    private TransferFTPService transferFTPService;

    @Scheduled(fixedRate = 100*600)
    public void execute(){

        List<FileTransfer> files = transferFTPService.execute();
    }
}
