package model;

public class Account {
	private int id;
	private String username;
	private String password;
	private String email;
	private String address;
	private int role;
	private String phone;
	private int status;

	// Constructor đầy đủ
	public Account(int id, String username, String password, String email, String address, int role, String phone, int status) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
		this.address = address;
		this.role = role;
		this.phone = phone;
		this.status = status;
	}

	// Constructor không có id và role và status (dùng khi đăng ký)
	public Account(String username, String password, String email, String address, String phone) {
		this.username = username;
		this.password = password;
		this.email = email;
		this.address = address;
		this.phone = phone;
		this.status = 0; // Mặc định là chưa xác minh
	}

	public Account() {}

	// Getters & Setters

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
}
