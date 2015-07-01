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
 * アカウント画面での動作
 * 
 * @author Y.Nishikawa
 *
 */
public class NewAccountServlet extends HttpServlet {

	/**
	 * アカウント
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
			case "returnBtn":
				this.getServletContext().getRequestDispatcher("/login.jsp")
						.forward(request, response);
				break;
			case "accountBtn":

				// 入力値の取得
				String id = request.getParameter("id");
				String loginPass = request.getParameter("loginPass");
				String name = request.getParameter("name");

				// チェックメソッド実行
				String check = Check.executeLogin(id, loginPass);

				if (check == "") {

					// 認証
					DBAccess dbAccess = new DBAccess();
					boolean auth = dbAccess.executeAccount(id, loginPass, name,
							request);
					if (auth) {
						request.setAttribute("ERROR_REQ", "アカウントが登録されました");
						// 掲示板画面へ遷移
						this.getServletContext()
								.getRequestDispatcher("/login.jsp")
								.forward(request, response);

					} else {
						request.setAttribute("ERROR_REQ", "そのIDはすでに使われています");
						this.getServletContext()
								.getRequestDispatcher("/newAccount.jsp")
								.forward(request, response);
					}

				} else {

					request.setAttribute("ERROR_REQ", check);
					this.getServletContext()
							.getRequestDispatcher("/newAccount.jsp")
							.forward(request, response);
				}
				break;
			}
		}
	}
}