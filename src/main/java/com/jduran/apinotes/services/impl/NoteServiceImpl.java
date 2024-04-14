package com.jduran.apinotes.services.impl;

import com.jduran.apinotes.entities.Note;
import com.jduran.apinotes.repository.NoteRepository;
import com.jduran.apinotes.services.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteServiceImpl implements NoteService {

    @Autowired
    private NoteRepository noteRepository;

    @Override
    public List<Note> getNotes() {
        return noteRepository.findAll();
    }

    @Override
    public Note getNoteById(Long id) {
        return noteRepository.findById(id).orElse(null);
    }

    @Override
    public Note save(Note note) {
        return noteRepository.save(note);
    }

    @Override
    public void delete(Long id) {
        noteRepository.deleteById(id);
    }

    @Override
    public Note update(Long id, Note note) {
        Note noteToUpdate = noteRepository.findById(id).orElse(null);
        if (noteToUpdate != null) {
            if (note.getTitle() != null) noteToUpdate.setTitle(note.getTitle());

            if (note.getContent() != null)  noteToUpdate.setContent(note.getContent());

            if (note.getAuthor() != null) noteToUpdate.setAuthor(note.getAuthor());

            return noteRepository.save(noteToUpdate);
        }
        return null;
    }



}
