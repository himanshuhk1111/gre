package flashCards;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
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
		int cid = Integer.parseInt(request.getParameter("cid"));
		ArrayList<String> words = (ArrayList<String>) sess.getAttribute("words");
		String ans = "";
		Cards cards = new Cards();
		
		if(cid<=0){
			cardBean cb = new cardBean();
			cb.setStatus(false);
			ans = cards.convertToJson(cb);
		}
		else if(words.size()>=cid)
		{
			ans = words.get(cid-1);
		}
		else
		{
			cards.done = done;
			cards.pdfFilePath = getServletContext().getRealPath("/")+"resources/pdfs/500 Essential Words GRE Vocabulary Flash Cards.pdf";
			cards.setPageStart((int)sess.getAttribute("pageStart"));
			cards.setPageEnd((int)sess.getAttribute("pageEnd"));
			cards.setWordCount((int)sess.getAttribute("wordCount"));
			cards.setPageCount((int)sess.getAttribute("pageCount"));
			cards.words = words;
			cards.setTotalPages();
			
			ans = cards.getCards();
			
			sess.setAttribute("pageEnd", cards.getPageEnd());
			sess.setAttribute("pageStart", cards.getPageStart());
			sess.setAttribute("done", cards.done);
			sess.setAttribute("wordCount", cards.getWordCount());
			sess.setAttribute("pageCount", cards.getPageCount());
			sess.setAttribute("words", cards.words);
		}
		
		response.setCharacterEncoding("UTF-8"); // so that encode string properly
		PrintWriter out = response.getWriter();
		out.print(ans);
	}
}
