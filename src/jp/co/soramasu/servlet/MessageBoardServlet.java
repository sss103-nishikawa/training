package jp.co.soramasu.servlet;

import java.io.IOException;
import java.sql.Date;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.co.soramasu.common.Check;
import jp.co.soramasu.common.DBAccess;
import jp.co.soramasu.common.Data;

/**
 * 一覧表示画面での動作
 * 
 * @author Y.Nishikawa
 *
 */
public class MessageBoardServlet extends HttpServlet {

	/**
	 * ボタン別処理
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		// 文字化け防止処理
		request.setCharacterEncoding("Windows-31J");

		Enumeration<String> en = request.getParameterNames();
		while (en.hasMoreElements()) {
			en.hasMoreElements();
			String action = en.nextElement();
			switch (action) {
			case "logoutBtn":
				this.getServletContext().getRequestDispatcher("/login.jsp")
						.forward(request, response);
				break;
			case "newBtn":
				// 新規投稿メソッド実行
				newInput(request, response);
				break;
			case "editBtn":
				// 編集メソッド実行
				edit(request, response);
				break;
			case "deleteBtn":
				// 削除メソッド実行
				delete(request, response);
				break;
			case "commentBtn":
				// コメントメソッド実行
				comment(request, response);
				break;
			case "resetBtn":
				// 投稿番号リセットメソッド実行
				reset(request, response);
				break;
			case "clearBtn":
				// 全件削除メソッド実行
				clear(request, response);
				break;
			case "renewBtn":
				renew(request, response);
				break;
			}
		}
	}

	/**
	 * 更新
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void renew(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		DBAccess dbAccess = new DBAccess();
		dbAccess.executeRenew(request);

		this.getServletContext().getRequestDispatcher("/messageBoard.jsp")
				.forward(request, response);
	}

	/**
	 * 新規投稿
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void newInput(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		Data data = new Data();
		Date date = new Date(System.currentTimeMillis());
		data.setData(-1, "", "", "", "", date);

		// セッションスコープへセット。
		HttpSession session = request.getSession();
		session.setAttribute("DATA_SES", data);
		String num = "0";
		session.setAttribute("NUM_SES", num);

		this.getServletContext().getRequestDispatcher("/input.jsp")
				.forward(request, response);
	}

	/**
	 * 編集
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void edit(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String num = request.getParameter("num");
		String editPass = request.getParameter("editPass");

		// チェックメソッド実行
		String check = Check.executeEdit(num, editPass);
		if (check == "") {

			// 選択したボタンをセッションスコープにセット
			HttpSession session = request.getSession();
			session.setAttribute("BTN_SES", "editBtn");

			// 認証
			DBAccess dbAccess = new DBAccess();
			boolean auth = dbAccess.executeEdiDel(num, editPass, request);
			if (auth) {

				// セッションスコープへセット。

				session.setAttribute("NUM_SES", num);

				// 掲示板画面へ遷移。
				this.getServletContext().getRequestDispatcher("/input.jsp")
						.forward(request, response);

			} else {
				request.setAttribute("ERROR_REQ", "番号またはパスワードが違います");
				this.getServletContext()
						.getRequestDispatcher("/messageBoard.jsp")
						.forward(request, response);
			}

		} else {

			request.setAttribute("ERROR_REQ", check);
			this.getServletContext().getRequestDispatcher("/messageBoard.jsp")
					.forward(request, response);
		}
	}

	/**
	 * 削除
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void delete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String num = request.getParameter("num");
		String editPass = request.getParameter("editPass");

		// チェックメソッド実行
		String check = Check.executeEdit(num, editPass);
		if (check == "") {

			// 選択したボタンをセッションスコープにセット
			HttpSession session = request.getSession();
			session.setAttribute("BTN_SES", "deleteBtn");

			// 認証
			DBAccess dbAccess = new DBAccess();
			boolean auth = dbAccess.executeEdiDel(num, editPass, request);
			if (auth) {

				// 掲示板画面へ遷移。
				response.sendRedirect("/keijiban/messageBoard.jsp");

			} else {
				request.setAttribute("ERROR_REQ", "番号またはパスワードが違います");
				this.getServletContext()
						.getRequestDispatcher("/messageBoard.jsp")
						.forward(request, response);
			}

		} else {

			request.setAttribute("ERROR_REQ", check);
			this.getServletContext().getRequestDispatcher("/messageBoard.jsp")
					.forward(request, response);
		}
	}

	/**
	 * 選択された投稿とそのコメントを格納
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void comment(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String num = request.getParameter("commentBtn");
		int intNum = Integer.parseInt(num);

		DBAccess dbAccess = new DBAccess();
		dbAccess.executeCom(intNum, request);

		this.getServletContext().getRequestDispatcher("/comment.jsp")
				.forward(request, response);
	}

	/**
	 * 投稿番号のリセット
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void reset(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		DBAccess dbAccess = new DBAccess();
		dbAccess.executeReset();

		this.getServletContext().getRequestDispatcher("/messageBoard.jsp")
				.forward(request, response);
	}

	/**
	 * オールクリア
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void clear(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		String id = (String) session.getAttribute("ID_SES");

		if (id.equals(00000)) {

			DBAccess dbAccess = new DBAccess();
			dbAccess.executeClear(request);

			this.getServletContext().getRequestDispatcher("/messageBoard.jsp")
					.forward(request, response);
		}
	}
}