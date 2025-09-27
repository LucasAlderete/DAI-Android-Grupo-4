package com.example.dai_android_grupo_4.data.api.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PageReservaDto {
    
    @SerializedName("content")
    private List<ReservaDto> content;
    
    @SerializedName("totalElements")
    private Long totalElements;
    
    @SerializedName("totalPages")
    private Integer totalPages;
    
    @SerializedName("size")
    private Integer size;
    
    @SerializedName("number")
    private Integer number;
    
    @SerializedName("numberOfElements")
    private Integer numberOfElements;
    
    @SerializedName("first")
    private Boolean first;
    
    @SerializedName("last")
    private Boolean last;
    
    @SerializedName("empty")
    private Boolean empty;

    // Constructores
    public PageReservaDto() {}

    // Getters y Setters
    public List<ReservaDto> getContent() { return content; }
    public void setContent(List<ReservaDto> content) { this.content = content; }

    public Long getTotalElements() { return totalElements; }
    public void setTotalElements(Long totalElements) { this.totalElements = totalElements; }

    public Integer getTotalPages() { return totalPages; }
    public void setTotalPages(Integer totalPages) { this.totalPages = totalPages; }

    public Integer getSize() { return size; }
    public void setSize(Integer size) { this.size = size; }

    public Integer getNumber() { return number; }
    public void setNumber(Integer number) { this.number = number; }

    public Integer getNumberOfElements() { return numberOfElements; }
    public void setNumberOfElements(Integer numberOfElements) { this.numberOfElements = numberOfElements; }

    public Boolean getFirst() { return first; }
    public void setFirst(Boolean first) { this.first = first; }

    public Boolean getLast() { return last; }
    public void setLast(Boolean last) { this.last = last; }

    public Boolean getEmpty() { return empty; }
    public void setEmpty(Boolean empty) { this.empty = empty; }
}
