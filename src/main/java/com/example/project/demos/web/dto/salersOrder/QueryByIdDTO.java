package com.example.project.demos.web.dto.salersOrder;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

@Data
public class QueryByIdDTO {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
}
