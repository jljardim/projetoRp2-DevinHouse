package dev.in.villaDevin.model;


public class User {
   
    private Long id;

   
    private String email;

   
    private String password;

    
    private Long residentId;
    
    public User() {
        
    }


    public User(String email, String password, Long residentId) {
        this.email = email;
        this.password = password;
        this.residentId = residentId;
    }

    public User(Long id, Long residentId, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.residentId = residentId;
    }

	public Long getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public Long getResidentId() {
		return residentId;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setResidentId(Long residentId) {
		this.residentId = residentId;
	}
    
    
}
