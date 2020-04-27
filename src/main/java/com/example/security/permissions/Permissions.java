package com.example.security.permissions;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.example.aspect.EncryptDecrypt.IntEncryptDecryptConverter;
import com.example.aspect.EncryptDecrypt.StringEncryptDecryptConverter;

@Entity
public class Permissions {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Convert(converter = IntEncryptDecryptConverter.class)
	private int PermissionID ;

	 @Convert(converter = StringEncryptDecryptConverter.class)
    private String PermissionName=" ";

	 @Convert(converter = StringEncryptDecryptConverter.class)
	private String PermissionType=" ";

	public Permissions(String permissionName ) {
		this.PermissionName = permissionName ; 
	}
	
	public Permissions(){}
	
	public int getPermissionID() {
		return PermissionID;
	}


	public void setPermissionID(int permissionID) {
		PermissionID = permissionID;
	}


	public String getPermissionName() {
		return PermissionName;
	}


	public void setPermissionName(String permissionName) {
		PermissionName = permissionName;
	}


	public String getPermissionType() {
		return PermissionType;
	}


	public void setPermissionType(String permissionType) {
		PermissionType = permissionType;
	}


	
	
	
}
