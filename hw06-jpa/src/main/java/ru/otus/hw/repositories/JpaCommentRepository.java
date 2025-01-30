package ru.otus.hw.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.Comment;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JpaCommentRepository implements CommentRepository {

    @PersistenceContext
    private final EntityManager entityManager;

    private final BookRepository bookRepository;

    @Override
    public Comment save(Comment comment) {
        if (comment.getId() == 0) {
            entityManager.persist(comment);
            return comment;
        }
        return entityManager.merge(comment);
    }

    @Override
    public Optional<Comment> findById(long id) {
        return Optional.ofNullable(entityManager.find(Comment.class, id));
    }

    @Override
    public void deleteById(long id) {
        var comment = findById(id);
        comment.ifPresent(entityManager::remove);
    }

    @Override
    public List<Comment> findByBookId(long id) {
        var book = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book not found"));
        return entityManager.createQuery(
                "select new ru.otus.hw.models.Comment(c.id, c.text) from Comment c "
                        + "where book = :book", Comment.class)
                .setParameter("book", book)
                .getResultList()
                .stream()
                .peek(projection -> projection.setBook(book))
                .toList();
    }
}
