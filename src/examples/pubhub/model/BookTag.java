package examples.pubhub.model;

/*
 * Represents a book tag.
 */
public class BookTag {
	
	private String tagName;
	
	public BookTag( String tagName ) {
		this.tagName = tagName;
	}
	
	public String getTagName() {
		return tagName;
	}
	
	@Override
	public String toString() {
		return "Book tag: " + tagName;
	}
}
