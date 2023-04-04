package br.com.macedo.leitorcsv.dto;


import br.com.macedo.leitorcsv.entity.RegistroAluno;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegistroAlunoDTO {

    private String nome;
    private String matricula;
    private String fone;
    private String serie;
    private String turno;
    private String titulo;
    private String autor;
    private String editora;
    private Integer anoPublicacao;
    private Double nota;
    private LocalDateTime devolucao;

    public RegistroAluno transformarObjeto(){
        return new RegistroAluno(nome, matricula, fone, serie, turno, titulo, autor, editora, anoPublicacao, nota, devolucao);
    }

}
