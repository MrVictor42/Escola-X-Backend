package io.github.mrvictor42.Escola.X.Backend.api

import io.github.mrvictor42.Escola.X.Backend.model.Student
import io.github.mrvictor42.Escola.X.Backend.model.Teacher
import io.github.mrvictor42.Escola.X.Backend.services.TeacherService
import lombok.RequiredArgsConstructor
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.net.URI
import javax.validation.Valid

@RestController
@RequestMapping("/teacher")
@RequiredArgsConstructor
class TeacherController(private val teacherService: TeacherService) {

    @PostMapping("/save")
    fun save(@Valid @RequestBody teacher: Teacher) : ResponseEntity<Teacher> {
        return try {
            val uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/teacher/save").toUriString())
            ResponseEntity.created(uri).body(teacherService.save(teacher))
        } catch (runtime : RuntimeException) {
            ResponseEntity.badRequest().body(null)
        }
    }
}