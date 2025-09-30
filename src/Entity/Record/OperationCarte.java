package Entity.Record;

import Entity.Enum.TypeOperation;

import java.util.Date;

public record OperationCarte(int id, double montant, Date date, TypeOperation type, String lieu, String idCarte) {
}
