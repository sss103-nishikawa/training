package jp.co.soramasu.common;

import java.io.Serializable;
import java.sql.Date;

/**
 * �f�[�^�N���X
 * 
 * @author Y.Nishikawa
 *
 */
public class Data implements Serializable {

	/** data�i�[�t�B�[���h */
	private int num;
	private String name;
	private String title;
	private String editPass;
	private String text;
	private Date date;
	private String com;
	private int accord;

	/**
	 * ���e��setter
	 * 
	 * @param num
	 *            ���e�ԍ�
	 * @param name
	 *            ���O
	 * @param title
	 *            �^�C�g��
	 * @param editPass
	 *            �p�X���[�h
	 * @param text
	 *            �{��
	 * @param date
	 *            ���t
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
	 * �R�����g��setter
	 * 
	 * @param num
	 *            �R�����g�ԍ�
	 * @param name
	 *            ���O
	 * @param com
	 *            �R�����g
	 * @param date
	 *            ���t
	 * @param accord
	 *            �Ή��ԍ�
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
	 * ���e�ԍ���getter
	 * 
	 * @return
	 */
	public int getNum() {
		return this.num;
	}

	/**
	 * ���O��getter
	 * 
	 * @return
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * �^�C�g����getter
	 * 
	 * @return
	 */
	public String getTitle() {
		return this.title;
	}

	/**
	 * �p�X���[�h��getter
	 * 
	 * @return
	 */
	public String getEditPass() {
		return this.editPass;
	}

	/**
	 * �{����getter
	 * 
	 * @return
	 */
	public String getText() {
		return this.text;
	}

	/**
	 * �R�����g��getter
	 * 
	 * @return
	 */
	public String getCom() {
		return this.com;
	}

	/**
	 * ���t��getter
	 * 
	 * @return
	 */
	public Date getDate() {
		return this.date;
	}

	/**
	 * ���e�Ή��ԍ���getter
	 * 
	 * @return
	 */
	public int getAccord() {
		return this.accord;
	}

}
