package net.proselyte.springbootdemo;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import net.proselyte.springbootdemo.model.Note;
import net.proselyte.springbootdemo.repository.NoteRepository;
import net.proselyte.springbootdemo.service.NoteService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Date;

@RunWith(MockitoJUnitRunner.class)
public class NoteServiceTest {

    @InjectMocks
    NoteService noteService;

    @Mock
    NoteRepository noteRepository;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    public void getAllNotesTest() {
        List<Note> notes = new ArrayList<>();
        Note firstNote = new Note(3L, "hello", "hello world", new Date(2021, 07, 12), new Date(2021, 06, 11));
        Note secondNote = new Note(4L, "vahe", "hello vahe", new Date(2021, 07, 12), new Date(2021, 06, 11));

        notes.add(firstNote);
        notes.add(secondNote);

        when(noteRepository.findAll()).thenReturn(notes);
        List<Note> noteList = noteService.findAll();
        assertEquals(2, noteList.size());
        verify(noteRepository, times(1)).findAll();
    }

    @Test
    public void getNoteByIdTest(){
        when(noteRepository.getOne(3L)).thenReturn(new Note(3L, "hello", "hello world", new Date(2021, 07, 12), new Date(2021, 06, 11)));
        Note note = noteService.findById(3L);
        assertEquals("hello", note.getTitle());
        assertEquals("hello world", note.getNote());
        assertEquals(new Date(2021, 07, 12),note.getCreateTime());
        assertEquals(new Date(2021, 06, 11), note.getLastUpdateTime());
    }

    public void createNoteTest(){
        Note note = new Note(3L, "hello", "hello world", new Date(2021, 07, 12), new Date(2021, 06, 11));
        noteService.saveNote(note);
        verify(noteRepository, times(1)).save(note);
    }
}
