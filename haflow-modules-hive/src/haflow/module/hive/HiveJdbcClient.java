package haflow.module.hive;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class HiveJdbcClient {
	public static String driverName = "org.apache.hadoop.hive.jdbc.HiveDriver";

	public static void execQuery(String uri, String sql, String separator, boolean printHead) throws SQLException{
		try {
			Class.forName(driverName);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.exit(1);
		}
		Connection con = DriverManager.getConnection(uri, "", "");
		Statement stmt = con.createStatement();		
		
		ResultSet res = stmt.executeQuery(sql);
		ResultSetMetaData resultSetMetaData = res.getMetaData();
		int columnCount = resultSetMetaData.getColumnCount();
		if( printHead){
			for( int i = 1; i <= columnCount; i++){
				System.out.print(resultSetMetaData.getColumnName(i) + separator);
			}
		}
		while (res.next()) {			
			for( int i = 1; i <= columnCount; i++){
				System.out.print(res.getString(i) + separator);
			}
			System.out.println();
		}
		con.close();
	}
	
	public static boolean execSql(String uri, String[] sqls) throws SQLException{
		try {
			Class.forName(driverName);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.exit(1);
		}
		Connection con = DriverManager.getConnection(uri, "", "");
		Statement stmt = con.createStatement();		
		for( String sql : sqls){
			stmt.execute(sql);
		}
		con.close();
		
		return true;
	}
	
	public static void main(String[] args) {
		if( args.length < 2){
			System.out.println("Invalid arguments!");
			System.exit(1);
		}
		String uri = args[0];
		String sql = args[1];
		try {
//			execQuery(uri, sql, ",", true);
			execSql(uri, sql.split(";"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}