package io.mrvictor42.escolax

import io.mrvictor42.escolax.model.Role
import io.mrvictor42.escolax.model.User
import io.mrvictor42.escolax.service.RoleService
import io.mrvictor42.escolax.service.UserService
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

@SpringBootApplication
class EscolaXApplication {

	@Bean
	fun passwordEncoder(): PasswordEncoder {
		return BCryptPasswordEncoder()
	}

	@Bean
	fun bCryptPasswordEncoder(): BCryptPasswordEncoder {
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
				userService.addRoleToUser("admin", "ROLE_ADMIN")
			} else {
				// Nothing to do
			}
		}
	}

	companion object {
		@JvmStatic
		fun main(args: Array<String>) {
			SpringApplication.run(EscolaXApplication::class.java, *args)
		}
	}
}