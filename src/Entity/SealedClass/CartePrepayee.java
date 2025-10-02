package Entity.SealedClass;

import Entity.Enum.StatuCarte;
import Entity.Enum.TypeOperation;

public final class CartePrepayee extends Carte {

    public CartePrepayee(String id, String numero, String dateExpiration, StatuCarte statut, String typeCarte, int idClient, float solde) {
        super(id, numero, dateExpiration, statut, typeCarte, idClient, solde);
    }
}
