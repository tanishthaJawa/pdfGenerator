package com.freightfox.pdfGenerator;

import com.freightfox.pdfGenerator.entities.OrderSummary;
import com.freightfox.pdfGenerator.exceptions.FileNotParsedException;
import com.freightfox.pdfGenerator.exceptions.OrderSummaryNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/orders")
@Slf4j
public class OrderSummaryController {


    @Autowired
    OrderSummaryService orderSummaryService;

    public OrderSummaryController() {
        log. info("Initializing Order summary controller");
    }

    @RequestMapping(path = "/pdf")
    public ResponseEntity<?> getPDF(@RequestBody OrderSummary orderSummary) throws OrderSummaryNotFoundException {

        Resource resource = orderSummaryService.createResource(orderSummary);

        if (resource == null) {
            return new ResponseEntity<>(new FileNotParsedException(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(MediaType.APPLICATION_PDF_VALUE))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);

    }

}
