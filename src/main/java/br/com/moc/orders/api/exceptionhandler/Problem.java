package br.com.moc.orders.api.exceptionhandler;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@NoArgsConstructor
public class Problem {
    private Integer status;
    private OffsetDateTime dateTime;
    private String title;
    private List<Field> fields;

    public Problem(Integer status, OffsetDateTime dateTime, String title) {
        this.status = status;
        this.dateTime = dateTime;
        this.title = title;
    }

    @AllArgsConstructor
    @Getter
    public static class Field {

        private String name;
        private String message;

    }
}
