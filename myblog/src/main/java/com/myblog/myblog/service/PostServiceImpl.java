package com.myblog.myblog.service;
import com.myblog.myblog.entity.Post;
import com.myblog.myblog.exception.ResourceNotFoundException;
import com.myblog.myblog.payload.PostDto;
import com.myblog.myblog.payload.PostResponse;
import com.myblog.myblog.repository.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService{
    private PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public PostDto savePost(PostDto postDto) {
       Post savedPost= mapToEntity(postDto);
       postRepository.save(savedPost);
        PostDto dto=mapToDto(savedPost);
        return dto;
    }

    @Override
    public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable=PageRequest.of(pageNo, pageSize,sort);
        Page<Post> pagePostObject = postRepository.findAll(pageable);

        List<Post> posts=pagePostObject.getContent();
        List<PostDto> dto=posts.stream().map(l->mapToDto(l)).collect(Collectors.toList());

        PostResponse response=new PostResponse();
        response.setDto(dto);
        response.setPageNo(pagePostObject.getNumber());
        response.setPageSize(pagePostObject.getSize());
        response.setLastPage(pagePostObject.isLast());
        response.setTotalPages(pagePostObject.getTotalPages());
        return response ;
    }

    @Override
    public boolean deletePostById(long id) {
        Post post =postRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Post not found with id: "+id)
        );
        postRepository.deleteById(id);
        return false;
    }

    @Override
    public PostDto updatePost(long id, PostDto postDto) {
         Post post=postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post not found with id: "+id)
        );
         post.setTitle(postDto.getTitle());
         post.setContent(postDto.getContent());
         post.setDescription(postDto.getDescription());
         Post savedPost=postRepository.save(post);
         PostDto dto=mapToDto(savedPost);
         return dto;

    }


    Post mapToEntity(PostDto postDto) {
         Post post = new Post();
         post.setTitle(postDto.getTitle());
         post.setDescription(postDto.getDescription());
         post.setContent(postDto.getContent());
          return post;
     }
     PostDto mapToDto(Post post){
        PostDto dto=new PostDto();
        dto.setPostId(post.getPostId());
        dto.setTitle(post.getTitle());
        dto.setContent(post.getContent());
        dto.setDescription(post.getDescription());
        return dto;

     }
}
