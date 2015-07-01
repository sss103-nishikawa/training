/**
  *入力された値をチェックする。
  *
  *@param id   ID
  *@param loginPass  パスワード
  *@return チェック結果に問題がなければ、trueを返す
  */
function LoginChkVal(id, loginPass) {

	//未入力チェック。
	var msg = "";
	
	var ID = "";
	var PASS = "";
	
	var match = new Boolean(false);
	
	if (id == "") {
		ID += "*ID";
		match = true;
	} else if (!/^\d{5}$/.test(id)) {
		msg += "IDは半角数字5桁で入力して下さい \n";
	}
	if (loginPass == "") {
		PASS += "*パスワード";
		match = true;
	} else if (!/^\w{1,20}$/.test(loginPass)) {
		msg += "パスワードは半角英数字20字以内で入力して下さい \n";
	}
	
	if (match == true) {
		msg += (ID + PASS + "*が入力されていません");
	}
	
	if (isError(msg)) {
		return false;		//画面遷移を防ぐため、falseを返す。
	} else {
		return true;
	}
	
}

function EditChkVal(num, editPass) {

	//未入力チェック。
	var msg = "";
	
	var NUM = "";
	var PASS = "";
	
	var match = new Boolean(false);
	
	if (num == "") {
		NUM += "*番号";
		match = true;
	} else if (!/\d/.test(num)) {
		msg += "番号は半角数字で入力して下さい \n";
	}
	if (editPass == "") {
		PASS += "*パスワード";
		match = true;
	} else if (!/^\w{1,20}$/.test(editPass)) {
		msg += "パスワードは半角英数字20字以内で入力して下さい \n";
	}
		
	if (match == true) {
		msg += (NUM + PASS + "*が入力されていません");
	}
	
	if (isError(msg)) {
		return false;		//画面遷移を防ぐため、falseを返す。
	} else {
		//実行確認処理
		var flag = confirm("編集または削除をしますか？");
		if (flag) {
			return true;		//画面遷移させるため、trueを返す。
		} else {
			alert("処理を中断します。");
			return false;		//画面遷移を防ぐため、falseを返す。
		}
	}
}

function InputChkVal(name, title, editPass, text) {

	//未入力チェック。
	var msg = "";
	
	var NAME = "";
	var TITLE = "";
	var PASS = "";
	var HONBUN = "";
	
	var match = new Boolean(false);
	
	if (name == "") {
		NAME += "*名前";
		match = true;
	} else if (!/^{1,20}$/.test(name)) {
		msg += "名前は20字以内で入力して下さい \n";
	}
	if (title == "") {
		TITLE += "*タイトル";
		match = true;
	}
	if (editPass == "") {
		PASS += "*パスワード";
		match = true;
	} else if (!/^\w{1,20}$/.test(editPass)) {
		msg += "パスワードは半角英数字20字以内で入力して下さい \n";
	}
	if (text == "") {
		HONBUN += "*本文";
		match = true;
	}

	if (match == true) {
		msg += (NAME + TITLE + PASS + HONBUN + "*が入力されていません");
	}
			
	if (isError(msg)) {
		return false;		//画面遷移を防ぐため、falseを返す。
	} else {
		//実行確認処理
		var flag = confirm("登録しますか？");
		if (flag) {
			return true;		//画面遷移させるため、trueを返す。
		} else {
			alert("登録処理を中断します。");
			return false;		//画面遷移を防ぐため、falseを返す。
		}
	}
}

/**
  *チェック結果に問題があれば、メッセージを表示する。
  *
  *@return チェック結果に問題があれば、trueを返す
  */
 function isError(msg) {
 	if (msg != "") {
 		alert(msg);
 		return true;
 	}
 	return false;
 }
