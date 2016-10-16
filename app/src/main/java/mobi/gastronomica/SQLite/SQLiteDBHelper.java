package mobi.gastronomica.SQLite;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.IOException;

import mobi.gastronomica.utils.AssetUtils;
import mobi.gastronomica.utils.Constants;

import static mobi.gastronomica.utils.Logs.LOGI;
import static mobi.gastronomica.utils.Logs.makeLogTag;

public class SQLiteDBHelper extends SQLiteOpenHelper {

    private Context context = null;

    private static final String TAG = makeLogTag(SQLiteDBHelper.class);

    // Position table columns
    public SQLiteDBHelper(Context context) {
        super(context, Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION);
        this.context = context;
    }

    public String getDatabaseName() {
        return Constants.DATABASE_NAME;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        LOGI(TAG, "Creating database object");

        try {
            execSqlFile(Constants.CREATEFILE, db);//pasar el nombre de la base de dato que va hacer parsiada
        } catch (Exception e) {
            // TODO: handle exception
            throw new RuntimeException("Database creation failed", e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub

        LOGI(TAG, "Upgrading database version from " + oldVersion + " to " + newVersion);

        try {
            for (String sqlFile : AssetUtils.list(Constants.SQL_DIR, context.getAssets())) {
                if (sqlFile.startsWith(Constants.UPGRADEFILE_PREFIX)) {
                    int fileVersion = Integer.parseInt(sqlFile.substring(Constants.UPGRADEFILE_PREFIX.length(), sqlFile.length() - Constants.UPGRADEFILE_SUFFIX.length()));
                    LOGI("fileVersion", Integer.toString(fileVersion));
                    LOGI("fileVersion",  Integer.toString(fileVersion)+ ">"+  Integer.toString(oldVersion)+ "&&"+  Integer.toString(fileVersion)+ "<=" + Integer.toString(newVersion));
                    if (fileVersion > oldVersion && fileVersion <= newVersion) {
                        execSqlFile(sqlFile, db);
                        LOGI("sqlFile", sqlFile);
                    }
                }
            }
            LOGI(TAG, "Database upgrade successful");
        } catch (IOException exception) {
            throw new RuntimeException("Database upgrade failed", exception);
        }
    }

    private void execSqlFile(String createfile, SQLiteDatabase db) throws SQLException, IOException {
        // TODO Auto-generated method stub

        LOGI(TAG, "exec sql file: {}" + createfile);

        try {
            for (String sqlInstruction : SQLParser.parseSqlFile(Constants.SQL_DIR + "/" + createfile, context.getAssets())) {
                LOGI(TAG, "sql: {}" + sqlInstruction);
                db.execSQL(sqlInstruction);
            }
        } catch (SQLException | IOException e) {
            // TODO Auto-generated catch block;
            LOGI(TAG, "Error executing command: ", e);
        }
    }

    public void onDelete(SQLiteDatabase db) {
        db.delete(Constants.TABLE_EVENTOS, null, null);
        db.delete(Constants.TABLE_CERCA, null, null);
        db.delete(Constants.TABLE_COLABORADORES, null, null);
        db.delete(Constants.TABLE_PUBLICACIONES, null, null);
        db.delete(Constants.TABLE_RESENA, null, null);
    }
}// endClass
