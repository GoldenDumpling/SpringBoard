package org.zerock.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.zerock.domain.CommentVO;

import lombok.extern.log4j.Log4j;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class CommentMapperTests {

    @Autowired
    private CommentMapper mapper;
    
    @Test
    public void testGetListByPostId() {
        List<CommentVO> comments = mapper.getListByPostId(3L); // Assume 1L as an existing post ID
        comments.forEach(comment -> log.info(comment));
    }
    
    @Test
    public void testInsert() {
        CommentVO comment = new CommentVO();
        comment.setPost_id(6L); 
        comment.setUser_id(1L); 
        comment.setContent("댓글 내용");
        mapper.insert(comment);
        log.info(comment);
    }
    
    @Test
    public void testUpdate() {
        CommentVO comment = new CommentVO();
        comment.setComment_id(4L); // Assume 1L as an existing comment ID to update
        comment.setContent("수정 댓글 내용");
        mapper.update(comment);
        log.info(comment);
    }
    
    @Test
    public void testDelete() {
        int count = mapper.delete(4L); // Assume 1L as an existing comment ID to delete
        log.info("Delete Count: " + count);
    }
}
