package net.examp1e.picoorm.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Iterator;

public class Main {

	public static void main(String[] args) {
		try {
			// setup
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager.getConnection("jdbc:sqlite:/tmp/sample.db");
			Statement stmt = conn.createStatement();
			stmt.executeUpdate("drop table if exists member");
			stmt.executeUpdate("create table member (id integer primary key,name string not null)");
			stmt.executeUpdate("create unique index name on member (name)");

			new Member().setName("yappo").insert(conn);
			new Member().setName("tokuhirom").insert(conn);
			new Member().setName("kazuho").insert(conn);

			Iterator<Member> iter = Member.name.is("yappo").or(Member.name.is("tokuhirom")).search(conn).iterator();
			while (iter.hasNext()) {
				Member m = iter.next();
				System.out.println(Long.toString(m.getId()) + ":" + m.getName());
			}

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
