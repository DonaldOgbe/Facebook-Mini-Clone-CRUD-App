package org.deodev.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GenericApiResponse<T> {
    boolean success;
    String message;
    T data;
}
