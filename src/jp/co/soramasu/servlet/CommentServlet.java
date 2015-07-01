package jp.co.soramasu.servlet;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.soramasu.common.Check;
import jp.co.soramasu.common.DBAccess;

/**
 * コメント画面での動作
 * 
 * @author Y.Nishikawa
 *
 */
public class CommentServlet extends HttpServlet {

	/**
	 * ボタン別処理
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		// 文字化け防止処理
		request.setCharacterEncoding("Windows-31J");

		Enumeration<String> en = request.getParameterNames();

		while (en.hasMoreElements()) {
			String action = en.nextElement();

			if ("returnBtn".equals(action)) {
				this.getServletContext()
						.getRequestDispatcher("/messageBoard.jsp")
						.forward(request, response);
				break;

			} else if ("inputBtn".equals(action)) {
				inputCom(request, response);
				break;
			} else if ("deleteBtn".equals(action)) {
				deleteCom(request, response);
				break;
			}
		}
	}

	/**
	 * コメント投稿
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	void inputCom(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 入力値の取得
		String name = request.getParameter("name");
		String comment = request.getParameter("comment");

		// チェックメソッド実行
		String check = Check.executeComment(name, comment);
		if (check == "") {

			// DBへのアクセス
			DBAccess dbAccess = new DBAccess();
			dbAccess.executeCom(name, comment, request);

			// 自画面へ遷移。
			this.getServletContext().getRequestDispatcher("/comment.jsp")
					.forward(request, response);

		} else {

			request.setAttribute("INPUT_ERROR_REQ", check);
			// 自画面へ遷移。
			this.getServletContext().getRequestDispatcher("/comment.jsp")
					.forward(request, response);
		}
	}

	/**
	 * コメント削除
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void deleteCom(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String num = request.getParameter("num");

		// チェックメソッド実行
		String check = Check.executeComDel(num);
		if (check == "") {

			// 認証
			DBAccess dbAccess = new DBAccess();
			dbAccess.deleteCom(num, request);

			// 掲示板画面へ遷移。
			this.getServletContext().getRequestDispatcher("/comment.jsp")
					.forward(request, response);

		} else {
			request.setAttribute("DELETE_ERROR_REQ", check);
			this.getServletContext().getRequestDispatcher("/comment.jsp")
					.forward(request, response);
		}
	}
}