package com.example.controllers;

import com.example.models.Post;
import com.example.repo.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Random;

@Controller
@RequestMapping("/")
public class BlogController {

    private PostRepository postRepository;
    Random random = new Random();

    @Autowired
    public void setPostRepository(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @GetMapping("/blog")
    public String blogMain(@RequestParam(name="name", defaultValue = "User")String name, Model model){
        Iterable<Post> posts = postRepository.findAll();
        model.addAttribute("posts", posts);
        model.addAttribute("name", name);
        return "blog-main";
    }
    @GetMapping("/blog/add")
    public String addPost(@RequestParam(name="name", defaultValue = "User")String name, Model model){
        model.addAttribute("name", name);
        return "blog-add";
    }
    @PostMapping("/blog/add")
    public String addPostTo(@RequestParam String title, @RequestParam String anons,
                            @RequestParam String full_text,
                            @RequestParam (name="name", defaultValue="User")
                                        String name, Model model){
        model.addAttribute("name", name);
        Post post = new Post();
        post.setTitle(title);
        post.setAnons(anons);
        post.setFullText(full_text);
        post.setViews(random.nextInt(100));
        postRepository.save(post);
        return "redirect:/blog";
    }

    @GetMapping("/blog/{id}")
    public String blogPage(@RequestParam(name="name", defaultValue = "User")String name,
                           @PathVariable(name = "id") Long id, Model model){
        if(!postRepository.existsById(id)) return "redirect:/";
        Optional<Post> posts = postRepository.findById(id);
        String title = postRepository.findById(id).get().getTitle();
        String anons = postRepository.findById(id).get().getAnons();
        String fullText = postRepository.findById(id).get().getFullText();
        int views = postRepository.findById(id).get().getViews();
        model.addAttribute("id", id);
        model.addAttribute("title", title);
        model.addAttribute("anons", anons);
        model.addAttribute("fulltext", fullText);
        model.addAttribute("views", views);
        model.addAttribute("name", name);
        return "blog-page";
    }

    @GetMapping("/blog/{id}/edit")
    public String blogPageEdit(@RequestParam(name="name", defaultValue = "User")String name,
                           @PathVariable(name = "id") Long id, Model model){
        if(!postRepository.existsById(id)) return "redirect:/";
        Optional<Post> posts = postRepository.findById(id);
        String title = postRepository.findById(id).get().getTitle();
        String anons = postRepository.findById(id).get().getAnons();
        String fullText = postRepository.findById(id).get().getFullText();
        int views = postRepository.findById(id).get().getViews();
        model.addAttribute("id", id);
        model.addAttribute("title", title);
        model.addAttribute("anons", anons);
        model.addAttribute("fulltext", fullText);
        model.addAttribute("views", views);
        model.addAttribute("name", name);
        return "blog-edit";
    }

    @PostMapping("/blog/{id}/edit")
    public String editPost(@PathVariable(name = "id") Long id,
                           @RequestParam String title, @RequestParam String anons,
                            @RequestParam String full_text,
                            @RequestParam (name="name", defaultValue="User")
                                    String name, Model model){
        model.addAttribute("name", name);
        Post post = postRepository.findById(id).orElseThrow();
        post.setTitle(title);
        post.setAnons(anons);
        post.setFullText(full_text);
        post.setViews(random.nextInt(100));
        postRepository.save(post);
        return "redirect:/blog";
    }

    @PostMapping("/blog/{id}/delete")
    public String deletePost(@PathVariable(name = "id") Long id,
                             @RequestParam (name="name", defaultValue="User")
                                   String name, Model model){
        model.addAttribute("name", name);
        Post post = postRepository.findById(id).orElseThrow();
        postRepository.delete(post);
            return "redirect:/blog";
    }
    @GetMapping("/all")
    public @ResponseBody Iterable<Post> getAll(){

        return postRepository.findAll();
    }

    @GetMapping("/about")
    public String blogAbout(@RequestParam(name="name", defaultValue = "User")String name,
                            Model model){
        model.addAttribute("name", name);
        return "about";
    }
}
