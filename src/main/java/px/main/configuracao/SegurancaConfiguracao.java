package px.main.configuracao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.transaction.annotation.Transactional;

@Configuration
@EnableWebSecurity
public class SegurancaConfiguracao extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	@Transactional
	public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(new Md5PasswordEncoder());

	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		
		.antMatchers("/usuario/alterarsenha").hasRole("VISITANTE")
		.antMatchers("/usuario/salvarsenha").hasRole("VISITANTE")
		.antMatchers("/usuario/**").hasRole("ADM")
		.antMatchers("/ocorrencia/**").hasRole("USER")  
		
		.antMatchers("/", "/home").permitAll()
		.antMatchers("/css/**", "/js/**", "/images/**").permitAll()
		.anyRequest().authenticated()
		
		.and().formLogin().loginPage("/login").defaultSuccessUrl("/ocorrencia/lista").permitAll()
		.and().exceptionHandling().accessDeniedPage("/403")
				
				.and().logout().permitAll()
				
				.and().csrf().disable();
	}
}
