package org.zerock.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.zerock.domain.PostVO;

import lombok.extern.log4j.Log4j;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class PostMapperTests {

    @Autowired
    private PostMapper mapper;
    
    @Test
    public void testGetList() {
        List<PostVO> posts = mapper.getList();
        posts.forEach(post -> log.info(post));
    }
    
    @Test
    public void testGetPaginatedPosts() {
        int start = 1;
        int end = 10;
        List<PostVO> posts = mapper.getPaginatedPosts(start, end);
        log.info("페이지별 게시글 목록: " + posts);
    }
    @Test
    public void testGetTotalCount() {
    	log.info(mapper.getTotalCount());
    }
    
    @Test
    public void testInsertSelectKey() {
        PostVO post = new PostVO();
        post.setUser_id(3L); 
        post.setTitle("글 제목");
        post.setContent("글 내용");
        post.setFile_uuid(UUID.randomUUID().toString());
        post.setFile_type("image/png");
        mapper.insertSelectKey(post);
        log.info(post);
    }
    
    @Test
    public void testRead() {
        PostVO post = mapper.read(45L); 
        log.info(post);
    }
    
    @Test
    public void testUpdate() {
        PostVO post = new PostVO();
        post.setPost_id(127L); 
        post.setTitle("수정 글 제목");
        post.setContent("수정 글 내용");
        post.setFile_uuid(UUID.randomUUID().toString()); 
        post.setFile_type("image/jpeg"); 
        int count = mapper.update(post);
        log.info("Update Count: " + count);

        assertNotNull(count);
        assertTrue(count > 0); // 업데이트된 레코드 수가 0보다 큰지 확인합니다.
    }
    
    @Test
    public void testDelete() {
        int count = mapper.delete(3L); 
        log.info("Delete Count: " + count);
    }
    
    
    @Test
    public void testIncreseLikes() {
    		mapper.increaseLikes(41L);
    }
    @Test
    public void testGetLikes() {
    	Long likes = mapper.getLikes(41L); 
    	log.info("likes Count: " + likes);
    }
    
    @Test
    public void testSearch() {
    	
    	String searchType = "author"; // 검색 유형 (all, title, author)
    	String searchValue = "zig3"; // 검색어
    	int start = 1;
    	int end = 10;
    	

    	List<PostVO> boardList = mapper.searchPosts(searchType,searchValue,start,end);
    }
    
}
