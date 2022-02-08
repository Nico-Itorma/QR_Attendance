package com.nicanoritorma.qrattendance.ui.adapter;
/**
 * Created by Nicanor Itorma
 */
import android.graphics.BitmapFactory;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nicanoritorma.qrattendance.R;
import com.nicanoritorma.qrattendance.model.QrModel;
import com.nicanoritorma.qrattendance.utils.EncryptorAndDecryptor;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class QrAdapter extends RecyclerView.Adapter<QrAdapter.QrAdapterVH> {

    private List<QrModel> studentList = new ArrayList<>();

    private SparseBooleanArray selectedQr = new SparseBooleanArray();

    public void addSelectedItem(Integer selectedItem)
    {
        selectedQr.put(selectedItem, true);
    }

    public void removeSelectedItem(Integer selectedItem) {
        selectedQr.delete(selectedItem);
    }

    public void clearSelectedItems() {
        selectedQr.clear();
    }

    public SparseBooleanArray getSelectedItems() {
        return selectedQr;
    }

    public static class QrAdapterVH extends RecyclerView.ViewHolder {

        private TextView tv_heading1, tv_heading2, tv_heading3;
        private ImageView iv_qrCode;
        private RelativeLayout item;

        public QrAdapterVH(@NonNull View itemView) {
            super(itemView);
            tv_heading1 = itemView.findViewById(R.id.tv_heading1);
            tv_heading2 = itemView.findViewById(R.id.tv_heading2);
            tv_heading3 = itemView.findViewById(R.id.tv_heading3);
            iv_qrCode = itemView.findViewById(R.id.iv_itemLayout);
            item = itemView.findViewById(R.id.item_relative_layout);
        }
    }

    public void setList(List<QrModel> studentList)
    {
        this.studentList = studentList;
        notifyDataSetChanged();
    }

    public QrModel getItem(int index) {
        return studentList.get(index);
    }

    @NonNull
    @Override
    public QrAdapterVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new QrAdapterVH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull QrAdapterVH holder, int position) {
        QrModel qrModel = studentList.get(position);
        initText(qrModel, holder);
        manageSelectionColor(position, holder);
    }

    private void initText(QrModel qrModel, QrAdapterVH holder)
    {
        if (!qrModel.getName().isEmpty())
        {
            holder.tv_heading1.setVisibility(View.VISIBLE);
            holder.tv_heading1.setText(qrModel.getName());
        }
        if (!qrModel.getIdNum().isEmpty())
        {
            holder.tv_heading2.setVisibility(View.VISIBLE);
            holder.tv_heading2.setText(qrModel.getIdNum());
        }
        if (!qrModel.getCollege().isEmpty())
        {
            holder.tv_heading3.setVisibility(View.VISIBLE);
            holder.tv_heading3.setText(qrModel.getCollege());
        }
        if (!qrModel.getQrCode().isEmpty())
        {
            holder.iv_qrCode.setVisibility(View.VISIBLE);
            String qr = qrModel.getQrCode();
            String decryptedData = EncryptorAndDecryptor.decrypt(qr);
            byte[] qrCode = Base64.getDecoder().decode(decryptedData);
            holder.iv_qrCode.setImageBitmap(
                    BitmapFactory.decodeByteArray
                            (qrCode, 0, qrCode.length));
        }
    }

    public void restoreDrawable(View v) {
        final int paddingBottom = v.getPaddingBottom();
        final int paddingLeft = v.getPaddingLeft();
        final int paddingRight = v.getPaddingRight();
        final int paddingTop = v.getPaddingTop();
        v.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
        v.setBackgroundResource(R.color.white);
    }

    private void manageSelectionColor(int position, QrAdapterVH holder) {
        if (selectedQr.get(position)) {
            holder.item.setBackgroundResource(R.drawable.selected_item_border);
        } else {
            restoreDrawable(holder.item);
        }
    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }
}
