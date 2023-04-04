package br.com.macedo.leitorcsv.mapper;

import br.com.macedo.leitorcsv.dto.RegistroAlunoDTO;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ConversorCsvToDtoMapper {

    public List<RegistroAlunoDTO> converterCsvParaDto(MultipartFile file) throws IOException {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        List<RegistroAlunoDTO> listaRegistros = new ArrayList<>();

        try (CSVReader reader = new CSVReader(new InputStreamReader(file.getInputStream()))){
            listaRegistros = reader.readAll().stream().skip(1).map(linha ->{
                RegistroAlunoDTO registroAluno = new RegistroAlunoDTO();
                registroAluno.setNome(linha[0]);
                registroAluno.setMatricula(linha[1]);
                registroAluno.setFone(linha[2]);
                registroAluno.setSerie(linha[3]);
                registroAluno.setTurno(linha[4]);
                registroAluno.setTitulo(linha[5]);
                registroAluno.setAutor(linha[6]);
                registroAluno.setEditora(linha[7]);
                registroAluno.setAnoPublicacao(Integer.valueOf(linha[8]));
                registroAluno.setNota(Double.valueOf(linha[9]));
                registroAluno.setDevolucao(LocalDateTime.parse(linha[10], formato));
                return registroAluno;
            }).collect(Collectors.toList());
        } catch (CsvException e) {
            throw new RuntimeException(e);
        }
        return listaRegistros;
    }
}
