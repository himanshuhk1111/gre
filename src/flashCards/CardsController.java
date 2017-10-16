package flashCards;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/CardsController")
public class CardsController extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession sess = request.getSession();
		HashSet<Integer> done = (HashSet<Integer>) sess.getAttribute("done");
		
		Cards cards = new Cards();
		cards.done = done;
		cards.pdfFilePath = getServletContext().getRealPath("/")+"resources/pdfs/500 Essential Words GRE Vocabulary Flash Cards.pdf";
		cards.setPageStart((int)sess.getAttribute("pageStart"));
		cards.setPageEnd((int)sess.getAttribute("pageEnd"));
		cards.setWordCount((int)sess.getAttribute("wordCount"));
		cards.setPageCount((int)sess.getAttribute("pageCount"));
		cards.setTotalPages();
		
		String ans = cards.getCards();
		
		sess.setAttribute("pageEnd", cards.getPageEnd());
		sess.setAttribute("pageStart", cards.getPageStart());
		sess.setAttribute("done", cards.done);
		sess.setAttribute("wordCount", cards.getWordCount());
		sess.setAttribute("pageCount", cards.getPageCount());
		
		PrintWriter out=response.getWriter();
		out.print(ans);
		
	}
	
}
