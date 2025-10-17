package Entity.SealedClass;

import Entity.Enum.StatuCarte;
import Entity.Enum.TypeOperation;

public final class CarteCredit extends Carte {
    private double plafondMensuel = 5000.0;
    private double tauxInteret = 0.15;

    public CarteCredit(String id, String numero, String dateExpiration, StatuCarte statut, String typeCarte, int idClient, float solde) {
        super(id, numero, dateExpiration, statut, typeCarte, idClient, solde);
    }

    public double getPlafondMensuel() { return plafondMensuel; }
    public void setPlafondMensuel(double plafond) { this.plafondMensuel = plafond; }
    public double getTauxInteret() { return tauxInteret; }
    public void setTauxInteret(double taux) { this.tauxInteret = taux; }
}
