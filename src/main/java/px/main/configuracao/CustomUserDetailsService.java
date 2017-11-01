package px.main.configuracao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import px.main.seguranca.modelos.Usuario;
import px.main.seguranca.repository.UsuarioRepository;

@Service("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService{
	private final UsuarioRepository usuarioRepository;
	
	@Autowired
	public CustomUserDetailsService(UsuarioRepository usuarioRepository){
		this.usuarioRepository=usuarioRepository;
		}
	
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
		Usuario user= usuarioRepository.findByLogin(username);
		if(null == user){
			System.out.println("loadUserByName informa: "+username+" não localizado.");
			throw new UsernameNotFoundException("Não foi localizado este usuario: "+username);
			}else{
				return new CustomUserDetails(user,user.getRegrasList());
			}
		}}