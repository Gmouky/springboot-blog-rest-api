package com.springboot.blog.service.impl;

import com.springboot.blog.dto.CommentDto;
import com.springboot.blog.dto.PostDto;
import com.springboot.blog.entity.Comment;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.BlogApiException;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.repository.CommentRepository;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;
    private PostRepository postRepository;

    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {
        Comment comment = mapDtoToEntity(commentDto);

        //retrieve post entity by id
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

        //set post to comment entity
        comment.setPost(post);

        //save comment entity to the db
        Comment newComment = commentRepository.save(comment);
        return mapEntityToDto(newComment);
    }

    @Override
    public List<CommentDto> getAllComment(long postId) {
        List<Comment> comments = commentRepository.findByPostId(postId);
        return comments.stream().map(comment -> mapEntityToDto(comment)).collect(Collectors.toList());
    }

    @Override
    public CommentDto getCommentById(long postId, long commentId) {
        Comment comment = getCommentByPostIdAndValidate(postId, commentId);
        return mapEntityToDto(comment);
    }

    @Override
    public CommentDto updateComment(long postId, long commentId, CommentDto commentDto) {

        Comment comment = getCommentByPostIdAndValidate(postId, commentId);

        comment.setBody(commentDto.getBody());
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());

        Comment newComment = commentRepository.save(comment);

        return mapEntityToDto(newComment);
    }

    @Override
    public void deleteComment(long postId, long commentId) {
        Comment comment = getCommentByPostIdAndValidate(postId, commentId);
        commentRepository.delete(comment);
    }

    private Comment mapDtoToEntity(CommentDto commentDto) {
        Comment comment = new Comment();
        comment.setId(comment.getId());
        comment.setBody(commentDto.getBody());
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        return comment;
    }

    private CommentDto mapEntityToDto(Comment comment) {
        CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setBody(comment.getBody());
        commentDto.setName(comment.getName());
        commentDto.setEmail(comment.getEmail());
        return commentDto;
    }

    private Comment getCommentByPostIdAndValidate(long postId, long commentId) {
        //retrieve post by id
        Post post = postRepository.findById(postId).orElseThrow(()
                -> new ResourceNotFoundException("Post", "id", postId));

        //retrieve comment by id
        Comment comment = commentRepository.findById(commentId).orElseThrow(()
                -> new ResourceNotFoundException("Comment", "id", commentId));

        if(!comment.getPost().getId().equals(post.getId())) {
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
        }
        return comment;
    }
}
