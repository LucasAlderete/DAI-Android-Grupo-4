package com.example.dai_android_grupo_4.historial.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class AsistenciaPageResponse {

    @SerializedName("content")
    private List<AsistenciaResponse> content;

    @SerializedName("totalElements")
    private Long totalElements;

    @SerializedName("totalPages")
    private Integer totalPages;

    @SerializedName("size")
    private Integer size;

    @SerializedName("number")
    private Integer number;

    @SerializedName("first")
    private Boolean first;

    @SerializedName("last")
    private Boolean last;

    @SerializedName("empty")
    private Boolean empty;

    // Getters y Setters
    public List<AsistenciaResponse> getContent() { return content; }
    public void setContent(List<AsistenciaResponse> content) { this.content = content; }

    public Long getTotalElements() { return totalElements; }
    public void setTotalElements(Long totalElements) { this.totalElements = totalElements; }

    public Integer getTotalPages() { return totalPages; }
    public void setTotalPages(Integer totalPages) { this.totalPages = totalPages; }

    public Integer getSize() { return size; }
    public void setSize(Integer size) { this.size = size; }

    public Integer getNumber() { return number; }
    public void setNumber(Integer number) { this.number = number; }

    public Boolean getFirst() { return first; }
    public void setFirst(Boolean first) { this.first = first; }

    public Boolean getLast() { return last; }
    public void setLast(Boolean last) { this.last = last; }

    public Boolean getEmpty() { return empty; }
    public void setEmpty(Boolean empty) { this.empty = empty; }
}