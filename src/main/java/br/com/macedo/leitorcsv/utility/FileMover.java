package br.com.macedo.leitorcsv.utility;

import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Component
public class FileMover {

    public void moverArquivo(File origem) throws IOException {
        String destino = "data/arquivosprocessados/" + origem.getName();
        Path arquivoOrigem = origem.toPath();
        Path arquivoDestino = Path.of(destino);
        Files.move(arquivoOrigem, arquivoDestino);
    }
}
