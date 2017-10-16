package flashCards;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.SynchronousQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.snowtide.PDF;
import com.snowtide.pdf.Document;
import com.snowtide.pdf.OutputTarget;
import com.sun.org.apache.bcel.internal.util.ClassPath;



public class Cards {

	String pdfFilePath ="";
	Random r = new Random();
	HashSet<Integer> done;
	
	
	private int pageStart;
	private int pageEnd;
	private int totalPages;
	private int wordCount;
	private int pageCount;
	
	public int getTotalPages() {
		return totalPages;
	}
	
	public void setTotalPages() {
		this.totalPages = pageEnd - pageStart + 1;
	}
	
	public int getPageCount() {
		return pageCount;
	}
	
	
	public void setPageCount(int x) {
		this.pageCount = x;
	}
	
	public int getWordCount() {
		return wordCount;
	}
	
	public void setWordCount(int x) {
		this.wordCount = x;
	}
	
	 // include the extremes also
	
	public int getPageStart() {
		return pageStart;
	}
	
	public void setPageStart(int x) {
		this.pageStart = x;
	}
	
	public void setPageEnd(int x) {
		this.pageEnd = x;
	}
	
	public int getPageEnd() {
		return pageEnd;
	}

	public String convertToJson(cardBean card){
		ObjectMapper mapper = new ObjectMapper();
		String res="";
		try {
			res = mapper.writeValueAsString(card);
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}
	
	@SuppressWarnings("finally")
	public String getCards() throws IOException{
		//pdfFilePath = "D:/jatin/gre/500 Essential Words GRE Vocabulary Flash Cards.pdf";
		//System.out.println(ClassPath.getClassPath());
//		System.out.println();
		//pdfFilePath = "500 Essential Words GRE Vocabulary Flash Cards.pdf";
		Document pdf = PDF.open(pdfFilePath);
		
		cardBean card = new cardBean();
		if(totalPages<=pageCount){
        	card.setStatus(false);
        	return convertToJson(card);
        }
		
		String resText="";
		int page = -1;
		
		System.out.println(totalPages);
		
		while(page == -1||done.contains(page)){
			page = r.nextInt(totalPages) + pageStart;
			if(page%2!=0)
				page--;
		}
		
		card.setStatus(true);
        pageCount+=2;
        wordCount++;
        card.setCid(wordCount);
        this.setPageCount(pageCount);
        done.add(page);
		
		try{
			System.out.println(page);
	    	StringBuilder text = new StringBuilder(1024);
	    	pdf.getPage(page-1).pipe(new OutputTarget(text));
	        resText = text.toString().trim();
	        card.setPageNo(page);
	        
	        Scanner sc = new Scanner(resText);
	        
	        card.setWord(sc.next());
	        card.setType(sc.next());
	        card.setOther(sc.next());
	        
	        
	        System.out.println("**************************************************************************************");
	        System.out.println("**************************************************************************************");
	        System.out.println(wordCount+"                          "+resText+"                             ");
	        System.out.println("--------------------------------------------------------------------------------------");
	        //String n = sc.next();
	        
	        text = new StringBuilder(1024);
	        pdf.getPage(page).pipe(new OutputTarget(text));
	        
	        
	        
	        
	        int start = 0;
	        int end = text.indexOf("Usage:");
	        card.setDef(text.substring(start,end).trim());
	        
	        start = text.indexOf("Usage:");
	        end = text.indexOf("Related Words:");
	        card.setUsage(text.substring(start,end).trim());
	        
	        System.out.println(card.getWord());
	        
	        
	        start = text.indexOf("Related Words:");
	        end = text.indexOf("More Info:");
	        card.setRelWords(text.substring(start,end).trim());
	        
	        start = text.indexOf("More Info:");
	        card.setInfo(text.substring(start,text.length()).trim());
	        
	        
	        
	        
	        resText = text.toString();
	        //System.out.println(resText);
	        
	        //resText = this.convertToJson(card);

			
		}
		catch(Exception e){
			e.getMessage();
		}
		finally{
			pdf.close();
			return this.convertToJson(card);
		}
	}
	/*public static void main(String[] args) {
		Cards c = new Cards();
		c.pageStart = 10;
		c.pageEnd = 20;
		c.setTotalPages();
		System.out.println(c.getCards());
	}*/
}
