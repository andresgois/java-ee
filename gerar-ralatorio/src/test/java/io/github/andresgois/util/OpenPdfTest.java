package io.github.andresgois.util;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPageEventHelper;
import com.lowagie.text.pdf.PdfWriter;

public class OpenPdfTest {

	@Test
	public void deverCriarPdf() {
		Document myPDFDoc = new Document(PageSize.A4.rotate(),
                40f,   // left
                40f,   // right
                20f,  // top
                20f); // down

        Rectangle footer = new Rectangle(30f, 30f, PageSize.A4.getRight(30f), 30f);
        footer.setBorder(Rectangle.BOX);
        footer.setBorderColor(Color.BLACK);
        footer.setBorderWidth(2f);

        Rectangle header = new Rectangle(30f, 30f, 30f,30f );//PageSize.A4.getRight(30f), 140f);
        header.setBorder(Rectangle.BOX);
        header.setBorderColor(Color.BLUE);
        header.setBorderWidth(1f);
        header.setTop(PageSize.A4.getTop(30f));
        header.setBottom(PageSize.A4.getTop(180f));

        // Define a string as title
        String title = "Learning OpenPDF with Java";
        // Define a paragraph
        String lorenIpsum1 = "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo";

        // Let's create a Table object
        Table myTable = new Table(3); // 3 columns
        myTable.setPadding(2f);
        myTable.setSpacing(1f);
        myTable.setWidth(100f);

        // Create the header of the table
        ArrayList<String> headerTable = new ArrayList<>();
        headerTable.add("Name");
        headerTable.add("Surname");
        headerTable.add("Age");

        headerTable.forEach(e -> {
            Cell current = new Cell(new Phrase(e));
            current.setHeader(true);
            current.setBackgroundColor(Color.LIGHT_GRAY);
            myTable.addCell(current);
        });

        // Then create a list of rows and add them to the table
        LinkedHashMap<Integer, List<String>> listRows = new LinkedHashMap<>();
        listRows.put(1, Arrays.asList("Miguel","Surname", "37"));
        listRows.put(2, Arrays.asList("Username1","Surname2", "40"));
        listRows.forEach((index,userDetailRow) -> {
            String currentName = userDetailRow.get(0);
            String currentSurname = userDetailRow.get(1);
            String currentAge = userDetailRow.get(2);

            myTable.addCell(new Cell(new Phrase(currentName)));
            myTable.addCell(new Cell(new Phrase(currentSurname)));
            myTable.addCell(new Cell(new Phrase(currentAge)));
        });


        try {
            //FileOutputStream pdfOutputFile = new FileOutputStream("C:\\Users\\andre\\workspace-2012\\app-tdd\\sample1.pdf");
        	ByteArrayOutputStream pdfOutputFile = new ByteArrayOutputStream();

            final PdfWriter pdfWriter = PdfWriter.getInstance(myPDFDoc, pdfOutputFile);
            pdfWriter.setPageEvent(new PdfPageEventHelper() {
                @Override
                public void onEndPage(PdfWriter writer, Document document) {
                    PdfContentByte cb = writer.getDirectContent();
                    cb.rectangle(header);
                    //cb.rectangle(footer);
                }
            });

            //1) Create a pdf object with using the class
            //  import com.lowagie.text.Image and the method getInstance
            //  with the url https://kesizo.github.io/assets/images/kesizo-logo-6-832x834.png
            Image image = Image.getInstance("https://kesizo.github.io/assets/images/kesizo-logo-6-832x834.png");
            image.scaleAbsolute(150f,150f);

            myPDFDoc.open();  // Open the Document

            /* Here we add some metadata to the generated pdf */
            myPDFDoc.addTitle("This is a simple PDF example");
            myPDFDoc.addSubject("This is a tutorial explaining how to use openPDF");
            myPDFDoc.addKeywords("Java, OpenPDF, Basic sample");
            myPDFDoc.addCreator("Miguel and Kesizo.com");
            myPDFDoc.addAuthor("Miguel Doctor");
            /* End of the adding metadata section */

            // Create a Font object
            Font titleFont = new Font(com.lowagie.text.Font.HELVETICA, 
            		20f, com.lowagie.text.Font.BOLD, 
            		Color.BLACK);

            // Create a paragraph with the new font
            Paragraph paragraph = new Paragraph(title,titleFont);

            // Element class provides properties to align
            // Content elements within the document
            paragraph.setAlignment(Element.ALIGN_CENTER);

            myPDFDoc.add(paragraph);

            // Adding an empty line
            myPDFDoc.add(new Paragraph(Chunk.NEWLINE));

            // Include the text as content of the pdf
            myPDFDoc.add(new Paragraph(lorenIpsum1));

            // Include the table to the document
            myPDFDoc.add(myTable);

            //2) Finally let's add the image to the document
            //myPDFDoc.add(image);

            myPDFDoc.close();
            pdfWriter.close();
            
            byte[] pdfBytes = pdfOutputFile.toByteArray();
            String base64String = java.util.Base64.getEncoder().encodeToString(pdfBytes);
            System.out.println(base64String);
        } catch (IOException de) {
            System.err.println(de.getMessage());
        }
		Assert.assertTrue(true);
	}
}
