package px.main.seguranca.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import px.main.seguranca.modelos.UsuarioRegra;
@Repository
public interface UsuarioRegraRepository extends JpaRepository<UsuarioRegra, Integer> {
	@Query("select regra FROM UsuarioRegra WHERE usuario.id=?")
	public List<String> findRegraByUserId(Integer id);
	@Query("select regra FROM UsuarioRegra Group By regra")
	public List<String> allRegras();
}
