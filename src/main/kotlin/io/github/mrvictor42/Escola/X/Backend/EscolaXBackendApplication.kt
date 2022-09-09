package io.github.mrvictor42.Escola.X.Backend

import io.github.mrvictor42.Escola.X.Backend.model.Admin
import io.github.mrvictor42.Escola.X.Backend.model.Student
import io.github.mrvictor42.Escola.X.Backend.model.Teacher
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
				populateDb(userService)
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

	private fun populateDb(userService: UserService) {
		val student = Student()
		val teacher = Teacher()
		val admin = Admin()

		admin.username = "admin"
		admin.password = "Bgatahkei42@"
		admin.name = "Admin"
		admin.email = "admin@gmail.com"
		admin.photo = null
		admin.phone = "617-555-0103"

		student.username = "student"
		student.password = "Bgatahkei42@"
		student.name = "Estudante Teste"
		student.email = "student@gmail.com"
		student.phone = "617-555-0133"
		student.photo = null
		student.birthDate = "09/07/1995"
		student.nameFather = "Francisco de Assis"
		student.nameMother = "Juscelina"
		student.responsible = "Os Pais"

		teacher.username = "teacher"
		teacher.password = "Bgatahkei42@"
		teacher.name = "Professor Teste"
		teacher.email = "teacher@gmail.com"
		teacher.phone = "617-555-0189"
		teacher.photo = null
		teacher.cpf = "703.438.003-14"
		teacher.registry = "424242"

		userService.save(admin)
		userService.save(student)
		userService.save(teacher)
	}
}