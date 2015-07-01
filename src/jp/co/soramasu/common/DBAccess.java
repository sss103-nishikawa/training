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
 * DBアクセスクラス
 * 
 * @author Y.Nishikawa
 *
 */
public class DBAccess {

	private final String DB_URL = "jdbc:postgresql://localhost/keijiban";
	private final String JDBC_DRIVER_NAME = "org.postgresql.Driver";
	private final String DB_USER = "postgres";
	private final String DB_PASSWORD = "postgres";
	private final String DB_ACCESS_ERROR_MESSAGE = "データベース接続エラー";

	private Connection con = null;
	private PreparedStatement ps = null;
	private ResultSet rs = null;

	/**
	 * お約束？
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
	 * 新規アカウント認証メソッド
	 * 
	 * @param id
	 *            ログインID
	 * @param loginPass
	 *            ログインパスワード
	 * @param request
	 * @return
	 */
	public boolean executeAccount(String id, String loginPass, String name,
			HttpServletRequest request) {

		try {
			// DBへ接続
			con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			con.setAutoCommit(false);

			String selectSql = "select id from login where id = ?";
			ps = con.prepareStatement(selectSql);

			// 検索処理
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
	 * アカウント登録メソッド
	 * 
	 * @param id
	 *            ログインID
	 * @param loginPass
	 *            パスワード
	 * @param name
	 *            名前
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
	 * ログイン認証メソッド
	 * 
	 * @param id
	 *            ログインID
	 * @param loginPass
	 *            ログインパスワード
	 * @param request
	 * @return
	 */
	public boolean executeLogin(String id, String loginPass,
			HttpServletRequest request) {

		try {
			// DBへ接続
			con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			con.setAutoCommit(false);

			String selectSql = "select id, login_pass from login where id=?";
			ps = con.prepareStatement(selectSql);

			// 検索処理
			ps.setString(1, id);
			rs = ps.executeQuery();

			while (rs.next()) {
				if (rs.getString(1).equals(id)
						&& rs.getString(2).equals(loginPass)) {

					// 格納メソッド実行
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
	 * 編集・削除のパスワードを認証するメソッド
	 * 
	 * @param num
	 *            投稿番号
	 * @param editPass
	 *            パスワード
	 * @param request
	 * @return
	 */
	public boolean executeEdiDel(String num, String editPass,
			HttpServletRequest request) {

		try {
			// DBへ接続
			con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			con.setAutoCommit(false);

			String selectSql = "select num, edit_pass from edit where num=?";
			ps = con.prepareStatement(selectSql);

			// 検索処理
			int intNum = Integer.parseInt(num);
			ps.setInt(1, intNum);
			rs = ps.executeQuery();

			while (rs.next()) {
				if (rs.getInt(1) == intNum && rs.getString(2).equals(editPass)) {

					HttpSession session = request.getSession();
					String button = (String) session.getAttribute("BTN_SES");

					if (button.equals("editBtn")) {
						// 1件のデータを呼び出し・格納
						storageOne(intNum, request);

					} else if (button.equals("deleteBtn")) {
						// 削除メソッド実行
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
	 * 投稿を削除するメソッド
	 * 
	 * @param num
	 *            投稿番号
	 * @param request
	 */
	public void delete(String num, HttpServletRequest request) {

		try {
			// 1件のデータを削除
			String deleteSql = "delete from edit where num=?";
			ps = con.prepareStatement(deleteSql);
			int intNum = Integer.parseInt(num);
			ps.setInt(1, intNum);
			ps.executeUpdate();
			con.commit();

			// 対応するコメントも削除
			String deleteComSql = "delete from comment where accord=?";
			ps = con.prepareStatement(deleteComSql);
			ps.setInt(1, intNum);
			ps.executeUpdate();
			con.commit();

			// 格納メソッド実行
			storageAll(request);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**********************************************************************************************
	 * 新規投稿・編集をするメソッド
	 * 
	 * @param name
	 *            名前
	 * @param title
	 *            タイトル
	 * @param editPass
	 *            パスワード
	 * @param text
	 *            本文
	 * @param request
	 */
	public void executeInput(String name, String title, String editPass,
			String text, HttpServletRequest request) {

		try {
			// DBへ接続
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

			// 格納メソッド実行
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
	 * 全投稿データをリストに格納するメソッド
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
			// セッションスコープへセット。
			HttpSession session = request.getSession();
			session.setAttribute("LIST_SES", msgList);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**********************************************************************************************
	 * １件の編集データを格納するメソッド
	 * 
	 * @param intNum
	 *            投稿番号
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

			// セッションスコープへセット。
			HttpSession session = request.getSession();
			session.setAttribute("DATA_SES", data);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**********************************************************************************************
	 * 投稿とコメントを格納するメソッド
	 * 
	 * @param num
	 * @param request
	 */
	public void executeCom(int intNum, HttpServletRequest request) {

		try {
			// DBへ接続
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
	 * コメント登録メソッド
	 * 
	 * @param name
	 * @param comment
	 * @param request
	 */
	public void executeCom(String name, String comment,
			HttpServletRequest request) {

		try {
			// DBへ接続
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

			// 格納メソッド実行
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
	 * コメントを削除するメソッド
	 * 
	 * @param num
	 * @param request
	 */
	public void deleteCom(String num, HttpServletRequest request) {

		// 1件のデータを削除
		try {
			// DBへ接続
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

			// 格納メソッド実行
			storageCom(accord, request);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**********************************************************************************************
	 * コメントをリストに格納するメソッド
	 * 
	 * @param accord
	 *            コメントに対応する投稿番号
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

			// セッションスコープへセット。
			HttpSession session = request.getSession();
			session.setAttribute("COMMENT_SES", comList);

		} catch (SQLException e) {
			e.printStackTrace();

		}
	}

	/**********************************************************************************************
	 * 投稿番号とコメント番号をリセットするメソッド
	 */
	public void executeReset() {

		try {
			// DBへ接続
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
	 * オールクリア
	 * 
	 * @param request
	 */
	public void executeClear(HttpServletRequest request) {

		try {
			// DBへ接続
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
	 * 更新
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