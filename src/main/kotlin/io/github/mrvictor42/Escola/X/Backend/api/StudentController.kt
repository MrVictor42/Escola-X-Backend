package io.github.mrvictor42.Escola.X.Backend.api

import io.github.mrvictor42.Escola.X.Backend.model.Student
import io.github.mrvictor42.Escola.X.Backend.services.StudentService
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
@RequestMapping("/student")
@RequiredArgsConstructor
class StudentController(private val studentService : StudentService) {

    @PostMapping("/save")
    fun save(@Valid @RequestBody student : Student) : ResponseEntity<Student> {
        return try {
            val uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/student/save").toUriString())
            ResponseEntity.created(uri).body(studentService.save(student))
        } catch (runtime : RuntimeException) {
            ResponseEntity.badRequest().body(null)
        }
    }
}