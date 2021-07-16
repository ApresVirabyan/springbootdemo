package net.proselyte.springbootdemo;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.proselyte.springbootdemo.controller.NoteController;
import net.proselyte.springbootdemo.model.Note;
import net.proselyte.springbootdemo.model.User;
import net.proselyte.springbootdemo.service.NoteService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(NoteController.class)
public class NoteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NoteService noteService;

    @Test
    public void getAllNotesTest() throws Exception {
        Note firstNote = new Note(1L, "hello", "hello world", new Date(2020, 11, 13), new Date(2019, 06, 14));
        Note secondNote = new Note(1L, "life", "beautiful life", new Date(2020, 11, 13), new Date(2019, 06, 14));
        List<Note> notes = new ArrayList<>();
        notes.add(firstNote);
        notes.add(secondNote);
        when(noteService.findAll()).thenReturn(notes);
        mockMvc.perform(get("/note/notes")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[*]").exists());
    }

    @Test
    public void getNoteByIdTest() throws Exception {
        Note firstNote = new Note(1L, "hello", "hello world", new Date(2020, 11, 13), new Date(2019, 06, 14));
        when(noteService.findById(1L)).thenReturn(firstNote);
        mockMvc.perform(get("/note/{id}", 1)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    public void createNote() throws Exception {
        Note firstNote = new Note(1L, "hello", "hello world", new Date(2020, 11, 13), new Date(2019, 06, 14));
        when(noteService.saveNote(firstNote)).thenReturn(firstNote);
        mockMvc.perform(MockMvcRequestBuilders
                .post("/note/createNote")
                .content(asJsonString(new Note(1L, "hello", "hello world", new Date(2020, 11, 13), new Date(2019, 06, 14))))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void updateNote() throws Exception {
        Note firstNote = new Note(1L, "hello", "hello world", new Date(2020, 11, 13), new Date(2019, 06, 14));
        when(noteService.findById(1L)).thenReturn(firstNote);
        when(noteService.saveNote(firstNote)).thenReturn(firstNote);
        mockMvc.perform(MockMvcRequestBuilders
                .put("/note/update/{id}", 1)
                .content(asJsonString(new Note(1L, "hello", "hello world", new Date(2020, 11, 13), new Date(2019, 06, 14))))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("hello"))
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.note").value("hello world"));
    }

    @Test
    public void deleteNote() throws Exception {
        Note firstNote = new Note(1L, "hello", "hello world", new Date(2020, 11, 13), new Date(2019, 06, 14));
        when(noteService.deleteById(firstNote.getId())).thenReturn(true);
        mockMvc.perform(delete("/note/delete/{id}", 1L)).andExpect(status().isOk());
    }
}
