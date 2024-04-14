package com.jduran.apinotes.services;

import com.jduran.apinotes.entities.Note;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface NoteService {
    public Page<Note> getNotes(Integer page, Integer size, Sort sort);
    public Note getNoteById(Long id);
    public Note save(Note note);
    public void delete(Long id);
    public Note update(Long id, Note note);
}
