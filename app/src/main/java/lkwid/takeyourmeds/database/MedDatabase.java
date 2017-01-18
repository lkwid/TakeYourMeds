package lkwid.takeyourmeds.database;

import java.util.List;

import lkwid.takeyourmeds.model.Medicine;

public interface MedDatabase {
    List<Medicine> getMeds();

    Medicine getMed(int position);

    void addMed(Medicine med);

    void updateMed(Medicine med, int position);
}
