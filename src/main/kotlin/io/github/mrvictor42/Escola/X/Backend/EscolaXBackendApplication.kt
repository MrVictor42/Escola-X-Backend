package io.github.mrvictor42.Escola.X.Backend

import io.github.mrvictor42.Escola.X.Backend.model.Admin
import io.github.mrvictor42.Escola.X.Backend.model.CharRoom
import io.github.mrvictor42.Escola.X.Backend.model.RankRoom
import io.github.mrvictor42.Escola.X.Backend.model.ShiftRoom
import io.github.mrvictor42.Escola.X.Backend.services.AdminService
import io.github.mrvictor42.Escola.X.Backend.services.RoomService
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
	fun run(adminService: AdminService, roomService : RoomService): CommandLineRunner? {
		return CommandLineRunner {
			if(adminService.countUser() == 0.toLong()) {
				populateDb(adminService, roomService)
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

	private fun populateDb(adminService: AdminService, roomService: RoomService) {
		val admin = Admin()
		val charList : MutableList<CharRoom> = mutableListOf()
		val rankList : MutableList<RankRoom> = mutableListOf()
		val shifts = arrayOf("Matutino", "Vespertino", "Noturno", "Integral")
		val shiftList : MutableList<ShiftRoom> = mutableListOf()

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

		for(aux in shifts.indices) {
			val shift = ShiftRoom()

			shift.name = shifts[aux]
			shiftList.add(shift)
		}

		for(aux in 65 until 91) {
			val char = CharRoom()

			char.letter = aux.toChar()
			charList.add(char)
		}

		adminService.save(admin)
		roomService.saveAll(rankList)
		roomService.saveAll(shiftList)
		roomService.saveAll(charList)
	}
}