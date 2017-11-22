package hisham.com.training.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.androidsx.rateme.RateMeDialog;

import java.util.List;

import hisham.com.training.R;
import hisham.com.training.adapters.StudentAdapterList;
import hisham.com.training.models.Student;
import hisham.com.training.sqlite.DBHandlers;

public class MainActivity extends AppCompatActivity {

    private DBHandlers db;
    private RecyclerView recyclerView;
    private static final String KEY_LAUNCH_TIMES = "rate_launch_times";
    private static final String PREF_NAME = "student";
    private static final int REPEAT_LAUNCH_TIMES = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new DBHandlers(this);
        setContentView(R.layout.activity_main);
        studentDataFillList();
        initRecyclerView();
        setAdapterRecyclerStudent();
        showRateDialog();
    }

    private void studentDataFillList() {
        if (db.isEmpty()) {
            db.addStudent(new Student("Malik ", 200810223, 25, 85));
            db.addStudent(new Student("Adnan ", 200510270, 25, 85));
            db.addStudent(new Student("Hisham ", 201110270, 25, 85));
        }
    }

    private void initRecyclerView() {
        recyclerView = findViewById(R.id.recycler_activity_main);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setAdapterRecyclerStudent() {
        List<Student> listStudent = db.getAllStudent();
        recyclerView.setAdapter(new StudentAdapterList(listStudent, this));
    }

    @Override
    protected void onStart() {
        super.onStart();
        setAdapterRecyclerStudent();
    }

    private boolean shouldShowRateDialog() {
        SharedPreferences sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        int numLaunchTimes = sharedPreferences.getInt(KEY_LAUNCH_TIMES, 0);
        numLaunchTimes++;
        if (numLaunchTimes > REPEAT_LAUNCH_TIMES) {
            numLaunchTimes = 1;
        }
        editor.putInt(KEY_LAUNCH_TIMES, numLaunchTimes);
        editor.apply();
        return (numLaunchTimes == REPEAT_LAUNCH_TIMES);
    }

    private void showRateDialog() {
        if (shouldShowRateDialog()) {
            new RateMeDialog.Builder(getPackageName(), getString(R.string.app_name))
                    .build()
                    .show(getFragmentManager(), "plain-dialog");
        }
    }
}
