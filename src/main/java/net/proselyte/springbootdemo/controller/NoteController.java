package net.proselyte.springbootdemo.controller;


import net.proselyte.springbootdemo.dto.NoteDto;
import net.proselyte.springbootdemo.model.Note;
import net.proselyte.springbootdemo.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/note")
public class NoteController {

    private final NoteService noteService;

    @Autowired
    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping("/notes")
    public ResponseEntity<List<NoteDto>> findAllNotes() {
        List<NoteDto> notes = noteService.findAll().stream().map(this::convertToNoteDto).collect(Collectors.toList());
        if (notes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(notes, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NoteDto> getNoteById(@PathVariable("id") long id) {
        Note note = noteService.findById(id);
        if (note != null) {
            return new ResponseEntity<>(convertToNoteDto(note), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/createNote")
    public ResponseEntity<NoteDto> createNote(@RequestBody NoteDto noteDto) {
        Note note = convertToNote(noteDto);
        Note createdNote = noteService.saveNote(note);
        if (createdNote != null) {
            return new ResponseEntity<>(convertToNoteDto(createdNote), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteNote(@PathVariable("id") Long id) {
        if (noteService.deleteById(id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<NoteDto> updateNote(@PathVariable("id") Long id, @RequestBody NoteDto noteForUpdate) {
        Note note = noteService.findById(id);
        if (note != null) {
            note.setTitle(noteForUpdate.getTitle());
            note.setNote(noteForUpdate.getNote());
            note.setCreateTime(noteForUpdate.getCreateTime());
            note.setLastUpdateTime(noteForUpdate.getLastUpdateTime());
            return new ResponseEntity<>(convertToNoteDto(noteService.saveNote(note)), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    private NoteDto convertToNoteDto(Note note){
        NoteDto noteDto = new NoteDto();
        noteDto.setId(note.getId());
        noteDto.setTitle(note.getTitle());
        noteDto.setNote(note.getNote());
        noteDto.setCreateTime(note.getCreateTime());
        noteDto.setLastUpdateTime(note.getLastUpdateTime());
        return noteDto;
    }

    private Note convertToNote(NoteDto user){
        Note note = new Note();
        note.setId(user.getId());
        note.setTitle(user.getTitle());
        note.setNote(user.getNote());
        note.setCreateTime(user.getCreateTime());
        note.setLastUpdateTime(user.getLastUpdateTime());
        return note;
    }
}
