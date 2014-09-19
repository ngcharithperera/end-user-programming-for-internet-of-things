package org.eup4iot.remindme.db;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataBaseHelper extends SQLiteOpenHelper {

    /** The tag. */
    private final String TAG = DataBaseHelper.this.getClass().getSimpleName();

    private static final int DATABASE_VERSION = 1;

	private static final String DATABASE_NAME = "remindme_db.sqlite";
	
    private SQLiteDatabase myDataBase = null;

    private final Context mContext;

    static final String TABLE_PROGRAM = "Program"; 
    
    static final String TABLE_ACTIVITY = "Activity";
    
    static final String TABLE_CALENDER = "Calender";
    
    static final String TABLE_CHANNEL = "Channel";
    
    static final String TABLE_LOCATION = "Location";
    
    static final String TABLE_OBJECTPROPERTY = "ObjectProperty";   
    
    static final String TABLE_SMARTOBJECT = "SmartObject";
    
    static final String TABLE_TASK = "Task";
    
    static final String TABLE_USER = "User";
    
    /**
     * Constructor Takes and keeps a reference of the passed context in order to access to the application assets and
     * resources.
     * 
     * @param context
     */
    public DataBaseHelper(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.mContext = context;

        try {
            createDataBase();
        } catch (IOException ioe) {
            ioe.printStackTrace();
            throw new Error("Unable to create database");
        }

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    	
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    /**
     * Creates a empty database on the system and rewrites it with your own database.
     * */
    public void createDataBase() throws IOException {
        Log.i(TAG, "createDataBase");
        boolean dbExist = checkDataBase_old();

        if (dbExist) {
            // do nothing - database already exist
            Log.i(TAG, "database already exist");
        } else {
            // By calling this method and empty database will be created into
            // the default system path
            // of your application so we are gonna be able to overwrite that
            // database with our database.
            this.getReadableDatabase();

            try {
                copyDataBase();
            } catch (IOException e) {
                e.printStackTrace();
                throw new Error("Error copying database");
            }
        }
    }

    /**
     * Check if the database already exist to avoid re-copying the file each time you open the application.
     * 
     * @return true if it exists, false if it doesn't
     */
    private boolean checkDataBase() {
        Log.i(TAG, "checkDataBase");
        SQLiteDatabase checkDB = null;
        try {
            String myPath = getDBPath() + getDBName();
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
        } catch (SQLiteException e) {
            // database does't exist yet.
        }

        if (checkDB != null) {
            checkDB.close();

        }
        return checkDB != null ? true : false;
    }

    /**
     * Check if the database already exist to avoid re-copying the file each time you open the application.
     * 
     * @return true if it exists, false if it doesn't
     */
    public boolean checkDataBase_old() {
        File dbFile = new File(getDBPath() + getDBName());
        return dbFile.exists();
    }

    /**
     * Copies your database from your local assets-folder to the just created empty database in the system folder, from
     * where it can be accessed and handled. This is done by transfering bytestream.
     * */
    private void copyDataBase() throws IOException {
        Log.i(TAG, "copyDataBase");
        // Open your local db as the input stream
        InputStream myInput = mContext.getAssets().open(getDBName());

        // Path to the just created empty db
        String outFileName = getDBPath() + getDBName();
        Log.i(TAG, outFileName);
        // Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);

        // transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }
        
        // Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    public void openDataBase() throws SQLException {
        Log.i(TAG, "openDataBase");
        // Open the database
        String myPath = getDBPath() + getDBName();
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
    }

    @Override
    public synchronized void close() {
        if (myDataBase != null) {
            myDataBase.close();
        }
        super.close();
    }

    // Add your public helper methods to access and get content from the
    // database.
    // You could return cursors by doing "return myDataBase.query(....)" so it'd
    // be easy
    // to you to create adapters for your views.

    private String getDBPath() {
        return "/data/data/" + mContext.getPackageName() + "/databases/";
    }

    private String getDBName() {
        return DATABASE_NAME;
    }

}
