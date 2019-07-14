package br.com.leonardo.processor;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.leonardo.commons.infrastruture.AmazonS3Client;
import br.com.leonardo.commons.infrastruture.FileService;
import br.com.leonardo.commons.model.FilePathData;
import br.com.leonardo.commons.model.ProductDTO;
import br.com.leonardo.commons.service.ProductService;
import br.com.leonardo.processor.service.ConvertFileService;

@Component
public class SpreadsheetProcessor {

	private static final Logger log = LoggerFactory.getLogger(SpreadsheetProcessor.class);

	@Autowired
	private AmazonS3Client amazonS3Service;

	@Autowired
	private ConvertFileService convertFileService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private FileService fileService;

	@RabbitListener(queues = Application.QUEUE_NAME)
	public void receiveMessage(FilePathData filePathData) {
		File file = null;
		try {
			file = amazonS3Service.download(filePathData);
			if (file != null) {
				List<ProductDTO> listProductDTO = convertFileService.convertFile(file);
				productService.saveList(listProductDTO);
			}else {
				throw new RuntimeException("File not found in S3");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			fileService.removeFile(file);
		}
		log.info("Task done: " + filePathData);
	}

}
