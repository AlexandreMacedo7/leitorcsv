package br.com.macedo.leitorcsv.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegistroAluno {

    private String nome;
    private String matricula;
    private String fone;
    private String serie;
    private String turno;
    private String titulo;
    private String autor;
    private String editora;
    private String anoPublicacao;
    private String nota;
    private String devolucao;
}
