<%@ page language="java" pageEncoding="Windows-31J" %>
<%@ page contentType="text/html;charset=Windows-31J" %>
<%@ page isELIgnored="false"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html lang="ja">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=Windows-31J">
	<meta http-equiv="Content-Style-Type" content="text/css"/>
	<meta http-equiv="Content-Script-Type" content="text/javascript"/>
	<script language="JavaScript" type="text/javascript" src="<%= request.getContextPath() %>/js/Check.js"></script>
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/Class.css" charset="Windows-31J"/>
	
	<title>�V�K�A�J�E���g�쐬</title>
</head>
<body>
	<div class="midashi">
		�V�K�A�J�E���g�쐬
	</div>
	<br>
	
	<div align="center" style="color:red;">${ERROR_REQ}</div>
	<form name="accountForm" action="/keijiban/newAccount" method="post">
	<div align="left"><input type="submit" name="returnBtn" value="�߂�"></div>
		<table align="center" border="1">
			<tr>
				<th align="right">���O</th>
				<td><input type="text" name="name" value="" style="width:180px"></td>
			</tr>
			<tr>
				<th align="right">���O�C��ID</th>
				<td><input type="text" name="id" value="" style="width:180px"></td>
			</tr>
			<tr>
				<th align="right">�p�X���[�h</th>
				<td><input type="password" name="loginPass" value="" style="width:180px"></td>
			</tr>
		</table>
		<br>
		<div align="center"><input type="submit" name="accountBtn" value="�o�^" onclick="return LoginChkVal(loginForm.id.value, loginPass.value);"/></div>
	</form>
</body>
</html>