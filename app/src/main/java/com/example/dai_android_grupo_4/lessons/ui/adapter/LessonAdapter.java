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

public class LessonAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_TYPE_HEADER = 0;
    private static final int VIEW_TYPE_LESSON = 1;

    private List<Object> items = new ArrayList<>(); // Puede ser String (header) o Lesson

    @Override
    public int getItemViewType(int position) {
        return items.get(position) instanceof String ? VIEW_TYPE_HEADER : VIEW_TYPE_LESSON;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_HEADER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lesson_header, parent, false);
            return new HeaderViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.clase_item, parent, false);
            return new LessonViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeaderViewHolder) {
            String header = (String) items.get(position);
            ((HeaderViewHolder) holder).tvHeader.setText(header);
        } else if (holder instanceof LessonViewHolder) {
            Lesson clase = (Lesson) items.get(position);
            LessonViewHolder lessonHolder = (LessonViewHolder) holder;

            lessonHolder.tvDisciplina.setText(clase.getDisciplina().getNombre());
            lessonHolder.tvFechaHora.setText(formatFechaHora(clase.getFechaInicio()));
            String instructorName = clase.getInstructor().getNombre() + " " + clase.getInstructor().getApellido();
            lessonHolder.tvInstructor.setText("Instructor: " + instructorName);
            lessonHolder.tvSede.setText("Sede: " + clase.getSede().getNombre());

            lessonHolder.itemView.setOnClickListener(v -> {
                Intent intent = new Intent(v.getContext(), LessonDetailActivity.class);
                intent.putExtra(LessonDetailActivity.EXTRA_CLASE_ID, clase.getId());
                v.getContext().startActivity(intent);
            });
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setLessons(List<Lesson> lessons) {
        items.clear();

        // Separar recomendadas vs otras
        List<Lesson> recommended = new ArrayList<>();
        List<Lesson> others = new ArrayList<>();

        for (Lesson lesson : lessons) {
            if (lesson.getRelevanceScore() > 0) {
                recommended.add(lesson);
            } else {
                others.add(lesson);
            }
        }

        // Agregar header + clases recomendadas
        if (!recommended.isEmpty()) {
            items.add("🌟 RECOMENDADAS PARA TI");
            items.addAll(recommended);
        }

        // Agregar header + otras clases
        if (!others.isEmpty()) {
            items.add("📋 OTRAS CLASES");
            items.addAll(others);
        }

        notifyDataSetChanged();
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

    static class HeaderViewHolder extends RecyclerView.ViewHolder {
        TextView tvHeader;

        public HeaderViewHolder(@NonNull View itemView) {
            super(itemView);
            tvHeader = itemView.findViewById(R.id.tvHeader);
        }
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
