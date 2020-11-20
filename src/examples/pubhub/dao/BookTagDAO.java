package examples.pubhub.dao;

import java.util.List;

import examples.pubhub.model.Book;
import examples.pubhub.model.BookTag;

/*
 * Interface to a Data Access Object handling queries related to book tags.
 */
public interface BookTagDAO {
	
	/*
	 * Adds a tag to a book. Returns boolean indicating success.
	 */
	public boolean addBookTag( Book book, BookTag bookTag );
	public boolean addBookTag( String isbn, BookTag bookTag );

	/*
	 * Removes a tag from a book. Returns boolean indicating success.
	 */
	public boolean removeBookTag( Book book, BookTag bookTag );
	public boolean removeBookTag( String isbn, BookTag bookTag );
	
	/*
	 * Gets all the tags for a given book.
	 */
	List<BookTag> getTagsByBook( Book book );
	List<BookTag> getTagsByBook( String isbn );
	
	/*
	 * Gets all books for a given tag.
	 */
	List<Book> getBooksByTag( BookTag bookTag );

	
}
