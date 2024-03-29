// 드롭다운 메뉴 열기
function toggleDropdown(event, dropdownId) {
    event.preventDefault();
    var dropdown = document.getElementById(dropdownId);
    dropdown.style.display = (dropdown.style.display === 'block') ? 'none' : 'block';
}

// 드롭다운 메뉴 외부를 클릭하면 드롭다운 메뉴 닫기
document.addEventListener('click', function(event) {
    var dropdowns = document.getElementsByClassName('dropdown-menu');
    for (var i = 0; i < dropdowns.length; i++) {
        var dropdown = dropdowns[i];
        if (dropdown.style.display === 'block' && !dropdown.contains(event.target) && !event.target.classList.contains('dot_img')) {
            dropdown.style.display = 'none';
        }
    }
});

// 댓글 등록 버튼
var addCommentBtn = document.getElementById('addCommentBtn');
if (addCommentBtn) {
    addCommentBtn.addEventListener('click', function() {
        var commentContent = document.getElementById('newCommentContent').value;
        if (commentContent.trim() !== '') {
            addComment(commentContent, postId, userId, username);
        }
    });
}
// 댓글 등록 함수
function addComment(commentContent, postId, userId, username) {
    var comment = {
        post_id: postId,
        user_id: userId,
        username: username,
        content: commentContent
    };

    fetch('/api/comments', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(comment)
    })
    .then(function(response) {
        if (response.ok) {
            // 댓글 추가 성공 시 댓글 목록 업데이트
            getCommentList(postId);
            document.getElementById('newCommentContent').value = '';
        } else {
            // 댓글 추가 실패 시 에러 처리
            console.error('댓글 추가 실패');
        }
    })
    .catch(function(error) {
        console.error('댓글 추가 중 에러 발생:', error);
    });
}

// 댓글 요청
function getCommentList(postId) {
    fetch('/api/comments/' + postId)
    .then(function(response) {
        if (response.ok) {
            return response.json();
        } else {
            throw new Error('댓글 목록 가져오기 실패');
        }
    })
    .then(function(comments) {
        updateCommentList(comments);
    })
    .catch(function(error) {
        console.error('댓글 목록 가져오기 중 에러 발생:', error);
    });
}

// 댓글 목록 다시 보여주기
function updateCommentList(comments) {
    var commentListContainer = document.querySelector('tbody');
    commentListContainer.innerHTML = ''; // 기존 댓글 목록 초기화

    comments.forEach(function(comment) {
        var userClass = sessionUsername === username ? 'user_own_comment' : '';
        var createdAt = new Date(comment.created_at);
        var formattedDate = createdAt.getFullYear() + '-' +
                            ('0' + (createdAt.getMonth() + 1)).slice(-2) + '-' +
                            ('0' + createdAt.getDate()).slice(-2) + ' ' +
                            ('0' + createdAt.getHours()).slice(-2) + ':' +
                            ('0' + createdAt.getMinutes()).slice(-2) + ':' +
                            ('0' + createdAt.getSeconds()).slice(-2);

        var commentHtml = `
            <tr class="user_info_wrap flex_wrap_between ${sessionUsername === comment.username ? 'user_own_comment' : ''}">
                <td>
                    <div>
                        <img src="/resources/images/star_icon.png" alt="유저 아이콘" class="star_icon"/>${comment.username}
                    </div>
                </td>
                <td>${formattedDate}</td>
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
                    </div>
                </td>
            </tr>
        `;
        commentListContainer.insertAdjacentHTML('beforeend', commentHtml);
    });
}

// 원래의 댓글 내용을 저장할 변수
var originalCommentText = '';

// 댓글 수정 버튼 클릭 이벤트
document.addEventListener('click', function(event) {
    if (event.target.classList.contains('modifyComment')) {
        event.preventDefault();
        
		var commentRow = event.target.closest('tr').previousElementSibling.previousElementSibling;
        var commentUsername = commentRow.querySelector('td:first-child div').textContent.trim();
        
        if (commentUsername === sessionUsername) {
            var commentRow = event.target.closest('tr').previousElementSibling;
	        var commentContent = commentRow.querySelector('.comment_content');
	        var commentText = commentContent.textContent.trim();
	        
	    	var commentId = event.target.closest('.dropdown-menu').id.replace('dropdownMenu', '');
	        commentRow.setAttribute('data-comment-id', commentId);
	        console.log(commentId);
	
	        // 원래 내용을 숨기고 수정용 textarea를 추가
	        commentContent.style.display = 'none';
	        var textAreaHtml = `
	            <textarea class="modify_textarea" rows="3" style="width: 100%; resize: none;">${commentText}</textarea>
	        `;
	        commentRow.insertAdjacentHTML('beforeend', textAreaHtml);
	
	        var buttonWrap = event.target.closest('.button_wrap');
	        buttonWrap.innerHTML = `
	            <button type="button" class="btn btn-primary btn-sm confirmModify">등록</button>
	            <button type="button" class="btn btn-secondary btn-sm cancelModify">취소</button>
	        `;
        } else {
            showModal("권한 없음", "본인이 작성한 댓글만 수정할 수 있습니다.");
        }
        
    }
});

// 댓글 삭제 버튼 클릭 이벤트
document.addEventListener('click', function(event) {
    if (event.target.classList.contains('deleteComment')) {
        event.preventDefault();

        var commentRow = event.target.closest('tr').previousElementSibling.previousElementSibling;
        console.log(commentRow);
        var commentUsername = commentRow.querySelector('td:first-child div').textContent.trim();
		console.log(commentUsername);
        if (commentUsername === sessionUsername) {
            var commentId = event.target.closest('.dropdown-menu').id.replace('dropdownMenu', '');
            deleteComment(commentId);
        } else {
            showModal("권한 없음", "본인이 작성한 댓글만 삭제할 수 있습니다.");
        }
    }
});

function deleteComment(commentId) {
    fetch('/api/comments/' + commentId, {
        method: 'DELETE'
    })
    .then(function(response) {
        if (response.ok) {
            // 댓글 삭제 성공 시 댓글 목록 업데이트
            getCommentList(postId);
        } else {
            // 댓글 삭제 실패 시 에러 처리
            console.error('댓글 삭제 실패');
        }
    })
    .catch(function(error) {
        console.error('댓글 삭제 중 에러 발생:', error);
    });
}









// 댓글 수정 등록 버튼 클릭 이벤트 처리
document.addEventListener('click', function(event) {
    if (event.target.classList.contains('confirmModify')) {
        var commentRow = event.target.closest('tr').previousElementSibling;
        var commentContent = commentRow.querySelector('.comment_content');
        var modifiedText = commentRow.querySelector('.modify_textarea').value;


        // 댓글 ID 가져오기
        var commentId = commentRow.getAttribute('data-comment-id');
        updateComment(commentId, modifiedText);
    }
});

function updateComment(commentId, modifiedText) {
    var comment = {
        content: modifiedText
    };

    fetch('/api/comments/' + commentId, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(comment)
    })
    .then(function(response) {
        if (response.ok) {
            // 댓글 수정 성공 시 댓글 목록 업데이트
            getCommentList(postId);
        } else {
            // 댓글 수정 실패 시 에러 처리
            console.error('댓글 수정 실패');
        }
    })
    .catch(function(error) {
        console.error('댓글 수정 중 에러 발생:', error);
    });
}


// 댓글 취소 버튼 클릭 이벤트
document.addEventListener('click', function(event) {
    if (event.target.classList.contains('cancelModify')) {
        event.preventDefault();

        var commentRow = event.target.closest('tr').previousElementSibling;
        console.log("commentRow:", commentRow);
        var commentContent = commentRow.querySelector('.comment_content');
        console.log("취소 버튼 commentContent " + commentContent);
        var textarea = commentRow.querySelector('.modify_textarea');

        // textarea 제거하고 원본 내용을 다시 표시
        textarea.remove();
        commentContent.style.display = '';

        var buttonWrap = event.target.closest('.button_wrap');
        buttonWrap.innerHTML = `
            <a href="#" onclick="toggleDropdown(event, 'dropdownMenu')">
                <img src="/resources/images/ellipsis.png" alt="점이미지" class="dot_img"/>
            </a>
            <ul id="dropdownMenu" class="dropdown-menu">
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
        `;
    }
});



// 추천 버튼 클릭 이벤트 핸들러
document.querySelector('.like-btn').addEventListener('click', function() {
    const url = '/post/' + postId + '/like'; // 요청 URL

    // AJAX 요청 설정
    const fetchOptions = {
        method: 'POST'
    };

    // AJAX 요청 실행
    fetch(url, fetchOptions)
    .then(function(response) {
        if (response.ok) {
            return response.json();
        } else {
            throw new Error('추천 실패');
        }
    })
    .then(function(data) {
        // 추천 수 업데이트
        document.getElementById('likeCount').textContent = data.likes;
    })
    .catch(function(error) {
    });


});