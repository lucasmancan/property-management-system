package br.com.lucasmancan.gap.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AppResponse implements Serializable {
    public static final Object OOPS = new AppResponse("Ooops.. some error occurred.", null, null);
    private String message;
    private Object data;
    private List<String> errors;

    public static AppResponse valueOf(String s, Object o, List<String> errors) {
        return new AppResponse(s, o, null);
    }

    public static AppResponse valueOf(String s) {
        return new AppResponse(s, null, null);
    }


    public static AppResponse valueOf(String s, Object o) {
        return new AppResponse(s, o, null);
    }
}
