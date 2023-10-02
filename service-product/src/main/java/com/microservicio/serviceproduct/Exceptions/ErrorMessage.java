package com.microservicio.serviceproduct.Exceptions;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

/**
 * Error de mensaje
 */
@Getter @Setter
@Builder
public class ErrorMessage {
    private String code ;
    private List<Map<String, String >> messages;
}