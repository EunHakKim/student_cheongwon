package study.student.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import study.student.domain.Board;
import study.student.domain.Comment;
import study.student.domain.Member;
import study.student.domain.Post;
import study.student.dto.CommentRequest;
import study.student.dto.LoginRequest;
import study.student.dto.PostRequest;
import study.student.service.AgreeDisagreeService;
import study.student.service.CommentService;
import study.student.service.MemberService;
import study.student.service.PostService;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final MemberService memberService;
    private final AgreeDisagreeService agreeDisagreeService;
    private final CommentService commentService;

    @GetMapping("/board")
    public String board(Model model, @SessionAttribute(name = "studentId", required = false) String studentId,
                        @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Optional<Member> findMember = memberService.findByStudentId(studentId);
        if (!findMember.isEmpty()) {
            model.addAttribute("name", findMember.get().getName());
        }
        Page<Post> findPosts = postService.findAll(pageable);

        int nowPage = findPosts.getPageable().getPageNumber() + 1;
        int startPage = Math.max(nowPage - 4, 1);
        int endPage = Math.min(nowPage + 9, findPosts.getTotalPages());

        model.addAttribute("posts", findPosts);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        return "post/board";
    }

    @GetMapping("/board/{board}")
    public String boards(Model model, @SessionAttribute(name = "studentId", required = false) String studentId,
                               @PageableDefault(page = 0, size = 10, sort="id", direction = Sort.Direction.DESC) Pageable pageable,
                               @PathVariable("board") String board) {
        Optional<Member> findMember = memberService.findByStudentId(studentId);
        if (!findMember.isEmpty()) {
            model.addAttribute("name", findMember.get().getName());
        }
        Page<Post> findPosts = postService.findAllByBoard(pageable, Board.valueOf(board.toUpperCase()));

        int nowPage = findPosts.getPageable().getPageNumber() + 1;
        int startPage = Math.max(nowPage - 4, 1);
        int endPage = Math.min(nowPage + 9, findPosts.getTotalPages());

        model.addAttribute("posts", findPosts);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("board", Board.valueOf(board.toUpperCase()));
        return "post/boards";
    }

    @GetMapping("/board/write")
    public String createPostRequest(Model model, @SessionAttribute(name = "studentId", required = false) String studentId) {
        Optional<Member> findMember = memberService.findByStudentId(studentId);
        if (findMember.isEmpty()) {
            return "redirect:/members/login";
        }
        model.addAttribute("postRequest", new PostRequest());
        return "post/write";
    }

    @PostMapping("/board/write")
    public String write(@Valid PostRequest postRequest, BindingResult result, Model model, Errors errors,
                        @SessionAttribute(name = "studentId", required = false) String studentId) {
        if(result.hasErrors()) {
            model.addAttribute("postRequest", postRequest);
            return "post/write";
        }
        Optional<Member> findMember = memberService.findByStudentId(studentId);
        if (findMember.isEmpty()) {
            return "redirect:/members/login";
        }
        postService.writePost(postRequest,findMember.get());
        return "redirect:/";
    }

    @GetMapping("/post/{postId}")
    public String post(@PathVariable("postId") Long postId, Model model,
                       @SessionAttribute(name = "studentId", required = false) String studentId,
                       @PageableDefault(page = 0, size = 10, sort="id", direction = Sort.Direction.DESC) Pageable pageable) {
        Post findPost = postService.findOne(postId);
        if(findPost==null) {
            throw new IllegalStateException("찾는 게시물이 없습니다");
        }
        Optional<Member> findMember = memberService.findByStudentId(studentId);
        if(findMember.isEmpty()) {
            model.addAttribute("agreeYn", null);
            model.addAttribute("disagreeYn", null);
        } else{
            Member member = findMember.get();
            Boolean agreeYn = agreeDisagreeService.checkAgree(findPost, member);
            Boolean disagreeYn = agreeDisagreeService.checkDisagree(findPost, member);
            model.addAttribute("agreeYn",agreeYn);
            model.addAttribute("disagreeYn",disagreeYn);
            model.addAttribute("name", member.getName());
        }
        Page<Comment> findComments = commentService.findAllByPost(pageable, findPost);

        int nowPage = findComments.getPageable().getPageNumber() + 1;
        int startPage = Math.max(nowPage - 4, 1);
        int endPage = Math.min(nowPage + 9, findComments.getTotalPages());

        model.addAttribute("findComments", findComments);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("post", findPost);
        model.addAttribute("commentRequest", new CommentRequest());
        return "post/post";

    }
}

