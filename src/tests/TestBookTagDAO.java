package tests;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import examples.pubhub.dao.BookDAO;
import examples.pubhub.dao.BookTagDAO;
import examples.pubhub.model.Book;
import examples.pubhub.model.BookTag;
import examples.pubhub.utilities.DAOUtilities;

public class TestBookTagDAO {

	/*
	 * This function prints the book_tags table.
	 */
	public static void printAllBookTags() {
		
		PreparedStatement statement = null;
		Connection conn = null;
		
		try {
			conn = DAOUtilities.getConnection();
			String sql = "select * from book_tags";

			System.out.println("Executing: \"" + sql + "\"");

			statement = conn.prepareStatement("select * from book_tags");

			ResultSet rs = statement.executeQuery();

			int cnt = 0;
			while (rs.next()) {
				String isbn = rs.getString("isbn_13");
				String tagName = rs.getString("tag_name");
				System.out.println("Row: (" + isbn + ", " + tagName + ")");
				cnt++;
			}
			System.out.println("Found " + cnt + " rows.");

		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			DAOUtilities.closeResources(conn, statement);
		}
	}
	
	/*
	 * This function minimally tests BookTagDAO.
	 * First, addBookTag and removeBookTag are tested by showing the contents
	 * of the book_tags table before and after the functions are called.
	 * The getTagsByBook and getBooksByTag methods are tested by simply
	 * displaying there output, which can be compared with the DB.
	 */
	
	public static void main(String[] args) {
		
		System.out.println("Testing BookTagDAO");
		
		BookTagDAO bookTagDao = DAOUtilities.getBookTagDAO();
		BookDAO bookDao = DAOUtilities.getBookDAO();

		Book lordOfTheRings = bookDao.getBookByISBN("1111111111112");
		BookTag lordOfTheRingsTag = new BookTag( "Best seller" );
		
		System.out.println("Created book " + lordOfTheRings.getTitle() );
		
		printAllBookTags();
		
		bookTagDao.addBookTag(lordOfTheRings, lordOfTheRingsTag);
		
		printAllBookTags();
		
		bookTagDao.removeBookTag(lordOfTheRings, lordOfTheRingsTag);

		printAllBookTags();
		
		List<BookTag> bookTags = bookTagDao.getTagsByBook(lordOfTheRings);
		
		for( BookTag bookTag : bookTags ) {
			System.out.println("Lord of the rings tag: " + bookTag.getTagName());
		}
		
		List<Book> books = bookTagDao.getBooksByTag( new BookTag("Fiction"));
		
		for( Book book : books ) {
			System.out.println("Fiction: " + book.getTitle());
		}

	}

}
