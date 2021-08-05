package com.orlando.demo;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Random;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.orlando.demo.domain.TenisGame;

@SpringBootTest
class TenisAppApplicationTests {

	private TenisGame game;
	
	@BeforeEach
	void initGame() {
		this.game = new TenisGame();
	}
	
	@Test
	void debeArrojarExcepcionCuandoSeInvocaAnotarYElJuegoNoHaComenzado() {		
		anotarRandomYAssertMensajeExcepcion();	
	}
	
	@Test
	void debeArrojarExcepcionCuandoSeInvocaAnotarYElJuegoHaTerminado() {
		
		this.game.iniciarPartida();
		this.game.anotar(true);
		this.game.anotar(true);
		this.game.anotar(true);
		this.game.anotar(true);
		
		anotarRandomYAssertMensajeExcepcion();
		
	}
	
	@Test
	void debeMostrarLocalGanador() {
		
		this.game.iniciarPartida();
		this.game.anotar(true);
		this.game.anotar(true);
		this.game.anotar(true);
		this.game.anotar(true);
		
		assertTrue(this.game.score().equals(String.format("LOCAL: %s\nVISITANTE: %s", "GANADOR", "PERDEDOR")));
	}
	
	@Test
	void debeMostrarVisitanteGanador() {
		
		this.game.iniciarPartida();
		this.game.anotar(false);
		this.game.anotar(false);
		this.game.anotar(false);
		this.game.anotar(false);
		
		assertTrue(this.game.score().equals(String.format("LOCAL: %s\nVISITANTE: %s", "PERDEDOR", "GANADOR")));
	}
	
	@Test
	void debeMostrarLocalFORTYVisitanteFIFTEEN() {
		
		this.game.iniciarPartida();
		this.game.anotar(true);
		this.game.anotar(false);
		this.game.anotar(true);
		this.game.anotar(true);
		
		assertTrue(this.game.score().equals(String.format("LOCAL: %s\nVISITANTE: %s", "FORTY", "FIFTEEN")));
	}
	
	@Test
	void debeMostrarLocalADVANTAGEVisitanteEMPTY() {
		
		this.game.iniciarPartida();
		this.game.anotar(true);
		this.game.anotar(false);
		this.game.anotar(true);
		this.game.anotar(false);
		this.game.anotar(true);
		this.game.anotar(false);
		this.game.anotar(true);
		
		assertTrue(this.game.score().equals(String.format("LOCAL: %s\nVISITANTE: %s", "ADVANTAGE", "")));
	}
	
	
	
	private void anotarRandomYAssertMensajeExcepcion() {
		Random random = new Random();	
		Exception exception = assertThrows(IllegalStateException.class, ()-> {
			this.game.anotar(random.nextBoolean());
		});
		String mensajeEsperado = "El juego aún no se ha iniciado o ya terminó";
		String mensajeActual = exception.getMessage();
		
		assertTrue(mensajeEsperado.equals(mensajeActual));	
	}

}
