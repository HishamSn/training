package hisham.com.training.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.gdacciaro.iOSDialog.iOSDialog;

import hisham.com.training.R;
import hisham.com.training.models.Student;
import hisham.com.training.sqlite.DBHandlers;

public class StudentDetailsActivity extends AppCompatActivity implements View.OnClickListener {
    private DBHandlers db;
    private Student student;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_details);
        db = new DBHandlers(this);
        fillStudentDetails();
    }

    private void fillStudentDetails() {
        student = db.getStudent(getIntent().getExtras().getInt(getString(R.string.key_intent_id_student)));
        if (student == (null)) {
            return;
        }
        ((TextView) (findViewById(R.id.textView_student_details_name))).setText(student.getName());
        ((TextView) (findViewById(R.id.textView_activity_student_id))).setText(String.valueOf(student.getId()));
        ((TextView) (findViewById(R.id.textView_activity_student_age))).setText(String.valueOf(student.getAge()));
        ((TextView) (findViewById(R.id.textView_activity_student_avg))).setText(String.valueOf(student.getAvg()));
        (findViewById(R.id.button_student_details_delete)).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (student == (null)) {
            return;
        }
        switch (v.getId()) {
            case R.id.button_student_details_delete:
                db.deleteStudent(student);
                getDialogStudentDeleted();
                break;
        }
    }

    private void getDialogStudentDeleted() {
        final iOSDialog iOSDialog = new iOSDialog(this);
        iOSDialog.setTitle(getString(R.string.dialog_title_msg));
        iOSDialog.setSubtitle(getString(R.string.dialog_subtitle_msg));
        iOSDialog.setPositiveLabel(getString(R.string.dialog_ok));
        iOSDialog.setBoldPositiveLabel(true);
        iOSDialog.setPositiveListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iOSDialog.dismiss();
                onBackPressed();
            }
        });
        iOSDialog.show();
    }
}

