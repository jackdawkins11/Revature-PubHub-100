package examples.pubhub.dao;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


import examples.pubhub.model.Book;
import examples.pubhub.model.BookTag;
import examples.pubhub.utilities.DAOUtilities;

public class BookTagDAOImpl implements BookTagDAO {
	
	Connection connection = null;
	PreparedStatement stmt = null;

	/*
	 * Adds a tag to a book. Returns boolean indicating success.
	 */
	
	@Override
	public boolean addBookTag(Book book, BookTag bookTag) {
		return addBookTag( book.getIsbn13(), bookTag );
	}

	@Override
	public boolean addBookTag(String isbn, BookTag bookTag) {
		
		try {
			connection = DAOUtilities.getConnection();
			
			String sql = "insert into book_tags values (?, ?)";
			stmt = connection.prepareStatement(sql);
			stmt.setString(1, isbn);
			stmt.setString(2, bookTag.getTagName());
			
			int rc = stmt.executeUpdate();
			return rc == 1;
		
		} catch (Exception e) {
			e.printStackTrace();
			return false;
			
		}finally {
			DAOUtilities.closeResources(connection, stmt);
		}
	}

	/*
	 * Removes a tag from a book. Returns boolean indicating success.
	 */
	@Override
	public boolean removeBookTag(Book book, BookTag bookTag) {
		return removeBookTag(book.getIsbn13(), bookTag);
	}

	@Override
	public boolean removeBookTag(String isbn, BookTag bookTag) {
		try {	
			connection = DAOUtilities.getConnection();
			
			String sql = "delete from book_tags where isbn_13 = ? and tag_name = ?";
			stmt = connection.prepareStatement(sql);
			stmt.setString(1, isbn);
			stmt.setString(2, bookTag.getTagName());
			
			int rc = stmt.executeUpdate();
			return true;
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
			
		}finally {
			DAOUtilities.closeResources(connection, stmt);
		}
	}

	/*
	 * Gets all the tags for a given book.
	 */
	@Override
	public List<BookTag> getTagsByBook(Book book) {
		return getTagsByBook( book.getIsbn13() );
	}

	@Override
	public List<BookTag> getTagsByBook(String isbn) {
		
		List<BookTag> bookTags = new ArrayList<>();
		
		try {
			connection = DAOUtilities.getConnection();
			String sql = "select tag_name from book_tags where isbn_13 = ?";
			stmt = connection.prepareStatement(sql);
			stmt.setString(1, isbn);
			
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				BookTag bookTag = new BookTag(rs.getString("tag_name"));
				bookTags.add(bookTag);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			
		}finally {
			DAOUtilities.closeResources(connection, stmt);
		}
		return bookTags;
	}
	
	/*
	 * Gets all books for a given tag.
	 */
	@Override
	public List<Book> getBooksByTag(BookTag bookTag) {
		
		List<Book> books = new ArrayList<>();

		try {
			connection = DAOUtilities.getConnection();
			
			String sql = "select books.* from book_tags inner join books on book_tags.isbn_13 = books.isbn_13 where book_tags.tag_name = ?";
			stmt = connection.prepareStatement(sql);
			stmt.setString(1, bookTag.getTagName());
			
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				Book book = new Book();

				book.setIsbn13(rs.getString("isbn_13"));
				book.setAuthor(rs.getString("author"));
				book.setTitle(rs.getString("title"));
				
				// The SQL DATE datetype maps to java.sql.Date... which isn't well supported anymore. 
				// We use a LocalDate instead, because this is Java 8.
				book.setPublishDate(rs.getDate("publish_date").toLocalDate());
				book.setPrice(rs.getDouble("price"));
				
				// The PDF file is tricky; file data is stored in the DB as a BLOB - Binary Large Object. It's
				// literally stored as 1's and 0's, so this one data type can hold any type of file.
				book.setContent(rs.getBytes("content"));
				
				books.add(book);
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			DAOUtilities.closeResources(connection, stmt);
		}
		
		return books;
	}

}
