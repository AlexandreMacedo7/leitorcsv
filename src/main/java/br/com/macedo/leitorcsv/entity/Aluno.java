package br.com.macedo.leitorcsv.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Aluno {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String nome;
    private String matricula;
    private String fone;
    private String serie;
    private String turno;

    @OneToOne
    private Avaliacao avaliacao;
    @OneToOne
    private Livro livro;
}
