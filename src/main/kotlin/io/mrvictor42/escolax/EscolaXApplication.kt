package io.mrvictor42.escolax

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

@SpringBootApplication
class EscolaXApplication {

	@Bean
	fun passwordEncoder(): PasswordEncoder? {
		return BCryptPasswordEncoder()
	}

	companion object {
		@JvmStatic
		fun main(args: Array<String>) {
			SpringApplication.run(EscolaXApplication::class.java, *args)
		}
	}
}