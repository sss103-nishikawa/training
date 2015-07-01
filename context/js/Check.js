/**
  *���͂��ꂽ�l���`�F�b�N����B
  *
  *@param id   ID
  *@param loginPass  �p�X���[�h
  *@return �`�F�b�N���ʂɖ�肪�Ȃ���΁Atrue��Ԃ�
  */
function LoginChkVal(id, loginPass) {

	//�����̓`�F�b�N�B
	var msg = "";
	
	var ID = "";
	var PASS = "";
	
	var match = new Boolean(false);
	
	if (id == "") {
		ID += "*ID";
		match = true;
	} else if (!/^\d{5}$/.test(id)) {
		msg += "ID�͔��p����5���œ��͂��ĉ����� \n";
	}
	if (loginPass == "") {
		PASS += "*�p�X���[�h";
		match = true;
	} else if (!/^\w{1,20}$/.test(loginPass)) {
		msg += "�p�X���[�h�͔��p�p����20���ȓ��œ��͂��ĉ����� \n";
	}
	
	if (match == true) {
		msg += (ID + PASS + "*�����͂���Ă��܂���");
	}
	
	if (isError(msg)) {
		return false;		//��ʑJ�ڂ�h�����߁Afalse��Ԃ��B
	} else {
		return true;
	}
	
}

function EditChkVal(num, editPass) {

	//�����̓`�F�b�N�B
	var msg = "";
	
	var NUM = "";
	var PASS = "";
	
	var match = new Boolean(false);
	
	if (num == "") {
		NUM += "*�ԍ�";
		match = true;
	} else if (!/\d/.test(num)) {
		msg += "�ԍ��͔��p�����œ��͂��ĉ����� \n";
	}
	if (editPass == "") {
		PASS += "*�p�X���[�h";
		match = true;
	} else if (!/^\w{1,20}$/.test(editPass)) {
		msg += "�p�X���[�h�͔��p�p����20���ȓ��œ��͂��ĉ����� \n";
	}
		
	if (match == true) {
		msg += (NUM + PASS + "*�����͂���Ă��܂���");
	}
	
	if (isError(msg)) {
		return false;		//��ʑJ�ڂ�h�����߁Afalse��Ԃ��B
	} else {
		//���s�m�F����
		var flag = confirm("�ҏW�܂��͍폜�����܂����H");
		if (flag) {
			return true;		//��ʑJ�ڂ����邽�߁Atrue��Ԃ��B
		} else {
			alert("�����𒆒f���܂��B");
			return false;		//��ʑJ�ڂ�h�����߁Afalse��Ԃ��B
		}
	}
}

function InputChkVal(name, title, editPass, text) {

	//�����̓`�F�b�N�B
	var msg = "";
	
	var NAME = "";
	var TITLE = "";
	var PASS = "";
	var HONBUN = "";
	
	var match = new Boolean(false);
	
	if (name == "") {
		NAME += "*���O";
		match = true;
	} else if (!/^{1,20}$/.test(name)) {
		msg += "���O��20���ȓ��œ��͂��ĉ����� \n";
	}
	if (title == "") {
		TITLE += "*�^�C�g��";
		match = true;
	}
	if (editPass == "") {
		PASS += "*�p�X���[�h";
		match = true;
	} else if (!/^\w{1,20}$/.test(editPass)) {
		msg += "�p�X���[�h�͔��p�p����20���ȓ��œ��͂��ĉ����� \n";
	}
	if (text == "") {
		HONBUN += "*�{��";
		match = true;
	}

	if (match == true) {
		msg += (NAME + TITLE + PASS + HONBUN + "*�����͂���Ă��܂���");
	}
			
	if (isError(msg)) {
		return false;		//��ʑJ�ڂ�h�����߁Afalse��Ԃ��B
	} else {
		//���s�m�F����
		var flag = confirm("�o�^���܂����H");
		if (flag) {
			return true;		//��ʑJ�ڂ����邽�߁Atrue��Ԃ��B
		} else {
			alert("�o�^�����𒆒f���܂��B");
			return false;		//��ʑJ�ڂ�h�����߁Afalse��Ԃ��B
		}
	}
}

/**
  *�`�F�b�N���ʂɖ�肪����΁A���b�Z�[�W��\������B
  *
  *@return �`�F�b�N���ʂɖ�肪����΁Atrue��Ԃ�
  */
 function isError(msg) {
 	if (msg != "") {
 		alert(msg);
 		return true;
 	}
 	return false;
 }
