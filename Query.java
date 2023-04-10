package model;

public class Query {
	private int Qid;
	private int ID;
	private String lname;
	private String fname;
	private String type;
	private String details;
	
	public Query() {
		super();
		Qid = 0;
		ID = 0;
		this.lname = "";
		this.fname = "";
		this.type = "";
		this.details = "";
	}
	
	
	public int getQid() {
		return Qid;
	}


	public void setQid(int qid) {
		Qid = qid;
	}


	public int getID() {
		return ID;
	}


	public void setID(int iD) {
		ID = iD;
	}


	public String getLname() {
		return lname;
	}


	public void setLname(String lname) {
		this.lname = lname;
	}


	public String getFname() {
		return fname;
	}


	public void setFname(String fname) {
		this.fname = fname;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public String getDetails() {
		return details;
	}


	public void setDetails(String details) {
		this.details = details;
	}


	public Query(int qid, int iD, String lname, String fname, String type, String details) {
		super();
		Qid = qid;
		ID = iD;
		this.lname = lname;
		this.fname = fname;
		this.type = type;
		this.details = details;
	}
	
	
	
	

}
