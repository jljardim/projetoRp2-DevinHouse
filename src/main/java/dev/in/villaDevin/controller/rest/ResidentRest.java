package dev.in.villaDevin.controller.rest;

import java.sql.SQLException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dev.in.villaDevin.controller.service.ResidentService;
import dev.in.villaDevin.exeptions.ResidentNotFoundExcetion;
import dev.in.villaDevin.model.Resident;
import dev.in.villaDevin.model.transport.CreateResidentRequestDTO;
import dev.in.villaDevin.model.transport.CreateResidentResponseDTO;
import dev.in.villaDevin.model.transport.ResidentNameAndIdResponseDTO;
import dev.in.villaDevin.model.transport.ResidentsByMonthResponseDTO;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/resident")
public class ResidentRest {
	
	private ResidentService residentService;
	
	
	public ResidentRest(ResidentService residentService) {
		this.residentService = residentService;
	}
	

	
	@GetMapping("/list-all")  // Workning this moment
	public List<ResidentNameAndIdResponseDTO> listResident() throws SQLException {
		return residentService.listResident();
	}
	
	

	/*
	 * @GetMapping("/{id}") public List<ResidentFindAllProjection>
	 * getById(@PathVariable("id") Long id) { return residentService.; }
	 */
	
	@GetMapping("/{id}")
	public CreateResidentRequestDTO getById(@PathVariable("id") Long id) throws SQLException {
		return residentService.getById(id);
	}
	
	@PostMapping("/create")
	public ResponseEntity<CreateResidentResponseDTO> createNewResident(@RequestBody CreateResidentRequestDTO resident)
			throws Exception {
		CreateResidentResponseDTO residentCreated = this.residentService.create(resident);
		if(residentCreated == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(residentCreated);
	}
	
	
	@GetMapping("/filter")
	public List<ResidentNameAndIdResponseDTO> getResidentByFilter(@RequestParam("name") String name)
			throws  SQLException {
		return residentService.getResidentDTOByFilter(name);
	}
	
	@DeleteMapping("/delete")
	public void deleteById(@RequestParam("id")Long id) throws Exception {
		residentService.delete(id);
		ResponseEntity.status(HttpStatus.OK).body("Deletado com sucesso!");
	}
	
	@GetMapping("/filter-month")
	public List<ResidentsByMonthResponseDTO> getResidentByFilterMonth(@RequestParam("month") String month)
			throws  SQLException {
		return residentService.getResidentFilterByMonth(month);
	} 
	
	@GetMapping("/filter-age")
	public List<ResidentsByMonthResponseDTO> listResidentAll(@RequestParam("age") Long age)
			throws SQLException {
		return residentService.getResidentFilterByAge(age);
	}
}
