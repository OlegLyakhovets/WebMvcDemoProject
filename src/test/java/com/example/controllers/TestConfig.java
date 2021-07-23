package com.example.controllers;

import com.example.models.Post;
import com.example.repo.PostRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ui.Model;

import static org.mockito.Mockito.mock;

@Configuration
public class TestConfig {

    @Bean
    public Post post1(){
        return new Post();
    }

    @Bean
    public BlogController blogController(){
        return new BlogController();
    }
    @Bean
    public Model model(){
        return mock(Model.class);
    }

    @Bean
    public PostRepository postRepository(){
        return mock(PostRepository.class);
    }
}
