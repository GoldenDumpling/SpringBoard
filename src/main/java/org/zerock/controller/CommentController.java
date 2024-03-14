package org.zerock.controller;

/*
	@RequestMapping("/api/comments")
	이 클래스 레벨의 어노테이션은 해당 컨트롤러의 모든 요청 매핑의 기본 경로를 설정합니다.
	"/api/comments"를 사용하는 이유는 RESTful API에서 리소스 중심의 URL 설계를 따르기 때문입니다.
	"/api"는 API 엔드포인트를 나타내는 일반적인 접두사로 사용되며, "/comments"는 댓글 리소스를 나타냅니다.
	
	@GetMapping("/{postId}")
	이 메서드 레벨의 어노테이션은 GET 요청을 처리하기 위한 매핑을 설정합니다.
	"/{postId}"는 URI 템플릿 변수를 사용하여 게시글 ID를 동적으로 받아옵니다.
	"postId"라는 변수명을 사용하는 이유는 자바의 명명 규칙인 camelCase를 따르기 때문입니다.
	
	@PathVariable
	이 어노테이션은 URI 템플릿 변수를 메서드 파라미터에 바인딩하는 데 사용됩니다.
	@PathVariable Long postId는 URI에서 "{postId}" 위치의 값을 Long 타입의 postId 파라미터에 바인딩합니다.
	
	@RequestBody
	이 어노테이션은 HTTP 요청의 본문(body)을 자바 객체로 변환하는 데 사용됩니다.
	@RequestBody CommentVO comment는 요청 본문의 JSON 데이터를 CommentVO 객체로 변환합니다.
	
	ResponseEntity
	이 클래스는 HTTP 응답을 나타내며, 응답 본문, 상태 코드, 헤더를 포함할 수 있습니다.
	new ResponseEntity<>(comments, HttpStatus.OK)는 comments 객체를 응답 본문으로 하고, HTTP 상태 코드를 200 OK로 설정하여 응답을 생성합니다.
	
	HttpStatus
	이 열거형(enum)은 HTTP 상태 코드를 나타냅니다.
	HttpStatus.CREATED는 201 Created 상태 코드를 나타내며, 새로운 리소스가 성공적으로 생성되었음을 의미합니다.
	HttpStatus.OK는 200 OK 상태 코드를 나타내며, 요청이 성공적으로 처리되었음을 의미합니다.
	HttpStatus.NOT_FOUND는 404 Not Found 상태 코드를 나타내며, 요청한 리소스가 존재하지 않음을 의미합니다.
*/
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zerock.domain.CommentVO;
import org.zerock.service.CommentService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@RestController
@Log4j
@RequestMapping("/api/comments")
@AllArgsConstructor
public class CommentController {

    private CommentService commentService;

    // 특정 게시글의 댓글 목록 조회
    @GetMapping("/{postId}")
    public ResponseEntity<List<CommentVO>> getListByPostId(@PathVariable Long postId) {
        List<CommentVO> comments = commentService.getListByPostId(postId);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    // 댓글 추가
    @PostMapping
    public ResponseEntity<String> addComment(@RequestBody CommentVO comment) {
        commentService.insert(comment);
        return new ResponseEntity<>("Comment added successfully", HttpStatus.CREATED);
    }

    // 댓글 수정
    @PutMapping("/{commentId}")
    public ResponseEntity<String> updateComment(@PathVariable Long commentId, @RequestBody CommentVO comment) {
        comment.setComment_id(commentId);
        int result = commentService.update(comment);
        if (result == 1) {
            return new ResponseEntity<>("Comment updated successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Comment not found", HttpStatus.NOT_FOUND);
        }
    }

    // 댓글 삭제
    @DeleteMapping("/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable Long commentId) {
        int result = commentService.delete(commentId);
        if (result == 1) {
            return new ResponseEntity<>("Comment deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Comment not found", HttpStatus.NOT_FOUND);
        }
    }
}