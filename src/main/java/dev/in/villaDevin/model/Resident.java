package dev.in.villaDevin.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.UUID;

import dev.in.villaDevin.model.transport.CreateResidentRequestDTO;


public class Resident {
	
	private Long id;
	private String uuid;
	private String name;
	
	private String lastName;
	
	private LocalDate dateNasc;
	
	
	private BigDecimal income;
	private String cpf;
	
	
	public Resident() {

	}

	public Resident(CreateResidentRequestDTO residentDTO) {
		this.name = residentDTO.getName();
		this.uuid = UUID.randomUUID().toString();
		this.lastName = residentDTO.getLastName();
		this.dateNasc = residentDTO.getDateNasc();
		this.income = residentDTO.getIncome();
		this.cpf = residentDTO.getCpf();
	}

	public Resident(Long id, String uuid, String name, String lastName, LocalDate dateNasc,
			BigDecimal income, String cpf) {
		super();
		this.id = id;
		this.uuid = uuid;
		this.name = name;
		this.lastName = lastName;
		this.dateNasc = dateNasc;
		this.income = income;
		this.cpf = cpf;
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
	
	  public static final Comparator<Resident> compareByCost = (Resident v1, Resident v2) -> {
		  return v1.getIncome().compareTo(v2.getIncome());
	    };

	@Override
	public String toString() {
		return "Resident [id=" + id + ", uuid=" + uuid + ", name=" + name + ", lastName=" + lastName + ", dateNasc="
				+ dateNasc + ", income=" + income + ", cpf=" + cpf + "]";
	}

}
