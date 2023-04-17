package br.com.macedo.leitorcsv.service;

import br.com.macedo.leitorcsv.entity.RegistroAluno;
import br.com.macedo.leitorcsv.mapper.ConversorCsvToEntityMapper;
import br.com.macedo.leitorcsv.repostitory.AlunoRepository;
import br.com.macedo.leitorcsv.repostitory.AvaliacaoRepository;
import br.com.macedo.leitorcsv.repostitory.LivroRepository;
import br.com.macedo.leitorcsv.utility.FileValidacao;
import com.opencsv.CSVWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileWriter;
import java.io.IOError;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class CsvService {

    @Autowired
    private AlunoRepository alunoRepository;
    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private AvaliacaoRepository avaliacaoRepository;
    @Autowired
    private ConversorCsvToEntityMapper conversorCsvToEntityMapper;

    @Autowired
    private FileValidacao fileValidacao;

    @Validated
    public void processarCsv(MultipartFile file) throws IOError, IOException {

        fileValidacao.isArquivoCsv(file);

        List<RegistroAluno> listaRegistros = conversorCsvToEntityMapper.converterCsvParaEntity(file);

        salvarCsv(listaRegistros);
    }

    private void salvarCsv(List<RegistroAluno> listaRegistros) throws IOException {

        LocalDateTime agora = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("_yyyyMMdd_HHmmss");
        String dataHora = agora.format(formatter);

        File diretorio = new File("data/arquivorecebido");

        String nomeArquivo = "Arquivo_recebido"+dataHora+".csv";
        String caminhoCompleto = diretorio.getAbsolutePath() + File.separator + nomeArquivo;

        try (CSVWriter writer = new CSVWriter(new FileWriter(caminhoCompleto))) {

            listaRegistros.stream()
                    .map(registro -> new String[]{
                            registro.getNome(), registro.getMatricula(), registro.getFone(),
                            registro.getSerie(), registro.getTurno(), registro.getTitulo(),
                            registro.getAutor(), registro.getEditora(), String.valueOf(registro.getAnoPublicacao()),
                            String.valueOf(registro.getNota()), String.valueOf(registro.getDevolucao())
                    }).forEach(writer::writeNext);
        }
    }
}
