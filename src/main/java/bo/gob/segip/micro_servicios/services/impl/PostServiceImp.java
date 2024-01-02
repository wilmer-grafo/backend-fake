package bo.gob.segip.micro_servicios.services.impl;

import bo.gob.segip.micro_servicios.entities.CategoryEntity;
import bo.gob.segip.micro_servicios.entities.PostEntity;
import bo.gob.segip.micro_servicios.entities.UserEntity;
import bo.gob.segip.micro_servicios.exceptions.ResourceNotFoundException;
import bo.gob.segip.micro_servicios.payloads.PostDto;
import bo.gob.segip.micro_servicios.payloads.PostResponse;
import bo.gob.segip.micro_servicios.repositories.ICategoryRepository;
import bo.gob.segip.micro_servicios.repositories.IPostRepository;
import bo.gob.segip.micro_servicios.repositories.IUserRepository;
import bo.gob.segip.micro_servicios.services.IPostService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImp implements IPostService {

    private final IPostRepository postRepository;
    private final IUserRepository userRepository;
    private final ICategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Override
    public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {

        UserEntity user = this.userRepository.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException("User", "User id", userId));

        CategoryEntity category = this.categoryRepository.findById(categoryId).orElseThrow(() ->
                new ResourceNotFoundException("Category", "Category id", categoryId));

        PostEntity post = this.modelMapper.map(postDto, PostEntity.class);

        post.setImageName("default.png");
        post.setAddedDate(new Date());
        post.setUser(user);
        post.setCategory(category);

        PostEntity newPost = this.postRepository.save(post);

        return this.modelMapper.map(newPost, PostDto.class);

    }

    @Override
    public PostDto updatePost(PostDto postDto, Integer postId) {

        PostEntity post = this.postRepository.findById(postId).orElseThrow(()
                -> new ResourceNotFoundException("Post", " id ", postId));

        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setImageName(postDto.getImageName());

        PostEntity updatedPost = this.postRepository.save(post);

        PostDto postDtos = this.modelMapper.map(updatedPost, PostDto.class);

        return postDtos;

    }

    @Override
    public void deletePost(Integer postId) {

        PostEntity post = this.postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post", " id ", postId));

        this.postRepository.delete(post);

    }

    @Override
    public PostDto getPostById(Integer postId) {

        PostEntity post = this.postRepository.findById(postId).orElseThrow(() ->
                new ResourceNotFoundException("Post", "Post id", postId));

        PostDto postDtos = this.modelMapper.map(post, PostDto.class);

        return postDtos;

    }

    // Without paginate

    @Override
    public List<PostDto> getAllPost() {

        List<PostEntity> allPosts = this.postRepository.findAll();

        List<PostDto> postDtos = allPosts.stream().map((post) ->
                this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());

        return postDtos;

    }

    @Override
    public List<PostDto> getAllPostPaginate(Integer pageNumber, Integer pageSize) {

        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<PostEntity> pagePost = this.postRepository.findAll(pageable);

        List<PostEntity> allPosts = pagePost.getContent();
        List<PostDto> postDtos = allPosts.stream().map((post) ->
                this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());

        return postDtos;

    }

    @Override
    public PostResponse getAllPostPaginateAndCustomResponse(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {

        Sort sort = (sortDir.equalsIgnoreCase("asc")) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pag = PageRequest.of(pageNumber, pageSize, sort); // Order by PostDto attributes

        Page<PostEntity> pagePost = this.postRepository.findAll(pag);

        List<PostDto> postDtos = pagePost.stream().map((p) -> this.modelMapper.map(p, PostDto.class)).collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();

        postResponse.setContent(postDtos);
        postResponse.setPageNumber(pagePost.getNumber());
        postResponse.setPageSize(pagePost.getSize());
        postResponse.setTotalElements(pagePost.getTotalElements());
        postResponse.setTotalPages(pagePost.getTotalPages());
        postResponse.setLastPage(pagePost.isLast());

        return postResponse;

    }

    @Override
    public List<PostDto> getPostByCategory(Integer catId) {

        CategoryEntity category = this.categoryRepository.findById(catId).orElseThrow(() ->
                new ResourceNotFoundException("Category", "Category id", catId));

        List<PostEntity> posts = this.postRepository.findByCategory(category);

        List<PostDto> postDtos = posts.stream().map((post) ->
                this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());

        return postDtos;

    }

    @Override
    public List<PostDto> getPostByUser(Integer userId) {

        UserEntity user = this.userRepository.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException("User", "User id", userId));

        List<PostEntity> posts = this.postRepository.findByUser(user);

        List<PostDto> postDtos = posts.stream().map((post) ->
                this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());

        return postDtos;

    }

    @Override
    public List<PostDto> searchPosts(String keyword) {

        List<PostEntity> list = this.postRepository.findByTitleContaining(keyword);

        List<PostDto> postDtos = list.stream().map((post) ->
                this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());

        return postDtos;

    }

    @Override
    public List<PostDto> searchPostsUsingJPQL(String title) {

        List<PostEntity> list = this.postRepository.searchByTitle("%" + title + "%");

        List<PostDto> postDtos = list.stream().map((post) ->
                this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());

        return postDtos;

    }
}
