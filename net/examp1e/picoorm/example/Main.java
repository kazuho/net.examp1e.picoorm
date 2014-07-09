package net.examp1e.picoorm.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

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

			// INSERT INTO member (name) VALUES (?)
			new Member().setName("yappo").insert(conn);
			new Member().setName("tokuhirom").insert(conn);
			new Member().setName("kazuho").insert(conn);

			// SELECT FROM member WHERE (name=?) OR (name=?)
			for (Member m : Member.name.is("yappo").or(Member.name.is("tokuhirom")).orderBy(Member.id.asc).search(conn)) {
				System.out.println(Long.toString(m.getId()) + ":" + m.getName());
			}

			// UPDATE member SET name=? WHERE name=?
			Member.name.is("yappo").update(conn, new Member().setName("seiitaishogun"));

			// SELECT FROM member WHERE id BETWEEN ? AND ?
			for (Member m: Member.id.between(1L, 1000L).limit(1).search(conn)) {
				System.out.println(Long.toString(m.getId()) + ":" + m.getName());
			}

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
