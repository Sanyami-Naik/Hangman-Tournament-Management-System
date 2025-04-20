package com.coditas.HangMan.payload;

import com.coditas.HangMan.entity.Tournament;
import lombok.Data;

import java.util.List;

@Data
public class CategoryDto {

    private long categoryId;
    private String categoryName;
    private boolean isDeleted;
    private List<Tournament> tournamentList;

    @Override
    public String toString() {
        return "Category{" +
                "categoryId=" + categoryId +
                ", categoryName='" + categoryName + '\'' +
                '}';
    }
}
