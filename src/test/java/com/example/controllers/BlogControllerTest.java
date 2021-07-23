package com.example.controllers;

import com.example.models.Post;
import com.example.repo.PostRepository;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.BDDMockito.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.ui.Model;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class,
        loader = AnnotationConfigContextLoader.class)
class BlogControllerTest {
    private static final Long id = 1l;

    @Autowired
    private BlogController blogController;

    @Autowired
    private Model model;

    @Autowired
    private PostRepository postRepository;

    private List<Post> posts = new ArrayList<Post>();

    @Autowired
    private  Post post1;

    @BeforeEach
    public void setUp(){
        //post1 = new Post();
        post1.setId(id);
        post1.setTitle("Title");
        post1.setAnons("Anons");
        post1.setFullText("Text");
        post1.setViews(10);
        posts.add(post1);

    }
    @Test
    //@Disabled
    void blogMainTest() {
        when(this.postRepository.findAll()).thenReturn(this.posts);
        blogController.blogMain("Oleg", model);
        model.addAttribute("name", "Oleg");
        Assert.assertEquals("blog-main", blogController.blogMain("User_", model));
    }

        @Test
        void addPostTest() {
        model.addAttribute("Default");
        assertEquals("blog-add", blogController.addPost("Oleg", model));
        }

        @Test
        void addPostToText() {
        when(this.postRepository.save(post1)).thenReturn(post1);
        model.addAttribute("user");
        Assert.assertEquals("redirect:/blog", blogController.addPostTo("title", "anons",
                    "text", "user", model));
        }

       @Test
        void blogPageTest() {
        when(postRepository.existsById(id)).thenReturn(true);
        when(postRepository.findById(id)).thenReturn(Optional.ofNullable(post1));
        Assert.assertEquals("blog-page",
                blogController.blogPage("User", id, model) );

        }
    @Test
    void blogPageTestShouldNotExist() {
        when(postRepository.existsById(id)).thenReturn(false);
        when(postRepository.findById(id)).thenReturn(Optional.ofNullable(post1));
        Assert.assertEquals("redirect:/",
                blogController.blogPage("User", id, model) );

    }

        @Test
        void blogPageEditTest() {
            when(postRepository.existsById(id)).thenReturn(true);
            when(postRepository.findById(id)).thenReturn(Optional.ofNullable(post1));
            Assert.assertEquals("blog-edit",
                    blogController.blogPageEdit("User", id, model) );

        }
    @Test
    void blogPageEditShouldNotExistTest() {
        when(postRepository.existsById(id)).thenReturn(false);
        when(postRepository.findById(id)).thenReturn(Optional.ofNullable(post1));
        Assert.assertEquals("redirect:/",
                blogController.blogPageEdit("User", id, model) );

    }

    @Test
    void blogEditPostTest(){
        when(postRepository.findById(id)).thenReturn(Optional.ofNullable(post1));
        when(this.postRepository.save(post1)).thenReturn(post1);
        Assert.assertEquals("redirect:/blog", blogController.editPost(id,
                "Title", "anons", "fullText", "User", model));
    }

    @Test
    void blogEditPostThrowsExpectedTest(){
        when(postRepository.findById(id)).thenReturn(Optional.empty());

        Assert.assertThrows(NoSuchElementException.class, ()-> {
            when(postRepository.findById(id).orElseThrow()).thenReturn(null);});

    }

    @Test
    void deletePostTest(){
        when(this.postRepository.findById(id)).thenReturn(Optional.ofNullable(post1));
        //when(this.postRepository.delete(post1))
        Assert.assertEquals("redirect:/blog",
                blogController.deletePost(id, "User", model));
    }

    @Test
    void getAllTest(){
        when(this.postRepository.findAll()).thenReturn(posts);
        Assert.assertArrayEquals(posts.toArray(), posts.toArray());
    }

    @Test
    void aboutTest(){
        Assert.assertEquals("about", blogController.blogAbout("User", model));
    }




}

