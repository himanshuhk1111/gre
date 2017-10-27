package flashCards;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/StartApp")
public class StartApp extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		System.out.println("in here doget");
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		int pageEnd = Integer.parseInt(request.getParameter("pageEnd"));
		int pageStart = Integer.parseInt(request.getParameter("pageStart"));
		
		System.out.println("here "+pageStart+" "+pageEnd);
		
		HashSet<Integer> done = new HashSet<Integer>();
		
		HttpSession sess = request.getSession();
		sess.setAttribute("pageEnd", pageEnd);
		sess.setAttribute("pageStart", pageStart);
		sess.setAttribute("done", done);
		sess.setAttribute("wordCount", 0);
		sess.setAttribute("pageCount", 0);
		sess.setAttribute("words", new ArrayList<String>());
		response.sendRedirect("cards.html");
	}

}
