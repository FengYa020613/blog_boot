package com.example.mingchao_boot.model.page;

import com.example.mingchao_boot.model.article.Assess;
import lombok.Data;

import java.util.List;
@Data
public class AssessPage {
    private Integer total;
    private List<Assess> assessList;
}
