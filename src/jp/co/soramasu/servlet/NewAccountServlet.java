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
 * �A�J�E���g��ʂł̓���
 * 
 * @author Y.Nishikawa
 *
 */
public class NewAccountServlet extends HttpServlet {

	/**
	 * �A�J�E���g
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		// ���������h�~����
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

				// ���͒l�̎擾
				String id = request.getParameter("id");
				String loginPass = request.getParameter("loginPass");
				String name = request.getParameter("name");

				// �`�F�b�N���\�b�h���s
				String check = Check.executeLogin(id, loginPass);

				if (check == "") {

					// �F��
					DBAccess dbAccess = new DBAccess();
					boolean auth = dbAccess.executeAccount(id, loginPass, name,
							request);
					if (auth) {
						request.setAttribute("ERROR_REQ", "�A�J�E���g���o�^����܂���");
						// �f����ʂ֑J��
						this.getServletContext()
								.getRequestDispatcher("/login.jsp")
								.forward(request, response);

					} else {
						request.setAttribute("ERROR_REQ", "����ID�͂��łɎg���Ă��܂�");
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