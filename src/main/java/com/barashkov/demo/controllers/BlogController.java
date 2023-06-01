package com.barashkov.demo.controllers;

import com.barashkov.demo.models.Post;
import com.barashkov.demo.models.PostAnons;
import com.barashkov.demo.models.PostTag;
import com.barashkov.demo.repo.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.function.Consumer;

@Controller
public class BlogController {

    @Autowired
    private PostRepository postRepository;

    @GetMapping("/blog")
    public String blogMain(Model model) {
        Iterable<Post> posts = postRepository.findAll();
        Collection<PostTag> postsTag = new ArrayList<>();
        Collection<PostAnons> postsAnons = new ArrayList<>();
        posts.forEach(new Consumer<Post>() {
            @Override
            public void accept(Post post) {
                if(post instanceof PostTag) {
                    postsTag.add((PostTag) post);
                } else if (post instanceof PostAnons) {
                    postsAnons.add((PostAnons) post);
                }
            }
        });
        model.addAttribute("postsTag", postsTag);
        model.addAttribute("postsAnons", postsAnons);
        return "blog-main";
    }

    @PostMapping("/blog")
    public String searchByTagPosts(@RequestParam String tag, Model model) throws UnsupportedEncodingException {
        return "redirect:/blog/search=" + URLEncoder.encode(tag, java.nio.charset.StandardCharsets.UTF_8.toString());
    }

    @GetMapping("/blog/search={tag}")
    public String blogEdit(@PathVariable(value = "tag") String tag, Model model) {
        Iterable<Post> posts = postRepository.findAll();
        Collection<PostTag> searchByTagPosts = new ArrayList<>();
        posts.forEach(new Consumer<Post>() {
            @Override
            public void accept(Post post) {
                if(post instanceof PostTag) {
                    PostTag postTag = (PostTag) post;
                    if (tag.equals(postTag.getTag()))
                        searchByTagPosts.add(postTag);
                }
            }
        });
        model.addAttribute("tag", tag);
        model.addAttribute("searchByTagPosts", searchByTagPosts);
        return "blog-search";
    }

    @GetMapping("/blog/add")
    public String blogAdd(Model model) {
        return "blog-add";
    }

    @PostMapping("/blog/add")
    public String blogPostAdd(@RequestParam String value, Model model) {
        if("tag".equals(value)) {
            return "redirect:/blog/add/post-tag";
        } else if("anons".equals(value)) {
            return "redirect:/blog/add/post-anons";
        }
        return "";
    }

    @GetMapping("/blog/add/post-tag")
    public String blogTagAdd(Model model) {
        return "blog-add-post-tag";
    }

    @PostMapping("/blog/add/post-tag")
    public String blogPostTagAdd(@RequestParam String title, @RequestParam String full_text, @RequestParam String tag, Model model) {
        Post post = new PostTag(title, full_text, tag);
        postRepository.save(post);
        return "redirect:/blog";
    }

    @GetMapping("/blog/add/post-anons")
    public String blogAnonsAdd(Model model) {
        return "blog-add-post-anons";
    }

    @PostMapping("/blog/add/post-anons")
    public String blogPostAnonsAdd(@RequestParam String title, @RequestParam String anons, @RequestParam String full_text, Model model) {
        Post post = new PostAnons(title, full_text, anons);
        postRepository.save(post);
        return "redirect:/blog";
    }

    @GetMapping("/blog/{id}")
    public String blogDetails(@PathVariable(value = "id") long id, Model model) {
        if(!postRepository.existsById(id)) {
            return "redirect:/blog";
        }

        Optional<Post> post = postRepository.findById(id);
        ArrayList<Post> res = new ArrayList<>();
        post.ifPresent(res::add);
        model.addAttribute("post", res);
        return "blog-details";
    }

    @GetMapping("/blog/{id}/edit")
    public String blogEdit(@PathVariable(value = "id") long id, Model model) {
        if(!postRepository.existsById(id)) {
            return "redirect:/blog";
        }

        Optional<Post> post = postRepository.findById(id);
        ArrayList<Post> res = new ArrayList<>();
        post.ifPresent(res::add);
        model.addAttribute("post", res);
        return "blog-edit";
    }

    @PostMapping("/blog/{id}/edit")
    public String blogPostUpdate(@PathVariable(value = "id") long id, @RequestParam String title, @RequestParam String fullText, Model model) {
        Post post = postRepository.findById(id).orElseThrow();
        post.setTitle(title);
        //post.setAnons(anons);
        post.setFullText(fullText);
        postRepository.save(post);

        return "redirect:/blog";
    }

    @PostMapping("/blog/{id}/remove")
    public String blogPostUpdate(@PathVariable(value = "id") long id, Model model) {
        Post post = postRepository.findById(id).orElseThrow();
        postRepository.delete(post);
        return "redirect:/blog";
    }
 }
