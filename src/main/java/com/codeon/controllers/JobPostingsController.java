package com.codeon.controllers;

import com.codeon.models.*;
import com.codeon.repositories.*;
import com.codeon.services.EmailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Controller
public class JobPostingsController {

    @Value("${filestack.api.key}")
    private String filestackKey;
    @Value("${talkjs.app.id}")
    private String talkJSAppId;
    private Random random = new Random();
    private PostRepo postDao;
    private UserRepo userDao;
    private PostTypeRepo postTypeDao;
    private PostCommentRepo postCommentDao;
    private ImageURLRepo imageURLDao;
    private EmailService emailService;
    private PostRatingRepo postRatingDao;

    public JobPostingsController(PostRepo postDao, PostRatingRepo postRatingDao, UserRepo userDao, PostTypeRepo postTypeDao, PostCommentRepo postCommentDao, ImageURLRepo imageURLDao, EmailService emailService) {
        this.postDao = postDao;
        this.userDao = userDao;
        this.postTypeDao = postTypeDao;
        this.postCommentDao = postCommentDao;
        this.imageURLDao = imageURLDao;
        this.emailService = emailService;
        this.postRatingDao = postRatingDao;
    }

    //SHOW IT JUST AS JSON.
    @GetMapping("/job-postings")
    @ResponseBody
    public List<Post> showJobPostingsAsJSON() {
        return postDao.findAllByPostTypeId_Type("job-postings");
    }

    @GetMapping("/job-postings/show")
    public String showAllJobPostings(Model model, Principal principal) {
        model.addAttribute("user", userDao.findUserByUsername(principal.getName()));
        model.addAttribute("talkJSAppId", talkJSAppId);
        model.addAttribute("postList", postDao.findAllByPostTypeId_Type("job-postings"));
        return "job-postings/show";
    }

    @GetMapping("/job-postings/create")
    public String jobPostingsCreateForm(Model model, Principal principal) {
        model.addAttribute("user", userDao.findUserByUsername(principal.getName()));
        model.addAttribute("talkJSAppId", talkJSAppId);
        model.addAttribute("post", new Post());
        model.addAttribute("filestackKey", filestackKey);
        return "job-postings/create";
    }

    @PostMapping("/job-postings/create")
    public String createJobPosting(@RequestParam Long postTypeId, @ModelAttribute Post post) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        post.setUser(user);
        Date now = new Date();
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        String date = formatter.format(now);
        post.setDateTime(date);
        post.setRatingTotal(0);
        post.setPostType(postTypeDao.getPostTypeById(postTypeId));
        postDao.save(post);
        postRatingDao.save(new PostRating(post, user, 0));
        return "redirect:/job-postings/show";
    }

    @GetMapping("/job-postings/{id}")
    public String getJobPostings(@PathVariable Long id, Model model, Principal principal){
        List<Post> postList = new ArrayList<>();
        postList.add(postDao.getOne(id));
        model.addAttribute("user", userDao.findUserByUsername(principal.getName()));
        model.addAttribute("talkJSAppId", talkJSAppId);
        model.addAttribute("postList", postList);
        return "job-postings/show";
    }

    @GetMapping("/job-postings/edit/{id}")
    public String getEditJobPostingForm(@PathVariable Long id, Model model, Principal principal){
        User user = userDao.findUserByUsername(principal.getName());
        Post post = postDao.findPostById(id);
        if(user.getId() != post.getUser().getId()) {
            return "redirect:/job-postings/show";
        }
        model.addAttribute("user", user);
        model.addAttribute("talkJSAppId", talkJSAppId);
        model.addAttribute("post", post);
        return "job-postings/edit";
    }

    @PostMapping("/job-postings/edit/{id}")
    public String updateJobPosting(@PathVariable Long id, @ModelAttribute Post post) {
        Post dbPost = postDao.findPostById(id);
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(user.getId() != dbPost.getUser().getId()) {
            return "redirect:/job-postings/show";
        }
        dbPost.setTitle(post.getTitle());
        dbPost.setBody(post.getBody());
        dbPost.setAnswer(post.getEmployer());
        System.out.println("here");
        postDao.save(dbPost);
        return "redirect:/job-postings/show";
    }

    @DeleteMapping("/job-postings/delete")
    @ResponseBody
    public String deletePost(@RequestParam Long id, Model model, Principal principal){
        Post post = postDao.findPostById(id);
        User user = userDao.findUserByUsername(principal.getName());
        if(user.getId() != post.getUser().getId()) {
            return "-1";
        }
        postDao.deleteById(id);
        return "id";
    }
}