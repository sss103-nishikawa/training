package jp.co.soramasu.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ���̓`�F�b�N�N���X
 * 
 * @author Y.Nishikawa
 *
 */
public class Check {

	/**
	 * ���O�C���`�F�b�N
	 * 
	 * @param id
	 *            ���O�C��ID
	 * @param loginPass
	 *            �p�X���[�h
	 * @return
	 */
	public static String executeLogin(String id, String loginPass) {

		Pattern ptn = Pattern.compile("\\d{5}");
		Matcher mc = ptn.matcher(id);

		Pattern ptn2 = Pattern.compile("\\w{1,20}");
		Matcher mc2 = ptn2.matcher(loginPass);

		String msg = "";

		if (id.length() == 0) {
			msg += "ID�����͂���Ă��܂��� <br>";
		} else if (!mc.matches()) {
			msg += "ID�͔��p����5���œ��͂��ĉ����� <br>";
		}
		if (loginPass.length() == 0) {
			msg += "�p�X���[�h�����͂���Ă��܂���";
		} else if (!mc2.matches()) {
			msg += "�p�X���[�h�͔��p�p����20���ȓ��œ��͂��ĉ�����";
		}
		return msg;
	}

	/**
	 * �ҏW�폜�`�F�b�N
	 * 
	 * @param num
	 *            ���e�ԍ�
	 * @param editPass
	 *            �p�X���[�h
	 * @return
	 */
	public static String executeEdit(String num, String editPass) {

		Pattern ptn = Pattern.compile("\\d{1,}");
		Matcher mc = ptn.matcher(num);

		Pattern ptn2 = Pattern.compile("\\w{1,20}");
		Matcher mc2 = ptn2.matcher(editPass);

		String msg = "";

		if (num.length() == 0) {
			msg += "�ԍ������͂���Ă��܂��� <br>";
		} else if (!mc.matches()) {
			msg += "�ԍ��͔��p�����œ��͂��ĉ����� <br>";
		}
		if (editPass.length() == 0) {
			msg += "�p�X���[�h�����͂���Ă��܂��� <br>";
		} else if (!mc2.matches()) {
			msg += "�p�X���[�h�͔��p�p����20���ȓ��œ��͂��ĉ����� <br>";
		}
		try {
			Integer.parseInt(num);
		} catch (NumberFormatException e) {
			msg += "���̔ԍ��͑��݂��܂���";
		}
		return msg;
	}

	/**
	 * ���e�`�F�b�N
	 * 
	 * @param name
	 *            ���O
	 * @param title
	 *            �^�C�g��
	 * @param editPass
	 *            �p�X���[�h
	 * @param text
	 *            �{��
	 * @return
	 */
	public static String executeInput(String name, String title,
			String editPass, String text) {

		Pattern ptn2 = Pattern.compile("\\w{1,20}");
		Matcher mc2 = ptn2.matcher(editPass);

		String msg = "";

		if (name.length() == 0) {
			msg += "���O�����͂���Ă��܂��� <br>";
		} else if (name.length() > 20) {
			msg += "���O��20���ȓ��œ��͂��ĉ����� <br>";
		}
		if (title.length() == 0) {
			msg += "�^�C�g�������͂���Ă��܂��� <br>";
		}
		if (editPass.length() == 0) {
			msg += "�p�X���[�h�����͂���Ă��܂��� <br>";
		} else if (!mc2.matches()) {
			msg += "�p�X���[�h�͔��p�p����20���ȓ��œ��͂��ĉ����� <br>";
		}
		if (text.length() == 0) {
			msg += "�{�������͂���Ă��܂��� <br>";
		}

		return msg;
	}

	/**
	 * �R�����g���e�`�F�b�N
	 * 
	 * @param name
	 *            ���O
	 * @param comment
	 *            �R�����g
	 * @return
	 */
	public static String executeComment(String name, String comment) {

		String msg = "";

		if (name.length() == 0) {
			msg += "���O�����͂���Ă��܂��� <br>";
		}
		if (comment.length() == 0) {
			msg += "�R�����g�����͂���Ă��܂��� <br>";
		}
		return msg;
	}

	/**
	 * �R�����g�폜�`�F�b�N
	 * 
	 * @param num
	 *            �R�����g�ԍ�
	 * @return
	 */
	public static String executeComDel(String num) {

		Pattern ptn = Pattern.compile("\\d{1,}");
		Matcher mc = ptn.matcher(num);

		String msg = "";

		if (num.length() == 0) {
			msg += "�ԍ������͂���Ă��܂��� <br>";
		} else if (!mc.matches()) {
			msg += "�ԍ��͔��p�����œ��͂��ĉ����� <br>";
		}
		return msg;
	}
}