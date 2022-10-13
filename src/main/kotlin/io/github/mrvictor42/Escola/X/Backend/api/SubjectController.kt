package io.github.mrvictor42.Escola.X.Backend.api

import io.github.mrvictor42.Escola.X.Backend.model.Subject
import io.github.mrvictor42.Escola.X.Backend.services.SubjectService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.net.URI
import javax.validation.Valid

@RestController
@RequestMapping("/subject")
class SubjectController(private val subjectService: SubjectService) {

    @PostMapping("/save")
    fun save(@Valid @RequestBody subject: Subject) : ResponseEntity<Subject> {
        return try {
            val uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/subject/save").toUriString())
            ResponseEntity.created(uri).body(subjectService.save(subject))
        } catch (runtime : RuntimeException) {
            ResponseEntity.badRequest().body(null)
        }
    }
}