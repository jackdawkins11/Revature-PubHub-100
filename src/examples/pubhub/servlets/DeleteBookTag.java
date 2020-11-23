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
 * Servlet implementation class DeleteBookTag
 */
@WebServlet("/DeleteBookTag")
public class DeleteBookTag extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteBookTag() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String isbn = request.getParameter("isbn");
		
		BookDAO bookDAO = DAOUtilities.getBookDAO();
		Book book = bookDAO.getBookByISBN(isbn);
		
		BookTagDAO bookTagDAO = DAOUtilities.getBookTagDAO();
		BookTag bookTag = new BookTag(request.getParameter("tagName"));
		
		System.out.println("Deleting book tag book=\"" + String.valueOf(book)
		+ "\" bookTag=\"" + String.valueOf(bookTag) + "\"" );
		
		if( book == null ) {
			errorMessage(request, response);
			return;
		}
		
		if( bookTagDAO.removeBookTag(book, bookTag) ) {
			successMessage( request, response, isbn );
		}else {
			errorMessage(request, response);
		}
		
	}
	
	/*
	 * Renders the bookDetails page with an error message
	 */
	private void errorMessage(HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
		request.getSession().setAttribute("message", "There was an error deleting that book tag");
		request.getSession().setAttribute("messageClass", "alert-danger");
		request.getRequestDispatcher("bookDetails.jsp").forward(request, response);
	}
	
	/*
	 * Renders the bookDetails page with a success message
	 */
	private void successMessage(HttpServletRequest request, HttpServletResponse response, String isbn13 ) throws ServletException, IOException {
		request.getSession().setAttribute("message", "Book tag successfully deleted");
		request.getSession().setAttribute("messageClass", "alert-success");
		response.sendRedirect("ViewBookDetails?isbn13=" + isbn13);
	}

}
