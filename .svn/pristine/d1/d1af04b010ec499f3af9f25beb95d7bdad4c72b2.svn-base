package com.iamnige.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import com.iamnige.logger.Logger;

public class CommonDBModel {
	private String tbName;
	public void setTbName(String name){
		this.tbName = name;
	}
	
	public ArrayList<ArrayList<DBField>> query(ArrayList<DBField> select, ArrayList<DBField> condition, String extra) throws Exception{
		ArrayList<ArrayList<DBField>> ret = new ArrayList<ArrayList<DBField>>();
		StringBuilder builder = new StringBuilder();
		StringBuffer buffer = new StringBuffer();
		builder.append("SELECT ");
		for(DBField selectField : select){
			buffer.append(selectField.key);
			buffer.append(", ");
		}
		builder.append(buffer.toString().substring(0, buffer.toString().length() - 2));
		builder.append(" FROM " + this.tbName + " WHERE ");
		for(DBField field : condition){
			builder.append(field.key + field.operator + "?");
			builder.append(" AND ");
		}
		builder.append("1 = 1");
		builder.append(extra);
		Logger.logInfo(builder.toString() + " condition:" + dataToString(condition));
		Connection connection = MariaDBConnection.getConnection();
		PreparedStatement state;
		try {
			state = connection.prepareStatement(builder.toString());
		} catch (SQLException e) {
			Logger.logError(e.getMessage());
			throw e;
		}
		
		setParameters(state, condition);
		ResultSet set;
		try{
			set = state.executeQuery();
		}catch(SQLException e){
			Logger.logError(e.getMessage());
			throw e;
		}
		while(set.next()){
			loadARow(set, select);
			ret.add(select);
		}
		return ret;
	}
	
	public boolean insert(ArrayList<DBField> condition) throws Exception{
		StringBuilder builder = new StringBuilder();
		StringBuffer bufferKs = new StringBuffer();
		StringBuffer bufferVs = new StringBuffer();
		bufferKs.append("(");
		bufferVs.append("(");
		builder.append("INSERT INTO " + this.tbName + " ");
		for(DBField field : condition){
			bufferKs.append(field.key + ", ");
			bufferVs.append("?, ");
		} 
		builder.append(bufferKs.toString().substring(0, bufferKs.toString().length() - 2)).append(")").append("VALUES")
		.append(bufferVs.toString().substring(0, bufferVs.toString().length() - 2)).append(")");
		Connection connection = MariaDBConnection.getConnection();
		Logger.logInfo(builder.toString() + " condition:" + dataToString(condition));
		PreparedStatement state;
		try {
			state = connection.prepareStatement(builder.toString());
		} catch (SQLException e) {
			Logger.logError(e.getMessage());
			throw e;
		}
		
		setParameters(state, condition);
		int retCode;
		try {
			retCode = state.executeUpdate();
		} catch (SQLException e) {
			Logger.logError(e.getMessage());
			throw e;
		}
		return true;
	}
	
	public boolean update(ArrayList<DBField> update, ArrayList<DBField> condition) throws Exception{
		StringBuilder builder = new StringBuilder();
		StringBuffer bufferKs = new StringBuffer();
		builder.append("UPDATE " + this.tbName + " SET ");
		for(DBField field : update){
			bufferKs.append(field.key + field.operator + "?, ");
		}
		builder.append(bufferKs.toString().substring(0, bufferKs.toString().length() - 2)).append(" WHERE ");
		bufferKs = new StringBuffer();
		for(DBField field : condition){
			bufferKs.append(field.key + field.operator + "?, ");
		}
		builder.append(bufferKs.toString().substring(0, bufferKs.capacity() - 2));
		Connection connection = MariaDBConnection.getConnection();
		Logger.logInfo(builder.toString() + "; condition:" + dataToString(condition) + " Parameters:" + dataToString(update));
		PreparedStatement state;
		try {
			state = connection.prepareStatement(builder.toString());
		} catch (SQLException e) {
			Logger.logError(e.getMessage());
			throw e;
		}
		update.addAll(condition);
		setParameters(state, update);
		int retCode;
		try {
			retCode = state.executeUpdate();
		} catch (SQLException e) {
			Logger.logError(e.getMessage());
			throw e;
		}
		return 0 == retCode;
	}
	
	private void loadARow(ResultSet set, ArrayList<DBField> select) throws Exception {
		for(DBField field : select){
			try {
				switch(field.type){
				case DBFieldType.STRING:	
					field.value = set.getString(field.key);
					break;
				case DBFieldType.INT:
					field.value = set.getInt(field.key);
					break;
				case DBFieldType.DOUBLE:
					field.value = set.getDouble(field.key);
					break;
				case DBFieldType.LONG:	
					field.value = set.getLong(field.key);
					break;
				case DBFieldType.TIMESTAMP:	
					field.value = set.getTimestamp(field.key);
					break;
				default:
					String message = "Undefined field type: " + field.type;
					Logger.logError(message);
					throw new Exception(message);
				}
			} catch (SQLException e) {
				Logger.logError(e.getMessage());
				throw new Exception(e.getMessage());
			}
		}
	}

	private void setParameters(PreparedStatement state, ArrayList<DBField> condition) throws Exception {
		for(int i = 0; i < condition.size(); i ++){
			try {
				DBField field = condition.get(i);
				switch(field.type){
				case DBFieldType.STRING:
					state.setString(i + 1, (String)field.value);
					break;
				case DBFieldType.INT:
					state.setInt(i + 1, (int)field.value);
					break;
				case DBFieldType.DOUBLE:	
					state.setDouble(i + 1, (double)field.value);
				case DBFieldType.LONG:	
					state.setLong(i + 1, (Long)field.value);
					break;
				case DBFieldType.TIMESTAMP:	
					state.setTimestamp(i + 1, (Timestamp)field.value);
					break;
				default:
					String message = "Undefined field type: " + field.type;
					Logger.logError(message);
					throw new Exception(message);
				}
			} catch (Exception e) {
				Logger.logError(e.getMessage());
				throw new Exception(e.getMessage());
			}
		}
	}
	
	private String dataToString(ArrayList<DBField> data){
		String ret = "(";
		for(DBField field : data){
			ret += "(" + field.key + ", " + field.value.toString() + ", " + field.type + ") ";
		}
		ret += ")";
		return ret;
	}
}
