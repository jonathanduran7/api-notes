package com.jduran.apinotes.controllers;

import com.jduran.apinotes.dto.CreateNoteDto;
import com.jduran.apinotes.entities.Note;
import com.jduran.apinotes.services.NoteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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
    public List<Note> getNotes() {
        return noteService.getNotes();
    }

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
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al guardar la nota");
        }
    }

}
