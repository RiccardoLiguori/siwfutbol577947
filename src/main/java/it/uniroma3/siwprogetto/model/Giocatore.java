package it.uniroma3.siwprogetto.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Giocatore {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String nome;
    private String cognome;
    private LocalDate dataNascita;
    private String luogoNascita;
    private String ruolo;

    @OneToMany(mappedBy = "giocatore", cascade = CascadeType.ALL, fetch = FetchType.EAGER )
    private List<GiocatoreTesserato> giocatoreTesserato = new ArrayList<>();

    // Getters e setters
    
    
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

	public String getRuolo() {
		return ruolo;
	}

	public void setRuolo(String ruolo) {
		this.ruolo = ruolo;
	}

	public List<GiocatoreTesserato> getGiocatoreTesserato() {
		return giocatoreTesserato;
	}

	public void setGiocatoreTesserato(List<GiocatoreTesserato> giocatoreTesserato) {
		this.giocatoreTesserato = giocatoreTesserato;
	}

	@Override
	public int hashCode() {
		return Objects.hash(cognome, dataNascita, id, luogoNascita, nome, ruolo, giocatoreTesserato);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Giocatore other = (Giocatore) obj;
		return Objects.equals(cognome, other.cognome) && Objects.equals(dataNascita, other.dataNascita)
				&& Objects.equals(id, other.id) && Objects.equals(luogoNascita, other.luogoNascita)
				&& Objects.equals(nome, other.nome) && Objects.equals(ruolo, other.ruolo)
				&& Objects.equals(giocatoreTesserato, other.giocatoreTesserato);
	}

    
}
