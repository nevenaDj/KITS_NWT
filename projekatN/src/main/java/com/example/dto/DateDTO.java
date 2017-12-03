package com.example.dto;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.example.model.Bill;
import com.example.model.Item_In_Princelist;

public class DateDTO {
	
	private Date start;
	private Date end;	

	
	public DateDTO(Date start, Date end) {
		this.start=start;
		this.end=end;		
	}


	public Date getStart() {
		return start;
	}


	public void setStart(Date start) {
		this.start = start;
	}


	public Date getEnd() {
		return end;
	}


	public void setEnd(Date end) {
		this.end = end;
	}
	

}
