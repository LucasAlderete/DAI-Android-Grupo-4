package com.example.dai_android_grupo_4.lessons.ui.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dai_android_grupo_4.R;
import com.example.dai_android_grupo_4.lessons.model.Lesson;
import com.example.dai_android_grupo_4.lessons.ui.LessonDetailActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class LessonAdapter extends RecyclerView.Adapter<LessonAdapter.LessonViewHolder> {

    private List<Lesson> clases = new ArrayList<>();

    @NonNull
    @Override
    public LessonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.clase_item, parent, false);
        return new LessonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LessonViewHolder holder, int position) {
        Lesson clase = clases.get(position);
        holder.tvDisciplina.setText(clase.getDisciplina().getNombre());
        holder.tvFechaHora.setText(formatFechaHora(clase.getFechaInicio()));
        String instructorName = clase.getInstructor().getNombre() + " " + clase.getInstructor().getApellido();
        holder.tvInstructor.setText("Instructor: " + instructorName);
        holder.tvSede.setText("Sede: " + clase.getSede().getNombre());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), LessonDetailActivity.class);
            intent.putExtra(LessonDetailActivity.EXTRA_CLASE_ID, clase.getId());
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return clases.size();
    }

    public void setLessons(List<Lesson> nuevasLessons) {
        this.clases = nuevasLessons;
        notifyDataSetChanged(); // Notifica al RecyclerView que los datos han cambiado
    }

    private String formatFechaHora(String fechaHoraStr) {
        if (fechaHoraStr == null || fechaHoraStr.isEmpty()) {
            return "Fecha no disponible";
        }
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS", Locale.getDefault());
        SimpleDateFormat outputFormat = new SimpleDateFormat("E d 'de' MMMM HH:mm'hs'", new Locale("es", "ES"));

        try {
            Date date = inputFormat.parse(fechaHoraStr);
            if (date != null) {
                String formattedDate = outputFormat.format(date);
                formattedDate = formattedDate.replace(".", "");
                return formattedDate.substring(0, 1).toUpperCase() + formattedDate.substring(1);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return fechaHoraStr;
    }

    static class LessonViewHolder extends RecyclerView.ViewHolder {
        TextView tvDisciplina;
        TextView tvFechaHora;
        TextView tvInstructor;
        TextView tvSede;

        public LessonViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDisciplina = itemView.findViewById(R.id.tvDisciplina);
            tvFechaHora = itemView.findViewById(R.id.tvFechaHora);
            tvInstructor = itemView.findViewById(R.id.tvInstructor);
            tvSede = itemView.findViewById(R.id.tvSede);
        }
    }
}
