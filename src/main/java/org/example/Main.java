package org.example;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.stream.Stream;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
// import com.itextpdf.text.pdf.parser.Path;
import com.itextpdf.text.pdf.parser.clipper.Paths;
//import com.utils.Utils;

class District {
    String districtCode;
    String districtName;
    String provinceCode;
    String provinceName;

    public District(String districtCode, String districtName, String provinceCode, String provinceName) {
        this.districtCode = districtCode;
        this.districtName = districtName;
        this.provinceCode = provinceCode;
        this.provinceName = provinceName;
    }

//    creation of District Constructor

    @Override
    public String toString() {
        return "District [districtCode=" + districtCode + ", districtName=" + districtName + ", provinceCode="
                + provinceCode + ", provinceName=" + provinceName + "]";
    }
    public String getDistrictCode() {
        return districtCode;
    }

    public String getDistrictName() {
        return districtName;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public String getProvinceName() {
        return provinceName;
    }

//    This class is initialisign the codes and names
//    And then create the class constructor
//    Then produce the tostring of the members of the class District
//    Then getMembers function
}

public class Main {
    public static void main(String[] args) {
        ArrayList<District> data = new ArrayList<District>();
//This is creation of the arraylist containing elements of type District
        try {
            Scanner scanner = new Scanner(new BufferedReader(new FileReader("districts.txt")));
//This is to first create the file object by FileReader
//Then read in buffer form for performance and efficiency
            scanner.useDelimiter("[,\n]");
            while (scanner.hasNext()) {
                try {
                    String dcode = scanner.next();
                    String dname = scanner.next();
                    String pcode = scanner.next();
                    String pname = scanner.next();
                    data.add(new District(dcode, dname, pcode, pname));

//              Data list will be containing the District-instances
                } catch (NoSuchElementException e) {
                    Utils.print(e.getMessage());
                }
            }
            // Utils.printArray(data.toArray());

            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream("districts.pdf"));

            document.open();
            Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
            Chunk chunk = new Chunk("List of districts", font);
            chunk.setTextRise(6);
            PdfPTable table = new PdfPTable(4);
            addTableHeader(table);
            for (District d : data) {
                addCustomRows(table, d);
            }
            document.add(chunk);
            document.add(table);
            document.close();

            // BufferedReader buffer = new BufferedReader(new FileReader("input.txt"));
            // String s;
            // while ((s = buffer.readLine()) != null) {
            // Utils.print(s);
            // }
            // buffer.close();

        } catch (IOException | DocumentException | URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    private static void addTableHeader(PdfPTable table) {
        Stream.of("district code", "district name", "province code", "province name")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setBorderWidth(2);
                    header.setPhrase(new Phrase(columnTitle));
                    table.addCell(header);
                });

    }



    private static void addCustomRows(PdfPTable table, District district)
            throws URISyntaxException, BadElementException, IOException {

        PdfPCell cell1 = new PdfPCell(new Phrase(district.getDistrictCode()));
        cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell1);

        PdfPCell cell2 = new PdfPCell(new Phrase(district.getDistrictName()));
        cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell2);

        PdfPCell cell3 = new PdfPCell(new Phrase(district.getProvinceCode()));
        cell3.setVerticalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell3);

        PdfPCell cell4 = new PdfPCell(new Phrase(district.getProvinceName()));
        cell4.setVerticalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell4);
    }
}