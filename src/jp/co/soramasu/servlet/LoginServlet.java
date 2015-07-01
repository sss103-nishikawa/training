package jp.co.soramasu.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.co.soramasu.common.Check;
import jp.co.soramasu.common.DBAccess;

/**
 * ログイン画面での動作
 * 
 * @author Y.Nishikawa
 *
 */
public class LoginServlet extends HttpServlet {

	/**
	 * ログイン認証
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		// 文字化け防止処理
		request.setCharacterEncoding("Windows-31J");

		// 入力値の取得
		String id = request.getParameter("id");
		String loginPass = request.getParameter("loginPass");

		// チェックメソッド実行
		String check = Check.executeLogin(id, loginPass);

		if (check == "") {

			// 認証
			DBAccess dbAccess = new DBAccess();
			boolean auth = dbAccess.executeLogin(id, loginPass, request);
			if (auth) {

				if (id.equals("00000")) {
					HttpSession session = request.getSession();
					session.setAttribute("ID_SES", id);
				}
				// 掲示板画面へ遷移
				this.getServletContext()
						.getRequestDispatcher("/messageBoard.jsp")
						.forward(request, response);

			} else {
				request.setAttribute("ERROR_REQ", "IDまたはパスワードが違います");
				this.getServletContext().getRequestDispatcher("/login.jsp")
						.forward(request, response);
			}

		} else {

			request.setAttribute("ERROR_REQ", check);
			this.getServletContext().getRequestDispatcher("/login.jsp")
					.forward(request, response);
		}
	}
}