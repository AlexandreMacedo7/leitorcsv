package br.com.macedo.leitorcsv.controller;

import br.com.macedo.leitorcsv.model.RegistroAluno;
import br.com.macedo.leitorcsv.service.CsvService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
public class CsvController {
    @Autowired
    private CsvService csvService;
    @PostMapping("/csv")
    public List<RegistroAluno> uploadCsv(@RequestPart("file") MultipartFile file) throws IOException {

        if (!file.getContentType().equals("text/csv")) {
            throw new IllegalArgumentException("O arquivo enviado não é um arquivo CSV válido.");
        }

        try {
            return csvService.processarCsv(file);
        } catch (IOException e) {
            throw new IOException("Ocorreu um erro ao processar o arquivo CSV: " + e.getMessage(), e);
        }
    }
}
