//package com.example.student_course_selection_system.Adapter;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.student_course_selection_system.Model.Course;
//import com.example.student_course_selection_system.R;
//
//import java.util.List;
//
//public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {
//    private List<Course> courseList;
//
//    public CourseAdapter(List<Course> courseList) {
//        this.courseList = courseList;
//    }
//
//    @NonNull
//    @Override
//    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_course, parent, false);
//        return new CourseViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
//        Course course = courseList.get(position);
//        holder.courseIdTextView.setText(String.valueOf(course.getId()));
//        holder.courseNameTextView.setText(course.getName());
//        holder.teacherNameTextView.setText(course.getTeacherName());
//    }
//
//    @Override
//    public int getItemCount() {
//        return courseList.size();
//    }
//
//    static class CourseViewHolder extends RecyclerView.ViewHolder {
//        TextView courseIdTextView;
//        TextView courseNameTextView;
//        TextView teacherNameTextView;
//
//        CourseViewHolder(@NonNull View itemView) {
//            super(itemView);
//            courseIdTextView = itemView.findViewById(R.id.courseIdTextView);
//            courseNameTextView = itemView.findViewById(R.id.courseNameTextView);
//            teacherNameTextView = itemView.findViewById(R.id.teacherNameTextView);
//        }
//    }
//}
