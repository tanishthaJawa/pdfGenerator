package com.freightfox.pdfGenerator;

import com.freightfox.pdfGenerator.entities.OrderSummary;
import com.freightfox.pdfGenerator.exceptions.OrderSummaryNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@Service
public class OrderSummaryService {
    @Autowired
    PdfGenerator pdfGenerator;

    public Resource createResource(OrderSummary orderSummary) throws OrderSummaryNotFoundException {
        if (orderSummary == null)
            throw new OrderSummaryNotFoundException();

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
        return resource;
    }
}
