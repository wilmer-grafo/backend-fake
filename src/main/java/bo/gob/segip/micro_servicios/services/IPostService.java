package bo.gob.segip.micro_servicios.services;


import bo.gob.segip.micro_servicios.payloads.PostDto;
import bo.gob.segip.micro_servicios.payloads.PostResponse;

import java.util.List;

public interface IPostService {

    PostDto createPost(PostDto postDto, Integer userId, Integer categoryId);

    PostDto updatePost(PostDto postDto, Integer postId);

    void deletePost(Integer postId);

    PostDto getPostById(Integer postId);

    List<PostDto> getAllPost();

    List<PostDto> getAllPostPaginate(Integer pageNumber, Integer pageSize);

    PostResponse getAllPostPaginateAndCustomResponse(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

    List<PostDto> getPostByCategory(Integer catId);

    List<PostDto> getPostByUser(Integer userId);

    List<PostDto> searchPosts(String keyword);

    List<PostDto> searchPostsUsingJPQL(String title);

}
