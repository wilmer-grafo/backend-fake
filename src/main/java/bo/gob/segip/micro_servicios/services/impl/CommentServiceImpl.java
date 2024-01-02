package bo.gob.segip.micro_servicios.services.impl;

import bo.gob.segip.micro_servicios.entities.CommentEntity;
import bo.gob.segip.micro_servicios.entities.PostEntity;
import bo.gob.segip.micro_servicios.exceptions.ResourceNotFoundException;
import bo.gob.segip.micro_servicios.payloads.CommentDto;
import bo.gob.segip.micro_servicios.repositories.ICommentRepository;
import bo.gob.segip.micro_servicios.repositories.IPostRepository;
import bo.gob.segip.micro_servicios.services.ICommentService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements ICommentService {

    private final IPostRepository postRepository;
    private final ICommentRepository commentRepository;

    private final ModelMapper modelMapper;

    @Override
    public CommentDto createComment(CommentDto commentDto, Integer pId) {

        PostEntity post = this.postRepository.findById(pId).orElseThrow(() ->
                new ResourceNotFoundException("Post", "Post id", pId));

        CommentEntity comment = this.modelMapper.map(commentDto, CommentEntity.class);
        comment.setPost(post);

        CommentEntity savedComment = this.commentRepository.save(comment);

        return this.modelMapper.map(savedComment, CommentDto.class);

    }

    @Override
    public void deleteComment(Integer cId) {

        CommentEntity comment = this.commentRepository.findById(cId).orElseThrow(() ->
                new ResourceNotFoundException("Post", "Post id", cId));

        this.commentRepository.delete(comment);

    }

}
