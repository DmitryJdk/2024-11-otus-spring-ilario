package ru.otus.hw.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.hw.models.Comment;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JpaCommentRepository implements CommentRepository {

    @PersistenceContext
    private final EntityManager entityManager;

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
        return entityManager.createQuery(
                "select c from Comment c left join fetch c.book b left join fetch b.author"
                        + " where b.id=:bookId", Comment.class
                )
                .setParameter("bookId", id)
                .getResultList();
    }
}
