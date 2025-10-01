package Entity.SealedClass;
import Entity.Enum.StatuCarte;
import Entity.Enum.TypeOperation;

public sealed class Carte permits CarteCredit, CarteDebit, CartePrepayee {

    String id;
    String numero;
    String dateExpiration;
    StatuCarte statut;
    double solde;
    int idClient;

    public Carte(String id, String numero, String dateExpiration, StatuCarte statut, double solde, int idClient) {
        this.id = id;
        this.numero = numero;
        this.dateExpiration = dateExpiration;
        this.statut = statut;
        this.solde = solde;
        this.idClient = idClient;
    }

    public String getId() {
        return id;
    }

    public String getNumero() {
        return numero;
    }

    public String getDateExpiration() {
        return dateExpiration;
    }

    public StatuCarte getStatut() {
        return statut;
    }

    public double getSolde() {
        return solde;
    }

    public int getIdClient() {
        return idClient;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public void setDateExpiration(String dateExpiration) {
        this.dateExpiration = dateExpiration;
    }

    public void setStatut(StatuCarte statut) {
        this.statut = statut;
    }

    public void setSolde(double solde) {
        this.solde = solde;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }
}
