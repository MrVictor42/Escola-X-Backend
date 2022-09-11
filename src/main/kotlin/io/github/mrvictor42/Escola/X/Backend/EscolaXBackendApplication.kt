package io.github.mrvictor42.Escola.X.Backend

import io.github.mrvictor42.Escola.X.Backend.model.Admin
import io.github.mrvictor42.Escola.X.Backend.model.RankRoom
import io.github.mrvictor42.Escola.X.Backend.services.RankService
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

	@Value("\${security.jwt.signing-key}")
	val secretKey : String? = null
	val log: Logger = LoggerFactory.getLogger(this.javaClass)

	@Bean
	fun passwordEncoder() : BCryptPasswordEncoder {
		return BCryptPasswordEncoder()
	}

	@Bean
	fun run(userService: UserService, rankService: RankService): CommandLineRunner? {
		return CommandLineRunner {
			if(userService.countUser() == 0.toLong()) {
				populateDb(userService, rankService)
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

	private fun populateDb(userService: UserService, rankService: RankService) {
		val admin = Admin()
		val rankList : MutableList<RankRoom> = mutableListOf()

		admin.username = "admin"
		admin.password = "Bgatahkei42@"
		admin.name = "Admin"
		admin.email = "admin@gmail.com"
		admin.photo = null
		admin.phone = "617-555-0103"

		for(aux in 1..9) {
			val rank = RankRoom()

			rank.name = "$aux" + "º ano"
			rankList.add(rank)
		}

		userService.save(admin)
		rankService.saveAll(rankList)
	}
}