package net.proselyte.springbootdemo.service;

import net.proselyte.springbootdemo.model.Note;
import net.proselyte.springbootdemo.repository.NoteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Note service.
 */
@Service
public class NoteService {
    /** Note repository. */
    private final NoteRepository noteRepository;

    /**
     * Constructor.
     *
     * @param noteRepository repository.
     */
    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    /**
     * Find note with id.
     *
     * @param id note id.
     * @return note.
     */
    public Note findById(Long id) {
        return noteRepository.getOne(id);
    }

    /**
     * Show all notes in database.
     *
     * @return list of all notes.
     */
    public List<Note> findAll() {
        return noteRepository.findAll();
    }

    /**
     * Save note.
     *
     * @param note note for save.
     * @return saved note.
     */
    public Note saveNote(Note note) {
        return noteRepository.save(note);
    }

    /**
     *
     * @param id note id.
     * @return true if note is save otherwise false.
     */
    public boolean deleteById(Long id) {
        if (id != null) {
            noteRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
