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
import com.nicanoritorma.qrattendance.model.AttendanceModel;
import com.nicanoritorma.qrattendance.model.StudentInAttendanceModel;

import java.util.ArrayList;
import java.util.List;

public class AttendanceAdapter extends RecyclerView.Adapter<AttendanceAdapter.AttendanceAdapterVH> {

    private List<AttendanceModel> attendanceList = new ArrayList<>();
    private SparseBooleanArray selectedAttendance = new SparseBooleanArray();

    public void addSelectedItem(Integer selectedStudent)
    {
        selectedAttendance.put(selectedStudent, true);
    }

    public void removeSelectedItem(Integer selectedItem) {
        selectedAttendance.delete(selectedItem);
    }

    public void clearSelectedItems() {
        selectedAttendance.clear();
    }

    public AttendanceModel getItem(int index) {
        return attendanceList.get(index);
    }

    public SparseBooleanArray getSelectedItems() {
        return selectedAttendance;
    }

    public static class AttendanceAdapterVH extends RecyclerView.ViewHolder {
        TextView tv_heading1, tv_heading2, tv_heading3;
        RelativeLayout item;

        public AttendanceAdapterVH(@NonNull View itemView) {
            super(itemView);
            tv_heading1 = itemView.findViewById(R.id.tv_heading1);
            tv_heading2 = itemView.findViewById(R.id.tv_heading2);
            tv_heading3 = itemView.findViewById(R.id.tv_heading3);
            item = itemView.findViewById(R.id.item_relative_layout);
        }
    }

    public void setList(List<AttendanceModel> attendanceList)
    {
        this.attendanceList = attendanceList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AttendanceAdapterVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new AttendanceAdapterVH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AttendanceAdapterVH holder, int position) {
        AttendanceModel attendanceModel = attendanceList.get(position);

        if (!attendanceModel.getAttendanceName().isEmpty())
        {
            holder.tv_heading1.setVisibility(View.VISIBLE);
            holder.tv_heading1.setText(attendanceModel.getAttendanceName());
        }
        if (!attendanceModel.getDetails().isEmpty())
        {
            holder.tv_heading2.setVisibility(View.VISIBLE);
            holder.tv_heading2.setText(attendanceModel.getDetails());
        }
        if ((!attendanceModel.getDate().isEmpty()) || (!attendanceModel.getTime().isEmpty()))
        {
            holder.tv_heading3.setVisibility(View.VISIBLE);
            holder.tv_heading3.setText(attendanceModel.getDate() + " " + attendanceModel.getTime());
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

    private void manageSelectionColor(int position, AttendanceAdapterVH holder) {
        if (selectedAttendance.get(position)) {
            holder.item.setBackgroundResource(R.drawable.selected_item_border);
        } else {
            restoreDrawable(holder.item);
        }
    }

    @Override
    public int getItemCount() {
        return attendanceList.size();
    }
}
