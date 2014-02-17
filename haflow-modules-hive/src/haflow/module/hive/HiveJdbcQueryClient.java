package haflow.module.hive;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.tools.ant.filters.StringInputStream;

public class HiveJdbcQueryClient {
	public static String driverName = "org.apache.hadoop.hive.jdbc.HiveDriver";

	public static void main(String[] args) {
		if( args.length < 3){
			System.out.println("Invalid arguments!");
			System.exit(1);
		}
		String uri = args[0];
		String sql = args[1];
		String output = args[2];
		try {
			execQuery(uri, sql, ",", true, output);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void execQuery(String uri, String sql, String separator, boolean printHead, String outPath) throws SQLException{
		try {
			Class.forName(driverName);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.exit(1);
		}
		Connection con = DriverManager.getConnection(uri, "", "");
		Statement stmt = con.createStatement();		
		//convert xml sql to formatted sql style
		String newSql=XmlFilter.decodeXml(sql);
		ResultSet res = stmt.executeQuery(newSql);
		ResultSetMetaData resultSetMetaData = res.getMetaData();
		int columnCount = resultSetMetaData.getColumnCount();
		StringBuilder sb = new StringBuilder();
		if( printHead){
			for( int i = 1; i <= columnCount; i++){
				sb.append(resultSetMetaData.getColumnName(i) + separator);
			}
			sb.append("\n");
		}
		while (res.next()) {			
			for( int i = 1; i <= columnCount; i++){
				sb.append(res.getString(i) + separator);
			}
			sb.append("\n");
		}
		writeFile(sb.toString(), outPath);
		con.close();
	}
	
	private static boolean writeFile(String content, String remotePath) {
		try {
			InputStream in = new BufferedInputStream(new StringInputStream(
					content));
			FileSystem fs = getFileSystem();
			Path output = new Path(remotePath);
			if( fs.exists(output)){
				deleteFile(remotePath);
			}
			OutputStream out = fs.create(output);
			IOUtils.copyBytes(in, out, 4096, true);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	private static FileSystem getFileSystem() throws IOException {
		Configuration conf = new Configuration();
		conf.set("fs.default.name","hdfs://m150:9000");
		return FileSystem.get(conf);
	}
	
	private static boolean deleteFile(String remotePath) {
		try {
			FileSystem fs = getFileSystem();
			return fs.delete(new Path(remotePath), false);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}