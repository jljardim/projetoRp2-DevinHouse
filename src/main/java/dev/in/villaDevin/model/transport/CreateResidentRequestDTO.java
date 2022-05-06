package dev.in.villaDevin.model.transport;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import dev.in.villaDevin.model.Resident;

public class CreateResidentRequestDTO {

	private Long id;
	private String uuid;
	private String name;
	private String lastName;
	private LocalDate dateNasc;
	private String email;
	private String password;
	private BigDecimal income;
	private String cpf;
	@JsonFormat(shape = JsonFormat.Shape.ARRAY)
    private List<String> roles;

	public CreateResidentRequestDTO() {

	}

	public CreateResidentRequestDTO(Resident resident) {
		this.id = resident.getId();
		this.uuid = resident.getUuid();
		this.name = resident.getName();
		this.lastName = resident.getLastName();
		this.dateNasc = resident.getDateNasc();
		this.income = resident.getIncome();
		this.cpf = resident.getCpf();
	}

	public CreateResidentRequestDTO(Long id, String uuid, String name, String lastName, LocalDate dateNasc, String email,
			BigDecimal income, String cpf, String password) {
		super();
		this.id = id;
		this.uuid = uuid;
		this.name = name;
		this.lastName = lastName;
		this.dateNasc = dateNasc;
		this.email = email;
		this.income = income;
		this.cpf = cpf;
		this.password = password;
	}

	
	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public LocalDate getDateNasc() {
		return dateNasc;
	}

	public void setDateNasc(LocalDate dateNasc) {
		this.dateNasc = dateNasc;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public BigDecimal getIncome() {
		return income;
	}

	public void setIncome(BigDecimal income) {
		this.income = income;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	@Override
	public String toString() {
		return "CreateResidentRequestDTO [id=" + id + ", uuid=" + uuid + ", name=" + name + ", lastName=" + lastName
				+ ", dateNasc=" + dateNasc + ", email=" + email + ", password=" + password + ", income=" + income
				+ ", cpf=" + cpf + ", roles=" + roles + "]";
	}

	

}
