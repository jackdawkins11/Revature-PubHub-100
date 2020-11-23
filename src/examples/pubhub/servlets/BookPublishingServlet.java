package examples.pubhub.servlets;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import examples.pubhub.dao.BookDAO;
import examples.pubhub.dao.BookTagDAO;
import examples.pubhub.model.Book;
import examples.pubhub.model.BookTag;
import examples.pubhub.utilities.DAOUtilities;

/*
 * This servlet will take you to the homepage for the Book Publishing module (level 100)
 */
@WebServlet("/BookPublishing")
public class BookPublishingServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		BookDAO dao = DAOUtilities.getBookDAO();
		BookTagDAO bookTagDAO = DAOUtilities.getBookTagDAO();
		
		List<Book> bookList = null;
		
		String tagName = request.getParameter("tagName");
		
		if( isValidTagName(tagName) ) {
			BookTag bookTag = new BookTag( tagName );
			bookList = bookTagDAO.getBooksByTag(bookTag);
		}else {
			bookList = dao.getAllBooks();
		}
		
		System.out.println("Rendering BookPublishing bookList=" + String.valueOf(bookList)
			+ " tagName=" + String.valueOf(tagName) );

		// Populate the list into a variable that will be stored in the session
		request.getSession().setAttribute("books", bookList);
		
		request.getRequestDispatcher("bookPublishingHome.jsp").forward(request, response);
	}
	
	private boolean isValidTagName( String tagName ) {
		
		if( tagName == null ) {
			return false;
		}
		
		Pattern pattern = Pattern.compile("[A-Za-z]+");
		Matcher matcher = pattern.matcher(tagName);
		
		return matcher.find();
	}
}
