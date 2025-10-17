package Entity.SealedClass;

import Entity.Enum.StatuCarte;
import Entity.Enum.TypeOperation;

public final class CarteDebit extends Carte {
    private double plafondJournalier = 1000.0;

    public CarteDebit(String id, String numero, String dateExpiration, StatuCarte statut, String typeCarte, int idClient, float solde) {
        super(id, numero, dateExpiration, statut, typeCarte, idClient, solde);
    }

    public double getPlafondJournalier() { return plafondJournalier; }
    public void setPlafondJournalier(double plafond) { this.plafondJournalier = plafond; }
}
