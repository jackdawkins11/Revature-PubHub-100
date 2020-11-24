package examples.pubhub.utilities;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Arrays;

public class InstallDatabase {

	public static void main(String args[]) {
		
		String sql[] = {
				"drop table if exists book_tags",
				"drop table if exists books",
				"create table books ( "
				+ "  isbn_13 varchar (13) primary key, "
				+ "  title varchar (100), "
				+ "  author varchar (80), "
				+ "  publish_date date, "
				+ "  price decimal (6,2), "
				+ "  content bytea "
				+ ")",
				"create table book_tags ( "
				+ "	isbn_13 varchar(13) references books(isbn_13), "
				+ "	tag_name varchar(100) "
				+ ")"
		};
		
		System.out.println("Executing: \"" + Arrays.toString(sql));
		
		Connection conn = null;
		Statement stmt = null;
		
		boolean success = false;
		try {
			conn = DAOUtilities.getConnection();

			for(String cSql : sql ) {
				stmt = conn.createStatement();
				stmt.execute(cSql);
			}
			
			success = true;
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("success: " + String.valueOf(success));
	}
}
