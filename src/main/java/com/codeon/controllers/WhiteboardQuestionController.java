package com.codeon.controllers;

import com.codeon.models.*;
import com.codeon.repositories.*;
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
public class WhiteboardQuestionController {

    @Value("${filestack.api.key}")
    private String filestackKey;
    @Value("${talkjs.app.id}")
    private String talkJSAppId;
    private Random random = new Random();
    private PostRepo postDao;
    private UserRepo userDao;
    private PostTypeRepo postTypeDao;
    private PostRatingRepo postRatingDao;

    public WhiteboardQuestionController(PostRepo postDao, PostRatingRepo postRatingDao, UserRepo userDao, PostTypeRepo postTypeDao) {
        this.postDao = postDao;
        this.userDao = userDao;
        this.postTypeDao = postTypeDao;
        this.postRatingDao = postRatingDao;
    }

    //SHOW IT JUST AS JSON.
    @GetMapping("/whiteboard-questions")
    @ResponseBody
    public List<Post> showWhiteboardQuestionsAsJSON() {
        return postDao.findAllByPostTypeId_Type("whiteboard-questions");
    }

    @GetMapping("/whiteboard-questions/show")
    public String showAllWhiteboardQuestions(Model model, Principal principal) {
        model.addAttribute("filestackKey", filestackKey);
        List<Post> whiteboardQuestions = postDao.findAllByPostTypeId_Type("whiteboard-questions");
        model.addAttribute("user", userDao.findUserByUsername(principal.getName()));
        model.addAttribute("talkJSAppId", talkJSAppId);
        model.addAttribute("postList", whiteboardQuestions);
        return "whiteboard-questions/show";
    }

    @GetMapping("/whiteboard-questions/show-one")
    public String showOneWhiteboardQuestion(Model model, Principal principal) {
        List<Post> whiteboardQuestions = postDao.findAllByPostTypeId_Type("whiteboard-questions");
        Post selectedPost = null;
        Integer pickQuestion, questionRoll, selectedPostRating;
        boolean determiningPost = true;
        while(determiningPost) {
            pickQuestion = random.nextInt(whiteboardQuestions.size());
            selectedPost = whiteboardQuestions.get(pickQuestion);
            selectedPostRating = selectedPost.getRatingTotal(selectedPost.getRatingList());
            questionRoll = random.nextInt(31) -10;
            if(selectedPostRating >= questionRoll) {
                determiningPost = false;
            }
        }
        model.addAttribute("user", userDao.findUserByUsername(principal.getName()));
        model.addAttribute("talkJSAppId", talkJSAppId);
        model.addAttribute("post", selectedPost);
        return "/whiteboard-questions/show-one";
    }

    @GetMapping("/whiteboard-questions/create")
    public String getWhiteboardQuestionCreateForm(Model model, Principal principal) {
        model.addAttribute("post", new Post());
        model.addAttribute("user", userDao.findUserByUsername(principal.getName()));
        model.addAttribute("talkJSAppId", talkJSAppId);
        model.addAttribute("filestackKey", filestackKey);
        return "whiteboard-questions/create";
    }

//    @PostMapping("/whiteboard-questions/create")
//    public String createWhiteboardQuestion(@RequestParam Long postTypeId, @ModelAttribute Post post) {
//        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        post.setUser(user);
//        Date now = new Date();
//        String pattern = "yyyy-MM-dd";
//        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
//        String date = formatter.format(now);
//        post.setDateTime(date);
//        post.setRatingTotal(0);
//        post.setPostType(postTypeDao.getPostTypeById(postTypeId));
//        postDao.save(post);
//        postRatingDao.save(new PostRating(post, user, 0));
//        return "redirect:/whiteboard-questions/show";
//    }
    @PostMapping("/whiteboard-questions/create")
    @ResponseBody
    public Post createPost(Principal principal, @RequestParam String title, @RequestParam String body, @RequestParam String answer, @RequestParam String employer) {
        User user = userDao.findUserByUsername(principal.getName());
        Date now = new Date();
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        String date = formatter.format(now);
        Post post = new Post(title, employer, body, answer, user, postTypeDao.getPostTypeByType("whiteboard-questions"), 0, date);
        post = postDao.save(post);
        postRatingDao.save(new PostRating(post, user, 0));
        return post;
    }

    @GetMapping("/whiteboard-questions/{id}")
    public String getWhiteboardQuestion(@PathVariable Long id, Model model, Principal principal){
        List<Post> postList = new ArrayList<>();
        postList.add(postDao.getOne(id));
        model.addAttribute("user", userDao.findUserByUsername(principal.getName()));
        model.addAttribute("talkJSAppId", talkJSAppId);
        model.addAttribute("postList", postList);
        return "whiteboard-questions/show";
    }

    @GetMapping("/whiteboard-questions/edit/{id}")
    public String getEditWhiteboardQuestionForm(@PathVariable Long id, Model model, Principal principal){
        User user = userDao.findUserByUsername(principal.getName());
        Post post = postDao.findPostById(id);
        if(user.getId() != post.getUser().getId()) {
            return "redirect:/whiteboard-questions/show";
        }
        model.addAttribute("user", user);
        model.addAttribute("talkJSAppId", talkJSAppId);
        model.addAttribute("post", post);
        return "whiteboard-questions/edit";
    }

    @PostMapping("/whiteboard-questions/edit/{id}")
    public String updateWhiteboardQuestion(@PathVariable Long id, @ModelAttribute Post post) {
        Post dbPost = postDao.findPostById(id);
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(user.getId() != dbPost.getUser().getId()) {
            return "redirect:/whiteboard-questions/show";
        }
        dbPost.setTitle(post.getTitle());
        dbPost.setBody(post.getBody());
        dbPost.setAnswer(post.getAnswer());
        postDao.save(dbPost);
        return "redirect:/whiteboard-questions/show";
    }

    @DeleteMapping("/whiteboard-questions/delete")
    @ResponseBody
    public String deleteWhiteboardQuestion(@RequestParam Long id, Model model, Principal principal){
        Post post = postDao.findPostById(id);
        User user = userDao.findUserByUsername(principal.getName());
        if(user.getId() != post.getUser().getId()) {
            return "-1";
        }
        postDao.deleteById(id);
        return String.format("%d", id);
    }
}
