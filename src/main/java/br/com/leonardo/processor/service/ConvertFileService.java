package br.com.leonardo.processor.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import br.com.leonardo.commons.model.ProductDTO;

@Service
public class ConvertFileService {
	
	private final int LM = 0;
	private final int NAME = 1;
	private final int FREE_SHIPPING = 2;
	private final int DESCRIPTION = 3;
	private final int PRICE = 4;
	
	public List<ProductDTO> convertFile(File file) {
		List<ProductDTO> listProductDTO = new ArrayList<ProductDTO>();
        try {
            FileInputStream fileStream = new FileInputStream(file);
            XSSFWorkbook workbook = new XSSFWorkbook(fileStream);
            XSSFSheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
           
            while(rowIterator.hasNext()){
                Row row = rowIterator.next();
                
                if(row.getRowNum() <= 2) continue;
                
                Iterator<Cell> cellIterator = row.cellIterator();
                ProductDTO productDTO = new ProductDTO();
                while (cellIterator.hasNext()){
                    Cell cell = cellIterator.next();
                    switch (cell.getColumnIndex()) {
						case LM:
							productDTO.setLm(convertCellToString(cell));
							break;
						case NAME:
							productDTO.setName(cell.getStringCellValue());
							break;
						case FREE_SHIPPING:
							productDTO.setFreeShipping(convertFreeShipping(cell.getNumericCellValue()));
							break;
						case DESCRIPTION:
							productDTO.setDescription(cell.getStringCellValue());
							break;
						case PRICE:
							productDTO.setPrice(convertCellToDouble(cell));
							break;
						default:
							break;
					}
                }
                listProductDTO.add(productDTO);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
		return listProductDTO;
	}
	
	private String convertCellToString(Cell cell) {
		cell.setCellType(Cell.CELL_TYPE_STRING);
		return cell.getStringCellValue();
	}
	
	private double convertCellToDouble(Cell cell) {
		return Double.parseDouble(cell.getStringCellValue());
	}
	
	private boolean convertFreeShipping(double cell) {
		return cell > 0.0; 
			
	}
	
}
