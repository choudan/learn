package com.yijia_login;

public class Doctor {
	private String id;
	private String name;
	private String hospital;
	private String department;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getHospital() {
		return hospital;
	}
	public void setHospital(String hospital) {
		this.hospital = hospital;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}

	public String toString(){
		
//		return "Doctor [id=" + id + ", name=" + name + ", hospital=" + hospital  
//                + ",department="+department+"]";
		
		return  id + name + hospital + department;	
	}
	
	public Doctor (String id,String name,String hospital,String department){
		super();
		this.id=id;
		this.name=name;
		this.hospital=hospital;
		this.department=department;		
	}
	public Doctor(){		
	}
}