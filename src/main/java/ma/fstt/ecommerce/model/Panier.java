package ma.fstt.ecommerce.model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "panier")
public class Panier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreation;

    @OneToOne
    @JoinColumn(name = "internaute_id")
    private Internaute internaute;

    @OneToMany(mappedBy = "panier", cascade = CascadeType.ALL)
    private List<LignePanier> lignesPanier;

    public Panier() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Date getDateCreation() { return dateCreation; }
    public void setDateCreation(Date dateCreation) { this.dateCreation = dateCreation; }
    public Internaute getInternaute() { return internaute; }
    public void setInternaute(Internaute internaute) { this.internaute = internaute; }
    public List<LignePanier> getLignesPanier() { return lignesPanier; }
    public void setLignesPanier(List<LignePanier> lignesPanier) { this.lignesPanier = lignesPanier; }
}
