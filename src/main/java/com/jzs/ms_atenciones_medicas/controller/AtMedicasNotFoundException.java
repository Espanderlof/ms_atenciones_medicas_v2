package com.jzs.ms_atenciones_medicas.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class AtMedicasNotFoundException extends RuntimeException {
    
    public AtMedicasNotFoundException(String message){
        super(message);
    }


}
