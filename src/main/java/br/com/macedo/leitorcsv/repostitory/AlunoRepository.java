package br.com.macedo.leitorcsv.repostitory;


import br.com.macedo.leitorcsv.model.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AlunoRepository extends JpaRepository<Aluno, Long> {
}
