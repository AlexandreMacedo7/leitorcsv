package br.com.macedo.leitorcsv.service;

import br.com.macedo.leitorcsv.dto.RegistroAlunoDTO;
import br.com.macedo.leitorcsv.entity.RegistroAluno;
import br.com.macedo.leitorcsv.mapper.RegistroAlunoMapper;
import br.com.macedo.leitorcsv.repostitory.AlunoRepository;
import br.com.macedo.leitorcsv.repostitory.AvaliacaoRepository;
import br.com.macedo.leitorcsv.repostitory.LivroRepository;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvValidationException;
import org.aspectj.apache.bcel.generic.MULTIANEWARRAY;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CsvService {

    @Autowired
    private AlunoRepository alunoRepository;
    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private AvaliacaoRepository avaliacaoRepository;
    @Autowired
    private RegistroAlunoMapper registroAlunoMapper;


    private boolean isArquivoCsv(MultipartFile multipartFile){

           return multipartFile.getContentType().equals("text/csv") || multipartFile.getOriginalFilename().endsWith(".csv");
    }

    public void processarCsv(MultipartFile file) throws IOError, IOException {

        if (!isArquivoCsv(file))throw new IllegalArgumentException("Arquivo deve ser do tipo CSV");

        List<RegistroAlunoDTO> listaRegistros = registroAlunoMapper.converterCsvParaDto(file);

        salvarCsv(listaRegistros);
    }

    private void salvarCsv(List<RegistroAlunoDTO> listaRegistros) throws IOException {

        File diretorio = new File("C:\\Projetos\\leitorcsv\\src\\main\\resources\\arquivorecebido");
        String nomeArquivo = "recebido";
        String caminhoCompleto = diretorio.getAbsolutePath() + "/" + nomeArquivo;

        try (CSVWriter writer = new CSVWriter(new FileWriter(caminhoCompleto))) {

            listaRegistros.stream()
                    .map(registro -> new String[]{
                            registro.getNome(), registro.getMatricula(), registro.getFone(),
                            registro.getSerie(), registro.getTurno(), registro.getTitulo(),
                            registro.getAutor(), registro.getEditora(), registro.getAnoPublicacao(),
                            registro.getNota(), registro.getDevolucao()
                    }).forEach(writer::writeNext);
        }

    }
}
