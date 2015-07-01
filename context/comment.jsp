<%@ page language="java" pageEncoding="Windows-31J" %>
<%@ page contentType="text/html;charset=Windows-31J" %>
<%@ page isELIgnored="false"%>

<%@ page import="java.util.List" %>
<%@ page import="jp.co.soramasu.common.Data" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html lnag="ja">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=Windows-31J">
	<meta http-equiv="Content-Style-Type" content="text/css"/>
	<meta http-equiv="Content-Script-Type" content="text/javascript"/>
	<script language="JavaScript" type="text/javascript" src="<%= request.getContextPath() %>/js/Check.js"></script>
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/Class.css" charset="Windows-31J"/>
	
	<title>�R�����g</title>

</head>
<body>
	<div class="midashi">�f����<br>(�R�����g)</div>
	<br>
	<form name="commentForm" action="/keijiban/comment" method="post">
		<div align="left"><input type="submit" name="returnBtn" value="�߂�"></div>
		<br>
		
		<!--���e���p�e�[�u��-->
		<table width="100%" border="1">
			<tr style="background-color:white;">
				<th valign="top" rowspan="3">No.${DATA_SES.num}</th>
				<th>���t</th>
				<td align="center"><fmt:formatDate value="${DATA_SES.date}" pattern="yyyy/MM/dd" /></td>
				<th>���O</th>
				<td>${DATA_SES.name}</td>
			</tr>
			<tr style="background-color:white;">
				<th>�^�C�g��</th>
				<td colspan="3">${DATA_SES.title}</td>
			</tr>
			<tr style="background-color:white;">
				<td class="kaigyo" width="100%" colspan="4"><c:out value="${DATA_SES.text}"></c:out></td>
			</tr>
		</table>
		
		<br>
		
		<!--�V�K�R�����g�e�[�u��-->
		<table width="100%" border="1">
			<tr>
				<th width="102px">���O</th>
				<td><input type="textbox" name="name" style="width:99%"></td>
			</tr>
			<tr>
				<th>�R�����g</th>
				<td colspan="2"><textarea name="comment" cols="62" rows="3" style="width:99%;"></textarea></td>
			</tr>
		</table>
		<div align="right"><input type="submit" name="inputBtn" value="�o�^"></div>
		
		
		<div align="right" style="color:red;">${INPUT_ERROR_REQ}</div>
		
		<br>
		
		<!--�R�����g�ꗗ�e�[�u��-->
		<c:if test="${COMMENT_SES.size() != 0}">
			<p align="center">----------------------�R�����g�ꗗ----------------------</p>
			<c:forEach var="dataCom" items="${COMMENT_SES}" varStatus="status">			
				<table width="100%" border="1">
					<tr style="background-color:white;">
						<th valign="top" rowspan="2">No.${status.index + 1}</th>
						<th>���t</th>
						<td align="center"><fmt:formatDate value="${dataCom.date}" pattern="yyyy/MM/dd" /></td>
						<th>���O</th>
						<td>${dataCom.name}</td>
					</tr>
					<tr style="background-color:white;">
						<td class="kaigyo" width="100%" colspan="4"><c:out value="${dataCom.com}"></c:out></td>
					</tr>
				</table>
			</c:forEach>
			
			<br>
			
			<table align="right" border="1">
				<tr>
					<th>�ԍ�</th>
					<td><input type="text" name="num" style="width:94px;ime-mode:inactive;"></td>
				</tr>
				<tr>
					<td style="border-left:hidden; border-bottom:hidden;"></td>
					<td align="right" style="border-left:hidden; border-right:hidden; border-bottom:hidden;"><input type="submit" name="deleteBtn" value="�폜"></td>
				</tr>
			</table>
			<br><br><br>
			
			<div align="right" style="color:red;">${DELETE_ERROR_REQ}</div>
		</c:if>
		
	</form>
</body>
</html>