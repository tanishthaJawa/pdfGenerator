package com.freightfox.pdfGenerator;

import com.freightfox.pdfGenerator.entities.OrderSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.TemplateEngine;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/orders")
public class OrderSummaryController {


    @Autowired
    PdfGenerator pdfGenerator;

    private final TemplateEngine templateEngine;

    public OrderSummaryController(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    @RequestMapping(path = "/pdf")
    public ResponseEntity<?> getPDF(@RequestBody OrderSummary orderSummary) throws Exception {


        if (orderSummary == null)

            throw new Exception("OrderSummary not present");

        Map<String, Object> orderSummaryMap = new HashMap<>();


        orderSummaryMap.put("seller", orderSummary.getSeller());

        orderSummaryMap.put("sellerGstin", orderSummary.getSellerGstin());

        orderSummaryMap.put("sellerAddress", orderSummary.getSellerAddress());
        orderSummaryMap.put("buyerAddress", orderSummary.getBuyerAddress());
        orderSummaryMap.put("buyer", orderSummary.getBuyer());
        orderSummaryMap.put("buyerGstin", orderSummary.getBuyerGstin());
        orderSummaryMap.put("items", orderSummary.getItems());

        Resource resource = null;

        try {

            String property = "java.io.tmpdir";

            String tempDir = System.getProperty(property);

            String fileNameUrl = pdfGenerator.createPdf("OrderSummary", orderSummaryMap);

            Path path = Paths.get(tempDir + "/" + fileNameUrl);

            resource = new UrlResource(path.toUri());

        } catch (Exception e) {
            e.printStackTrace();

        }
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(MediaType.APPLICATION_PDF_VALUE))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);

    }

}
