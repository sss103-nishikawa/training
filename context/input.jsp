<%@ page language="java" pageEncoding="Windows-31J" %>
<%@ page contentType="text/html;charset=Windows-31J" %>
<%@ page isELIgnored="false"%>

<%@ page import="jp.co.soramasu.common.Data" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html lnag="ja">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=Windows-31J">
	<meta http-equiv="Content-Style-Type" content="text/css"/>
	<meta http-equiv="Content-Script-Type" content="text/javascript"/>
	<script language="JavaScript" type="text/javascript" src="<%= request.getContextPath() %>/js/Check.js"></script>
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/Class.css" charset="Windows-31J"/>
	
	<title>�V�K���e</title>

</head>
<body>
	<div class="midashi">�f����<br>(�V�K���e/�ҏW)</div>
	<br>
	<form name="inputForm" action="/keijiban/input" method="post">
		<div align="left"><input type="submit" name="returnBtn" value="�߂�"></div>
	<br>
		<table width="100%" border="1">
			<tr>
				<th>���O</th>
				<td><input type="textbox" name="name" style="width:323px" value="${DATA_SES.name}"</td>
			</tr>
			<tr>
				<th>�^�C�g��</th>
				<td><input type="textbox" name="title" style="width:323px" value="${DATA_SES.title}"></td>
			</tr>
			<tr>
				<th>�p�X���[�h<br>(�ҏW/�폜�p)</th>
				<td><input type="password" name="editPass" style="width:323px; height:26px;" value="${DATA_SES.editPass}"></td>
			</tr>
		</table>
		
		<br>
		
		<table width="100%" border="1">
			<tr>
				<th width="102px">�{��</th>
				<td style="border-top:hidden; border-right:hidden;"></td>
			</tr>
			<tr>
				<td colspan="2"><textarea name="text" cols="62" rows="6" style="width:428px;">${DATA_SES.text}</textarea></td>
			</tr>
		</table>
		
		<br>
		
		<div align="right"><input type="submit" name="inputBtn" value="�o�^" onclick="return InputChkVal(inputForm.name.value, inputForm.title.value, editPass.value, text.value);"/></div>
		
		<div align="center" style="color:red;">${ERROR_REQ}</div>
	</form>
</body>
</html>