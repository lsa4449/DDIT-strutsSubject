<%@ page language="JAVA" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<!--BEGIN SIDEBAR MENU-->
<nav id="sidebar" role="navigation" data-step="2"
	data-intro="Template has &lt;b&gt;many navigation styles&lt;/b&gt;"
	data-position="right" class="navbar-default navbar-static-side">
	<div class="sidebar-collapse menu-scroll">
		<ul id="side-menu" class="nav">
			<div class="clearfix"></div>
			<li class="active">
				<a href="${pageContext.request.contextPath}/user/noticeBoard/noticeBoardList.do">
					<i class="fa fa-tachometer fa-fw"><div class="icon-bg bg-orange"></div></i>
					<span class="menu-title">공지사항</span>
				</a>
			</li>
			<li>
				<a href="${pageContext.request.contextPath}/user/freeBoard/freeBoardList.do">
					<i class="fa fa-desktop fa-fw"><div class="icon-bg bg-pink"></div></i>
					<span class="menu-title">자유게시판</span>
				</a>
			</li>
			<li>
				<a href="${pageContext.request.contextPath}/user/referenceBoard/referenceBoardList.do">
					<i class="fa fa-send-o fa-fw"><div class="icon-bg bg-green"></div></i>
					<span class="menu-title">자료실</span>
				</a>
			</li>
			<li>
				<a href="${pageContext.request.contextPath}/user/qnaBoard/qnaboardList.do">
					<i class="fa fa-edit fa-fw"><div class="icon-bg bg-violet"></div></i>
					<span class="menu-title">Q&A게시판</span>
				</a>
			</li>
			<li>
				<a href="${pageContext.request.contextPath}/user/fquestionBoard/fquestionBoardList.do">
					<i class="fa fa-th-list fa-fw"><div class="icon-bg bg-blue"></div></i>
					<span class="menu-title">자주하는질문</span>
				</a>
			</li>
			
			<li>
				<a href="${pageContext.request.contextPath}/user/imageBoard/imageBoardList.do">
					<i class="fa fa-database fa-fw"><div class="icon-bg bg-red"></div></i>
					<span class="menu-title">이미지게시판</span>
				</a>
			</li> 
		</ul>
	</div>
</nav>
</body>
</html>