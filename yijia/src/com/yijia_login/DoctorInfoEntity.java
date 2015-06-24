
package com.yijia_login;

public class DoctorInfoEntity {
    private static final String TAG = DoctorInfoEntity.class.getSimpleName();

    private String name;

    private String hospital ;

    private String department;
    
    private int doctor_id;

    public DoctorInfoEntity() {
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

	public int getDoctor_id() {
		return doctor_id;
	}

	public void setDoctor_id(int doctor_id) {
		this.doctor_id = doctor_id;
	}

	public static String getTag() {
		return TAG;
	}

	public DoctorInfoEntity(String name, String hospital, String department,String doctor_id) {
        super();
        
    }
}
