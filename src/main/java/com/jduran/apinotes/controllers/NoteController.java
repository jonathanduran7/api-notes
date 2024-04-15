package com.jduran.apinotes.controllers;

import com.jduran.apinotes.dto.CreateNoteDto;
import com.jduran.apinotes.dto.UpdateNoteDto;
import com.jduran.apinotes.entities.Note;
import com.jduran.apinotes.services.NoteService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/notes")
@Validated
public class NoteController {

    @Autowired
    private NoteService noteService;

    @GetMapping
    public Page<Note> getNotes(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(defaultValue = "id") String sortField,
            @RequestParam(defaultValue = "asc") String sortOrder
    ) {
        Sort sort = sortOrder.equalsIgnoreCase("asc") ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        return noteService.getNotes(page,size, sort);
    }



    @GetMapping("/{id}")
    @ApiResponse(
            responseCode = "200",
            description = "OK",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Note.class)
            ))
    @ApiResponse(
            responseCode = "404",
            description = "Not Found",
            content = @Content(
                    mediaType = "text/plain",
                    schema = @Schema(implementation = String.class)
            ))
    public ResponseEntity<?> getNoteById(@PathVariable Long id) {
        Note note = noteService.getNoteById(id);

        if (note == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Nota no encontrada");
        }

        return ResponseEntity.ok(note);
    }

    @ApiResponse(
            responseCode = "200",
            description = "OK",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = String.class)
            ))
    @PostMapping
    public ResponseEntity<?> save(@RequestBody @Valid CreateNoteDto noteDto) {
        try {
            Note note = new Note();
            note.setTitle(noteDto.getTitle());
            note.setContent(noteDto.getContent());
            note.setAuthor(noteDto.getAuthor());

            noteService.save(note);

            return ResponseEntity
                    .ok()
                    .body("Nota guardada correctamente");
        } catch (DataAccessException e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al guardar la nota");
        }
    }

    @ApiResponse(
            responseCode = "200",
            description = "OK",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = String.class)
            ))
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody @Valid UpdateNoteDto noteDto) {
        Note note = new Note();

        note.setTitle(noteDto.getTitle());
        note.setContent(noteDto.getContent());
        note.setAuthor(noteDto.getAuthor());

        Note updatedNote = noteService.update(id, note);

        if (updatedNote == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Nota no encontrada");
        }

        return ResponseEntity.ok(updatedNote);
    }

    @ApiResponse(
            responseCode = "200",
            description = "OK",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = String.class)
            ))
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        noteService.delete(id);
        return ResponseEntity.ok("Nota eliminada correctamente");
    }

}
