package net.proselyte.springbootdemo.repository;

import net.proselyte.springbootdemo.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for crud operations with note.
 */
@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {
}
