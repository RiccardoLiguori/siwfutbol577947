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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Squadra {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String nome;
    private LocalDate annoFondazione;
    private String indirizzoSede;
	private String imagePath;


	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "presidente_id")
    private Presidente presidente;

    @OneToMany(mappedBy = "squadra", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<GiocatoreTesserato> giocatoriTesserati = new ArrayList<>();

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

	public LocalDate getAnnoFondazione() {
		return annoFondazione;
	}

	public void setAnnoFondazione(LocalDate annoFondazione) {
		this.annoFondazione = annoFondazione;
	}

	public String getIndirizzoSede() {
		return indirizzoSede;
	}

	public void setIndirizzoSede(String indirizzoSede) {
		this.indirizzoSede = indirizzoSede;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public Presidente getPresidente() {
		return presidente;
	}

	public void setPresidente(Presidente presidente) {
		this.presidente = presidente;
	}

	public List<GiocatoreTesserato> getGiocatoriTesserati() {
		return giocatoriTesserati;
	}

	public void setGiocatoriTesserati(List<GiocatoreTesserato> giocatoriTesserati) {
		this.giocatoriTesserati = giocatoriTesserati;
	}

	@Override
	public int hashCode() {
		return Objects.hash(annoFondazione, giocatoriTesserati, id, indirizzoSede, nome, presidente);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Squadra other = (Squadra) obj;
		return Objects.equals(annoFondazione, other.annoFondazione)
				&& Objects.equals(giocatoriTesserati, other.giocatoriTesserati) && Objects.equals(id, other.id)
				&& Objects.equals(indirizzoSede, other.indirizzoSede) && Objects.equals(nome, other.nome)
				&& Objects.equals(presidente, other.presidente);
	}


    
}
