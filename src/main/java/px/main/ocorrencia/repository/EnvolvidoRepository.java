package px.main.ocorrencia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import px.main.ocorrencia.modelos.Envolvido;

@Repository
public interface EnvolvidoRepository extends JpaRepository<Envolvido, Integer>, JpaSpecificationExecutor<Envolvido> {
}
