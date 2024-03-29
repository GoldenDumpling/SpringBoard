<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<c:choose>
	    <c:when test="${editMode}">
			<title>게시글 수정하기</title>
		</c:when>
		<c:when test="${writeMode}"> <!-- writeMode 분기 추가 -->
			<title>게시글 작성하기</title>
		</c:when>
		<c:otherwise>
			<title>게시글 상세보기</title>
		</c:otherwise>
</c:choose>
<!-- 부트스트랩 -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
<!-- 폰트어썸 -->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
<!-- CKEditor  -->
 <script src="https://cdn.ckeditor.com/ckeditor5/41.2.1/super-build/ckeditor.js"></script>
<style>
.container {
    max-width: 960px;
    padding: 15px;
    padding-top: 50px;
}

/* 추천 버튼 애니메이션 */
.like-btn {
    width: 50px;
    transition: transform 0.2s;
}

.like-btn img {
    width: 100%;
}

.like-btn:active {
    transform: scale(1.2);
}

.post-info {
    display: flex;
    justify-content: space-between;
    border-bottom: 1px solid #ccc;
    padding-bottom: 10px;
    margin-bottom: 20px;
    border-top: rgb(46, 67, 97) solid 2px;
    background-color: rgb(241, 247, 255);
    padding: 5px;
    border-radius: 5px;
}

.star_icon {
    width: 20px;
    margin-right: 5px;
    margin-bottom: 3px;
}

.comment-icon {
    width: 24px;
    height: 24px;
    margin-right: 5px;
    margin-bottom: 2px;
}

.speech_span {
    font-weight: 700;
}

.context_container {
    min-height: 300px;
    
}
.context_container img{
	max-width: 100% !important; 
	height: auto !important;
}

.button-container {
    display: flex;
    justify-content: center;
    position: relative;
    margin-top: 20px;
    top: -15px;
}

.like-btn-container {
    position: absolute;
    left: 50%;
    transform: translateX(-50%);
}

.button-group {
    position: absolute;
    right: 0;
}

.button-group .btn {
    margin-left: 5px;
}
.speeach_tap{
	margin-top: 50px;
	margin-bottom: 10px;
}
.flex_wrap_between{
	display: flex;
	justify-content: space-between;
	
}

.star_icon {
    width: 20px;
    margin-right: 5px;
}

.button_wrap {
    text-align: right;
    position: relative;
}

.button_wrap .btn {
    margin-left: 5px;
}
.user_info_wrap {
    background-color: #e8e8e8;
    border-bottom: 1px solid #ccc;
    padding: 5px;
    border-radius: 5px;
}
.user_info_wrap td{
	background-color: #e8e8e8;
	border-bottom-width: 0;
	padding: 0;
	
}
.container .table tbody tr, .container .table tbody tr td{
	border-bottom-width: 0;
}
.container .table tfoot tr, .container .table tfoot tr td{
	border-bottom-width: 0;
}
.dot_img{
	width: 14px;
	height: 14px;
}
.dropdown-menu {
    border: none;
    display: none;
    position: absolute;
    min-width: 120px;
    box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.2);
    z-index: 1;
    animation: fadeIn 0.3s;
    border-radius: 0;
    padding: 5px;
    right: 0;
    bottom: 5px;
}

@keyframes fadeIn {
    from {
        opacity: 0;
        transform: translateY(-10px);
    }
    to {
        opacity: 1;
        transform: translateY(0);
    }
}

.dropdown-menu a {
    color: black;
    text-decoration: none;
}

.dropdown-menu a:hover {
    text-decoration: none;
}

.dropdown-menu .ed.icon i {
    color: black;
}

.trash_icon{
	margin-left: 1px;
	margin-right: 3px;
}
#newCommentContent {
    border: 1px solid #ccc;
    padding: 5px;
    border-radius: 5px;
}

#newCommentContent:focus {
    outline: none;
    border-color: #007bff;
    box-shadow: 0 0 0 0.2rem rgba(0, 123, 255, 0.25);
}
.user_own_comment, .user_own_comment td{
	background-color: #deeeff;
}
#likeCount{
	font-weight: 700;
	font-size: 18px;
	margin-right: 5px;
}
/* modal */
.modal {
       display: none;
       position: fixed;
       z-index: 100;
       left: 0;
       top: 0;
       width: 100%;
       height: 100%;
       overflow: auto;
       background-color: rgba(0, 0, 0, 0.4);
   }

   .modal-content {
       background-color: #fefefe;
       margin: 15% auto;
       padding: 20px;
       border: 1px solid #888;
       width: 300px;
       text-align: center;
   }

   .close {
       color: #aaa;
       float: right;
       font-size: 28px;
       font-weight: bold;
       cursor: pointer;
   }

   .close:hover,
   .close:focus {
       color: black;
       text-decoration: none;
       cursor: pointer;
   }
      .modal-button {
          background-color: #4CAF50;
          color: #fff;
          padding: 10px 20px;
          border: none;
          border-radius: 4px;
          cursor: pointer;
          font-size: 16px;
          margin-top: 10px;
      }

      .modal-button:hover {
          background-color: #45a049;
      }
      .cke_contents {
        height: 600px !important; /* 원하는 높이 값으로 변경 */
      }
      .comment_content {
	    white-space: pre-wrap; /* 공백, 줄바꿈을 그대로 유지 */
	  }
	  
	  /* 대체 되는 CK Editor 5버전 */
	  .ck-editor__editable {
	    min-height: 300px;
	  }
  
</style>
</head>
<body>
<div class="container">
	<c:choose>
	    <c:when test="${editMode or writeMode}">
	        <form id="postForm" method="post" action="/post/${editMode ? 'update' : 'write'}" enctype="multipart/form-data">
   	            <c:if test="${editMode}">
	                <input type="hidden" name="post_id" value="${post.post_id}">
	            </c:if>
	            <div class="mb-3">
	                <label for="title" class="form-label"></label>
	                <input type="text" class="form-control" id="title" name="title" value="${post.title}" required>
	            </div>
	            <div class="mb-3">
	                <label for="editor" class="form-label"></label>
	                <textarea class="form-control" id="editor" name="content" rows="10">${post.content}</textarea>
	            </div>
				<div class="mb-3">
				    <label for="file" class="form-label">사진 및 파일첨부</label>
				    <input type="file" class="form-control" id="file" name="file">
				    <c:if test="${editMode && post.file_uuid != null && post.originalFileName != null}">
				        <div class="mt-2">
				            <span>현재 첨부된 파일: </span>
				            <a href="/post/uploads/${post.file_uuid}?originalFileName=${post.originalFileName}" download="${post.originalFileName}">${post.originalFileName}</a>
				        </div>
				    </c:if>
				</div>
	        </form>
	    </c:when>
	    <c:otherwise>
	        <h3>${post.title}</h3>
	        <div class="post-info">
	            <span><img src="/resources/images/star_icon.png" alt="유저_아이콘" class="star_icon"/>${post.username}</span>
	            <span><fmt:formatDate value="${post.created_at}" pattern="yyyy-MM-dd HH:mm:ss" /></span>
	        </div>
	        <div class="context_container">
	            <p>${post.content}</p>
	        </div>
			<c:if test="${post.file_uuid != null and post.originalFileName != null}"> <!-- 파일이 존재하는지 확인 -->
			    <div class="file-download">
			        <a href="/post/uploads/${post.file_uuid}?originalFileName=${post.originalFileName}" download="${post.originalFileName}">${post.originalFileName}</a>
			    </div>
			</c:if>
	    </c:otherwise>
	</c:choose>

    <div class="button-container">
        <c:if test="${not (editMode or writeMode)}"> <!-- 상세보기 모드에서만 좋아요 버튼 표시 -->
            <div class="like-btn-container">
                <span id="likeCount">${post.likes}</span>
                <button type="button" class="btn btn-primary like-btn">
                    <img src="/resources/images/like_button.png" alt="추천 아이콘" />
                </button>
            </div>
        </c:if>
		<div class="button-group">
		    <button type="button" class="btn btn-primary btn-sm" onclick="window.location.href='/post/list'">목록</button>
		    <c:choose>
		        <c:when test="${editMode or writeMode}">
		            <button type="submit" form="postForm" class="btn btn-secondary btn-sm">${editMode ? '수정' : '등록'}</button>
		            <c:if test="${editMode}"> <!-- editMode일 때만 취소 버튼 표시 -->
		                <button type="button" class="btn btn-danger btn-sm" onclick="cancelEdit()">취소</button>
		            </c:if>
		        </c:when>
				<c:otherwise>
				    <button type="button" class="btn btn-secondary btn-sm" onclick="editPost()">수정</button>
				    <button type="button" class="btn btn-danger btn-sm" onclick="deletePost(${post.post_id})">삭제</button>
				</c:otherwise>
		    </c:choose>
		</div>
    </div>
    <c:if test="${not (editMode or writeMode)}"> <!-- 상세보기 모드에서만 댓글 표시 -->
               <div class="speeach_tap">
            <img src="/resources/images/speech_icon.png" alt="댓글 아이콘" class="comment-icon">
            <span class="speech_span">댓글</span>
        </div>
        <table class="table" style="width: 100%;">
            <tbody>
                <c:if test="${not empty comments}">
                    <c:forEach var="comment" items="${comments}">
                        <tr class="user_info_wrap flex_wrap_between ${sessionScope.username eq comment.username ? 'user_own_comment' : ''}">
                            <td>
                                <div>
                                    <img src="/resources/images/star_icon.png" alt="유저_아이콘" class="star_icon"/>${comment.username}
                                </div>
                            </td>
                            <td>
                                <fmt:formatDate value="${comment.created_at}" pattern="yyyy-MM-dd HH:mm:ss" />
                            </td>
                        </tr>
                        <tr>
                            <td colspan="2" class="comment_content">${comment.content}</td>
                        </tr>
                        <tr>
                            <td colspan="2">
                                <div class="button_wrap">
                                    <a href="#" onclick="toggleDropdown(event, 'dropdownMenu${comment.comment_id}')">
                                        <img src="/resources/images/ellipsis.png" alt="점이미지" class="dot_img"/>
                                    </a>
                                    <c:if test="${not empty username}">
                                        <ul id="dropdownMenu${comment.comment_id}" class="dropdown-menu">
                                            <li>
                                                <a class="modifyComment" href="#">
                                                    <span class="ed icon"><i class="fas fa-edit"></i></span>
                                                    수정
                                                </a>
                                            </li>
                                            <li>
                                                <a class="deleteComment" href="#">
                                                    <span class="ed icon trash_icon"><i class="fas fa-trash"></i></span>
                                                    삭제
                                                </a>
                                            </li>
                                        </ul>
                                    </c:if>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                </c:if>
            </tbody>
            <tfoot>
                <c:if test="${not empty username}">
                    <tr>
                        <td colspan="2">
                            <textarea class="comment_content" id="newCommentContent" rows="3" style="width: 100%; resize: none;"></textarea>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2" style="text-align: right;">
                            <button type="button" class="btn btn-primary btn-sm" id="addCommentBtn">등록</button>
                        </td>
                    </tr>
                </c:if>
            </tfoot>
        </table>
    </c:if>
    <!-- 수정: 모달창 코드 수정 -->
    <div id="errorModal" class="modal">
        <div class="modal-content">
            <h3 id="modalTitle"></h3>
            <p id="modalMessage"></p>
            <button class="modal-button" onclick="closeModal()">확인</button>
        </div>
    </div>
    
</div>
<script>
    var postId = '${post.post_id}';
    var username = '${sessionScope.username}';
    var userId = '${sessionScope.user_id}';
    var sessionUsername = '${sessionScope.username}';
    
    // 수정: editPost 함수 추가
    function editPost() {
        if (!username) {
            showModal("로그인 필요", "로그인이 필요한 기능입니다.");
        } else if (username !== '${post.username}') {
            showModal("권한 없음", "해당 글의 작성자만 수정할 수 있습니다.");
        } else {
            toggleEditMode();
        }
    }
    
	// 삭제
	function deletePost(postId) {
	if (!username) {
	    showModal("로그인 필요", "로그인이 필요한 기능입니다.");
	  } else if (username !== '${post.username}') {
	    showModal("권한 없음", "해당 글의 작성자만 삭제할 수 있습니다.");
	  } else {
	    fetch(`/post/delete/\${postId}`, {
	        method: 'DELETE',
	        headers: {
	            'Content-Type': 'application/json'
	        }
	    })
	    .then(response => {
	        if (!response.ok) {
	            throw new Error('게시글 삭제에 실패했습니다.');
	        }
	        // 삭제 성공 시 리다이렉트 또는 적절한 처리
	        window.location.href = '/post/list';
	    })
	    .catch(error => {
	        console.error('Error:', error);
	        // 오류 발생 시 적절한 처리
	    });
	 }
}


    // 수정모드로
    function toggleEditMode() {
        window.location.href = '/post/detail?post_id=' + postId + '&editMode=true';
    }

    // 취소모드로
    function cancelEdit() {
        window.location.href = '/post/detail?post_id=' + postId;
    }

 	// 수정: showModal 함수 수정
    function showModal(title, message) {
        document.getElementById("modalTitle").innerText = title;
        document.getElementById("modalMessage").innerText = message;
        document.getElementById("errorModal").style.display = "block";
    }

    // 모달창 닫기 함수
    function closeModal() {
        document.getElementById("errorModal").style.display = "none";
    }
    
	/* CK에디터  */
	let editor; 
	CKEDITOR.ClassicEditor.create(document.getElementById("editor"), {
          // https://ckeditor.com/docs/ckeditor5/latest/features/toolbar/toolbar.html#extended-toolbar-configuration-format
          toolbar: {	
              items: [
                  'heading', '|',
                  'bold', 'italic', 'strikethrough', 'underline', 'code', 'subscript', 'superscript', 'removeFormat', '|',
                  'bulletedList', 'numberedList', 'todoList', '|',
                  'outdent', 'indent', '|',
                  'undo', 'redo',
                  '-',
                  'fontSize', 'fontFamily', 'fontColor', 'fontBackgroundColor', 'highlight', '|',
                  'alignment', '|',
                  'link', 'uploadImage', 'blockQuote', 'insertTable', 'mediaEmbed', 'codeBlock','|',
                  'specialCharacters', 'horizontalLine', 'pageBreak', '|',
                  'sourceEditing',                 
                  '|','htmlEmbed'
              ],
              shouldNotGroupWhenFull: true
          },
          list: {
              properties: {
                  styles: true,
                  startIndex: true,
                  reversed: true
              }
          },
          // https://ckeditor.com/docs/ckeditor5/latest/features/headings.html#configuration
          heading: {
              options: [
                  { model: 'paragraph', title: 'Paragraph', class: 'ck-heading_paragraph' },
                  { model: 'heading1', view: 'h1', title: 'Heading 1', class: 'ck-heading_heading1' },
                  { model: 'heading2', view: 'h2', title: 'Heading 2', class: 'ck-heading_heading2' },
                  { model: 'heading3', view: 'h3', title: 'Heading 3', class: 'ck-heading_heading3' },
                  { model: 'heading4', view: 'h4', title: 'Heading 4', class: 'ck-heading_heading4' },
                  { model: 'heading5', view: 'h5', title: 'Heading 5', class: 'ck-heading_heading5' },
                  { model: 'heading6', view: 'h6', title: 'Heading 6', class: 'ck-heading_heading6' }
              ]
          },
          // https://ckeditor.com/docs/ckeditor5/latest/features/editor-placeholder.html#using-the-editor-configuration
          placeholder: 'Welcome to CKEditor 5!',
          // https://ckeditor.com/docs/ckeditor5/latest/features/font.html#configuring-the-font-family-feature
          fontFamily: {
              options: [
                  'default',
                  'Arial, Helvetica, sans-serif',
                  'Courier New, Courier, monospace',
                  'Georgia, serif',
                  'Lucida Sans Unicode, Lucida Grande, sans-serif',
                  'Tahoma, Geneva, sans-serif',
                  'Times New Roman, Times, serif',
                  'Trebuchet MS, Helvetica, sans-serif',
                  'Verdana, Geneva, sans-serif'
              ],
              supportAllValues: true
          },
          // https://ckeditor.com/docs/ckeditor5/latest/features/font.html#configuring-the-font-size-feature
          fontSize: {
              options: [ 10, 12, 14, 'default', 18, 20, 22 ],
              supportAllValues: true
          },
          // Be careful with the setting below. It instructs CKEditor to accept ALL HTML markup.
          // https://ckeditor.com/docs/ckeditor5/latest/features/general-html-support.html#enabling-all-html-features
          htmlSupport: {
              allow: [
                  {
                      name: /.*/,
                      attributes: true,
                      classes: true,
                      styles: true
                  }
              ]
          },
          // Be careful with enabling previews
          // https://ckeditor.com/docs/ckeditor5/latest/features/html-embed.html#content-previews
          htmlEmbed: {
              showPreviews: true
          },
          // https://ckeditor.com/docs/ckeditor5/latest/features/link.html#custom-link-attributes-decorators
          link: {
              decorators: {
                  addTargetToExternalLinks: true,
                  defaultProtocol: 'https://',
                  toggleDownloadable: {
                      mode: 'manual',
                      label: 'Downloadable',
                      attributes: {
                          download: 'file'
                      }
                  }
              }
          },
          // https://ckeditor.com/docs/ckeditor5/latest/features/mentions.html#configuration
          mention: {
              feeds: [
                  {
                      marker: '@',
                      feed: [
                          '@apple', '@bears', '@brownie', '@cake', '@cake', '@candy', '@canes', '@chocolate', '@cookie', '@cotton', '@cream',
                          '@cupcake', '@danish', '@donut', '@dragée', '@fruitcake', '@gingerbread', '@gummi', '@ice', '@jelly-o',
                          '@liquorice', '@macaroon', '@marzipan', '@oat', '@pie', '@plum', '@pudding', '@sesame', '@snaps', '@soufflé',
                          '@sugar', '@sweet', '@topping', '@wafer'
                      ],
                      minimumCharacters: 1
                  }
              ]
          },
          // The "superbuild" contains more premium features that require additional configuration, disable them below.
          // Do not turn them on unless you read the documentation and know how to configure them and setup the editor.
          removePlugins: [
              // These two are commercial, but you can try them out without registering to a trial.
              'ExportPdf',
              'ExportWord',
              'AIAssistant',
              'CKBox',
              'CKFinder',
              'EasyImage',
              // This sample uses the Base64UploadAdapter to handle image uploads as it requires no configuration.
              // https://ckeditor.com/docs/ckeditor5/latest/features/images/image-upload/base64-upload-adapter.html
              // Storing images as Base64 is usually a very bad idea.
              // Replace it on production website with other solutions:
              // https://ckeditor.com/docs/ckeditor5/latest/features/images/image-upload/image-upload.html
              // 'Base64UploadAdapter',
              'RealTimeCollaborativeComments',
              'RealTimeCollaborativeTrackChanges',
              'RealTimeCollaborativeRevisionHistory',
              'PresenceList',
              'Comments',
              'TrackChanges',
              'TrackChangesData',
              'RevisionHistory',
              'Pagination',
              'WProofreader',
              // Careful, with the Mathtype plugin CKEditor will not load when loading this sample
              // from a local file system (file://) - load this site via HTTP server if you enable MathType.
              'MathType',
              // The following features are part of the Productivity Pack and require additional license.
              'SlashCommand',
              'Template',
              'DocumentOutline',
              'FormatPainter',
              'TableOfContents',
              'PasteFromOfficeEnhanced',
              'CaseChange'
          ]
      })
      .then(newEditor => {
          editor = newEditor; // 에디터 인스턴스를 전역 변수에 저장
      })
      .catch(error => {
          console.error('Error initializing CKEditor:', error);
      });

      
      ;
	// 폼 제출 시 에디터 내용 가져오기
	document.querySelector('#postForm').addEventListener('submit', function (event) {
	    event.preventDefault();
	    const content = editor.getData().trim();
	
	    if (content === '') {
	        alert('내용을 입력해주세요.');
	        editor.editing.view.focus(); // 에디터에 포커스 설정
	    } else {
	        this.querySelector('#editor').value = content;
	        this.submit();
	    }
	});
</script>
<script src="/resources/js/Detail.js"></script>
</body>
</html>