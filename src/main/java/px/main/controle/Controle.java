package px.main.controle;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Controle {

	@RequestMapping("/home")
	public String hello() {
		return "index";
	}
	@RequestMapping("/olamundo")
	public String olamundo(){
		return "index";
	}
	@RequestMapping("")
	public String index(){
		return "index";
	}
	@RequestMapping("/login")
	public String goLogin() {
		return "login";
	}
	@RequestMapping("/logout")
	public String logout() {
		return "login";
	}
}
