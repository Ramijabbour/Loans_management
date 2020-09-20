package com.example.settelmets.RTGSLink;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import com.example.SiteConfig.MasterService;
import com.example.aspect.EncryptDecrypt.IntEncryptDecryptConverter;
import com.example.aspect.EncryptDecrypt.StringEncryptDecryptConverter;

@Entity
public class RTGSUser implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Convert(converter = IntEncryptDecryptConverter.class)
	private int id ;

	@Column(nullable = false )
	@Convert(converter = StringEncryptDecryptConverter.class)
	private String email=" ";

	@Column(nullable = false )
	private String password = " ";
	@Convert(converter = StringEncryptDecryptConverter.class)
	private String username ;
	@Convert(converter = StringEncryptDecryptConverter.class)
	private String branchName = " " ;
	@Convert(converter = StringEncryptDecryptConverter.class)
	private String bankName = " " ;
	@Convert(converter = StringEncryptDecryptConverter.class)
	private String branchCode = " ";
	@Convert(converter = StringEncryptDecryptConverter.class)
	private String Gender="" ;
	@Convert(converter = StringEncryptDecryptConverter.class)
	private String UserPermissions = "none" ;
	@Convert(converter = StringEncryptDecryptConverter.class)
	private String UserRoles ="none";

	@Convert(converter = StringEncryptDecryptConverter.class)
	private String lastCode = "" ; 
	
	private String TokenExpireDate ; 
	
	private int TokenExpireTimeInMinutes ;  
	
	private boolean tokenEntered = false ; 
	
	private boolean sent ; 
	
	private String createdAt ; 
	private boolean Active = false ; 


	public RTGSUser() {
		this.createdAt = MasterService.getDateAsString() ;
		this.UserRoles = "none"; 
		this.UserPermissions = "none";
	}
	
	public RTGSUser(String email, String password, String username, String branchName, String bankName,String branchCode, String gender,
			String userPermissions, String userRoles, String createdAt, boolean active) {
		super();
		this.email = email;
		this.password = password;
		this.username = username;
		this.branchName = branchName;
		this.bankName = bankName;
		Gender = gender;
		UserPermissions = userPermissions;
		UserRoles = userRoles;
		this.createdAt = createdAt;
		Active = active;
		this.branchCode = branchCode ; 
	}

	public void flatUserDetailes() {
		System.out.println("user ID : "+this.id+" username :"+this.username+" user email : "+this.email+" gender : "
	+this.Gender+" role "+this.UserRoles +" permissions : "+this.UserPermissions);
	}
	
	public int getId() {
		return id;
	}

	public void setId(int userID) {
		id = userID;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getGender() {
		return Gender;
	}

	public void setGender(String gender) {
		Gender = gender;
	}

	public String getUserPermissions() {
		return UserPermissions;
	}

	public void setUserPermissions(String userPermissions) {
		UserPermissions = userPermissions;
	}

	public String getUserRoles() {
		return UserRoles;
	}

	public void setUserRoles(String userRoles) {
		UserRoles = userRoles;
	}

	public boolean isActive() {
		return Active;
	}

	public void setActive(boolean active) {
		Active = active;
	}
	

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public boolean hasRole(String role) {
		if(this.UserRoles.equalsIgnoreCase(" ")) {
			return false ; 
		}
		else if (!this.convertRolesToList().contains(role)) {
			return false ;
		}
		return true ; 
	}
	
	public boolean hasPermission(String permission) {
		if(this.UserPermissions.equalsIgnoreCase(" ")) {
			return false ; 
		}
		else if (!this.convertPermissionsToList().contains(permission)) {
			return false  ; 
		}
		return true ; 
	}

	public void addRole(String role ) {
		if(this.UserRoles.equalsIgnoreCase(" ") || this.UserRoles.equalsIgnoreCase("")) {
			this.UserRoles=role;
		}else
		this.UserRoles+=","+role;
	}
	
	public void addPermission(String permission ) {
		if(this.UserPermissions.equalsIgnoreCase(" ") || this.UserPermissions.equalsIgnoreCase("")) {
			this.UserPermissions=permission;
		}else
		this.UserPermissions+=","+permission;
	}

	public List<String> convertPermissionsToList(){
		if(this.UserPermissions.equalsIgnoreCase("")) {
			return null ; 
		}
		else {
			List<String> userPermissions = new ArrayList<String>() ;
			String[] permissions = this.UserPermissions.split(",");
			for(int i = 0 ; i<permissions.length ; i++) {
				userPermissions.add(permissions[i]);
			}
			return userPermissions ; 
		}
	}
	
	public List<String> convertRolesToList(){
		if(this.UserRoles.equalsIgnoreCase("")) {
			return null ; 
		}
		else {
			List<String> userRoles = new ArrayList<String>() ;
			String[] roles = this.UserRoles.split(",");
			for(int i =0 ; i < roles.length ; i++) {
				userRoles.add(roles[i]); 
			}
			return userRoles ; 
			
		}
	}
	
	public void revokeRoleFromUser(String role ) {
		List<String> userRoles = this.convertRolesToList() ; 
		if(userRoles.size() != 0 ) {
		if(userRoles.contains(role)) {
			userRoles.remove(userRoles.indexOf(role));
			this.UserRoles = "";
			for(String tempRole : userRoles) {
				this.addRole(tempRole);
				}
			}
		}
	}
	
	public void revokePermissionFromUser(String permission) {
		List<String> userPermissions = this.convertPermissionsToList();
		if(userPermissions.size() != 0 ) {
			if(userPermissions.contains(permission)) {
				userPermissions.remove(permission);
				this.UserPermissions = "";
				for(String tempPermission : userPermissions) {
					this.addPermission(tempPermission);
				}
			}
		}
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getLastCode() {
		return lastCode;
	}

	public void setLastCode(String lastCode) {
		this.lastCode = lastCode;
	}
	
	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public boolean isTokenEntered() {
		return tokenEntered;
	}

	public void setTokenEntered(boolean tokenEntered) {
		this.tokenEntered = tokenEntered;
	}

	public boolean validateToken(String token ) {
		return token.equalsIgnoreCase(this.lastCode) ; 
	}

	public boolean isSent() {
		return sent;
	}

	public void setSent(boolean sent) {
		this.sent = sent;
	}
	
	public String getTokenExpireDate() {
		return TokenExpireDate;
	}

	public void setTokenExpireDate(String tokenExpireDate) {
		TokenExpireDate = tokenExpireDate;
	}

	public int getTokenExpireTimeInMinutes() {
		return TokenExpireTimeInMinutes;
	}

	public void setTokenExpireTimeInMinutes(int tokenExpireTimeInMinutes) {
		TokenExpireTimeInMinutes = tokenExpireTimeInMinutes;
	}

	
}


