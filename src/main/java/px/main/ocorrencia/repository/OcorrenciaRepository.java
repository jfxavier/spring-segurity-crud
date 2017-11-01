package px.main.ocorrencia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import px.main.ocorrencia.modelos.Ocorrencia;

@Repository
public interface OcorrenciaRepository extends JpaRepository<Ocorrencia, Integer>, JpaSpecificationExecutor<Ocorrencia> {
	@Query(value = "SELECT max(numero) FROM ocorrencia WHERE year(data_registro)=?1", nativeQuery=true)
	int MaiorNumero(int ano);
	
	@Query("select max(o) from Ocorrencia o WHERE year(o.dataRegistro)=?1")
	Ocorrencia ExistOcorrencia(int ano);
	
	Ocorrencia findByNumero(int numero);
}
