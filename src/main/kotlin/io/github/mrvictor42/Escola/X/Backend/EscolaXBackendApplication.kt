package io.github.mrvictor42.Escola.X.Backend

import io.github.mrvictor42.Escola.X.Backend.model.Role
import io.github.mrvictor42.Escola.X.Backend.model.User
import io.github.mrvictor42.Escola.X.Backend.services.RoleService
import io.github.mrvictor42.Escola.X.Backend.services.UserService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@SpringBootApplication
class EscolaXBackendApplication {

	val log: Logger = LoggerFactory.getLogger(this.javaClass)

	@Value("\${security.jwt.signing-key}")
	val secretKey : String? = null

	@Bean
	fun passwordEncoder() : BCryptPasswordEncoder {
		return BCryptPasswordEncoder()
	}

	@Bean
	fun run(userService: UserService, roleService: RoleService): CommandLineRunner? {
		return CommandLineRunner {
			if(userService.countUser() == 0.toLong()) {
				roleService.save(Role(null, "ROLE_USER"))
				roleService.save(Role(null, "ROLE_MANAGER"))
				roleService.save(Role(null, "ROLE_ADMIN"))

				userService.save(
					User(null, "admin", "Bgatahkei42@", "admin", "victor042@gmail.com", "02951294174", mutableListOf())
				)
				userService.save(
					User(null, "john", "12345678", "John Travolta", "john@gmail.com", "02951294174", mutableListOf())
				)
				userService.save(
					User(null, "will", "12345678", "Will Smith", "will@gmail.com", "02951294174", mutableListOf())
				)
				userService.save(
					User(null, "jim", "12345678", "Jim Carry", "jim@gmail.com", "02951294174", mutableListOf())
				)

				userService.addRoleToUser("admin", "ROLE_ADMIN")
				userService.addRoleToUser("will", "ROLE_MANAGER")
				userService.addRoleToUser("will", "ROLE_USER")
				userService.addRoleToUser("jim", "ROLE_USER")
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
