package com.bounswe2016group3.safa;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by sand94 on 10.05.2016.
 */
public class Database {
    private static Connection conn = null;
    private static final String dbName = "sand";
	private static final String tableName = "ChessMaster";
    private static PreparedStatement statement = null;
	private static ResultSet res = null;

    private static boolean start(){
        conn = null;
        String url = "jdbc:mysql://localhost:3306/"+dbName;
        String user = "root";
        String password = "6pefwgb";
        try {
            conn = DriverManager.getConnection(url, user, password);
            return true;
        }
        catch (SQLException ex){
            System.out.println("ERROR!!!");
        }
        return false;
    }

    public static boolean push(ArrayList<ChessMaster> masters){
		String sql = "INSERT INTO " + tableName + " (FullName,Title,ELO) VALUES";
		int sze = masters.size();
		for(int i = 0; i < sze - 1; i++)
			sql += masters.get(i).toString() + ",";
		sql += masters.get(sze-1).toString() + ";";
        try {
            start();
			return excalibur(conn.prepareStatement(sql));
        } catch (SQLException e){
            System.out.println("Cannot connect!!!");
        }
		return false;
    }

	public static boolean drop() throws SQLException {
		start();
		String sql = "DROP DATABASE IF EXISTS "+dbName;
		return excalibur(conn.prepareStatement(sql));
	}

	public static boolean create() throws SQLException {
		start();
		String sql = "CREATE DATABASE " + dbName;
		return excalibur(conn.prepareStatement(sql));
	}

	public static boolean createDataTable() throws SQLException {
		start();
		String sql = "CREATE TABLE "+ tableName + " (FullName varchar(50) PRIMARY KEY," +
				" Title varchar(30), ELO int)";
		return excalibur(conn.prepareStatement(sql));
	}

	private static boolean excalibur(PreparedStatement ps){
		try {
			ps.execute();
			conn.close();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
}
