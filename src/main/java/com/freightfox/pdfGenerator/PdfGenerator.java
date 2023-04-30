package com.freightfox.pdfGenerator;

import com.lowagie.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Map;


@Component
public class PdfGenerator {

    @Autowired
    private TemplateEngine templateEngine;

    public String createPdf(String templateName, Map<String,Object> map) throws IOException, DocumentException {

        String fileNameUrl = "";

        Context ctx = new Context();

        if (map != null) {

            for (Map.Entry<String, Object> pair : map.entrySet()) {
                ctx.setVariable(pair.getKey(), pair.getValue());
            }

        }

        String processedHtml = templateEngine.process(templateName, ctx);

        FileOutputStream os = null;


        try {

            final File outputFile = File.createTempFile("OrderSummary_" + LocalDate.now() + "_", ".pdf");

            os = new FileOutputStream(outputFile);

            ITextRenderer itr = new ITextRenderer();

            itr.setDocumentFromString(processedHtml);

            itr.layout();

            itr.createPDF(os, false);

            itr.finishPDF();

            fileNameUrl = outputFile.getName();

        } catch (DocumentException e) {
            throw new RuntimeException(e);
        } finally {

            if (os != null) {
                os.close();
            }

        }

        return fileNameUrl;

    }

}





