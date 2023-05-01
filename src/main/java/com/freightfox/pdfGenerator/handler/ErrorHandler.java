package com.freightfox.pdfGenerator.handler;

import com.freightfox.pdfGenerator.exceptions.FileNotParsedException;
import com.freightfox.pdfGenerator.exceptions.OrderSummaryNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ErrorHandler {
    @ExceptionHandler(value = OrderSummaryNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public  ErrorResponse  orderSummaryNotFoundExceptionHandler(){
        return new ErrorResponse("Order Summary not found",HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = FileNotParsedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public  ErrorResponse  fileNotParsedExceptionHandler(){
        return new ErrorResponse("file Not Parsed", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
