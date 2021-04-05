package br.com.maddytec.service;

import br.com.maddytec.gateway.json.FileUUIDJson;
import br.com.maddytec.kafka.KafkaRequestSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.util.Scanner;

@Service
public class SendFileKafkaService {

    private static final String SYSTEM_1 = "[SISTEMA1]";
    private static final String SYSTEM_2 = "[SISTEMA2]";

    @Value("${kafka.topic1}")
    private String topics1;

    @Value("${kafka.topic2}")
    private String topics2;

    @Autowired
    private KafkaRequestSender kafkaRequestSender;

    public void execute(FileTransfer fileTransfer) throws FileNotFoundException {
        Scanner scanner =  new Scanner(fileTransfer.getPathLocal());
        String firstLine = null;
        while (scanner.hasNextLine()){
            firstLine = scanner.nextLine();
            break;
        }

        String topic = null;
        if(SYSTEM_1.equals(firstLine)){
topic = topics1;
        } else if(SYSTEM_2.equals(firstLine)){
            topic = topics2;
        }
        kafkaRequestSender.sendMessage(topic, FileUUIDJson.builder().uuid(fileTransfer.getUuid()).build());
    }
}
