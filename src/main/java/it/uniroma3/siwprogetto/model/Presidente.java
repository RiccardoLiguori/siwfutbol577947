package it.uniroma3.siwprogetto.model;

import java.time.LocalDate;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class Presidente {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String nome;
    private String cognome;
    private String codiceFiscale;
    private LocalDate dataNascita;
    private String luogoNascita;

	// Nuovo campo per distinguere un amministratore da un vero presidente
	private boolean isAdmin=false;

    @OneToOne(mappedBy = "presidente")
    private Squadra squadra;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	public LocalDate getDataNascita() {
		return dataNascita;
	}

	public void setDataNascita(LocalDate dataNascita) {
		this.dataNascita = dataNascita;
	}

	public String getLuogoNascita() {
		return luogoNascita;
	}

	public void setLuogoNascita(String luogoNascita) {
		this.luogoNascita = luogoNascita;
	}

	public Squadra getSquadra() {
		return squadra;
	}

	public void setSquadra(Squadra squadra) {
		this.squadra = squadra;
	}

	@Override
	public int hashCode() {
		return Objects.hash(codiceFiscale, cognome, dataNascita, id, luogoNascita, nome, squadra);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Presidente other = (Presidente) obj;
		return Objects.equals(codiceFiscale, other.codiceFiscale) && Objects.equals(cognome, other.cognome)
				&& Objects.equals(dataNascita, other.dataNascita) && Objects.equals(id, other.id)
				&& Objects.equals(luogoNascita, other.luogoNascita) && Objects.equals(nome, other.nome)
				&& Objects.equals(squadra, other.squadra);
	}

    // Getters e setters
    
    
}
