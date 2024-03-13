package com.myblog.myblog.repository;

import com.myblog.myblog.entity.Comment;
import com.myblog.myblog.payload.CommentDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
   @Query("SELECT c FROM Comment c WHERE c.post.id = :postId")
   List<Comment> findByPostId(@Param("postId") long postId);

}

