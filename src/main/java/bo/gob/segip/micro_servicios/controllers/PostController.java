package bo.gob.segip.micro_servicios.controllers;

import bo.gob.segip.micro_servicios.config.AppConstants;
import bo.gob.segip.micro_servicios.payloads.ApiResponse;
import bo.gob.segip.micro_servicios.payloads.PostDto;
import bo.gob.segip.micro_servicios.payloads.PostResponse;
import bo.gob.segip.micro_servicios.services.IFileService;
import bo.gob.segip.micro_servicios.services.IPostService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PostController {

    private final IPostService postService;

    private final IFileService fileService;

    @Value("${project.image}")
    private String path;

    //	Create posts

    @PostMapping("/user/{uId}/category/{cId}/posts")
    public ResponseEntity<PostDto> createPost(
            @RequestBody PostDto postDto,
            @PathVariable Integer uId,
            @PathVariable Integer cId) {

        PostDto createPost = this.postService.createPost(postDto, uId, cId);

        return new ResponseEntity<>(createPost, HttpStatus.CREATED);

    }

    //	Get posts by user

    @GetMapping("/user/{uId}/posts")
    public ResponseEntity<List<PostDto>> getPostByUser(@PathVariable Integer uId) {

        List<PostDto> posts = this.postService.getPostByUser(uId);

        return new ResponseEntity<>(posts, HttpStatus.OK);

    }

    //	Get by category

    @GetMapping("/category/{cId}/posts")
    public ResponseEntity<List<PostDto>> getPostByCategory(@PathVariable Integer cId) {

        List<PostDto> posts = this.postService.getPostByCategory(cId);

        return new ResponseEntity<>(posts, HttpStatus.OK);

    }

    //	Get all posts

    /*
    @GetMapping("/posts")
    public ResponseEntity<List<PostDto>> getAllPost() {

        List<PostDto> allPost = this.postService.getAllPost();

        return new ResponseEntity<>(allPost, HttpStatus.OK);

    }
    */

    //	Get all posts using paginate

    /*
    @GetMapping("/posts")
    public ResponseEntity<List<PostDto>> getAllPost(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "5", required = false) Integer pageSize
    ) {

        List<PostDto> allPost = this.postService.getAllPostPaginate(pageNumber, pageSize);

        return new ResponseEntity<>(allPost, HttpStatus.OK);

    }
    */

    //	Get all posts using paginate and custom response
    @GetMapping("/posts")
    public ResponseEntity<PostResponse> getAllPost(
            @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir) {

        PostResponse postResponse = this.postService.getAllPostPaginateAndCustomResponse(pageNumber, pageSize, sortBy, sortDir);

        return new ResponseEntity<>(postResponse, HttpStatus.OK);
    }

    //	Get post details by id

    @GetMapping("/posts/{pId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Integer pId) {

        PostDto postDto = this.postService.getPostById(pId);

        return new ResponseEntity<>(postDto, HttpStatus.OK);

    }

    //	Delete post

    @DeleteMapping("/posts/{pId}")
    public ApiResponse deletePost(@PathVariable Integer pId) {

        this.postService.deletePost(pId);

        return new ApiResponse("Post is successfully deleted", true);

    }

    //	Update post

    @PutMapping("/posts/{pId}")
    public ResponseEntity<PostDto> updatePost(
            @RequestBody PostDto postDto,
            @PathVariable Integer pId) {

        PostDto pDto = this.postService.updatePost(postDto, pId);

        return new ResponseEntity<>(pDto, HttpStatus.OK);

    }

    //	Search post

    @GetMapping("/posts/search/{keywords}")
    public ResponseEntity<List<PostDto>> searchPostsByTitle(@PathVariable String keywords) {

        List<PostDto> result = this.postService.searchPosts(keywords);

        return new ResponseEntity<>(result, HttpStatus.FOUND);

    }

    //	Post image upload

    @PostMapping("/posts/image/upload/{pId}")
    public ResponseEntity<PostDto> uploadPostImage(
            @RequestParam("image") MultipartFile file,
            @PathVariable Integer pId) throws IOException {

        PostDto postDto = this.postService.getPostById(pId);

        String fileName = this.fileService.uploadImage(path, file);

        postDto.setImageName(fileName);
        PostDto updatedPost = this.postService.updatePost(postDto, pId);

        return new ResponseEntity<>(updatedPost, HttpStatus.OK);

    }

    //	Download image
    @GetMapping(value = "posts/image/{imgName}", produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(
            @PathVariable("imgName") String imageName,
            HttpServletResponse response) throws IOException {

        InputStream inputStream = this.fileService.getResource(path, imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(inputStream, response.getOutputStream());

    }

}
