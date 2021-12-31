package com.neo.msocial.service;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DB {
	
	public synchronized int update(String Sql, Connection conn) throws SQLException {
		CallableStatement cstmt=conn.prepareCall(Sql);
    	int out_= cstmt.executeUpdate();
		cstmt.close();
		conn.close();        
		return out_;
		
	}
	public synchronized ResultSet select(String Sql, Connection conn) throws SQLException {
		CallableStatement cstmt=conn.prepareCall(Sql);
    	ResultSet r = cstmt.executeQuery();
    	return r;
	}
	
	public synchronized String callFunc(String Sql, Connection conn) throws SQLException {
    	CallableStatement cstmt=conn.prepareCall(Sql);
    	cstmt.registerOutParameter(1, Types.VARCHAR);
		cstmt.execute();
		conn.commit();
		String out = cstmt.getString(1);
		cstmt.close();
		conn.close();        
		return out;
    }
	
	public List<Map<String, String>> toList(ResultSet rs) throws SQLException {
        ResultSetMetaData meta = rs.getMetaData();

        int count = meta.getColumnCount();
        List<Map<String, String>> data = new ArrayList<Map<String, String>>();
        while (rs.next()) {
            Map<String, String> row = new LinkedHashMap<String, String>();
            for (int i = 0; i < count; i++) {
                int columnNumber = i + 1;
                String columnName;
                try {
                    columnName = meta.getColumnLabel(columnNumber);
                } catch (SQLException e) {
                    columnName = meta.getColumnName(columnNumber);
                }
                Object r = rs.getObject(columnNumber);
                row.put(columnName, (r==null)?"":r.toString());
            }    
            data.add(row);
        }
        rs.close();
        return data;
    }
}
