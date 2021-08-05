package com.orlando.demo.domain;

public class TenisGame {

	private Estado estado;
	private int puntosLocal;
	private int puntosVisitante;
	private String[] puntaje = { "LOVE", "FIFTEEN", "THIRTY", "FORTY" };
	private String puntajeLocal;
	private String puntajeVisitante;

	public TenisGame() {
		estado = Estado.PENDIENTE;
		puntosLocal = 0;
		puntosVisitante = 0;
		puntajeLocal = puntaje[0];
		puntajeVisitante = puntaje[0];
	}

	public void anotar(boolean local) {

		if (!sePuedeAnotar()) {
			throw new IllegalStateException("El juego aún no se ha iniciado o ya terminó");
		}

		if (local)
			puntosLocal++;
		else
			puntosVisitante++;

		actualizarScore();

	}

	private boolean sePuedeAnotar() {
		return estado == Estado.INICIADO;
	}
	
	private void actualizarScore() {
		
		int relativoLocal = puntosLocal - puntosVisitante;		
		if((Math.max(puntosVisitante, puntosLocal) < 4) || Math.abs(relativoLocal) <= 2) {			
			colocarPuntaje(relativoLocal);
			return;
		}			
		colocarGanadorPerdedor(relativoLocal);
		
	}

	public void iniciarPartida() {
		estado = Estado.INICIADO;
	}

	private void terminarPartida() {
		estado = Estado.TERMINADO;
	}
	
	private void colocarPuntaje(int relativoLocal) {
		if (Math.min(puntosLocal, puntosVisitante) >= 3) {
			colocarPuntajeSobreTres(relativoLocal);
			return;
		}
		puntajeLocal = puntaje[puntosLocal];
		puntajeVisitante = puntaje[puntosVisitante];
	}
	
	private void colocarPuntajeSobreTres(int relativoLocal) {
		if (puntosLocal == puntosVisitante) {
			puntajeLocal = "DEUCE";
			puntajeVisitante = "DEUCE";
			return;
		}
		puntajeLocal = relativoLocal > 0 ? "ADVANTAGE": "";
		puntajeVisitante = relativoLocal < 0 ? "ADVANTAGE": "";
	}
	
	private void colocarGanadorPerdedor(int relativoLocal) {
		puntajeLocal = relativoLocal > 0 ? "GANADOR" : "PERDEDOR";
		puntajeVisitante = relativoLocal < 0 ? "GANADOR" : "PERDEDOR";		
		terminarPartida();
	}
	
	public String score() {
		return String.format("LOCAL: %s\nVISITANTE: %s", puntajeLocal, puntajeVisitante);
	}
	

}
