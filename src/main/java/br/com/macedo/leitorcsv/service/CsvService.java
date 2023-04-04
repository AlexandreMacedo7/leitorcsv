package br.com.macedo.leitorcsv.service;

import br.com.macedo.leitorcsv.dto.RegistroAlunoDTO;
import br.com.macedo.leitorcsv.entity.RegistroAluno;
import br.com.macedo.leitorcsv.repostitory.AlunoRepository;
import br.com.macedo.leitorcsv.repostitory.AvaliacaoRepository;
import br.com.macedo.leitorcsv.repostitory.LivroRepository;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvValidationException;
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

    public void processarCsv(MultipartFile file) throws IOError, IOException {

        List<RegistroAlunoDTO> listaRegistros = new ArrayList<>();

        try (CSVReader reader = new CSVReader(new InputStreamReader(file.getInputStream()))) {
            listaRegistros = reader.readAll().stream().skip(1).map(linha -> {
                RegistroAlunoDTO registroAluno = new RegistroAlunoDTO();
                registroAluno.setNome(linha[0]);
                registroAluno.setMatricula(linha[1]);
                registroAluno.setFone(linha[2]);
                registroAluno.setSerie(linha[3]);
                registroAluno.setTurno(linha[4]);
                registroAluno.setTitulo(linha[5]);
                registroAluno.setAutor(linha[6]);
                registroAluno.setEditora(linha[7]);
                registroAluno.setAnoPublicacao(linha[8]);
                registroAluno.setNota(linha[9]);
                registroAluno.setDevolucao(linha[10]);
                return registroAluno;
            }).collect(Collectors.toList());
        } catch (CsvException e) {
            throw new RuntimeException(e);
        }

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
