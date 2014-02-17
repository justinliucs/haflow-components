package haflow.module.hive;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class HiveJdbcClientTest {
	public static void main(String[] args) throws SQLException {
//		com.facebook.fb303.FacebookService s;
		try {
			Class.forName(HiveJdbcClient.driverName);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.exit(1);
		}
		Connection con = DriverManager.getConnection(
				"jdbc:hive://133.133.2.150:10000/default", "", "");
		System.out.println("hehe");
		Statement stmt = con.createStatement();
		ResultSet res=stmt.executeQuery("show tables");
		while(res.next()){
			System.out.println(res.getString(1));
		}
		String sql1="select v.rname, v.commoney " +
		"from (select round(sum(a1.commoney)/10000,2) commoney, 1 areacode, 'live in hospital' rname "+
            "from fact_mv_hosp_areacode a1 "+
            "where a1.comdate>'2013-01-01' "+
            "and  a1.comdate<'2013-12-02' "+
            "and (a1.areacode like '%') "+

    "union all  "+
            "select round(sum(a2.commoney)/10000,2) commoney, 1 areacode, 'out patient' rname "+
            "from fact_mv_cli_areacode a2 "+
            "where a2.comdate>'2013-01-01' "+
            "and a2.comdate<'2013-12-02' "+
            "and (a2.areacode like '%') "+
    ")v";
		
		res=stmt.executeQuery(sql1);
		while(res.next()){
			System.out.println(res.getString(1)+"\t"+res.getString(2));
		}
		/*String tableName = "testHiveDriverTable";
		stmt.executeQuery("drop table " + tableName);
		res = stmt.executeQuery("create table " + tableName
				+ " (key int, value string) row format delimited fields terminated by ','");
		// show tables
		String sql = "show tables '" + tableName + "'";
		System.out.println("Running: " + sql);
		res = stmt.executeQuery(sql);
		if (res.next()) { 
			System.out.println(res.getString(1));
		}
		// describe table
		sql = "describe " + tableName;
		System.out.println("Running: " + sql);
		res = stmt.executeQuery(sql);
		while (res.next()) {
			System.out.println(res.getString(1) + "\t" + res.getString(2));
		}

		// load data into table
		// NOTE: filepath has to be local to the hive server
		// NOTE: /tmp/a.txt is a ctrl-A separated file with two fields per line
		String filepath = "/opt/zptest/a.txt";
		sql = "load data local inpath '" + filepath + "' into table "
				+ tableName;
		System.out.println("Running: " + sql);
		res = stmt.executeQuery(sql);

		// select * query
		sql = "select * from " + tableName;
		System.out.println("Running: " + sql);
		res = stmt.executeQuery(sql);
		while (res.next()) {
			System.out.println(String.valueOf(res.getInt(1)) + "\t"
					+ res.getString(2));
		}

		// regular hive query
		sql = "select count(1) from " + tableName;
		System.out.println("Running: " + sql);*/
		//too slow
//		res = stmt.executeQuery(sql);
//		while (res.next()) {
//			System.out.println(res.getString(1));
//		}
	}
}
