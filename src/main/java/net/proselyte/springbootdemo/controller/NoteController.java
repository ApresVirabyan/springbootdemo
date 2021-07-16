package net.proselyte.springbootdemo.controller;


import net.proselyte.springbootdemo.dto.NoteDto;
import net.proselyte.springbootdemo.model.Note;
import net.proselyte.springbootdemo.service.NoteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller for operations with note.
 */
@RestController
@RequestMapping("/note")
public class NoteController {

    /** Note service. */
    private final NoteService noteService;

    /**
     * Constructor.
     *
     * @param noteService note service.
     */
    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    /**
     * Send all notes.
     *
     * @return response with list of all notes.
     */
    @GetMapping("/notes")
    public ResponseEntity<List<NoteDto>> findAllNotes() {
        List<NoteDto> notes = noteService.findAll().stream().map(this::convertToNoteDto).collect(Collectors.toList());
        if (notes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(notes, HttpStatus.OK);
    }

    /**
     * Send note response.
     *
     * @param id notes id.
     * @return note with passed id.
     */
    @GetMapping("/{id}")
    public ResponseEntity<NoteDto> getNoteById(@PathVariable("id") long id) {
        Note note = noteService.findById(id);
        if (note != null) {
            return new ResponseEntity<>(convertToNoteDto(note), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Create note.
     *
     * @param noteDto note DTO.
     * @return note.
     */
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

    /**
     * Delete note with passed id.
     *
     * @param id note id.
     * @return true if note is deleted otherwise false.
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteNote(@PathVariable("id") Long id) {
        if (noteService.deleteById(id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    /**
     * Update note.
     *
     * @param id note id.
     * @param noteForUpdate note with info for update.
     * @return updated note.
     */
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

    /**
     * Convert note to noteDTO.
     *
     * @param note note.
     * @return note DTO.
     */
    private NoteDto convertToNoteDto(Note note){
        NoteDto noteDto = new NoteDto();
        noteDto.setId(note.getId());
        noteDto.setTitle(note.getTitle());
        noteDto.setNote(note.getNote());
        noteDto.setCreateTime(note.getCreateTime());
        noteDto.setLastUpdateTime(note.getLastUpdateTime());
        return noteDto;
    }

    /**
     * Convert noteDto to note.
     *
     * @param noteDto note DTO.
     * @return note.
     */
    private Note convertToNote(NoteDto noteDto){
        Note note = new Note();
        note.setId(noteDto.getId());
        note.setTitle(noteDto.getTitle());
        note.setNote(noteDto.getNote());
        note.setCreateTime(noteDto.getCreateTime());
        note.setLastUpdateTime(noteDto.getLastUpdateTime());
        return note;
    }
}
