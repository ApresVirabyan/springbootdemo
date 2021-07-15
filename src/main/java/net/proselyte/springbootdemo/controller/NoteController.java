package net.proselyte.springbootdemo.controller;


import net.proselyte.springbootdemo.model.Note;
import net.proselyte.springbootdemo.model.User;
import net.proselyte.springbootdemo.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/note")
public class NoteController {

    private final NoteService noteService;

    @Autowired
    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping("/notes")
    public ResponseEntity<List<Note>> findAllNotes() {
        List<Note> notes = noteService.findAll();
        if (notes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(notes, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Note> getNoteById(@PathVariable("id") long id) {
        Note note = noteService.findById(id);
        if (note != null) {
            return new ResponseEntity<>(note, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<Note> createUser(@RequestBody Note note) {
        Note createdNote = noteService.saveNote(note);
        if (createdNote != null) {
            return new ResponseEntity<>(createdNote, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") Long id) {
        noteService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Note> updateNoteForm(@PathVariable("id") Long id, @RequestBody Note noteForUpdate) {
        Note note = noteService.findById(id);
        if (note != null) {
            note.setTitle(noteForUpdate.getTitle());
            note.setNote(noteForUpdate.getNote());
            note.setCreateTime(noteForUpdate.getCreateTime());
            note.setLastUpdateTime(noteForUpdate.getLastUpdateTime());
            return new ResponseEntity<>(noteService.saveNote(note), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
