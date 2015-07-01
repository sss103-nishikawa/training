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
	
	<title>�f����</title>

</head>
<body>
	<form name="msgForm" action="/keijiban/edit" method="post">
		<div class="midashi">�f����<br>(�ꗗ�\��)</div>
		
		<br>
		
		<table width="100%">
			<tr>
				<td align="left"><input type="submit" name="logoutBtn" value="���O�A�E�g"></td>
				<td align="center"><input type="submit" name="renewBtn" value="�X�V"></td>
				<td align="right"><input type="submit" name="newBtn" value="�V�K���e"></td>
			</tr>
		</table>
		
		<br>
		
		<c:forEach var="data" items="${LIST_SES}">
			<table width="100%" border="1">
				<tr style="background-color:white;">
					<th valign="top" rowspan="3">No.${data.num}</th>
					<th>���t</th>
					<td align="center"><fmt:formatDate value="${data.date}" pattern="yyyy/MM/dd" /></td>
					<th>���O</th>
					<td>${data.name}</td>
				</tr>
				<tr style="background-color:white;">
					<th>�^�C�g��</th>
					<td colspan="3">${data.title}</td>
				</tr>
				<tr style="background-color:white;">
					<td class="kaigyo" width="100%" colspan="4"><c:out value="${data.text}"></c:out></td>
				</tr>
			</table>	
				<button type="submit" name="commentBtn" value="${data.num}" style="width:100%;">�R�����g����</button></td>
				
			<br>
			<br>
		</c:forEach>
		
		<br>
		
		<c:choose>
			<c:when test="${LIST_SES.size() != 0}">
				<table align="right" border="1">
					<tr>
						<th>�ԍ�</th>
						<td><input type="text" name="num" style="width:94px;ime-mode:inactive;"></td>
					</tr>
					<tr>
						<th>�p�X���[�h</th>
						<td><input type="password" name="editPass" style="width:94px"></td>
					</tr>
					<tr>
						<td style="border-left:hidden; border-bottom:hidden;"></td>
						<td><input type="submit" name="editBtn" value="�ҏW" onclick="return EditChkVal(num.value, editPass.value);"/><input type="submit" name="deleteBtn" value="�폜" onclick="return EditChkVal(num.value, editPass.value);"/></td>
					</tr>
					
					<div align="right" style="color:red;">${ERROR_REQ}</div>
				</table>
					<c:if test="${ID_SES.equals('00000')}">
					<input type="submit" name="clearBtn" value="ALL CLEAR">
					</c:if>
			</c:when>
			<c:otherwise>
				<input type="submit" name="resetBtn" value="No���Z�b�g">
			</c:otherwise>
		</c:choose>
	</form>
	<br><br><br><br>
	<!--<div align="center"><a href="http://localhost:8080/keijiban/messageBoard.jsp">�O��5��</a>
		
		<a href="http://localhost:8080/keijiban/messageBoard.jsp">����5��</a></div>-->
</body>
</html>