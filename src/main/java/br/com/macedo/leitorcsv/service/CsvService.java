package br.com.macedo.leitorcsv.service;

import br.com.macedo.leitorcsv.dto.RegistroAlunoDTO;
import br.com.macedo.leitorcsv.mapper.RegistroAlunoMapper;
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
    private RegistroAlunoMapper registroAlunoMapper;

    @Autowired
    private FileValidacao fileValidacao;


    @Validated
    public void processarCsv(MultipartFile file) throws IOError, IOException {

        fileValidacao.isArquivoCsv(file);

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
                            registro.getAutor(), registro.getEditora(), String.valueOf(registro.getAnoPublicacao()),
                            String.valueOf(registro.getNota()), String.valueOf(registro.getDevolucao())
                    }).forEach(writer::writeNext);
        }

    }
}
