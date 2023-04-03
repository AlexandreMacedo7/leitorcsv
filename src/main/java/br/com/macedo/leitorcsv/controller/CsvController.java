package br.com.macedo.leitorcsv.controller;

import br.com.macedo.leitorcsv.dto.RegistroAlunoDTO;
import br.com.macedo.leitorcsv.entity.RegistroAluno;
import br.com.macedo.leitorcsv.service.CsvService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
public class CsvController {
    @Autowired
    private CsvService csvService;
    @PostMapping("/csv")
    public ResponseEntity<?> uploadCsv(@RequestPart("file") MultipartFile file) throws IOException {
        csvService.processarCsv(file);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
