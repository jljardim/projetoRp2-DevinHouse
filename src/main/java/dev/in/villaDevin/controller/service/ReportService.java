package dev.in.villaDevin.controller.service;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import dev.in.villaDevin.model.Resident;
import dev.in.villaDevin.model.transport.ReportResponseDTO;

@Service
public class ReportService {
 
	@Value("${ORCAMENTO_VILLA}")
	private Float orcamentoDaVilla;
	
	private ResidentService residentService;

	public ReportService(ResidentService residentService) {
		super();
		this.residentService = residentService;
	}
	
	public ReportResponseDTO reportGenerator() throws SQLException{
		final List<Resident> residents = residentService.getListAllResidents();
		final Float orcamentoInicial = orcamentoDaVilla;
		final Float valorTotalDosCustos = residents.stream().reduce(
				0F,(accumulator, resident) -> accumulator + resident.getIncome().floatValue(),
				Float :: sum
		);
		
		final Float custo = orcamentoInicial - valorTotalDosCustos;
		final Resident residenteComMaiorCusto = residents.stream().max(
				Resident.compareByCost).orElse(null);
		final String nomeDoResidente = String.format("%s %s", 
				residenteComMaiorCusto.getName(),
				residenteComMaiorCusto.getLastName());
		
		return new ReportResponseDTO(custo, orcamentoInicial, valorTotalDosCustos,
				nomeDoResidente);
	}
	
}
