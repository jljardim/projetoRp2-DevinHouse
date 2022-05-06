package dev.in.villaDevin.controller.service;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import dev.in.villaDevin.exeptions.ResidentNotFoundExcetion;
import dev.in.villaDevin.model.Resident;
import dev.in.villaDevin.model.repository.ResidentRepository;
import dev.in.villaDevin.model.transport.CreateResidentRequestDTO;
import dev.in.villaDevin.model.transport.CreateResidentResponseDTO;
import dev.in.villaDevin.model.transport.ResidentNameAndIdResponseDTO;
import dev.in.villaDevin.model.transport.ResidentsByMonthResponseDTO;

@Service
public class ResidentService {

	private final Logger LOG = LogManager.getLogger(ResidentService.class);
	
	private UserService userService;

	
	private ResidentRepository residentRepository;

	public ResidentService(ResidentRepository residentRepository, UserService userService) {
		this.residentRepository = residentRepository;
		this.userService = userService;
	}

	public CreateResidentResponseDTO create(CreateResidentRequestDTO createResidentDTO)
			throws Exception {

		if (!isValidCPF(createResidentDTO.getCpf())) {
			throw new IllegalArgumentException("Cpf Invalido");
		}

		if (!isValidName(createResidentDTO.getName())) {
			throw new IllegalArgumentException("Nome Invalido: Não pode conter espaços nem numero");
		}
		
		if (!isValidName(createResidentDTO.getLastName())) {
			throw new IllegalArgumentException("SobreNome Invalido: Não pode conter espaços nem numero");
		}
		
		if (createResidentDTO.getIncome() == null) {
			throw new IllegalArgumentException("Renda não pode ser estar vazia");
		}
		
		if (createResidentDTO.getIncome().compareTo(BigDecimal.ZERO) <= 0) {
			throw new IllegalArgumentException("Renda não pode ser negativa ou igual a zero");
		}
		
		if (createResidentDTO.getDateNasc() == null) {
			throw new IllegalArgumentException("Data de nascimento não pode ser nulo.");
		}
		
		if(LocalDate.now().isBefore(createResidentDTO.getDateNasc())) {
			throw new IllegalArgumentException("Data de nascimento não pode maior do que hoje.");
		}

		// if (createResidentDTO == null) {
//			throw new IllegalArgumentException("O morador está nulo");
//		}

		
		Resident resident = residentRepository.save(new Resident(createResidentDTO));
		
		try {
			userService.create(resident, createResidentDTO.getEmail(), createResidentDTO.getPassword(), new HashSet<>(createResidentDTO.getRoles()));
			CreateResidentResponseDTO saveResident = new CreateResidentResponseDTO(resident);
			return saveResident;
		} catch (Exception e) {
			this.delete(resident.getId());
			
			throw e;
		}
		
		
	}

	public List<ResidentNameAndIdResponseDTO> listResident() throws SQLException {
		List<Resident> result = getListAllResidents();
        return buildResidentNameAndIdResponseDTO(result);
		
	}
	
	public List<Resident> getListAllResidents() throws SQLException {
		return this.residentRepository.findAll();
	}


	private boolean isValidCPF(final String cpf) {
		if (null == cpf) {
			return false;
		}

		final Pattern pattern = Pattern.compile("(^\\d{3}\\x2E\\d{3}\\x2E\\d{3}\\x2D\\d{2}$)");
		return pattern.matcher(cpf).matches();
	}

	private boolean isValidName(final String name) {
		if (null == name || name.trim().isEmpty()) {
			return false;
		}

		final Pattern pattern = Pattern.compile("^[A-Za-zÀ-ÖØ-öø-ÿ]+(([',. -][A-Za-zÀ-ÖØ-öø-ÿ ])?[A-Za-zÀ-ÖØ-öø-ÿ])$");
		return pattern.matcher(name).matches();
	}


	public CreateResidentRequestDTO getById(Long id) throws SQLException {

		if (id == null) {
			throw new IllegalArgumentException("O id não pode ser nulo");
		}

		Resident resident = this.residentRepository.findAllById(id);
		return new CreateResidentRequestDTO(resident);
	}

	public List<ResidentNameAndIdResponseDTO> getResidentDTOByFilter(String name) throws SQLException {

		if (name == null || name.isEmpty()) {
			throw new IllegalArgumentException("O nome não pode ser nulo");
		}

		List<Resident> residents = residentRepository.findByName(name);
		return buildResidentNameAndIdResponseDTO(residents);

	}

	public void delete(Long id) throws  SQLException {
		residentRepository.deleteById(id);
	}

	public List<ResidentsByMonthResponseDTO> getResidentFilterByMonth(String month) throws SQLException {

		if (month == null || month.isEmpty()) {
			throw new IllegalArgumentException("O mês não pode ser nulo");
		}

		List<Resident> residents = residentRepository.findAll();

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM").withLocale(new Locale("pt", "BR"));

		List<ResidentsByMonthResponseDTO> filteredResidents = residents.stream().filter((resident) -> {
			String monthNasc = resident.getDateNasc().format(formatter);
			return monthNasc.equalsIgnoreCase(month);
		}).map(resident -> new ResidentsByMonthResponseDTO(resident.getName(), resident.getId()))
				.collect(Collectors.toList());

		return filteredResidents;

	}

	public List<ResidentsByMonthResponseDTO> getResidentFilterByAge(Long age) throws SQLException {

		if (age == null) {
			throw new IllegalArgumentException("O Idade não pode ser nulo");
		}

		List<Resident> residentList = residentRepository.findAll();

		LocalDate now = LocalDate.now();

		List<ResidentsByMonthResponseDTO> filterResidentsAge = residentList.stream().filter((resident) -> {
			LocalDate dateBirthdayResident = resident.getDateNasc();
			Period period = Period.between(now, dateBirthdayResident);
			Integer ageResident = Math.abs(period.getYears());
			return ageResident >= age;
		}).map(resident -> new ResidentsByMonthResponseDTO(resident.getName(), resident.getId()))
				.collect(Collectors.toList());

		return filterResidentsAge;

	}
	
	private List<ResidentNameAndIdResponseDTO> buildResidentNameAndIdResponseDTO(List<Resident> result) {
        return result.stream().map(resident -> new ResidentNameAndIdResponseDTO(resident.getId(), resident.getName())).collect(Collectors.toList());
    }

}
