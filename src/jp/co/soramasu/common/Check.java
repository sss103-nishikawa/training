package jp.co.soramasu.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 入力チェッククラス
 * 
 * @author Y.Nishikawa
 *
 */
public class Check {

	/**
	 * ログインチェック
	 * 
	 * @param id
	 *            ログインID
	 * @param loginPass
	 *            パスワード
	 * @return
	 */
	public static String executeLogin(String id, String loginPass) {

		Pattern ptn = Pattern.compile("\\d{5}");
		Matcher mc = ptn.matcher(id);

		Pattern ptn2 = Pattern.compile("\\w{1,20}");
		Matcher mc2 = ptn2.matcher(loginPass);

		String msg = "";

		if (id.length() == 0) {
			msg += "IDが入力されていません <br>";
		} else if (!mc.matches()) {
			msg += "IDは半角数字5桁で入力して下さい <br>";
		}
		if (loginPass.length() == 0) {
			msg += "パスワードが入力されていません";
		} else if (!mc2.matches()) {
			msg += "パスワードは半角英数字20字以内で入力して下さい";
		}
		return msg;
	}

	/**
	 * 編集削除チェック
	 * 
	 * @param num
	 *            投稿番号
	 * @param editPass
	 *            パスワード
	 * @return
	 */
	public static String executeEdit(String num, String editPass) {

		Pattern ptn = Pattern.compile("\\d{1,}");
		Matcher mc = ptn.matcher(num);

		Pattern ptn2 = Pattern.compile("\\w{1,20}");
		Matcher mc2 = ptn2.matcher(editPass);

		String msg = "";

		if (num.length() == 0) {
			msg += "番号が入力されていません <br>";
		} else if (!mc.matches()) {
			msg += "番号は半角数字で入力して下さい <br>";
		}
		if (editPass.length() == 0) {
			msg += "パスワードが入力されていません <br>";
		} else if (!mc2.matches()) {
			msg += "パスワードは半角英数字20字以内で入力して下さい <br>";
		}
		try {
			Integer.parseInt(num);
		} catch (NumberFormatException e) {
			msg += "その番号は存在しません";
		}
		return msg;
	}

	/**
	 * 投稿チェック
	 * 
	 * @param name
	 *            名前
	 * @param title
	 *            タイトル
	 * @param editPass
	 *            パスワード
	 * @param text
	 *            本文
	 * @return
	 */
	public static String executeInput(String name, String title,
			String editPass, String text) {

		Pattern ptn2 = Pattern.compile("\\w{1,20}");
		Matcher mc2 = ptn2.matcher(editPass);

		String msg = "";

		if (name.length() == 0) {
			msg += "名前が入力されていません <br>";
		} else if (name.length() > 20) {
			msg += "名前は20字以内で入力して下さい <br>";
		}
		if (title.length() == 0) {
			msg += "タイトルが入力されていません <br>";
		}
		if (editPass.length() == 0) {
			msg += "パスワードが入力されていません <br>";
		} else if (!mc2.matches()) {
			msg += "パスワードは半角英数字20字以内で入力して下さい <br>";
		}
		if (text.length() == 0) {
			msg += "本文が入力されていません <br>";
		}

		return msg;
	}

	/**
	 * コメント投稿チェック
	 * 
	 * @param name
	 *            名前
	 * @param comment
	 *            コメント
	 * @return
	 */
	public static String executeComment(String name, String comment) {

		String msg = "";

		if (name.length() == 0) {
			msg += "名前が入力されていません <br>";
		}
		if (comment.length() == 0) {
			msg += "コメントが入力されていません <br>";
		}
		return msg;
	}

	/**
	 * コメント削除チェック
	 * 
	 * @param num
	 *            コメント番号
	 * @return
	 */
	public static String executeComDel(String num) {

		Pattern ptn = Pattern.compile("\\d{1,}");
		Matcher mc = ptn.matcher(num);

		String msg = "";

		if (num.length() == 0) {
			msg += "番号が入力されていません <br>";
		} else if (!mc.matches()) {
			msg += "番号は半角数字で入力して下さい <br>";
		}
		return msg;
	}
}