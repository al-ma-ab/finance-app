package io.com.github.al_ma_ab.finance_app;

import io.com.github.al_ma_ab.finance_app.model.User;
import io.com.github.al_ma_ab.finance_app.repository.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FinanceAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(FinanceAppApplication.class, args);
		//var context = SpringApplication.run(FinanceAppApplication.class, args);
		//UserRepository repository = context.getBean(UserRepository.class);

		//saveRegistrationUser(repository);
	}
/*
	public static void saveRegistrationUser(UserRepository userRepository){
		User user = new User();
		user.setName("Alexandre martins de abreu");
		user.setEmail("alexandremartins.net@gmail.com");
		user.setPassword("Jcgm07!!*#");

		var userSave = userRepository.save(user);

		System.out.println("Usuario salvo: " + userSave);

	}
*/
}
