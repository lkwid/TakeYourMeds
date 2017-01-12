package lkwid.takeyourmeds.model;

import android.app.AlarmManager;
import android.widget.ImageView;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

@DatabaseTable(tableName = "medicine")
public class Medicine {
    public static final int MORNING = 1;
    public static final int AFTERNOON = 2;
    public static final int EVENING = 3;
    public static final int USER_DEFINED = 4;

    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField(canBeNull = false)
    private String name;
    @DatabaseField(canBeNull = false)
    private int whenToTake;
    @DatabaseField()
    private ImageView medPicture;

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

    public String getWhenToTake() {
        String timeOfDay = "Wprowadź godzinę";

        switch (whenToTake) {
            case MORNING:
                timeOfDay =  "Rano";
                break;
            case AFTERNOON:
                timeOfDay = "Po południu";
                break;
            case EVENING:
                timeOfDay = "Wieczorem";
                break;
        }
        return timeOfDay;
    }

    public void setWhenToTake(int whenToTake) {
        this.whenToTake = whenToTake;
    }
}


