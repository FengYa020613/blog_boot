package com.example.mingchao_boot.Annotations;

public @interface TableColumn {
    String column();
    String columnUse() default "";
}
