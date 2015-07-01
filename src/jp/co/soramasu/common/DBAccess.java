package jp.co.soramasu.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * DB�A�N�Z�X�N���X
 * 
 * @author Y.Nishikawa
 *
 */
public class DBAccess {

	private final String DB_URL = "jdbc:postgresql://localhost/keijiban";
	private final String JDBC_DRIVER_NAME = "org.postgresql.Driver";
	private final String DB_USER = "postgres";
	private final String DB_PASSWORD = "postgres";
	private final String DB_ACCESS_ERROR_MESSAGE = "�f�[�^�x�[�X�ڑ��G���[";

	private Connection con = null;
	private PreparedStatement ps = null;
	private ResultSet rs = null;

	/**
	 * ���񑩁H
	 */
	public DBAccess() {
		try {
			Class.forName(JDBC_DRIVER_NAME).newInstance();
		} catch (Exception e) {
			System.out.println(DB_ACCESS_ERROR_MESSAGE);
			e.printStackTrace();
		}
	}

	/**********************************************************************************************
	 * �V�K�A�J�E���g�F�؃��\�b�h
	 * 
	 * @param id
	 *            ���O�C��ID
	 * @param loginPass
	 *            ���O�C���p�X���[�h
	 * @param request
	 * @return
	 */
	public boolean executeAccount(String id, String loginPass, String name,
			HttpServletRequest request) {

		try {
			// DB�֐ڑ�
			con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			con.setAutoCommit(false);

			String selectSql = "select id from login where id = ?";
			ps = con.prepareStatement(selectSql);

			// ��������
			ps.setString(1, id);
			rs = ps.executeQuery();

			while (rs.next()) {
				return false;
			}

			insert(id, loginPass, name, request);
			return true;

		} catch (SQLException e) {
			System.out.println(DB_ACCESS_ERROR_MESSAGE);
			e.printStackTrace();
			return false;
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				System.out.println(DB_ACCESS_ERROR_MESSAGE);
				e.printStackTrace();
				return false;
			}
		}
	}

	/**
	 * �A�J�E���g�o�^���\�b�h
	 * 
	 * @param id
	 *            ���O�C��ID
	 * @param loginPass
	 *            �p�X���[�h
	 * @param name
	 *            ���O
	 * @param request
	 */
	private void insert(String id, String loginPass, String name,
			HttpServletRequest request) {

		try {
			String insertSql = "insert into login (id, login_pass, name) values (?, ?, ?)";
			ps = con.prepareStatement(insertSql);
			ps.setString(1, id);
			ps.setString(2, loginPass);
			ps.setString(3, name);

			ps.executeUpdate();
			con.commit();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**********************************************************************************************
	 * ���O�C���F�؃��\�b�h
	 * 
	 * @param id
	 *            ���O�C��ID
	 * @param loginPass
	 *            ���O�C���p�X���[�h
	 * @param request
	 * @return
	 */
	public boolean executeLogin(String id, String loginPass,
			HttpServletRequest request) {

		try {
			// DB�֐ڑ�
			con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			con.setAutoCommit(false);

			String selectSql = "select id, login_pass from login where id=?";
			ps = con.prepareStatement(selectSql);

			// ��������
			ps.setString(1, id);
			rs = ps.executeQuery();

			while (rs.next()) {
				if (rs.getString(1).equals(id)
						&& rs.getString(2).equals(loginPass)) {

					// �i�[���\�b�h���s
					storageAll(request);
					return true;
				}
			}
		} catch (SQLException e) {
			System.out.println(DB_ACCESS_ERROR_MESSAGE);
			e.printStackTrace();
			return false;
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				System.out.println(DB_ACCESS_ERROR_MESSAGE);
				e.printStackTrace();
				return false;
			}
		}
		return false;
	}

	/**********************************************************************************************
	 * �ҏW�E�폜�̃p�X���[�h��F�؂��郁�\�b�h
	 * 
	 * @param num
	 *            ���e�ԍ�
	 * @param editPass
	 *            �p�X���[�h
	 * @param request
	 * @return
	 */
	public boolean executeEdiDel(String num, String editPass,
			HttpServletRequest request) {

		try {
			// DB�֐ڑ�
			con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			con.setAutoCommit(false);

			String selectSql = "select num, edit_pass from edit where num=?";
			ps = con.prepareStatement(selectSql);

			// ��������
			int intNum = Integer.parseInt(num);
			ps.setInt(1, intNum);
			rs = ps.executeQuery();

			while (rs.next()) {
				if (rs.getInt(1) == intNum && rs.getString(2).equals(editPass)) {

					HttpSession session = request.getSession();
					String button = (String) session.getAttribute("BTN_SES");

					if (button.equals("editBtn")) {
						// 1���̃f�[�^���Ăяo���E�i�[
						storageOne(intNum, request);

					} else if (button.equals("deleteBtn")) {
						// �폜���\�b�h���s
						delete(num, request);
					}
					return true;
				}
			}

		} catch (SQLException e) {
			System.out.println(DB_ACCESS_ERROR_MESSAGE);
			e.printStackTrace();
			return false;

		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				System.out.println(DB_ACCESS_ERROR_MESSAGE);
				e.printStackTrace();
				return false;
			}
		}
		return false;
	}

	/********************************************************************************************
	 * ���e���폜���郁�\�b�h
	 * 
	 * @param num
	 *            ���e�ԍ�
	 * @param request
	 */
	public void delete(String num, HttpServletRequest request) {

		try {
			// 1���̃f�[�^���폜
			String deleteSql = "delete from edit where num=?";
			ps = con.prepareStatement(deleteSql);
			int intNum = Integer.parseInt(num);
			ps.setInt(1, intNum);
			ps.executeUpdate();
			con.commit();

			// �Ή�����R�����g���폜
			String deleteComSql = "delete from comment where accord=?";
			ps = con.prepareStatement(deleteComSql);
			ps.setInt(1, intNum);
			ps.executeUpdate();
			con.commit();

			// �i�[���\�b�h���s
			storageAll(request);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**********************************************************************************************
	 * �V�K���e�E�ҏW�����郁�\�b�h
	 * 
	 * @param name
	 *            ���O
	 * @param title
	 *            �^�C�g��
	 * @param editPass
	 *            �p�X���[�h
	 * @param text
	 *            �{��
	 * @param request
	 */
	public void executeInput(String name, String title, String editPass,
			String text, HttpServletRequest request) {

		try {
			// DB�֐ڑ�
			con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			con.setAutoCommit(false);

			HttpSession session = request.getSession();
			String numSes = (String) session.getAttribute("NUM_SES");

			int intNum = Integer.parseInt(numSes);
			String insertSql = "";

			if (intNum != 0) {
				insertSql = "update edit set name=?, title=?, edit_pass=?, text=?, date=CURRENT_TIMESTAMP where num=?";
				ps = con.prepareStatement(insertSql);
				ps.setString(1, name);
				ps.setString(2, title);
				ps.setString(3, editPass);
				ps.setString(4, text);
				ps.setInt(5, intNum);

			} else {
				insertSql = "insert into edit (name, title, edit_pass, text, date) values (?, ?, ?, ?, CURRENT_TIMESTAMP)";
				ps = con.prepareStatement(insertSql);
				ps.setString(1, name);
				ps.setString(2, title);
				ps.setString(3, editPass);
				ps.setString(4, text);
			}

			ps.executeUpdate();
			con.commit();

			// �i�[���\�b�h���s
			storageAll(request);

		} catch (SQLException e) {
			System.out.println(DB_ACCESS_ERROR_MESSAGE);
			e.printStackTrace();

		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				System.out.println(DB_ACCESS_ERROR_MESSAGE);
				e.printStackTrace();
			}
		}
	}

	/**********************************************************************************************
	 * �S���e�f�[�^�����X�g�Ɋi�[���郁�\�b�h
	 * 
	 * @param request
	 */
	private void storageAll(HttpServletRequest request) {

		try {
			List<Data> msgList = new ArrayList<Data>();
			String selectSql = "select * from edit order by num desc";

			ps = con.prepareStatement(selectSql);
			rs = ps.executeQuery();

			while (rs.next()) {
				Data data = new Data();
				data.setData(rs.getInt(1), rs.getString(2), rs.getString(3),
						rs.getString(4), rs.getString(5), rs.getDate(6));
				msgList.add(data);
			}
			// �Z�b�V�����X�R�[�v�փZ�b�g�B
			HttpSession session = request.getSession();
			session.setAttribute("LIST_SES", msgList);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**********************************************************************************************
	 * �P���̕ҏW�f�[�^���i�[���郁�\�b�h
	 * 
	 * @param intNum
	 *            ���e�ԍ�
	 * @param request
	 */
	private void storageOne(int intNum, HttpServletRequest request) {

		try {
			String selectSql = "select * from edit where num=?";
			ps = con.prepareStatement(selectSql);
			ps.setInt(1, intNum);
			rs = ps.executeQuery();

			rs.next();
			Data data = new Data();
			data.setData(rs.getInt(1), rs.getString(2), rs.getString(3),
					rs.getString(4), rs.getString(5), rs.getDate(6));

			// �Z�b�V�����X�R�[�v�փZ�b�g�B
			HttpSession session = request.getSession();
			session.setAttribute("DATA_SES", data);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**********************************************************************************************
	 * ���e�ƃR�����g���i�[���郁�\�b�h
	 * 
	 * @param num
	 * @param request
	 */
	public void executeCom(int intNum, HttpServletRequest request) {

		try {
			// DB�֐ڑ�
			con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			con.setAutoCommit(false);

			storageOne(intNum, request);

			storageCom(intNum, request);

		} catch (SQLException e) {
			System.out.println(DB_ACCESS_ERROR_MESSAGE);
			e.printStackTrace();

		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				System.out.println(DB_ACCESS_ERROR_MESSAGE);
				e.printStackTrace();
			}
		}
	}

	/**********************************************************************************************
	 * �R�����g�o�^���\�b�h
	 * 
	 * @param name
	 * @param comment
	 * @param request
	 */
	public void executeCom(String name, String comment,
			HttpServletRequest request) {

		try {
			// DB�֐ڑ�
			con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			con.setAutoCommit(false);

			HttpSession session = request.getSession();
			Data dataSes = (Data) session.getAttribute("DATA_SES");
			int accord = dataSes.getNum();

			String insertSql = "";
			insertSql = "insert into comment (name, com, date, accord) values (?, ?, CURRENT_TIMESTAMP,?)";
			ps = con.prepareStatement(insertSql);
			ps.setString(1, name);
			ps.setString(2, comment);
			ps.setInt(3, accord);

			ps.executeUpdate();
			con.commit();

			// �i�[���\�b�h���s
			storageCom(accord, request);

		} catch (SQLException e) {
			System.out.println(DB_ACCESS_ERROR_MESSAGE);
			e.printStackTrace();

		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				System.out.println(DB_ACCESS_ERROR_MESSAGE);
				e.printStackTrace();
			}
		}
	}

	/**********************************************************************************************
	 * �R�����g���폜���郁�\�b�h
	 * 
	 * @param num
	 * @param request
	 */
	public void deleteCom(String num, HttpServletRequest request) {

		// 1���̃f�[�^���폜
		try {
			// DB�֐ڑ�
			con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			con.setAutoCommit(false);

			String deleteSql = "delete from comment where num in (select num from "
					+ "(select num, row_number() over (partition by accord order by num)"
					+ " as rnum from comment where accord = ?) c where c.rnum = ?)";
			ps = con.prepareStatement(deleteSql);

			HttpSession session = request.getSession();
			Data dataSes = (Data) session.getAttribute("DATA_SES");
			int accord = dataSes.getNum();

			int intNum = Integer.parseInt(num);

			ps.setInt(1, accord);
			ps.setInt(2, intNum);
			ps.executeUpdate();
			con.commit();

			// �i�[���\�b�h���s
			storageCom(accord, request);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**********************************************************************************************
	 * �R�����g�����X�g�Ɋi�[���郁�\�b�h
	 * 
	 * @param accord
	 *            �R�����g�ɑΉ����铊�e�ԍ�
	 * @param request
	 */
	private void storageCom(int accord, HttpServletRequest request) {

		try {
			List<Data> comList = new ArrayList<Data>();
			String selectSql = "select * from comment where accord=? order by num asc";
			ps = con.prepareStatement(selectSql);
			ps.setInt(1, accord);
			rs = ps.executeQuery();

			while (rs.next()) {
				Data data = new Data();
				data.setDataCom(rs.getInt(1), rs.getString(2), rs.getString(3),
						rs.getDate(4), rs.getInt(5));
				comList.add(data);
			}

			// �Z�b�V�����X�R�[�v�փZ�b�g�B
			HttpSession session = request.getSession();
			session.setAttribute("COMMENT_SES", comList);

		} catch (SQLException e) {
			e.printStackTrace();

		}
	}

	/**********************************************************************************************
	 * ���e�ԍ��ƃR�����g�ԍ������Z�b�g���郁�\�b�h
	 */
	public void executeReset() {

		try {
			// DB�֐ڑ�
			con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			con.setAutoCommit(false);

			String selectSql = "select setval('edit_num_seq', 1, false)";
			ps = con.prepareStatement(selectSql);
			rs = ps.executeQuery();

			String selectComSql = "select setval('comment_num_seq', 1, false)";
			ps = con.prepareStatement(selectComSql);
			rs = ps.executeQuery();

		} catch (SQLException e) {
			System.out.println(DB_ACCESS_ERROR_MESSAGE);
			e.printStackTrace();

		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				System.out.println(DB_ACCESS_ERROR_MESSAGE);
				e.printStackTrace();
			}
		}
	}

	/**********************************************************************************************
	 * �I�[���N���A
	 * 
	 * @param request
	 */
	public void executeClear(HttpServletRequest request) {

		try {
			// DB�֐ڑ�
			con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			con.setAutoCommit(false);

			String deleteSql = "delete from edit";
			ps = con.prepareStatement(deleteSql);
			ps.executeUpdate();
			con.commit();

			String deleteComSql = "delete from comment";
			ps = con.prepareStatement(deleteComSql);
			ps.executeUpdate();
			con.commit();

			storageAll(request);

		} catch (SQLException e) {
			System.out.println(DB_ACCESS_ERROR_MESSAGE);
			e.printStackTrace();

		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				System.out.println(DB_ACCESS_ERROR_MESSAGE);
				e.printStackTrace();
			}
		}
	}

	/**
	 * �X�V
	 * 
	 * @param request
	 */
	public void executeRenew(HttpServletRequest request) {

		try {
			con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			con.setAutoCommit(false);

			storageAll(request);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}