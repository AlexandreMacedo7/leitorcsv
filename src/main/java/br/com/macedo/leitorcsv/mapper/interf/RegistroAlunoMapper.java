package br.com.macedo.leitorcsv.mapper.interf;

import br.com.macedo.leitorcsv.dto.RegistroAlunoDTO;
import br.com.macedo.leitorcsv.entity.RegistroAluno;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RegistroAlunoMapper {

    RegistroAluno toRegistroAluno(RegistroAlunoDTO registroAlunoDTO);
}
