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
 * ���O�C����ʂł̓���
 * 
 * @author Y.Nishikawa
 *
 */
public class LoginServlet extends HttpServlet {

	/**
	 * ���O�C���F��
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		// ���������h�~����
		request.setCharacterEncoding("Windows-31J");

		// ���͒l�̎擾
		String id = request.getParameter("id");
		String loginPass = request.getParameter("loginPass");

		// �`�F�b�N���\�b�h���s
		String check = Check.executeLogin(id, loginPass);

		if (check == "") {

			// �F��
			DBAccess dbAccess = new DBAccess();
			boolean auth = dbAccess.executeLogin(id, loginPass, request);
			if (auth) {

				if (id.equals("00000")) {
					HttpSession session = request.getSession();
					session.setAttribute("ID_SES", id);
				}
				// �f����ʂ֑J��
				this.getServletContext()
						.getRequestDispatcher("/messageBoard.jsp")
						.forward(request, response);

			} else {
				request.setAttribute("ERROR_REQ", "ID�܂��̓p�X���[�h���Ⴂ�܂�");
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