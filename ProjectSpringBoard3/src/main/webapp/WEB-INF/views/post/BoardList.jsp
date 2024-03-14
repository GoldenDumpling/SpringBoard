<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix = "c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>



<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>전체 게시판 목록</title>
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
	<style>
	   table, th, td {
	        border: none !important; /* 모든 테이블 셀의 외곽선 제거 */
	    }
		td,th {
			text-align: center;

			border-bottom: 1px solid #ccc !important; 
		
		}
		#board_wrap{
			margin: 0 auto;
			max-width: 900px;
		}
		
		#board_wrap .table .top_row th {
		    color: #fff !important;
		   	background-color: #0072f9 !important;
		}
		.button {
			width: 90%;
			margin: auto 0;
			
		}
		.flex{
			display: flex;

		}
		.flex-flow-column{
			flex-flow: column wrap;
			margin: 10px;
		}

		.login_wrap{
			width: 200px;
			border: 1px solid #ccc;
			border-radius: 5px;
			margin: 5px;
			margin-left: auto; /* 왼쪽 마진을 auto로 설정하여 오른쪽으로 밀어냄 */
			margin-bottom: 40px;
		}
		h1{
			font-size: 30px;
			font-weight: 700;
			text-align: center;
			margin-top: 5vh;
		}
		.pagenav{
			display: flex;
			justify-content: center;
			margin-top: 50px;
			
		}
		.search_wrap{
			justify-content: space-between;
		}
		.title_col{
			text-align: left;

		}
		.user_name_em{
			font-size: 24px;	
			font-weight: 700;	
			text-align: center;
		}
		
		/* modal */
		.modal {
	        display: none;
	        position: fixed;
	        z-index: 1;
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
	</style>
	<!-- 추가적인 사용자 정의 스타일 -->

</head>
<body>


<fmt:formatDate var="currentDate" value="${nowDate}" pattern="yyyyMMdd" />
	<div id="board_wrap">
		<header>
			<h1>전체 게시판</h1>
		</header>
		
		<div class="login_wrap">
		    <c:choose>
		        <c:when test="${not empty username}">
		            <div class="flex flex-flow-column">
		                <span class="user_name_em">${username}</span>
		            </div>
		            <div class="flex flex-flow-column">
		                <button type="button" onclick="location.href='/user/logout'" class="btn btn-secondary">로그아웃</button>
		            </div>
		        </c:when>
		        <c:otherwise>
		            <div class="flex flex-flow-column">
		                <button type="button" class="btn btn-primary" onclick="location.href='/user/login'">로그인</button>
		            </div>
		            <div class="flex flex-flow-column">
		                <button type="button" onclick="location.href='/user/register'" style="text-decoration: none;" class="btn btn-secondary">회원가입</button>
		            </div>
		        </c:otherwise>
		    </c:choose>
		</div>

		<section>
			<table class="table table-bordered">
				<tr class="top_row">
					<th width="100">번호</th>
					<th width="300">제목</th>
					<th width="150">작성자</th>
					<th width="150">작성일</th>
					<th width="100">조회수</th>
					<th width="100">추천</th>
				</tr>
				
<!-- 포워딩 받은 데이터 처리 -->
			<c:forEach var="posts" items="${posts}">
				<tr>
					<td width="100">${posts.post_id}</td>
					<td width="300" class ="title_col">
						<a href="/post/detail?post_id=${posts.post_id}">${posts.title}</a>
					</td>
					<td width="150">${posts.username}</td>
					<td width="150">
					<fmt:formatDate var="postDate" value="${posts.created_at}" pattern="yyyyMMdd" />
						<c:choose>
						    <c:when test="${postDate == currentDate}">
						        <fmt:formatDate value="${posts.created_at}" pattern="HH:mm" />
						    </c:when>
						    <c:otherwise>
						        <fmt:formatDate value="${posts.created_at}" pattern="yyyy-MM-dd" />
						    </c:otherwise>
						</c:choose>
					</td>
					<td width="100">${posts.readcount}</td>
					<td width="100">${posts.likes }</td>
				</tr>
			</c:forEach>
			</table>
			
		<!-- 검색버튼 -->
		
		<div class="flex search_wrap">
	        <form action="/post/search" method="get" class="flex form-control-sm">
	            <select class="form-select form-select-sm w-50" aria-label="Default select example" name="searchType">
	                <option value="all">전체</option>
	                <option value="title">제목</option>
	                <option value="author">작성자</option>
	            </select>
	            <input class="form-control" value="" name="searchValue">
	            <button class="btn btn-secondary btn-sm " style="white-space:nowrap;" type="submit">검색</button>
	        </form>
	        <div class="writeBoard">
	            <button class="btn btn-secondary btn-sm" type="button" onclick="location.href='/post/write'">글쓰기</button>
	        </div>
	    </div>
    
		<!-- 페이징,카운터 -->
            <!-- 계산을 위한 바인딩 데이터 사용 -->
            <c:if test="${count > 0 }"> <!-- 총글갯수가 몇개라고 가정 -->
               <!-- 총글갯수가 7개라 가정 0.7 + 1 = 1.7 -->
               <c:set var="pageCount" value="${count / pageSize + (count%pageSize == 0 ? 0 : 1)}" />
               <!-- startPate = 1 -->
               <c:set var="startPage" value="${1 }" />

            
               <c:if test="${currentPage%10 != 0 }"> <!-- 시작페이지가 0이 아니면 -->
                  <!-- 결과를 정수형으로 받기위해 fmt 사용 -->
                  <!-- integerOnly = 정수 부분만 파싱할지 여부를 지정 | 기본값은 false -->
                  <!-- result = 1/10 = 0.1 -->
                  <fmt:parseNumber var="result" value="${currentPage/10 }" integerOnly="true" />
                  <!-- startPage = 0.1*10+1=1 -->
                  <c:set var="startPage" value="${result*10+1 }"/>
               </c:if>
               <c:if test="${currentPage%10 == 0 }"> <!-- 시작페이지가 0이면 10페이지%10 -->
				    <fmt:parseNumber var="result" value="${currentPage / 10}" integerOnly="true" />
				    <c:set var="startPage" value="${(result - 1) * 10 + 1}"/>
               </c:if>
               
               <!-- endPage 화면에 보여질 페이지 숫자 -->
               <c:set var="pageBlock" value="${10 }" />
               <c:set var="endPage" value="${startPage+pageBlock-1 }" />
               
               <!-- 마지막 페이지가 10보다 크면 -->
               <c:if test="${endPage > pageCount }">
                  <!-- 11할당 -->
                  <c:set var="endPage" value="${pageCount }" />
               </c:if>

               
				<nav aria-label="Page navigation" class="pagenav">
				  <ul class="pagination">
				    <!-- << 버튼: 항상 첫 페이지로 -->
				    <c:choose>
				      <c:when test="${currentPage > 1}">
				        <li>
				          <a href="/post/list?pageNum=1" aria-label="First" class="page-link">«</a>
				        </li>
				      </c:when>
				      <c:otherwise>
				        <li>
				          <a href="#" aria-label="First" class="page-link" onclick="event.preventDefault();">«</a>
				        </li>
				      </c:otherwise>
				    </c:choose>
				
				    <!-- 이전 버튼 -->
				    <c:choose>
				      <c:when test="${startPage > 1}">
				        <li>
				          <a href="/post/list?pageNum=${startPage-10}" aria-label="Previous" class="page-link">‹</a>
				        </li>
				      </c:when>
				      <c:otherwise>
				        <li  >
				          <a href="#" aria-label="Previous" class="page-link" onclick="event.preventDefault();">‹</a>
				        </li>
				      </c:otherwise>
				    </c:choose>
				
				    <!-- 페이지 번호 -->
				    <c:forEach var="i" begin="${startPage }" end="${endPage }" >
				      <li class="${currentPage == i ? 'active' : ''}">
				        <a href="/post/list?pageNum=${i }" class="page-link">${i}</a>
				      </li>
				    </c:forEach>
				
				    <!-- 다음 버튼 -->
				    <c:choose>
				      <c:when test="${endPage < pageCount}">
				        <li>
				          <a href="/post/list?pageNum=${startPage+10}" aria-label="Next" class="page-link">›</a>
				        </li>
				      </c:when>
				      <c:otherwise>
				        <li  >
				          <a href="#" aria-label="Next" class="page-link" onclick="event.preventDefault();">›</a>
				        </li>
				      </c:otherwise>
				    </c:choose>
				
				    <!-- >> 버튼: 마지막 페이지로 -->
				    <c:choose>
				      <c:when test="${currentPage < pageCount}">
				        <li>
				          <fmt:parseNumber var="totalPages" value="${count / pageSize + (count % pageSize == 0 ? 0 : 1)}" integerOnly="true" />
				          <a href="/post/list?pageNum=${totalPages}" aria-label="Last" class="page-link">»</a>
				        </li>
				      </c:when>
				      <c:otherwise>
				        <li>
				          <a href="#" aria-label="Last" class="page-link" onclick="event.preventDefault();">»</a>
				        </li>
				      </c:otherwise>
				    </c:choose>
				  </ul>
				</nav>
			</c:if>
<!-- /페이징,카운터 -->
		</section>
		
        <c:if test="${not empty modalMessage}">
            <!-- 모달창 추가 -->
            <div id="errorModal" class="modal">
                <div class="modal-content">
                    <h3>${modalTitle }</h3>
                    <p>${modalMessage}</p>
                    <button class="modal-button" onclick="closeModal()">확인</button>
                </div>
            </div>

            <script>
                // 모달창 표시 함수
                function showModal() {
                    document.getElementById("errorModal").style.display = "block";
                }

                // 모달창 닫기 함수
                function closeModal() {
                    document.getElementById("errorModal").style.display = "none";
                }

                // 로그인 실패 시 모달창 표시
                <c:if test="${not empty modalMessage}">
                    showModal();
                </c:if>
            </script>
        </c:if>
	</div>
	
</body>
</html>