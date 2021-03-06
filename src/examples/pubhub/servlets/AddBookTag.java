package examples.pubhub.servlets;

import java.io.IOException;
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

/**
 * Servlet implementation class AddBookTag
 */
@WebServlet("/AddBookTag")
public class AddBookTag extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddBookTag() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String isbn13 = request.getParameter("isbn13");
		
		BookDAO bookDAO = DAOUtilities.getBookDAO();
		BookTagDAO bookTagDAO = DAOUtilities.getBookTagDAO();

		Book book = bookDAO.getBookByISBN(isbn13);
		
		BookTag bookTag = new BookTag(request.getParameter("tagName"));
		
		//Log the data used in the servlet
		System.out.println("Adding book tag book=\"" + String.valueOf(book)
		+ "\" bookTag=\"" + String.valueOf(bookTag) + "\"" );
		
		//success will be false when book == null or we couldn't add a book tag with the given book
		//and the given tag name
		boolean isSuccess = false;
		
		if(book != null) {
			isSuccess = bookTagDAO.addBookTag(book, bookTag);
		}
		
		//On success, render the detail page for the book specified
		//On failure, render the detail page with an error message
		if(isSuccess){
			request.getSession().setAttribute("message", "Book tag successfully added");
			request.getSession().setAttribute("messageClass", "alert-success");
			response.sendRedirect("ViewBookDetails?isbn13=" + isbn13);
		}else {
			request.getSession().setAttribute("message", "There was a problem adding this book tag");
			request.getSession().setAttribute("messageClass", "alert-danger");
			request.getRequestDispatcher("bookDetails.jsp").forward(request, response);
		}
	}

}
