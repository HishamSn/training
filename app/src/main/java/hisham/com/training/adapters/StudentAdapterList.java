package hisham.com.training.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import hisham.com.training.R;
import hisham.com.training.activities.StudentDetailsActivity;
import hisham.com.training.models.Student;


/**
 * Created by Hisham on 11/14/2017.
 */

public class StudentAdapterList extends RecyclerView.Adapter<StudentAdapterList.StudentViewHolder> {
    private Context context;
    private List<Student> studentList;

    public StudentAdapterList() {
    }

    public StudentAdapterList(List<Student> studentList, Context context) {
        this.studentList = studentList;
        notifyDataSetChanged();
        this.context = context;
    }

    @Override
    public StudentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_student, parent, false);
        return new StudentViewHolder(row);
    }

    @Override
    public void onBindViewHolder(StudentViewHolder holder, int position) {
        Student student = studentList.get(position);
        holder.name.setText(student.getName());
        holder.id.setText(String.valueOf(student.getId()));
        holder.age.setText(String.valueOf(student.getAge()));
        holder.avg.setText(String.valueOf(student.getAvg()));
        rowItemClick(holder);
    }

    private void rowItemClick(final StudentViewHolder holder) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer studentID = studentList.get(holder.getAdapterPosition()).getId();
                if (studentID == null) {
                    return;
                }
                Intent studentDetailsIntent = new Intent(context, StudentDetailsActivity.class);
                studentDetailsIntent.putExtra(context.getString(R.string.key_intent_id_student), studentID);
                context.startActivity(studentDetailsIntent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    protected class StudentViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView id;
        private TextView age;
        private TextView avg;

        public StudentViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.textView_row_student_name);
            id = itemView.findViewById(R.id.textView_row_student_id);
            age = itemView.findViewById(R.id.textView_row_student_age);
            avg = itemView.findViewById(R.id.textView_row_student_avg);
        }
    }
}
