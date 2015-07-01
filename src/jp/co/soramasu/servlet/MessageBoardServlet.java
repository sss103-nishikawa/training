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
 * �ꗗ�\����ʂł̓���
 * 
 * @author Y.Nishikawa
 *
 */
public class MessageBoardServlet extends HttpServlet {

	/**
	 * �{�^���ʏ���
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
			case "logoutBtn":
				this.getServletContext().getRequestDispatcher("/login.jsp")
						.forward(request, response);
				break;
			case "newBtn":
				// �V�K���e���\�b�h���s
				newInput(request, response);
				break;
			case "editBtn":
				// �ҏW���\�b�h���s
				edit(request, response);
				break;
			case "deleteBtn":
				// �폜���\�b�h���s
				delete(request, response);
				break;
			case "commentBtn":
				// �R�����g���\�b�h���s
				comment(request, response);
				break;
			case "resetBtn":
				// ���e�ԍ����Z�b�g���\�b�h���s
				reset(request, response);
				break;
			case "clearBtn":
				// �S���폜���\�b�h���s
				clear(request, response);
				break;
			case "renewBtn":
				renew(request, response);
				break;
			}
		}
	}

	/**
	 * �X�V
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
	 * �V�K���e
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

		// �Z�b�V�����X�R�[�v�փZ�b�g�B
		HttpSession session = request.getSession();
		session.setAttribute("DATA_SES", data);
		String num = "0";
		session.setAttribute("NUM_SES", num);

		this.getServletContext().getRequestDispatcher("/input.jsp")
				.forward(request, response);
	}

	/**
	 * �ҏW
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

		// �`�F�b�N���\�b�h���s
		String check = Check.executeEdit(num, editPass);
		if (check == "") {

			// �I�������{�^�����Z�b�V�����X�R�[�v�ɃZ�b�g
			HttpSession session = request.getSession();
			session.setAttribute("BTN_SES", "editBtn");

			// �F��
			DBAccess dbAccess = new DBAccess();
			boolean auth = dbAccess.executeEdiDel(num, editPass, request);
			if (auth) {

				// �Z�b�V�����X�R�[�v�փZ�b�g�B

				session.setAttribute("NUM_SES", num);

				// �f����ʂ֑J�ځB
				this.getServletContext().getRequestDispatcher("/input.jsp")
						.forward(request, response);

			} else {
				request.setAttribute("ERROR_REQ", "�ԍ��܂��̓p�X���[�h���Ⴂ�܂�");
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
	 * �폜
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

		// �`�F�b�N���\�b�h���s
		String check = Check.executeEdit(num, editPass);
		if (check == "") {

			// �I�������{�^�����Z�b�V�����X�R�[�v�ɃZ�b�g
			HttpSession session = request.getSession();
			session.setAttribute("BTN_SES", "deleteBtn");

			// �F��
			DBAccess dbAccess = new DBAccess();
			boolean auth = dbAccess.executeEdiDel(num, editPass, request);
			if (auth) {

				// �f����ʂ֑J�ځB
				response.sendRedirect("/keijiban/messageBoard.jsp");

			} else {
				request.setAttribute("ERROR_REQ", "�ԍ��܂��̓p�X���[�h���Ⴂ�܂�");
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
	 * �I�����ꂽ���e�Ƃ��̃R�����g���i�[
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
	 * ���e�ԍ��̃��Z�b�g
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
	 * �I�[���N���A
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