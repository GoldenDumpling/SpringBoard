package org.zerock.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.domain.CommentVO;
import org.zerock.domain.PostVO;
import org.zerock.service.CommentService;
import org.zerock.service.PostService;

import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Controller
@Log4j
@RequestMapping("/post/*")
@AllArgsConstructor
public class PostController {
	@Setter(onMethod_ = {@Autowired})
	private PostService service;
	
	@Setter(onMethod_ = {@Autowired})
	private CommentService CommentService;
	
    @GetMapping("/list")
    public String list(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum, Model model) {
	    int pageSize = 10; // 페이지 당 게시글 수
	    
	    log.info("pageNum : " + pageNum);
	    // 전체 게시글 수 계산
	    int count = service.getTotalCount();
	
	    // 페이징 처리를 위한 계산
	    int currentPage = pageNum;
	    int start = (currentPage - 1) * pageSize + 1;
	    int end = currentPage * pageSize;
	    log.info("startRow : " + start);
	    log.info("endRow : " + end);
	    List<PostVO> posts = service.getPaginatedPosts(start, end);
	    // 페이징을 위한 번호
	    int number = count - (currentPage - 1) * pageSize;
	
	    // 모델 바인딩
	    model.addAttribute("posts", posts);
	    model.addAttribute("number", number);
	    model.addAttribute("pageSize", pageSize);
	    model.addAttribute("count", count);
	    model.addAttribute("currentPage", currentPage);
	
	    return "/post/BoardList"; // 게시글 목록을 보여주는 JSP 페이지
	}
    
    @GetMapping("/detail")
    public String detail(@RequestParam("post_id") Long postId, 
                         @RequestParam(value = "editMode", defaultValue = "false") boolean editMode, 
                         Model model) {
        log.info("게시글 상세보기");
        
        // 조회수 증가
        if (!editMode) {
            service.increaseReadCount(postId);
        }
        
        // 게시글 정보 조회
        PostVO post = service.read(postId);
        
        // 댓글 목록 조회
        List<CommentVO> comments = CommentService.getListByPostId(postId);
        
        // 모델 바인딩
        model.addAttribute("post", post);
        model.addAttribute("comments", comments);
        model.addAttribute("editMode", editMode);
        
        return "/post/Detail"; // 게시글 상세보기 JSP 페이지
    }
    
    
    
    @PostMapping("/{postId}/like")
    public ResponseEntity<Map<String, Long>> likePost(@PathVariable Long postId) {
        // 추천 기능 처리 로직 구현
        service.increaseLikes(postId);
        Long likes = service.getLikes(postId);

        Map<String, Long> response = new HashMap<>();
        response.put("likes", likes);

        return ResponseEntity.ok(response);
    }
    

    @GetMapping("/search")
    public String search(
            @RequestParam("searchType") String searchType,
            @RequestParam("searchValue") String searchValue,
    		@RequestParam(value = "pageNum", defaultValue = "1") int pageNum, 
    		Model model) {
    	int pageSize = 10; // 페이지 당 게시글 수
        int start = (pageNum - 1) * pageSize + 1;
        int end = pageNum * pageSize;
        
        List<PostVO> searchResults = service.searchPosts(searchType, searchValue, start, end);
        int count = service.getSearchCount(searchType, searchValue);
    	
        // 페이징 처리를 위한 계산
	    int currentPage = pageNum;
	    // 페이징을 위한 번호
	    int number = count - (currentPage - 1) * pageSize;
        

	    // 모델 바인딩
	    model.addAttribute("posts", searchResults);
	    model.addAttribute("number", number);
	    model.addAttribute("pageSize", pageSize);
	    model.addAttribute("count", count);
	    model.addAttribute("currentPage", currentPage);
	    
	    return "/post/BoardList";
	}
    
    
    //글 쓰기
    
    @GetMapping("/write")
    public String writeForm(Model model, HttpSession session, RedirectAttributes rttr) {
        // 로그인 상태 확인
        Long userId = (Long) session.getAttribute("user_id");
        if (userId == null) {
            // 로그인되어 있지 않은 경우 모달 메시지 추가
            rttr.addFlashAttribute("modalTitle", "로그인 필요");
            rttr.addFlashAttribute("modalMessage", "글을 작성하려면 로그인이 필요합니다.");
            return "redirect:/post/list"; // 게시글 목록 페이지로 리다이렉트
        }
        
        model.addAttribute("writeMode", true);
        return "/post/Detail";
    }

	@PostMapping("/write")
	public String write(PostVO post, @RequestParam(value = "file", required = false)MultipartFile file, HttpSession session) throws IOException {
	    log.info("글 작성 처리, file is " + (file != null ? "not null" : "null"));
	    
	    if (file != null && !file.isEmpty()) {
	        log.info("Processing file upload...");
	    }
	    
	    
	    // 세션에서 로그인한 사용자의 user_id를 가져옴
	    Long userId = (Long) session.getAttribute("user_id");
	    post.setUser_id(userId);
	    service.savePostWithFile(post, file);
	    return "redirect:/post/list";
	}

	// 글 수정
	@PostMapping("/update")
	public String update(PostVO post, @RequestParam(value = "file", required = false) MultipartFile file, HttpSession session) throws IOException {
	    log.info("게시글 수정 처리, file is " + (file != null ? "not null" : "null"));
	    
	    if (file != null && !file.isEmpty()) {
	        log.info("Processing file upload for update...");
	    }
	    
	    service.updatePostWithFile(post, file);
	    return "redirect:/post/detail?post_id=" + post.getPost_id();
	}
	
	@DeleteMapping("delete/{postId}")
	public ResponseEntity<String> deletePost(@PathVariable Long postId) {
	    // 삭제할 게시글 정보 조회
	    PostVO post = service.read(postId);

	    // 게시글에 첨부된 파일이 있는 경우 파일 삭제
	    if (post.getFile_uuid() != null && !post.getOriginalFileName().isEmpty()) {
	        String fileExtension = post.getOriginalFileName().substring(post.getOriginalFileName().lastIndexOf("."));
	        String filePath = "C:/upload/" + post.getFile_uuid() + fileExtension;
	        File file = new File(filePath);

	        if (file.exists()) {
	            if (file.delete()) {
	                log.info("파일 삭제 성공: " + filePath);
	            } else {
	                log.warn("파일 삭제 실패: " + filePath);
	            }
	        } else {
	            log.warn("삭제할 파일이 존재하지 않습니다: " + filePath);
	        }
	    }

	    // 게시글 삭제 로직 수행
	    service.delete(postId);

	    // 삭제 성공 시 응답
	    return ResponseEntity.ok("게시글과 첨부 파일이 삭제되었습니다.");
	}
	
	@GetMapping("/uploads/{file_uuid}")
	public void downloadFile(@PathVariable String file_uuid, @RequestParam("originalFileName") String originalFileName, HttpServletResponse response) throws IOException {
	    String uploadDir = "C:/upload/";
	    String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
	    String filePath = uploadDir + file_uuid + fileExtension;
	    File file = new File(filePath);

	    if (!file.exists()) {
	        log.warn("파일이 존재하지 않습니다.");
	        response.sendError(HttpServletResponse.SC_NOT_FOUND);
	        return;
	    }

	    response.setContentType("application/octet-stream");
	    response.setHeader("Content-Disposition", "attachment; filename=\"" + URLEncoder.encode(originalFileName, "UTF-8") + "\"");
	    response.setContentLength((int) file.length());

	    try (InputStream in = new FileInputStream(file); OutputStream out = response.getOutputStream()) {
	        byte[] buffer = new byte[4096];
	        int bytesRead;
	        while ((bytesRead = in.read(buffer)) != -1) {
	            out.write(buffer, 0, bytesRead);
	        }
	    } catch (Exception e) {
	        log.error("파일 전송 중 오류 발생", e);
	    }
	}
}
	
