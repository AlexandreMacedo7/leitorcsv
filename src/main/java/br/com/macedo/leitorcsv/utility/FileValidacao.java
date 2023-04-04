package br.com.macedo.leitorcsv.utility;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileValidacao {

    public void isArquivoCsv(MultipartFile multipartFile){

        if (!multipartFile.getOriginalFilename().endsWith(".csv")){
            throw new IllegalArgumentException("O arquivo precisa ser um CSV");
        }
    }
}
