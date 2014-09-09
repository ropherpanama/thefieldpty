package com.otacm.thefieldpty.database.beans;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName="remainders")
public class Remainder {
	@DatabaseField(columnName = "id", id = true, useGetSet = true, generatedId = true) private int id;
	@DatabaseField(columnName = "title", id = true, useGetSet = true, index=true) private String title;
	@DatabaseField(columnName = "description", id = true, useGetSet = true)private String description;
	@DatabaseField(columnName = "date", id = true, useGetSet = true)private String date;
	@DatabaseField(columnName = "status", id = true, useGetSet = true)private int status;
	@DatabaseField(columnName = "favid", id = true, useGetSet = true, index = true)private int favid;
 
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getFavid() {
		return favid;
	}

	public void setFavid(int favid) {
		this.favid = favid;
	}
}
