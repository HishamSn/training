package hisham.com.training.sqlite;

import android.content.*;
import android.database.Cursor;
import android.database.sqlite.*;

import java.util.*;

import hisham.com.training.models.Student;

public class DBHandlers extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "studentInfo";
    private static final String CONTACTS_TABLE_NAME = "student";
    private static final String CONTACTS_COLUMN_NAME = "name";
    private static final String CONTACTS_COLUMN_ID = "id";
    private static final String CONTACTS_COLUMN_AGE = "age";
    private static final String CONTACTS_COLUMN_AVG = "avg";

    public DBHandlers(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE =
                "CREATE TABLE " + CONTACTS_TABLE_NAME
                        + "("
                        + CONTACTS_COLUMN_ID + " INTEGER PRIMARY KEY,"
                        + CONTACTS_COLUMN_NAME + " TEXT,"
                        + CONTACTS_COLUMN_AGE + " INTEGER,"
                        + CONTACTS_COLUMN_AVG + " INTEGER"
                        + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + CONTACTS_TABLE_NAME);
        onCreate(db);
    }

    public void addStudent(Student student) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CONTACTS_COLUMN_ID, student.getId());
        values.put(CONTACTS_COLUMN_NAME, student.getName());
        values.put(CONTACTS_COLUMN_AGE, student.getAge());
        values.put(CONTACTS_COLUMN_AVG, student.getAvg());
        db.insert(CONTACTS_TABLE_NAME, null, values);
        db.close();
    }

    public Student getStudent(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(CONTACTS_TABLE_NAME, new String[]{CONTACTS_COLUMN_ID,
                        CONTACTS_COLUMN_NAME, CONTACTS_COLUMN_AGE, CONTACTS_COLUMN_AVG}, CONTACTS_COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor == null || isEmpty()) {
            return null;
        }
        cursor.moveToFirst();
        Student student = new Student(
                cursor.getString(cursor.getColumnIndex(CONTACTS_COLUMN_NAME)),
                cursor.getInt(cursor.getColumnIndex(CONTACTS_COLUMN_ID)),
                cursor.getInt(cursor.getColumnIndex(CONTACTS_COLUMN_AGE)),
                cursor.getInt(cursor.getColumnIndex(CONTACTS_COLUMN_AVG)));
        db.close();
        return student;
    }

    public List<Student> getAllStudent() {
        List<Student> studentList = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + CONTACTS_TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Student student = new Student();
                student.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(CONTACTS_COLUMN_ID))));
                student.setName(cursor.getString(cursor.getColumnIndex(CONTACTS_COLUMN_NAME)));
                student.setAge(Integer.parseInt(cursor.getString(cursor.getColumnIndex(CONTACTS_COLUMN_AGE))));
                student.setAvg(Integer.parseInt(cursor.getString(cursor.getColumnIndex(CONTACTS_COLUMN_AVG))));

                studentList.add(student);
            } while (cursor.moveToNext());
        }
        db.close();
        return studentList;
    }

    public void deleteStudent(Student student) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(CONTACTS_TABLE_NAME, CONTACTS_COLUMN_ID + " = ?",
                new String[]{String.valueOf(student.getId())});
        db.close();
    }

    public Boolean isEmpty() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor mCursor = db.rawQuery("SELECT * FROM " + CONTACTS_TABLE_NAME, null);
        return !mCursor.moveToFirst();
    }
}

