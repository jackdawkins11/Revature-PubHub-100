package examples.pubhub.dao;

import java.util.List;

import examples.pubhub.model.Book;

/*
 * Interface to a Data Access Object handling queries related to book tags.
 */
public interface BookTagDAO {
	
	/*
	 * Adds a tag to a book. Returns boolean indicating success.
	 */
	public boolean addBookTag( Book book, String tagName );
	public boolean addBookTag( String isbn, String tagName );

	/*
	 * Removes a tag from a book. Returns boolean indicating success.
	 */
	public boolean removeBookTag( Book book, String tagName );
	public boolean removeBookTag( String isbn, String tagName );
	
	/*
	 * Gets all the tags for a given book.
	 */
	List<String> getTagsByBook( Book book );
	List<String> getTagsByBook( String isbn );
	
	/*
	 * Gets all books for a given tag.
	 */
	List<Book> getBooksByTag( String tagName );

	
}
