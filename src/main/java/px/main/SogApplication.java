package px.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import px.main.ocorrencia.modelos.Ocorrencia;
import px.main.ocorrencia.repository.OcorrenciaRepository;
import px.main.seguranca.modelos.Usuario;
import px.main.seguranca.repository.UsuarioRepository;

@SpringBootApplication
@EnableJpaRepositories(basePackageClasses={UsuarioRepository.class,OcorrenciaRepository.class})
@EntityScan(basePackageClasses={Usuario.class,Ocorrencia.class})
public class SogApplication {

	public static void main(String[] args) {
		SpringApplication.run(SogApplication.class, args);
	}
}
