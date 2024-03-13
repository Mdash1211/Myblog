package com.myblog.myblog.controller;

import com.myblog.myblog.payload.CommentDto;

import com.myblog.myblog.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
     private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    //http://localhost:8080/api/comments?postId=
    @PostMapping()
    public ResponseEntity<CommentDto> createComment(@RequestParam long postId , @RequestBody CommentDto commentDto){
        CommentDto dto=commentService.createComment(postId,commentDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }
    @GetMapping()
    public ResponseEntity<List<CommentDto>> getCommentsByPostId(@RequestParam long postId ){
        List<CommentDto> ListOfComments=commentService.getCommentsByPostId(postId);
        return new ResponseEntity<>(ListOfComments,HttpStatus.OK);
    }
    @PutMapping("/update")
    public ResponseEntity<CommentDto> updateComment(@RequestParam long commentId,@RequestBody CommentDto commentDto){
        CommentDto dto=commentService.updateComment(commentId,commentDto);
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteCommentById (@RequestParam long postId,@RequestParam long commentId){
        commentService.deleteComment(postId,commentId);
        return new ResponseEntity<>("comment deleted",HttpStatus.OK);
    }

}

