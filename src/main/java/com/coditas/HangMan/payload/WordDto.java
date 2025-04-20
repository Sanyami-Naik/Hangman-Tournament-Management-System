package com.coditas.HangMan.payload;

import com.coditas.HangMan.entity.Hint;
import com.coditas.HangMan.entity.Tournament;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WordDto {
    private String wordName;
    private String hintName;


}
