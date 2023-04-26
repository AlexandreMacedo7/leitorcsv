package br.com.macedo.leitorcsv.service;

import br.com.macedo.leitorcsv.entity.Aluno;
import br.com.macedo.leitorcsv.entity.Avaliacao;
import br.com.macedo.leitorcsv.entity.Livro;
import br.com.macedo.leitorcsv.entity.RegistroAluno;
import br.com.macedo.leitorcsv.mapper.ConversorCsvToEntityMapper;
import br.com.macedo.leitorcsv.repostitory.AlunoRepository;
import br.com.macedo.leitorcsv.repostitory.AvaliacaoRepository;
import br.com.macedo.leitorcsv.repostitory.LivroRepository;
import br.com.macedo.leitorcsv.utility.FileMonitoramento;
import br.com.macedo.leitorcsv.utility.FileValidacao;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    private ConversorCsvToEntityMapper conversorCsvToEntityMapper;

    @Autowired
    private FileValidacao fileValidacao;

    private final static Logger LOGGER = LoggerFactory.getLogger(FileMonitoramento.class);

    @Validated
    public void processarCsv(MultipartFile file) throws IOError, IOException {

        fileValidacao.isArquivoCsv(file);

        List<RegistroAluno> registroAlunoList = conversorCsvToEntityMapper.converterCsvParaEntity(file);

        salvarCsvEmPasta(registroAlunoList);

        LOGGER.info("Arquivo Processado!");
    }

    private void salvarCsvEmPasta(List<RegistroAluno> listaDeRegistros) throws IOException {

        LocalDateTime agora = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("_yyyyMMdd_HHmmss");
        String dataHora = agora.format(formatter);

        File diretorio = new File("data/arquivosrecebidos");

        String nomeArquivo = "Arquivo_"+dataHora+".csv";
        String caminhoCompleto = diretorio.getAbsolutePath() + File.separator + nomeArquivo;

        try (CSVWriter writer = new CSVWriter(new FileWriter(caminhoCompleto))) {

            listaDeRegistros.stream()
                    .map(registro -> new String[]{
                            registro.getNome(), registro.getMatricula(), registro.getFone(),
                            registro.getSerie(), registro.getTurno(), registro.getTitulo(),
                            registro.getAutor(), registro.getEditora(), String.valueOf(registro.getAnoPublicacao()),
                            String.valueOf(registro.getNota()), String.valueOf(registro.getDevolucao())
                    }).forEach(writer::writeNext);

            LOGGER.info("Arquivo Salvo na Pasta de arquivos Recebidos!");
        }
    }

    public List<Aluno> lerCsv(String caminhoCompleto) throws IOException {

        LOGGER.info("Metodo ler arquivo chamado!");

        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

        List<Aluno> listaRegistros = new ArrayList<>();

        try (CSVReader reader = new CSVReader(new FileReader(caminhoCompleto))) {
            listaRegistros = reader.readAll().stream().map(linha -> {
                Aluno aluno = new Aluno();
                aluno.setNome(linha[0]);
                aluno.setMatricula(linha[1]);
                aluno.setFone(linha[2]);
                aluno.setSerie(linha[3]);
                aluno.setTurno(linha[4]);

                Livro livro = new Livro();
                livro.setTitulo(linha[5]);
                livro.setAutor(linha[6]);
                livro.setEditora(linha[7]);
                livro.setAnoPublicacao(Integer.valueOf(linha[8]));

                Avaliacao avaliacao = new Avaliacao();
                avaliacao.setNota(Double.valueOf(linha[9]));
                avaliacao.setDataAvaliacao(LocalDateTime.parse(linha[10], formato));

                aluno.setLivro(livro);
                aluno.setAvaliacao(avaliacao);

                // adiciona as entidades criadas na lista de registros
                List<Aluno> registros = new ArrayList<>();
                registros.add(aluno);

                return registros;
            }).flatMap(List::stream).collect(Collectors.toList());
        } catch (CsvException e) {
            throw new RuntimeException(e);
        }
        LOGGER.info("Leitura de arquivo csv finalizada!");
        return listaRegistros;
    }

    public void persistirEntidades(List<Aluno> listaDeRegistros) {

        LOGGER.info("Metodo de persistencia de entidades chamado!");

        for (Aluno registro : listaDeRegistros) {
            livroRepository.save(registro.getLivro());
            avaliacaoRepository.save(registro.getAvaliacao());
            alunoRepository.save(registro);
        }

        LOGGER.info("Entidades Persitidas!");
    }
}
