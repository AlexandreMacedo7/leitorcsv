package br.com.macedo.leitorcsv.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MonitoradorService {

    @Autowired
    private CsvService csvService;

    @Value("${csv.folder}")
    private String past = "C:\\Projetos\\leitorcsv\\src\\main\\resources";


}
