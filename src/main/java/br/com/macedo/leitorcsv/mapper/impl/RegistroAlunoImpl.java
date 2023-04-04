package br.com.macedo.leitorcsv.mapper.impl;

import br.com.macedo.leitorcsv.dto.RegistroAlunoDTO;
import br.com.macedo.leitorcsv.entity.RegistroAluno;
import br.com.macedo.leitorcsv.mapper.interf.RegistroAlunoMapper;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class RegistroAlunoImpl implements RegistroAlunoMapper {

    @Override
    public RegistroAluno toRegistroAluno(RegistroAlunoDTO dto){

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
