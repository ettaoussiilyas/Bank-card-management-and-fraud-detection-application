package Entity.SealedClass;

import Entity.Enum.StatuCarte;
import Entity.Enum.TypeOperation;

public final class CarteDebit extends Carte {

    public CarteDebit(String id, String numero, String dateExpiration, StatuCarte statut, double solde, int idClient) {
        super(id, numero, dateExpiration, statut, solde, idClient);
    }
}
