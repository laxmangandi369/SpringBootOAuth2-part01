package willydekeyser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootOauth2ClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootOauth2ClientApplication.class, args);
	}

}




	/*
	@GetMapping
	public String welcome() {
		return "Welcome to Google !!";
	}

	@GetMapping("/user")
	public Principal user(Principal principal) {
		System.out.println("username : " + principal.getName());
		return principal;
	}
	*/