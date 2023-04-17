package br.com.macedo.leitorcsv.utility;

import br.com.macedo.leitorcsv.mapper.ConversorCsvToEntityMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

@Component("fileMonitoramentoBuscaCsv")
public class FileMonitoramento {

    @Autowired
    ConversorCsvToEntityMapper csvToEntityMapper;
    @Autowired
    CsvToMultipart csvToMultipart;
    private final static Logger LOGGER = LoggerFactory.getLogger(FileMonitoramento.class);


    @Scheduled(fixedRate = 5000)
    private void buscaCsv() {


        File folderPath = new File(("data/arquivorecebido/"));

        LOGGER.info("Buscando arquivo CSV");

        File[] arquivos = folderPath.listFiles((dir, name) -> name.toLowerCase().endsWith(".csv"));

        if(arquivos != null){
        Arrays.stream(arquivos).forEach(arquivo -> {
                LOGGER.info("Arquivo encontrado: " + arquivo.getName());
            try {
                MultipartFile multipartFile = csvToMultipart.CsvToMultipartFile(arquivo);
                csvToEntityMapper.converterCsvParaEntity(multipartFile);
                LOGGER.info("Arquivo convertido em entidade!");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        }
    }

}
