package bo.gob.segip.micro_servicios.services;

import bo.gob.segip.micro_servicios.payloads.CommentDto;

public interface ICommentService {

    CommentDto createComment(CommentDto commentDto, Integer pId);

    void deleteComment(Integer cId);

}
