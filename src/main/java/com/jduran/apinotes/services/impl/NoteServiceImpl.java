package com.jduran.apinotes.services.impl;

import com.jduran.apinotes.dto.CreateNoteDto;
import com.jduran.apinotes.entities.Note;
import com.jduran.apinotes.repository.NoteRepository;
import com.jduran.apinotes.services.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class NoteServiceImpl implements NoteService {

    @Autowired
    private NoteRepository noteRepository;

    @Override
    public Page<Note> getNotes(Integer page, Integer size, Sort sort) {
        System.out.println("page: " + page + " size: " + size);
        Pageable pageable = PageRequest.of(page, size, sort);
        return noteRepository.findAll(pageable);
    }

    @Override
    public Note getNoteById(Long id) {
        return noteRepository.findById(id).orElse(null);
    }

    @Override
    public void save(CreateNoteDto note) {
        Note noteDto = new Note();
        noteDto.setTitle(note.getTitle());
        noteDto.setContent(note.getContent());
        noteDto.setAuthor(note.getAuthor());
        noteRepository.save(noteDto);
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
