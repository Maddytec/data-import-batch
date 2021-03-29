package br.com.maddytec.service;

import lombok.Builder;
import lombok.Data;

import java.io.File;

@Data
@Builder
public class FileTransfer {
    private String uuid;
    private String path;
    private File pathLocal;
}
