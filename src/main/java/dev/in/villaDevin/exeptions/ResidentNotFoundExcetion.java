package dev.in.villaDevin.exeptions;

public class ResidentNotFoundExcetion extends Exception{
	private static final long serialVersionUID = 1L;

	public ResidentNotFoundExcetion() {
		super("Não foram encontrados Vingadores no Banco");
	}
	
}
