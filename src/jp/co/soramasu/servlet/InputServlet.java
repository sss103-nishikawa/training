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
 * 投稿画面での動作
 * 
 * @author Y.Nishikawa
 *
 */
public class InputServlet extends HttpServlet {

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
				input(request, response);
				break;
			}
		}
	}

	/**
	 * 投稿
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	void input(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 入力値の取得
		String name = request.getParameter("name");
		String title = request.getParameter("title");
		String editPass = request.getParameter("editPass");
		String text = request.getParameter("text");

		// チェックメソッド実行
		String check = Check.executeInput(name, title, editPass, text);
		if (check == "") {

			// DBへのアクセス
			DBAccess dbAccess = new DBAccess();
			dbAccess.executeInput(name, title, editPass, text, request);

			// 掲示板画面へ遷移。
			this.getServletContext().getRequestDispatcher("/messageBoard.jsp")
					.forward(request, response);

		} else {

			request.setAttribute("ERROR_REQ", check);
			// 掲示板画面へ遷移。
			this.getServletContext().getRequestDispatcher("/input.jsp")
					.forward(request, response);
		}
	}
}