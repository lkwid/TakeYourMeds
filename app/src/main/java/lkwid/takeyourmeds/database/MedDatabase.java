package lkwid.takeyourmeds.database;

import java.util.List;

import lkwid.takeyourmeds.model.Medicine;

public interface MedDatabase {
    List<Medicine> getMeds();

    void addMed(Medicine med);
}
