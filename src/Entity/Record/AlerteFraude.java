package Entity.Record;

import Entity.Enum.NiveauAlerte;

public record AlerteFraude(int id, String description, NiveauAlerte niveau, int idCarte) {
}
