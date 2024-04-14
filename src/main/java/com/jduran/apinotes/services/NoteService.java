package com.jduran.apinotes.services;

import com.jduran.apinotes.entities.Note;

import java.util.List;

public interface NoteService {
    public List<Note> getNotes();
    public Note getNoteById(Long id);
    public Note save(Note note);
    public void delete(Long id);
    public Note update(Long id, Note note);
}
