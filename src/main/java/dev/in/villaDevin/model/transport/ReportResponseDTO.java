package dev.in.villaDevin.model.transport;

public class ReportResponseDTO {

	private final Float custoDaVila;
	private final Float orcamentoInicial;
	private final Float somaDosCustosDosResidentes;
	private final String residenteComMaiorRenda;

	public ReportResponseDTO(Float custoDaVila, Float orcamentoInicial, Float somaDosCustosDosResidentes,
			String residenteComMaiorRenda) {
		this.custoDaVila = custoDaVila;
		this.orcamentoInicial = orcamentoInicial;
		this.somaDosCustosDosResidentes = somaDosCustosDosResidentes;
		this.residenteComMaiorRenda = residenteComMaiorRenda;
	}

	public Float getCustoDaVila() {
		return custoDaVila;
	}

	public Float getOrcamentoInicial() {
		return orcamentoInicial;
	}

	public Float getSomaDosCustosDosResidentes() {
		return somaDosCustosDosResidentes;
	}

	public String getResidenteComMaiorRenda() {
		return residenteComMaiorRenda;
	}

	@Override
	public String toString() {
		return "ReportResponseDTO [custoDaVila=" + custoDaVila + ", orcamentoInicial=" + orcamentoInicial
				+ ", somaDosCustosDosResidentes=" + somaDosCustosDosResidentes + ", residenteComMaiorRenda="
				+ residenteComMaiorRenda + "]";
	}

}
