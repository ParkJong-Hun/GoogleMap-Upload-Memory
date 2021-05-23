package pjh.mjc.project_gimal_2017081066;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PostDBHelper extends SQLiteOpenHelper {
    public PostDBHelper(Context context) { super(context, "naverDB", null, 1); }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE user (id VARCHAR(15) PRIMARY KEY, password VARCHAR(15));");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS user");
        onCreate(db);
    }
}
