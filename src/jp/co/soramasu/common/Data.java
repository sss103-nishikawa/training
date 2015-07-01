package jp.co.soramasu.common;

import java.io.Serializable;
import java.sql.Date;

/**
 * データクラス
 * 
 * @author Y.Nishikawa
 *
 */
public class Data implements Serializable {

	/** data格納フィールド */
	private int num;
	private String name;
	private String title;
	private String editPass;
	private String text;
	private Date date;
	private String com;
	private int accord;

	/**
	 * 投稿のsetter
	 * 
	 * @param num
	 *            投稿番号
	 * @param name
	 *            名前
	 * @param title
	 *            タイトル
	 * @param editPass
	 *            パスワード
	 * @param text
	 *            本文
	 * @param date
	 *            日付
	 */
	public void setData(int num, String name, String title, String editPass,
			String text, Date date) {
		this.num = num;
		this.name = name;
		this.title = title;
		this.editPass = editPass;
		this.text = text;
		this.date = date;
	}

	/**
	 * コメントのsetter
	 * 
	 * @param num
	 *            コメント番号
	 * @param name
	 *            名前
	 * @param com
	 *            コメント
	 * @param date
	 *            日付
	 * @param accord
	 *            対応番号
	 */
	public void setDataCom(int num, String name, String com, Date date,
			int accord) {
		this.num = num;
		this.name = name;
		this.com = com;
		this.date = date;
		this.accord = accord;
	}

	/**
	 * 投稿番号のgetter
	 * 
	 * @return
	 */
	public int getNum() {
		return this.num;
	}

	/**
	 * 名前のgetter
	 * 
	 * @return
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * タイトルのgetter
	 * 
	 * @return
	 */
	public String getTitle() {
		return this.title;
	}

	/**
	 * パスワードのgetter
	 * 
	 * @return
	 */
	public String getEditPass() {
		return this.editPass;
	}

	/**
	 * 本文のgetter
	 * 
	 * @return
	 */
	public String getText() {
		return this.text;
	}

	/**
	 * コメントのgetter
	 * 
	 * @return
	 */
	public String getCom() {
		return this.com;
	}

	/**
	 * 日付のgetter
	 * 
	 * @return
	 */
	public Date getDate() {
		return this.date;
	}

	/**
	 * 投稿対応番号のgetter
	 * 
	 * @return
	 */
	public int getAccord() {
		return this.accord;
	}

}
