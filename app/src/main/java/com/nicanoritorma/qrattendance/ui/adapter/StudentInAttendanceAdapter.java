package com.nicanoritorma.qrattendance.ui.adapter;
/**
 * Created by Nicanor Itorma
 */
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nicanoritorma.qrattendance.R;
import com.nicanoritorma.qrattendance.model.StudentInAttendanceModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter for Recyclerview of ClickedAttendance Activity
 */
public class StudentInAttendanceAdapter extends RecyclerView.Adapter<StudentInAttendanceAdapter.StudentInAttendanceVH> {

    private List<StudentInAttendanceModel> studentsAdded = new ArrayList<>();
    private SparseBooleanArray selectedStudents = new SparseBooleanArray();

    public void addSelectedItem(Integer selectedStudent)
    {
        selectedStudents.put(selectedStudent, true);
    }

    public void removeSelectedItem(Integer selectedItem) {
        selectedStudents.delete(selectedItem);
    }

    public void clearSelectedItems() {
        selectedStudents.clear();
    }

    public StudentInAttendanceModel getItem(int index) {
        return studentsAdded.get(index);
    }

    public SparseBooleanArray getSelectedItems() {
        return selectedStudents;
    }

    public static class StudentInAttendanceVH extends RecyclerView.ViewHolder
    {
        TextView tv_heading1, tv_heading2;
        RelativeLayout item;

        public StudentInAttendanceVH(@NonNull View itemView) {
            super(itemView);
            tv_heading1 = itemView.findViewById(R.id.tv_heading1);
            tv_heading2 = itemView.findViewById(R.id.tv_heading2);
            item = itemView.findViewById(R.id.item_relative_layout);
        }
    }

    public void setList(List<StudentInAttendanceModel> studentsAdded)
    {
        this.studentsAdded = studentsAdded;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public StudentInAttendanceVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new StudentInAttendanceVH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentInAttendanceVH holder, int position) {
        StudentInAttendanceModel student = studentsAdded.get(position);
        if (student.getFullname().length() > 0)
        {
            holder.tv_heading1.setVisibility(View.VISIBLE);
            holder.tv_heading1.setText(student.getFullname());
        }
        if (student.getIdNum().length() > 0)
        {
            holder.tv_heading2.setVisibility(View.VISIBLE);
            holder.tv_heading2.setText(student.getIdNum());
        }
        manageSelectionColor(position, holder);
    }

    public void restoreDrawable(View v) {
        final int paddingBottom = v.getPaddingBottom();
        final int paddingLeft = v.getPaddingLeft();
        final int paddingRight = v.getPaddingRight();
        final int paddingTop = v.getPaddingTop();
        v.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
        v.setBackgroundResource(R.color.white);
    }

    private void manageSelectionColor(int position, StudentInAttendanceVH holder) {
        if (selectedStudents.get(position)) {
            holder.item.setBackgroundResource(R.drawable.selected_item_border);
        } else {
            restoreDrawable(holder.item);
        }
    }

    @Override
    public int getItemCount() {
        return studentsAdded.size();
    }
}