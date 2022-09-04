package io.github.mrvictor42.Escola.X.Backend

import io.github.mrvictor42.Escola.X.Backend.model.Student
import io.github.mrvictor42.Escola.X.Backend.services.StudentService
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
	fun run(userService: UserService, studentService: StudentService): CommandLineRunner? {
		return CommandLineRunner {
			if(userService.countUser() == 0.toLong()) {

				val student = Student()

				student.username = "student"
				student.password = "Bgatahkei42@"
				student.name = "Estudante Teste"
				student.email = "student@gmail.com"
				student.phone = "61994592869"
				student.photo = null
				student.birthDate = "09/07/1995"
				student.nameFather = "Francisco de Assis"
				student.nameMother = "Juscelina"
				student.responsible = "Os Pais"

				studentService.save(student)
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