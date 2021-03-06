<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- <c:set var="qnaMap" value="${qnaMap}"></c:set> --%>
<c:url var="qnaDeleteURI" value="/user/qnaBoard/qnaboardDelete.do"></c:url>
<c:url var="QnAListURI" value="/user/qnaBoard/qnaboardList.do"/>
<c:url var="qnaUpdateURI" value="/user/qnaBoard/qnaBoardUpadate.do"/>
<%-- <c:url var="InsertURI" value="/user/qnaBoard/qnaboardForm.do"/> --%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<!-- 이미지 슬라이드 사이즈 조정 -->
<style type="text/css">
.carousel{
	width:200px;
    height:150px;
    margin-left: 200px;
}
.carousel-inner > .item > img{
    width:200px;
    height:150px;
}       
</style>
<script>
$(function(){
	// 섬머노트를 div를 활용한 textarea에 추가.
	// http://summernote.org 활용
    $('#post_content').summernote({
			height: 150,
			codemirror: {
			theme: 'monokai'
		}
    });
    
    $('#post_title').val("${qnaMap.post_title}");
	$('#post_nickname').val("${qnaMap.mem_name}");
	$('#post_pwd').val("${qnaMap.post_pw}");
	$('#post_content').summernote('code', "${qnaMap.post_content}");
	$('#mem_mail').val("${qnaMap.mem_mail}");
	
	//삭제
	$('#btn2').click(function(){
		var flag = true;
    	if(eval('${!empty LOGINMEMBERINFO}')){
    		if('${LOGINMEMBERINFO.mem_name}' == '${qnaMap.mem_name}'){
				$(location).attr('href','${qnaDeleteURI}?post_no=${qnaMap.post_no}');
    		}else{
    		flag = false;
    		}
   		}else{
    		flag = false;
   		}
    	if(!flag){
        		BootstrapDialog.show({
        		    title: '알림',
        		    message: '해당 게시물은 작성자만 삭제할 수 있습니다.'
        		});
    	}
	});
	
// 	목록
	$('#btn4').click(function(){
// 		$(location).attr('href','${QnAListURI}');
		window.history.back();
	});
	
// 	$('#btn1').click(function(){
// 		$(location).attr('href','${InsertURI}');
// 	});
	
	
	// 수정
	$('#btn5').click(function(){
		var flag = true;
    	if(eval('${!empty LOGINMEMBERINFO}')){
    		if('${LOGINMEMBERINFO.mem_name}' == '${qnaMap.mem_name}'){
    			$('form[name=qnaUpdate]').submit(function(){
    				var post_content = $('#post_content').summernote('code');
    				$(this).append('<input type="hidden" name="post_content" value="'+ post_content +'"/>');
    				$(this).append('<input type="hidden" name="post_no" value="${qnaMap.post_no}"/>');
    				
    				$(this).append('<input type="hidden" name="post_ip" value="${pageContext.request.remoteAddr}"/>');
    				
    				$(this).attr('action','${qnaUpdateURI}');
    			});
    		}else{
    		flag = false;
    		}
   		}else{
    		flag = false;
   		}
    	if(!flag){
        		BootstrapDialog.show({
        		    title: '알림',
        		    message: '해당 게시물은 작성자만 수정할 수 있습니다.'
        		});
    	}
	});
	
	//수정
	
//     $('#bo_nickname').val("${qnaMap[MEM_NAME]}");
// 	$('#bo_title').val("${qnaMap.post_title}");
// 	$('#bo_content').val("${qnaMap[POST_CONTENT]}");
});
</script>
</head>
<body>
<form name="qnaUpdate" class="form-horizontal" role="form" action="" method="post">
	<div class="form-group">
		<label class="control-label col-sm-2" for="bo_title">제목:</label>
		<div class="col-sm-10">
			<input type="text" class="form-control" id="post_title" name="post_title" >
		</div>
	</div>
	<div class="form-group">
		<label class="control-label col-sm-2" for="bo_nickname">작성자 대화명:</label>
		<div class="col-sm-10"> 
			<input type="text" class="form-control" id="post_nickname" name="post_nickname" readonly="readonly" >
		</div>
	</div>
	<div class="form-group">
		<label class="control-label col-sm-2" for="bo_pwd">패스워드:</label>
		<div class="col-sm-10"> 
			<input type="password" class="form-control" id="post_pwd" name="post_pwd" >
		</div>
	</div>
	<div class="form-group">
		<label class="control-label col-sm-2" for="bo_mail">메일:</label>
		<div class="col-sm-10"> 
			<input type="text" class="form-control" id="mem_mail" name="mem_mail" >
		</div>
	</div>
	<div class="form-group">
		<label class="control-label col-sm-2" for="bo_content">내용:</label>
		<div class="col-sm-10"> 
			<div id="post_content"></div>
		</div>
	</div>
		<div class="form-group">
		<label class="control-label col-sm-2" for="bo_content">첨부파일:</label>
		<div id="myCarousel" class="carousel slide" data-ride="carousel">
			<!-- Indicators -->
			<ol class="carousel-indicators">
				<li data-target="#myCarousel" data-slide-to="0" class="active"></li>
				<li data-target="#myCarousel" data-slide-to="1"></li>
			</ol>

			<!-- Wrapper for slides -->
			<div class="carousel-inner" role="listbox" style="height: 200px;">
			  <c:forEach items="${qnaMap.items }" var="fileitemInfo" varStatus="stat">
			  	<c:if test="${stat.first }">
				<div class="item active">
				</c:if>
				
				<c:if test="${stat.last }">
					<div class="item">
				</c:if>
								<img src="/files/${fileitemInfo.file_save_name }" alt="pic1"
									onclick="javascript:location.href='${pageContext.request.contextPath }/admin/qnaBoard/qnaboardFileDownload.do?fileName=${fileitemInfo.file_upload_name }';">
								</div>
				</c:forEach>
			</div>
			<!-- Left and right controls -->
			<a class="carousel-control left" href="#myCarousel" data-slide="prev">&lsaquo;</a>
			<a class="carousel-control right" href="#myCarousel" data-slide="next">&rsaquo;</a>
		</div>
	</div>
	<div class="form-group"> 
		<div class="col-sm-offset-2 col-sm-10">
<!-- 			<button type="button" class="btn btn-success" id="btn1">글쓰기</button> -->
			<button type="button" class="btn btn-danger" id="btn2">삭제</button>
			<button type="button" class="btn btn-info" id="btn4">목록</button>
			<button type="submit" class="btn btn-default" style="float: right" id="btn5">수정</button>
		</div>
	</div>
</form>
</body>
</html>