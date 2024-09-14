package it.uniroma3.siwprogetto.model;

import java.time.LocalDate;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

@Entity
public class GiocatoreTesserato {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "giocatore_id")
    private Giocatore giocatore;

    @ManyToOne
    @JoinColumn(name = "squadra_id")
    private Squadra squadra;

    private LocalDate dataInizioTesseramento;
    private LocalDate dataFineTesseramento;
	
    

    // Getters e setters
    public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Giocatore getGiocatore() {
		return giocatore;
	}
	public void setGiocatore(Giocatore giocatore) {
		this.giocatore = giocatore;
	}
	public Squadra getSquadra() {
		return squadra;
	}
	public void setSquadra(Squadra squadra) {
		this.squadra = squadra;
	}
	public LocalDate getDataInizioTesseramento() {
		return dataInizioTesseramento;
	}
	public void setDataInizioTesseramento(LocalDate dataInizioTesseramento) {
		this.dataInizioTesseramento = dataInizioTesseramento;
	}
	public LocalDate getDataFineTesseramento() {
		return dataFineTesseramento;
	}
	public void setDataFineTesseramento(LocalDate dataTermineTesseramento) {
		this.dataFineTesseramento = dataTermineTesseramento;
	}
	@Override
	public int hashCode() {
		return Objects.hash(dataInizioTesseramento, dataFineTesseramento, giocatore, id, squadra);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GiocatoreTesserato other = (GiocatoreTesserato) obj;
		return Objects.equals(dataInizioTesseramento, other.dataInizioTesseramento)
				&& Objects.equals(dataFineTesseramento, other.dataFineTesseramento)
				&& Objects.equals(giocatore, other.giocatore) && Objects.equals(id, other.id)
				&& Objects.equals(squadra, other.squadra);
	}

    
}
