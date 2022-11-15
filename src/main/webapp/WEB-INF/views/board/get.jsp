<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.net.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Insert title here</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.2.0/css/all.min.css" integrity="sha512-xh6O/CkQoPOWDdYTDqeRdPCVd1SpvCA9XXcUnZS2FmJNp1coAFzvtCN9BmamE+4aHK8yyUHUSCcJHgXloTyT2A==" crossorigin="anonymous" referrerpolicy="no-referrer" />
</head>
<body>
	<my:navBar></my:navBar>
	<div class="container-md">
		<div class="row">
			<div class="col">
			
	
				<h1>
					${board.id }번 게시물
					 
					<c:url value="/board/modify" var="modifyLink">
						<c:param name="id" value="${board.id }"></c:param>
					</c:url>
					<a class="btn btn-warning" href="${modifyLink }">
						<i class="fa-solid fa-pen-to-square"></i>
					</a>
				</h1>
			
				<div class="mb-3">
					<label class="form-label">
						제목 
					</label>
					<input class="form-control" type="text" value="${board.title }" readonly>
				</div>	
				
				<div class="mb-3">
					<label for="" class="form-label">
					본문 
					</label>
					<textarea rows="5" class="form-control" readonly>${board.content }</textarea>
				</div>
				
				<%-- 이미지 출력 --%>
				<div>
					<c:forEach items="${board.fileName }" var="name">
						<div>
						                                             <%-- aws 저장소로 주소 변경 --%>
							<img class="img-fluid img-thumbnail" src="${imgUrl }/${board.id }/${URLEncoder.encode(name, 'utf-8')}" alt="">
						</div>
					</c:forEach>		
				</div>
				
				<div class="mb-3">
					<label for="" class="form-label">
						작성자 
					</label>
					<input class="form-control" type="text" value="${board.writer }" readonly>
				</div>
				
				<div class="mb-3">
					<label for="" class="form-label">
						작성일시 
					</label>
					<input class="form-control" type="datetime-local" value="${board.inserted }" readonly>
				</div>
	
	
			</div>
		</div>
	</div>
	
	<hr>
	
	<%-- toast --%>
	<div id="replyMessageToast" class="toast align-items-center top-0 start-50 translate-middle-x position-fixed" role="alert" aria-live="assertive" aria-atomic="true">
  <div class="d-flex">
    <div id="replyMessage1" class="toast-body">
      Hello, world! This is a toast message.
    </div>
    <button type="button" class="btn-close me-2 m-auto" data-bs-dismiss="toast" aria-label="Close"></button>
  </div>
</div>
	
	<%-- 댓글쓰기 --%>
	<div class="container-md">
			<div class="row">
				<div class="col">
					<%-- 댓글 경계선 아이콘 --%>
					<h3><i class="fa-solid fa-comments"></i></h3>
				</div>
			</div>
			<div class="row">
				<div class="col">							
				<%-- 댓글 작성하는 부분 --%>
					<input type="hidden" id="boardId" value="${board.id }">
					
					<div class="input-group">
						<input type="text" id="replyInput1">
						<button class="btn btn-outline-secondary" id="replySendButton1"><i class="fa-solid fa-message"></i></button>
					</div>
				</div>
			</div>
			
			<div class="row mt-3">
				<div class="col">
					<div class="list-group" id="replyListContainer">
						<%-- 댓글 리스트 출력되는곳 --%>
					</div>
				</div>
			</div>
		</div>
	
	
	<%-- 댓글 삭제 확인 모달 --%>
	<!-- Modal -->
	<div class="modal fade" id="removeReplyConfirmModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
	  <div class="modal-dialog">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h1 class="modal-title fs-5" id="exampleModalLabel">댓글 삭제 확인</h1>
	        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
	      </div>
	      <div class="modal-body">
	        댓글을 삭제하시겠습니까?
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
	        <button type="button" data-bs-dismiss="modal" id="removeConfirmModalSubmitButton" class="btn btn-danger">삭제</button>
	      </div>
	    </div>
	  </div>
	</div>
	
	<%-- 댓글 수정 모달 --%>
	<!-- Modal -->
	<div class="modal fade" id="modifyReplyFormModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
	  <div class="modal-dialog">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h1 class="modal-title fs-5">댓글 수정 양식</h1>
	        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
	      </div>
	      <div class="modal-body"> <%-- class="form-control" 길게 --%>
	        <input type="text" class="form-control" id="modifyReplyInput">
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
	        <button type="button" data-bs-dismiss="modal" id="modifyFormModalSubmitButton" class="btn btn-primary">수정</button>
	      </div>
	    </div>
	  </div>
	</div>
	
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-OERcA2EqjJCMA+/3y+gxIOqMEjwtxJY7qPCqsdltbNJuaOe923+mo//f6V8Qbsw3" crossorigin="anonymous"></script>
<script>
const ctx = "${pageContext.request.contextPath}";

listReply();

// 댓글 toast 자바스크립트
const toast = new bootstrap.Toast(document.querySelector("#replyMessageToast"));

// 댓글 수정 폼 모달
document.querySelector("#modifyFormModalSubmitButton").addEventListener("click", function() {
	const content = document.querySelector("#modifyReplyInput").value;
	// dataset.replyId에서 꺼내 씀
	const id = this.dataset.replyId;
	const data = {id, content};
	
	// 댓글 수정되는 시점
	fetch(`\${ctx}/reply/modify`, {
		method : "put",
		headers : {
			"Content-Type" : "application/json"
		},
		body : JSON.stringify(data)
	})
	.then(res => res.json())
	.then(data => {
		document.querySelector("#replyMessage1").innerText = data.message;
		toast.show(); // 업데이트 하고 나서 보여주는 토스트
	})
	.then(() => listReply()); // 댓글 수정하고 수정완료 되었다는 메세지 띄우기
});


// 댓글 삭제 모달확인버튼
document.querySelector("#removeConfirmModalSubmitButton").addEventListener("click", function() {
	removeReply(this.dataset.replyId);
});

// 댓글 수정하기위해 전 댓글 가져오기
function readReplyAndSetModalForm(id) {
	fetch(`\${ctx}/reply/get/\${id}`)
	.then(res => res.json())
	.then(reply => {
		document.querySelector("#modifyReplyInput").value = reply.content;
	});
}


// 댓글 수정
function listReply() {
	const boardId = document.querySelector("#boardId").value;
	// 댓글을 가져오는 시점
	fetch(`\${ctx}/reply/list/\${boardId}`)
	.then(res => res.json())
	.then(list => {
		// 댓글 새로고침 안하고 바로 화면출력
		const replyListContainer = document.querySelector("#replyListContainer");
		replyListContainer.innerHTML = "";
		
		// 댓글을 만들고 리스트로 가져오는 시점
		for (const item of list) {
			
			// 댓글 수정 버튼
			const modifyReplyButtonId = `modifyReplyButton\${item.id}`;
			// 댓글 삭제 버튼 이벤트 추가
			const removeReplyButtonId = `removeReplyButton\${item.id}`;
			// console.log(item.id);
			
			// 오른쪽 마진 "me-auto"
			const replyDiv = `
				<div class="list-group-item d-flex">
					<div class="me-auto"> 
						<div>
								\${item.content} 
						</div>
						
							<small class="text-muted">
								<i class="fa-regular fa-clock"></i> 
								\${item.ago}
							</small>
					</div>
					<div>
						<button class="btn btn-light" data-bs-toggle="modal" data-bs-target="#modifyReplyFormModal" data-reply-id="\${item.id}" id="\${modifyReplyButtonId}">
							<i class="fa-solid fa-highlighter"></i>
						</button>
						<button class="btn btn-light" data-bs-toggle="modal" data-bs-target="#removeReplyConfirmModal" data-reply-id="\${item.id}" id="\${removeReplyButtonId}">
							<i class="fa-solid fa-delete-left"></i>
						</button>
					</div>
					</div>`;
				/* data-reply-id => dataset 이라는 attribute를 이용해 reply-id에서 id값을 꺼내쓸수 있음 */
			replyListContainer.insertAdjacentHTML("beforeend", replyDiv);
			// 수정 폼 모달에 댓글 내용 넣기
			document.querySelector("#" + modifyReplyButtonId)
				.addEventListener("click", function() {
					document.querySelector("#modifyFormModalSubmitButton").setAttribute("data-reply-id", this.dataset.replyId);
					readReplyAndSetModalForm(this.dataset.replyId);
				});
			
			
			// 삭제확인 버튼에 replyId 옮기기
			document.querySelector("#" + removeReplyButtonId)
				.addEventListener("click", function() { // 삭제하는 함수
					// console.log(this.id + "번 삭제버튼 클릭됨");
					console.log(this.dataset.replyId + "번 댓글 삭제할 예정, 모달 띄움") // dataset을 통해 "#" + removeReplyButtonId의 참조값을 가져옴
																						 // 그래서 this를 사용
					document.querySelector("#removeConfirmModalSubmitButton").setAttribute("data-reply-id", this.dataset.replyId);
					// removeReply(this.dataset.replyId);
				});
		}
	});
}

// 댓글 삭제
function removeReply(replyId) {
	// /reply/remove/{id}, method:"delete"
	fetch(ctx + "/reply/remove/" + replyId, {
		method: "delete"
	})
	.then(res => res.json())
	.then(data => {
		document.querySelector("#replyMessage1").innerText = data.message;
		toast.show();
	})
	.then(() => listReply()); // 댓글 삭제되고 새로고침필요없이 바로 화면출력
}

// 게시글 댓글 추가
document.querySelector("#replySendButton1").addEventListener("click", function() {
	const boardId = document.querySelector("#boardId").value;
	const content = document.querySelector("#replyInput1").value;
	
	const data = {
		boardId,
		content
	};
	
	// 댓글이 등록되는 시점
	fetch(`\${ctx}/reply/add`, {
		method : "post",
		headers : {
			"Content-Type" : "application/json"
		},
		body : JSON.stringify(data)
	})
	.then(res => res.json())
	.then(data => {
		// 새댓글이 등록되고 다시 빈스트링으로 초기화
		document.querySelector("#replyInput1").value = "";
		document.querySelector("#replyMessage1").innerText = data.message;
		toast.show();
	})
	// 새로운 댓글 바로 출력
	.then(() => listReply());
});
</script>
</body>
</html>









