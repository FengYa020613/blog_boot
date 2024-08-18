package com.example.mingchao_boot.util;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class Calculater {
    public Float getCalculate(String express){
        Object result = null;
        String expression = express.replace("%","");
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("JavaScript");
        try {
            result = engine.eval(expression);
        } catch (ScriptException e) {
            System.err.println("表达式计算错误: " + e.getMessage());
        }
        return Float.valueOf(result.toString());
    }
}
