package com.example.dai_android_grupo_4.preferences.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dai_android_grupo_4.R;
import com.example.dai_android_grupo_4.preferences.model.DisciplinaFavorita;

import java.util.ArrayList;
import java.util.List;

public class DisciplinaFavoritaAdapter extends RecyclerView.Adapter<DisciplinaFavoritaAdapter.ViewHolder> {

    private List<DisciplinaFavorita> items = new ArrayList<>();
    private OnRemoveClickListener onRemoveClickListener;

    public interface OnRemoveClickListener {
        void onRemove(DisciplinaFavorita disciplinaFavorita);
    }

    public DisciplinaFavoritaAdapter(OnRemoveClickListener listener) {
        this.onRemoveClickListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_preference_chip, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DisciplinaFavorita item = items.get(position);
        holder.tvName.setText(item.getDisciplinaNombre());
        holder.btnRemove.setOnClickListener(v -> {
            if (onRemoveClickListener != null) {
                onRemoveClickListener.onRemove(item);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(List<DisciplinaFavorita> newItems) {
        this.items = newItems;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        ImageButton btnRemove;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvPreferenceName);
            btnRemove = itemView.findViewById(R.id.btnRemove);
        }
    }
}
