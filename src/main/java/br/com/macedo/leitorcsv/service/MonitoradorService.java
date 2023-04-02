package br.com.macedo.leitorcsv.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.concurrent.Executors;

@Service
public class MonitoradorService {

    @Autowired
    private CsvService csvService;

    //Caminho para a pasta
    @Value("${csv.folder}")
    private String folderPath = "C:\\Projetos\\leitorcsv\\src\\main\\resources";


    public void inicio() throws IOException {

        //Objeto para monitoramento da pasta
        WatchService watchService = FileSystems.getDefault().newWatchService();
        Path folder = Paths.get(folderPath);

        //Registrar status de alteração da pasta para criação
        folder.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);
        //informar qual arquivo entrou na pasta
        System.out.println("Monotamento da pasta: " + folderPath);

        //inicia a thread para processar possiveis eventos

        Executors.newSingleThreadExecutor().submit(() -> {

            while (true) {
                //Declaração do objeto que sera a pasta que recebera a pasta que sera monitorada
                WatchKey key;
                try {
                    key = watchService.take(); //bloqueia a thread atual
                } catch (InterruptedException e) {
                    return;
                }
                for (WatchEvent<?> event : key.pollEvents()) {//Itera sobre eventos que possam ocorrer na chave
                    WatchEvent.Kind<?> kind = event.kind();//recupera o tipo de evento na chave //kind = tipo

                    if (kind == StandardWatchEventKinds.OVERFLOW) {//Caso ocorra alguma perda de evento
                        continue;
                    }

                    @SuppressWarnings("unchecked") //ignorar warnings
                    WatchEvent<Path> e = (WatchEvent<Path>) event; //convert event em tipo WatchEvent

                    Path fileName = e.context(); //obtem o nome do arquivo criado
                    System.out.println("Arquivo criado: " + fileName);

                    //Processa o arquivo csv
                    if (fileName.toString().endsWith(".csv")) {//verifica se o arquivo é um csv

                        Path filePath = folder.resolve(fileName); //cria um objeto Path

                        try {
                            csvService.processarCsv((MultipartFile) filePath.toFile());
                            System.out.println("CSV file processed: " + fileName);
                        } catch (IOException exception) {
                            System.out.println("Erro no processo: " + fileName);
                            exception.printStackTrace();
                        }
                    }

                }
                boolean valid = key.reset();
                if (!valid) {
                    break;
                }
            }

        });

    }
}
