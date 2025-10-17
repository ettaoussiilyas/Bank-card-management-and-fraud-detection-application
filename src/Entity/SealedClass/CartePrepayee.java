package Entity.SealedClass;

import Entity.Enum.StatuCarte;
import Entity.Enum.TypeOperation;

public final class CartePrepayee extends Carte {
    private double soldeDisponible;

    public CartePrepayee(String id, String numero, String dateExpiration, StatuCarte statut, String typeCarte, int idClient, float solde) {
        super(id, numero, dateExpiration, statut, typeCarte, idClient, solde);
        this.soldeDisponible = solde;
    }

    public double getSoldeDisponible() { return soldeDisponible; }
    public void setSoldeDisponible(double solde) { this.soldeDisponible = solde; }
    public boolean deduireMonant(double montant) {
        if (montant <= soldeDisponible) {
            soldeDisponible -= montant;
            return true;
        }
        return false;
    }
}
