package br.com.macedo.leitorcsv.utility;

import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@Component
public class CsvToMultipart {

    public MultipartFile CsvToMultipartFile(File file) throws IOException {

        FileInputStream fileInputStream = new FileInputStream(file);
        byte[] bytes = new byte[(int) file.length()];

        fileInputStream.read(bytes);
        MultipartFile multipartFile = new MockMultipartFile(file.getName(),
                file.getName(),"text/csv", bytes);

        fileInputStream.close();
        return multipartFile;
        }
}
