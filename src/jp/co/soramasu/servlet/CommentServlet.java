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
 * �R�����g��ʂł̓���
 * 
 * @author Y.Nishikawa
 *
 */
public class CommentServlet extends HttpServlet {

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
				inputCom(request, response);
				break;
			} else if ("deleteBtn".equals(action)) {
				deleteCom(request, response);
				break;
			}
		}
	}

	/**
	 * �R�����g���e
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	void inputCom(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// ���͒l�̎擾
		String name = request.getParameter("name");
		String comment = request.getParameter("comment");

		// �`�F�b�N���\�b�h���s
		String check = Check.executeComment(name, comment);
		if (check == "") {

			// DB�ւ̃A�N�Z�X
			DBAccess dbAccess = new DBAccess();
			dbAccess.executeCom(name, comment, request);

			// ����ʂ֑J�ځB
			this.getServletContext().getRequestDispatcher("/comment.jsp")
					.forward(request, response);

		} else {

			request.setAttribute("INPUT_ERROR_REQ", check);
			// ����ʂ֑J�ځB
			this.getServletContext().getRequestDispatcher("/comment.jsp")
					.forward(request, response);
		}
	}

	/**
	 * �R�����g�폜
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void deleteCom(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String num = request.getParameter("num");

		// �`�F�b�N���\�b�h���s
		String check = Check.executeComDel(num);
		if (check == "") {

			// �F��
			DBAccess dbAccess = new DBAccess();
			dbAccess.deleteCom(num, request);

			// �f����ʂ֑J�ځB
			this.getServletContext().getRequestDispatcher("/comment.jsp")
					.forward(request, response);

		} else {
			request.setAttribute("DELETE_ERROR_REQ", check);
			this.getServletContext().getRequestDispatcher("/comment.jsp")
					.forward(request, response);
		}
	}
}