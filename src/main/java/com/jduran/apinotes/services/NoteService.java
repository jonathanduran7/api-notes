package com.jduran.apinotes.services;

import com.jduran.apinotes.dto.CreateNoteDto;
import com.jduran.apinotes.entities.Note;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

public interface NoteService {
    Page<Note> getNotes(Integer page, Integer size, Sort sort);
    Note getNoteById(Long id);
    void save(CreateNoteDto note);
    void delete(Long id);
    Note update(Long id, Note note);
}
