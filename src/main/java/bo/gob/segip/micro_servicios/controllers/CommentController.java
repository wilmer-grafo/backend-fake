package bo.gob.segip.micro_servicios.controllers;

import bo.gob.segip.micro_servicios.payloads.ApiResponse;
import bo.gob.segip.micro_servicios.payloads.CommentDto;
import bo.gob.segip.micro_servicios.services.ICommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CommentController {

    private final ICommentService commentService;

    @PostMapping("/post/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(
            @RequestBody CommentDto commentDto,
            @PathVariable(name = "postId") Integer pId) {

        CommentDto createCommentDto = this.commentService.createComment(commentDto, pId);

        return new ResponseEntity<>(createCommentDto, HttpStatus.CREATED);

    }

    @DeleteMapping("/comments/{cId}")
    public ResponseEntity<ApiResponse> deleteComment(
            @PathVariable Integer cId) {

        this.commentService.deleteComment(cId);

        return new ResponseEntity<>(
                new ApiResponse("Comment has been deleted successfully", true),
                HttpStatus.OK
        );

    }

}
