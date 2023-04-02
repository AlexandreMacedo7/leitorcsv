package br.com.macedo.leitorcsv.service;

import br.com.macedo.leitorcsv.entity.RegistroAluno;
import br.com.macedo.leitorcsv.repostitory.AlunoRepository;
import br.com.macedo.leitorcsv.repostitory.AvaliacaoRepository;
import br.com.macedo.leitorcsv.repostitory.LivroRepository;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class CsvService {

    @Autowired
    private AlunoRepository alunoRepository;
    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private AvaliacaoRepository avaliacaoRepository;

    public List<RegistroAluno> processarCsv(MultipartFile file) throws IOError, IOException {

        List<RegistroAluno> listaRegistros = new ArrayList<>();

        try (CSVReader reader = new CSVReader(new InputStreamReader(file.getInputStream()))) {
            String[] linha;
            while ((linha = reader.readNext()) != null) {
                RegistroAluno RegistroAluno = new RegistroAluno();
                RegistroAluno.setNome(linha[0]);
                RegistroAluno.setMatricula(linha[1]);
                RegistroAluno.setFone(linha[2]);
                RegistroAluno.setSerie(linha[3]);
                RegistroAluno.setTurno(linha[4]);
                RegistroAluno.setTitulo(linha[5]);
                RegistroAluno.setAutor(linha[6]);
                RegistroAluno.setEditora(linha[7]);
                RegistroAluno.setAnoPublicacao(linha[8]);
                RegistroAluno.setNota(linha[9]);
                RegistroAluno.setDevolucao(linha[10]);

                listaRegistros.add(RegistroAluno);
            }
        } catch (
                CsvValidationException e) {
            throw new RuntimeException(e);
        }

        salvarCsv(listaRegistros);

        return listaRegistros;
    }

    private void salvarCsv(List<RegistroAluno> listaRegistros) throws IOException {
        File diretorio = new File("C:\\Projetos\\leitorcsv\\src\\main\\resources\\arquivorecebido");

        String nomeArquivo = "recebido";

        String caminhoCompleto = diretorio.getAbsolutePath() + "/" + nomeArquivo;

        try (CSVWriter writer = new CSVWriter(new FileWriter(caminhoCompleto))) {

            String[] cabecalho = {"Nome", "Matricula", "Fone", "Serie","Turno", "Titulo", "Autor", "Editora", "AnoPublicacao", "Nota", "Devolucao"};
            writer.writeNext(cabecalho);

            for (RegistroAluno registro : listaRegistros) {
                String[] linha = {
                        registro.getNome(),
                        registro.getMatricula(),
                        registro.getFone(),
                        registro.getSerie(),
                        registro.getTurno(),
                        registro.getTitulo(),
                        registro.getAutor(),
                        registro.getEditora(),
                        registro.getAnoPublicacao(),
                        registro.getNota(),
                        registro.getDevolucao()
                };
                writer.writeNext(linha);
            }

        }
    }
}
