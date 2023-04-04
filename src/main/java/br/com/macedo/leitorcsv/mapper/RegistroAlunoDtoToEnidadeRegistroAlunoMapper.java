package br.com.macedo.leitorcsv.mapper;

import br.com.macedo.leitorcsv.dto.RegistroAlunoDTO;
import br.com.macedo.leitorcsv.entity.RegistroAluno;

import java.util.Optional;

public class RegistroAlunoDtoToEnidadeRegistroAlunoMapper {

    public RegistroAluno toEntidade(RegistroAlunoDTO dto){

        return Optional.ofNullable(dto)
                .map(d -> {
                    RegistroAluno entidade = new RegistroAluno();
                    entidade.setNome(d.getNome());
                    entidade.setMatricula(d.getMatricula());
                    entidade.setFone(d.getFone());
                    entidade.setSerie(d.getSerie());
                    entidade.setTurno(d.getTurno());
                    entidade.setTitulo(d.getTitulo());
                    entidade.setAutor(d.getAutor());
                    entidade.setEditora(d.getEditora());
                    entidade.setNota(d.getNota());
                    entidade.setDevolucao(d.getDevolucao());
                    return entidade;
                })
                .orElse(null);
    }
}
