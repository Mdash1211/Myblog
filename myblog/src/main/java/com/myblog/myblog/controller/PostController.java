package com.myblog.myblog.controller;

import com.myblog.myblog.payload.ErrorResponseDTO;
import com.myblog.myblog.payload.PostDto;
import com.myblog.myblog.payload.PostResponse;
import com.myblog.myblog.service.PostService;
import com.myblog.myblog.util.EmailService;
import com.myblog.myblog.util.PdfService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.util.List;


@RestController
@RequestMapping("/api/posts")
//@SuppressWarnings(value = "unused")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @Autowired
    private PdfService pdfService;

    @Autowired
    private EmailService emailService;

    @PostMapping("save")
    public ResponseEntity<?> createPost(@Valid @RequestBody PostDto postDto, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(result.getFieldError().getDefaultMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        PostDto savedPostDto = postService.savePost(postDto);
        if (savedPostDto != null) {
            // Generate PDF after saving the post
            ByteArrayOutputStream pdfStream = pdfService.generatePdf(savedPostDto);

            // Send email notification with PDF attachment
            String to = "rajraj1801109184@gmail.com"; // Change to recipient's email address
            String subject = "New Post Created";
            String body = "A new post titled '" + savedPostDto.getTitle() + "' has been created.";

            // Attach the PDF to the email and send it
            emailService.sendEmailWithAttachment(to, subject, body, pdfStream, "post.pdf");

            return new ResponseEntity<>(savedPostDto, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("Failed to save the post", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    //    http://localhost:8080/api/posts?pageNo=0&pageSize=3&sortBy=Title&sortDir=desc
    @GetMapping
    public PostResponse getAllPosts(@RequestParam(name = "pageNo", defaultValue = "0", required = false) int pageNo,
                                    @RequestParam(name = "pageSize", defaultValue = "10", required = false) int pageSize,
                                    @RequestParam(name = "sortBy", defaultValue = "id", required = false) String sortBy,
                                    @RequestParam(name = "sortDir", defaultValue = "asc", required = false) String sortDir){
        PostResponse postResponse =postService.getAllPosts(pageNo, pageSize,sortBy,sortDir);
        return postResponse;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePostById(@PathVariable long id){
        boolean deleted = postService.deletePostById(id);
        if (deleted) {
            return new ResponseEntity<>("Post is Deleted!!!", HttpStatus.OK);
        } else {
            ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(HttpStatus.NOT_FOUND.value(), "Post not found with ID: " + id);
            return new ResponseEntity<>(errorResponseDTO, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePost(@Valid @PathVariable long id,@RequestBody PostDto postDto, BindingResult result){
        if(result.hasErrors()){
            return new ResponseEntity<>(result.getFieldError().getDefaultMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        PostDto dto=postService.updatePost(id,postDto);
         return new ResponseEntity<>(dto,HttpStatus.OK);
    }
}

