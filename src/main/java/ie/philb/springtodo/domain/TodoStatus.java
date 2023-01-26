/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package ie.philb.springtodo.domain;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Philip.Bradley
 */
public enum TodoStatus {

    Pending("PND"),
    Complete("CMP");

    private final String code;

    private TodoStatus(String code) {
        this.code = code;
    }

    public String code() {
        return code;
    }

    private static final Map<String, TodoStatus> valueMap = new HashMap<>();

    static {
        for (TodoStatus value : values()) {
            valueMap.put(value.code(), value);
        }
    }

    public static TodoStatus fromCode(String code) {
        return valueMap.get(code);
    }
}
