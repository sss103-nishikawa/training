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
 * ���e��ʂł̓���
 * 
 * @author Y.Nishikawa
 *
 */
public class InputServlet extends HttpServlet {

	/**
	 * �{�^���ʏ���
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		// ���������h�~����
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
	 * ���e
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	void input(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// ���͒l�̎擾
		String name = request.getParameter("name");
		String title = request.getParameter("title");
		String editPass = request.getParameter("editPass");
		String text = request.getParameter("text");

		// �`�F�b�N���\�b�h���s
		String check = Check.executeInput(name, title, editPass, text);
		if (check == "") {

			// DB�ւ̃A�N�Z�X
			DBAccess dbAccess = new DBAccess();
			dbAccess.executeInput(name, title, editPass, text, request);

			// �f����ʂ֑J�ځB
			this.getServletContext().getRequestDispatcher("/messageBoard.jsp")
					.forward(request, response);

		} else {

			request.setAttribute("ERROR_REQ", check);
			// �f����ʂ֑J�ځB
			this.getServletContext().getRequestDispatcher("/input.jsp")
					.forward(request, response);
		}
	}
}