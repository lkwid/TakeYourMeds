package lkwid.takeyourmeds.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "medicine")
public class Medicine {
    public static final int PIECES = 0;
    public static final int MILLILITERS = 1;

    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField(canBeNull = false)
    private String name;
    @DatabaseField(canBeNull = false)
    private String regularity;
    @DatabaseField(canBeNull = false)
    private int dosage;
    @DatabaseField(canBeNull = false)
    private int unit;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegularity() {
        return regularity;
    }

    public String printRegularity() {
        StringBuilder printableRegularity = new StringBuilder();
        if (regularity.charAt(0) == '1')
            printableRegularity.append("Rano");
        if (printableRegularity.length() > 0)
            printableRegularity.append(" ");
        if (regularity.charAt(1) == '1')
            printableRegularity.append("Południe");
        if (printableRegularity.length() > 0)
            printableRegularity.append(" ");
        if (regularity.charAt(2) == '1')
            printableRegularity.append("Wieczór");

        return printableRegularity.toString();
        }

    public void setRegularity(String regularity) {
        this.regularity = regularity;
    }

    public int getDosage() {
        return dosage;
    }

    public void setDosage(int dosage) {
        this.dosage = dosage;
    }

    public int getUnit() {
        return unit;
    }

    public String printUnit() {
        String printableUnits = new String();
        if (unit == 0)
            printableUnits = "szt";
        else if (unit == 1)
            printableUnits = "ml";

        return printableUnits;
    }

    public void setUnit(int unit) {
        this.unit = unit;
    }
}


