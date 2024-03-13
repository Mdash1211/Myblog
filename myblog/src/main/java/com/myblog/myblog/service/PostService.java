package com.myblog.myblog.service;

import com.myblog.myblog.payload.PostDto;
import com.myblog.myblog.payload.PostResponse;

import java.util.List;

public interface PostService {
    PostDto savePost(PostDto postDto);

    PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);

    boolean deletePostById(long id);

    PostDto updatePost(long id, PostDto postDto);
}
