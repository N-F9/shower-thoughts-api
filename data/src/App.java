import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.*;

public class App {
	public static String url = "jdbc:postgresql://localhost:5432/showerthoughts";
	public static String user = "postgres";
	public static String password = System.getenv("password");

	public static void main(String[] args) throws Exception {
		Dotenv dotenv = Dotenv.load();
		password = dotenv.get("password");

		Properties props = new Properties();
		props.setProperty("user", user);
		props.setProperty("password", password);
		Connection conn = DriverManager.getConnection(url, props);

		PreparedStatement st = conn.prepareStatement("CREATE TABLE IF NOT EXISTS thoughts (" +
				"title varchar(255)," +
				"created date," +
				"over_18 boolean," +
				"id varchar(255) UNIQUE," +
				"author varchar(255)," +
				"permalink varchar(255)" +
				");");

		st.execute();

		conn.close();

		Runnable runnable = new Runnable() {
			public void run() {
				try {
					getPosts();
					System.out.println("Success");
					System.out.println("------------------");
				} catch (Exception e) {
					System.out.println(e);
					System.out.println("------------------");
				}
			}
		};
		ScheduledExecutorService exec = Executors.newScheduledThreadPool(1);
		exec.scheduleAtFixedRate(runnable, 0, 90, TimeUnit.SECONDS);
	}

	public static void getPosts() throws SQLException, MalformedURLException, IOException, ParseException {
		Properties props = new Properties();
		props.setProperty("user", user);
		props.setProperty("password", password);
		Connection conn = DriverManager.getConnection(url, props);

		try {
			String stringJson = stream(new URL("https://www.reddit.com/r/Showerthoughts/new.json?limit=10"));
			JSONParser parser = new JSONParser();
			JSONObject json = (JSONObject) parser.parse(stringJson);
			JSONArray children = (JSONArray) ((JSONObject) json.get("data")).get("children");
			for (int i = 0; i < children.size(); i++) {
				JSONObject child = (JSONObject) ((JSONObject) children.get(i)).get("data");

				PreparedStatement stc = conn.prepareStatement("SELECT COUNT(1) FROM thoughts WHERE id = ?;");
				stc.setString(1, (String) child.get("id"));

				ResultSet rsc = stc.executeQuery();

				if (rsc.next() && !rsc.getBoolean(1)) {
					PreparedStatement st = conn.prepareStatement("INSERT INTO thoughts VALUES (?, ?, ?, ?, ?, ?);");

					if(((String) child.get("title")).length() > 255) {
						continue;
					}

					st.setString(1, (String) child.get("title"));
					st.setDate(2, new java.sql.Date((long) ((double) child.get("created") * 1000)));
					st.setBoolean(3, (boolean) child.get("over_18"));
					st.setString(4, (String) child.get("id"));
					st.setString(5, (String) child.get("author"));
					st.setString(6, (String) child.get("permalink"));

					st.execute();
				}
			}
			conn.close();
		} catch (Exception e) {
			System.out.println(e);
			conn.close();
			throw e;
		}
	}

	public static String stream(URL url) throws IOException {
		try (InputStream input = url.openStream()) {
			InputStreamReader isr = new InputStreamReader(input);
			BufferedReader reader = new BufferedReader(isr);
			StringBuilder json = new StringBuilder();
			int c;
			while ((c = reader.read()) != -1) {
				json.append((char) c);
			}
			return json.toString();
		}
	}
}
