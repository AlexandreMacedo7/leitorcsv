package br.com.macedo.leitorcsv.utility;

import br.com.macedo.leitorcsv.mapper.ConversorCsvToEntityMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component("fileMonitoramentoBuscaCsv")
public class FileMonitoramento {


    @Autowired
    private ResourceLoader resourceLoader;
    @Autowired
    ConversorCsvToEntityMapper csvToDtoMapper;
    private final static Logger LOGGER = LoggerFactory.getLogger(FileMonitoramento.class);


    @Scheduled(fixedRate = 5000)
    private void buscaCsv() {

        Resource resource = resourceLoader.getResource("classpath:/arquivorecebido/");
        File folderPath = null;
        try {
            folderPath = resource.getFile();
        } catch (
                IOException e) {
            LOGGER.error("Erro ao obter pasta de recursos", e);
        }

        List<MultipartFile> multipartFile = new ArrayList<>();

        LOGGER.info("Buscando arquivo CSV");

        assert folderPath != null;
        File[] arquivos = folderPath.listFiles((dir, name) -> name.toLowerCase().endsWith(".csv"));

        assert arquivos != null;
        Arrays.stream(arquivos).forEach(arquivo -> {
            try {
                LOGGER.info("Arquivo entrontado: " + arquivo.getName());
                csvToDtoMapper.converterCsvParaDto((MultipartFile) arquivo);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

}
