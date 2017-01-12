package lkwid.takeyourmeds.database;

import android.content.Context;

import com.j256.ormlite.android.AndroidConnectionSource;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import lkwid.takeyourmeds.model.Medicine;

public class SqliteMedDatabase implements MedDatabase {
    private Dao<Medicine, Integer> mDao;

    public SqliteMedDatabase(Context context) {
        MedDatabaseOpenHelper medOpenHelper = new MedDatabaseOpenHelper(context);
        ConnectionSource connectionSource = new AndroidConnectionSource(medOpenHelper);
        try {
            mDao = DaoManager.createDao(connectionSource, Medicine.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Medicine> getMeds() {
        try {
            return mDao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    @Override
    public void addMed(Medicine med) {
        try {
            mDao.create(med);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
