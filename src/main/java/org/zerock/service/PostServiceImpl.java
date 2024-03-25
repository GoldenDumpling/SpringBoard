package org.zerock.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.zerock.domain.PostVO;
import org.zerock.mapper.PostMapper;

import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Log4j
@Service
@AllArgsConstructor
public class PostServiceImpl implements PostService {

    @Setter(onMethod_ = {@Autowired})
    private PostMapper mapper;
    
    
    
    
    @Override
    public List<PostVO> getList() {
        log.info("게시글 목록을 조회합니다.");
        return mapper.getList();
    }

    @Override
    public void insertSelectKey(PostVO post) {
        log.info("게시글을 등록합니다: {}" + post);
        mapper.insertSelectKey(post);
    }

    @Override
    public PostVO read(Long post_id) {
        log.info("게시글(ID: {})을 조회합니다." + post_id);
        return mapper.read(post_id);
    }

    @Override
    public int delete(Long post_id) {
        log.info("게시글(ID: {})을 삭제합니다." + post_id);
        return mapper.delete(post_id);
    }

    @Override
    public int update(PostVO post) {
        log.info("게시글을 수정합니다: {}" + post);
        return mapper.update(post);
    }
    
    @Override
    public int getTotalCount() {
        log.info("게시글 총 수를 조회합니다.");
        return mapper.getTotalCount();
    }

    @Override
    public List<PostVO> getPaginatedPosts(int start, int end) {
        log.info("페이지별 게시글 목록을 조회합니다. 시작: " + start + ", 끝: " + end);
        return mapper.getPaginatedPosts(start, end);
    }
    
    @Override
	public void increaseLikes(Long post_id) {
	    log.info("게시글 번호 " + post_id + "의 추천수를 증가시킵니다.");
	    mapper.increaseLikes(post_id);
    }
    
    @Override
	public Long getLikes(Long post_id) {
	    log.info("게시글 번호 " + post_id + "의 추천수를 조회합니다.");
	    return mapper.getLikes(post_id);
    }
    
    @Override
    public void increaseReadCount(Long post_id) {
    	log.info("게시글 번호 " + post_id + "의 조회수를 증가합니다.");
        mapper.increaseReadCount(post_id);
    }
    
    @Override
    public List<PostVO> searchPosts(String searchType, String searchValue, int start, int end) {
    	log.info("검색한 페이지별 게시글 목록을 조회합니다. 시작: " + start + ", 끝: " + end);
        return mapper.searchPosts(searchType, searchValue, start, end);
    }
    
    @Override
    public int getSearchCount(String searchType, String searchValue) {
        return mapper.getSearchCount(searchType, searchValue);
    }
    
    @Override
    public void savePostWithFile(PostVO post, MultipartFile file) throws IOException {
        log.info("Starting to save post with file, file is " + (file != null ? "not null" : "null"));
        log.info(file);
        if (file != null && !file.isEmpty()) {
            String originalFileName = file.getOriginalFilename();
            String fileUuid = UUID.randomUUID().toString();
            String fileExtension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
            String fileName = fileUuid + fileExtension;
            
            // 실제 서버 내 파일 저장 위치 얻기
            String uploadDir = "C:/upload/";
            // 업로드 폴더가 존재하지 않으면 생성
            
            File uploadDirFile = new File(uploadDir);
            if (!uploadDirFile.exists()) {
                uploadDirFile.mkdirs();
            }
            
            File saveFile = new File(uploadDir, fileName);

            log.info("File original name: " + file.getOriginalFilename());
            log.info("File size: " + file.getSize());
            log.info("File content type: " + file.getContentType());
            log.info("Saving file to: " + saveFile.getAbsolutePath());

            file.transferTo(saveFile);
            log.info("File saved successfully");

            post.setOriginalFileName(originalFileName);
            post.setFile_uuid(fileUuid);
            post.setFile_type(file.getContentType());
        } else {
            log.warn("No file to save.");
            post.setFile_uuid(""); // 파일이 없는 경우 file_uuid를 빈 문자열로 설정
            post.setFile_type(""); // 파일이 없는 경우 file_type을 빈 문자열로 설정
            post.setOriginalFileName("");
        }

        insertSelectKey(post);
    }


    @Override
    public void updatePostWithFile(PostVO post, MultipartFile file) throws IOException {
        log.info("Updating post with file, file is " + (file != null ? "not null" : "null"));
        log.info("post.id : " + post.getPost_id());
        
        PostVO existingPost = read(post.getPost_id()); // 기존 게시물 데이터를 불러옵니다.

        if (file != null && !file.isEmpty()) {
            String uploadDir = "C:/upload/";
            
            // 기존 파일이 존재하는 경우 삭제
            if (existingPost.getFile_uuid() != null && !existingPost.getFile_uuid().isEmpty()) {
                String existingFilePath = uploadDir + existingPost.getFile_uuid() + existingPost.getOriginalFileName().substring(existingPost.getOriginalFileName().lastIndexOf("."));
                File existingFile = new File(existingFilePath);
                if (existingFile.exists()) {
                    if (existingFile.delete()) {
                        log.info("Existing file deleted successfully: " + existingFilePath);
                    } else {
                        log.warn("Failed to delete existing file: " + existingFilePath);
                    }
                }
            }

            // 새 파일 저장
            String originalFileName = file.getOriginalFilename();
            String fileUuid = UUID.randomUUID().toString();
            String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
            String fileName = fileUuid + fileExtension;
            File saveFile = new File(uploadDir, fileName);
            file.transferTo(saveFile);

            post.setOriginalFileName(originalFileName);
            post.setFile_uuid(fileUuid);
            post.setFile_type(file.getContentType());
        } else {
            // 파일이 없는 경우 기존 파일 정보를 유지
            post.setOriginalFileName(existingPost.getOriginalFileName());
            post.setFile_uuid(existingPost.getFile_uuid());
            post.setFile_type(existingPost.getFile_type());
        }

        update(post); // 게시글 정보를 업데이트합니다.
    }


    
    
    
}
