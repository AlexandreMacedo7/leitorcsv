package br.com.macedo.leitorcsv.repostitory;

import br.com.macedo.leitorcsv.entity.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Long> {
}
