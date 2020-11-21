package tests;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import examples.pubhub.dao.BookDAO;
import examples.pubhub.dao.BookDAOImpl;
import examples.pubhub.dao.BookTagDAO;
import examples.pubhub.dao.BookTagDAOImpl;
import examples.pubhub.model.Book;
import examples.pubhub.model.BookTag;
import examples.pubhub.utilities.DAOUtilities;

public class TestBookTagDAO {
	
	static PreparedStatement statement = null;
	static Connection conn = null;

	public static void printAllBookTags() {
		
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
			closeResources();
		}
	}
	
	private static void closeResources() {
		try {
			if (statement != null)
				statement.close();
		} catch (SQLException e) {
			System.out.println("Could not close statement!");
			e.printStackTrace();
		}

		try {
			if (conn != null)
				conn.close();
		} catch (SQLException e) {
			System.out.println("Could not close connection!");
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		
		BookTagDAO bookTagDao = new BookTagDAOImpl();
		BookDAO bookDao = new BookDAOImpl();

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
