package br.com.maddytec.service;

import br.com.maddytec.domain.File;
import br.com.maddytec.enums.FileStatusEnum;
import br.com.maddytec.gateway.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FileService {

    @Autowired
    FileRepository fileRepository;

    @Transactional
    public String  execute(File file){
        file.setStatus(FileStatusEnum.RECEBIDO.toString());
        fileRepository.save(file);
        return file.getId().toString();
    }
}
