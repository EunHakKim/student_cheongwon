package study.student.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import study.student.domain.Post;
import study.student.service.PostService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {

    private final PostService postService;

    @GetMapping("/lecture")
    public String lectureBoard(Model model) {
        List<Post> findPosts = postService.findAll();
        model.addAttribute("posts", findPosts);
        return "post/lectureBoard";
    }

    @GetMapping("/facility")
    public String facilityBoard() {
        return "";
    }

    @GetMapping("/welfare")
    public String welfareBoard() {
        return "";
    }

    @GetMapping("/cafeteria")
    public String cafeteriaBoard() {
        return "";
    }

    @GetMapping("/dormitory")
    public String dormitoryBoard() {
        return "";
    }

    @GetMapping("/etc")
    public String etcBoard() {
        return "";
    }
}
