package br.com.leonardo.processor;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.leonardo.commons.infrastruture.AmazonS3Client;
import br.com.leonardo.commons.infrastruture.FileService;
import br.com.leonardo.commons.model.FilePathData;
import br.com.leonardo.commons.model.ProductDTO;
import br.com.leonardo.commons.service.ProductService;
import br.com.leonardo.processor.service.ConvertFileService;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class SpreadsheetProcessorTest {

	@Mock
	private AmazonS3Client amazonS3Service;

	@Mock
	private ConvertFileService convertFileService;
	
	@Mock
	private ProductService productService;
	
	@Mock
	private FileService fileService;
	
	@InjectMocks
	private SpreadsheetProcessor spreadsheetProcessor;
	
	@Test
	public void processFilePathDataTest() throws IOException {
		
		FilePathData filePathData = new FilePathData("some", "some");
		
		File expectedFile = new File("some");
		
		when(amazonS3Service.download(filePathData)).thenReturn(expectedFile);
		
		doNothing().when(productService).saveList(any());
		
		doNothing().when(fileService).removeFile(any());
		
		List<ProductDTO> listProductDTO = new ArrayList<>();
		
		listProductDTO.add(new ProductDTO());
		
		when(convertFileService.convertFile(any())).thenReturn(listProductDTO);
		
		spreadsheetProcessor.receiveMessage(filePathData);
		
		verify(amazonS3Service,times(1)).download(any());
		
		verify(convertFileService,times(1)).convertFile(any());
		
		verify(productService,times(1)).saveList(any());
		
		verify(fileService,times(1)).removeFile(any());
		
	}
	
	@Test(expected = RuntimeException.class)
	public void processFileNotFound() throws IOException {
		
		FilePathData filePathData = new FilePathData("some", "some");
		
		when(amazonS3Service.download(filePathData)).thenReturn(null);
		
		spreadsheetProcessor.receiveMessage(filePathData);
		
	}

}
