package com.angelo.logging.logger;

import java.util.Date;


public class TempleteMatchStatus {
	private int id;
	private int fragmentId;
	private Date newestTempleteDate;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getFragmentId() {
		return fragmentId;
	}
	public void setFragmentId(int fragmentId) {
		this.fragmentId = fragmentId;
	}
	public Date getNewestTempleteDate() {
		return newestTempleteDate;
	}
	public void setNewestTempleteDate(Date newestTempleteDate) {
		this.newestTempleteDate = newestTempleteDate;
	}
}
