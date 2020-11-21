package examples.pubhub.model;

/*
 * Represents a book tag.
 */
public class BookTag {
	
	/*
	 * Book tags only store a string.
	 */
	private String tagName;
	
	/*
	 * Create a new BookTag
	 */
	public BookTag( String tagName ) {
		this.tagName = tagName;
	}
	
	/*
	 * Get the tag name.
	 */
	public String getTagName() {
		return tagName;
	}
	
	/*
	 * Get a string describing the BookTag.
	 */
	@Override
	public String toString() {
		return "Book tag: " + tagName;
	}
}
