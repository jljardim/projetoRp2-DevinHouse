package dev.in.villaDevin.model.transport;

import java.math.BigDecimal;
import java.time.LocalDate;

import dev.in.villaDevin.model.Resident;

public class CreateResidentResponseDTO {

	private Long id;

	private String name;

	private String lastName;

	private LocalDate dateNasc;
	private String email;
	private BigDecimal income;
	private String cpf;

	public CreateResidentResponseDTO(Resident residentDTO) {
		this.id = residentDTO.getId();
		this.name = residentDTO.getName();
		this.lastName = residentDTO.getLastName();
		this.dateNasc = residentDTO.getDateNasc();
		this.income = residentDTO.getIncome();
		this.cpf = residentDTO.getCpf();
	}

	public CreateResidentResponseDTO(Long id, String name, String lastName, LocalDate dateNasc,
			String email, BigDecimal income, String cpf) {
		super();
		this.id = id;
		this.name = name;
		this.lastName = lastName;
		this.dateNasc = dateNasc;
		this.email = email;
		this.income = income;
		this.cpf = cpf;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
		return "CreateResidentResponseDTO [id=" + id + ", name=" + name + ", lastName=" + lastName + ", dateNasc="
				+ dateNasc + ", email=" + email + ", income=" + income + ", cpf=" + cpf + "]";
	}

}