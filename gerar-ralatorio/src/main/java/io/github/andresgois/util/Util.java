package io.github.andresgois.util;


import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import com.lowagie.text.Cell;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPageEventHelper;
import com.lowagie.text.pdf.PdfWriter;

import io.github.andresgois.model.Usuario;

public class Util {


	public static String CriarRelatorio(List<Usuario> users) {
		Document myPDFDoc = new Document(PageSize.A4.rotate(),40f,40f,20f,20f);

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

        String title = "Relatório";
        String lorenIpsum1 = "Relatório de Usuário por região";

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
        int i = 1;
        LinkedHashMap<Integer, Usuario> listRows = new LinkedHashMap<>();
        for (Usuario u : users) {
        	listRows.put(i, u);

        	listRows.forEach((index,userDetailRow) -> {
        		Long id = userDetailRow.getId();
        		String nome = userDetailRow.getNome();
        		String estado = userDetailRow.getEstado();
        		
        		myTable.addCell(new Cell(new Phrase(id)));
        		myTable.addCell(new Cell(new Phrase(nome)));
        		myTable.addCell(new Cell(new Phrase(estado)));
        	});
		}

        try {
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
            return base64String;
        } catch (IOException de) {
            System.err.println(de.getMessage());
        }
		return null;
		
	}
}
