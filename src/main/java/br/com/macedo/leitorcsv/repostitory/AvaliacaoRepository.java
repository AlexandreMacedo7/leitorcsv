package br.com.macedo.leitorcsv.repostitory;

import br.com.macedo.leitorcsv.entity.Avaliacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Long> {
}
