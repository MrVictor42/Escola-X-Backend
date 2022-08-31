package io.github.mrvictor42.Escola.X.Backend

import io.github.mrvictor42.Escola.X.Backend.model.User
import io.github.mrvictor42.Escola.X.Backend.services.UserService
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@SpringBootApplication
class EscolaXBackendApplication {

	@Value("\${security.jwt.signing-key}")
	val secretKey : String? = null

	@Bean
	fun passwordEncoder() : BCryptPasswordEncoder {
		return BCryptPasswordEncoder()
	}

	@Bean
	fun run(userService: UserService): CommandLineRunner? {
		return CommandLineRunner {
			if(userService.countUser() == 0.toLong()) {

//				userService.save(
//					User(null, "admin", "Bgatahkei42@", "admin", "victor042@gmail.com", null, null), null
//				)
			} else {
				// Nothing to do
			}
		}
	}

	companion object {
		@JvmStatic
		fun main(args: Array<String>) {
			SpringApplication.run(EscolaXBackendApplication::class.java, *args)
		}
	}
}