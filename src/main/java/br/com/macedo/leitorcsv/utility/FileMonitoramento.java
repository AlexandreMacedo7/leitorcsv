package br.com.macedo.leitorcsv.utility;

import br.com.macedo.leitorcsv.entity.Aluno;
import br.com.macedo.leitorcsv.mapper.ConversorCsvToEntityMapper;
import br.com.macedo.leitorcsv.service.CsvService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component("fileMonitoramentoBuscaCsv")
public class FileMonitoramento {

    @Autowired
    ConversorCsvToEntityMapper csvToEntityMapper;
    @Autowired
    CsvService csvService;
    @Autowired
    FileMover fileMover;
    private final static Logger LOGGER = LoggerFactory.getLogger(FileMonitoramento.class);


    @Scheduled(fixedRate = 5000)
    private void buscaCsv() {


        File folderPath = new File(("data/arquivosrecebidos/"));

        LOGGER.info("Buscando arquivo CSV");

        File[] arquivos = folderPath.listFiles((dir, name) -> name.toLowerCase().endsWith(".csv"));

        if(arquivos != null){
            Arrays.stream(arquivos).forEach(arquivo -> {
                LOGGER.info("Arquivo encontrado: " + arquivo.getName());
                try {

                    List<Aluno> registros = csvService.lerCsv(arquivo.getAbsolutePath());
                    csvService.persistirEntidades(registros);
                    fileMover.moverArquivo(arquivo);

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }

}
