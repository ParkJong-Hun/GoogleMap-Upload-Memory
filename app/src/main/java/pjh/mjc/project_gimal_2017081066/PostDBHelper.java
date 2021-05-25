package pjh.mjc.project_gimal_2017081066;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PostDBHelper extends SQLiteOpenHelper {
    public PostDBHelper(Context context) { super(context, "gimalDB", null, 1); }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Post (code INTEGER PRIMARY KEY, title VARCHAR(50) NOT NULL, article VARCHAR(500) NOT NULL, url VARCHAR(500), latitude VARCHAR(300) NOT NULL, " +
                "longitude VARCHAR(300) NOT NULL, date VARCHAR(100) NOT NULL, FOREIGN KEY (poster) REFERENCES User(id));");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Post");
        onCreate(db);
    }
}
