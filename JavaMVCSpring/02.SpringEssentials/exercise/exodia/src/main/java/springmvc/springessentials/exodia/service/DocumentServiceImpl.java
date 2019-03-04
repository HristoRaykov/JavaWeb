package springmvc.springessentials.exodia.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springmvc.springessentials.exodia.domain.entities.Document;
import springmvc.springessentials.exodia.domain.models.services.DocumentServiceModel;
import springmvc.springessentials.exodia.repository.DocumentRepository;


import java.util.ArrayList;
import java.util.Arrays;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class DocumentServiceImpl implements DocumentService {
	
	private final DocumentRepository documentRepository;
	private final ModelMapper modelMapper;
	
	@Autowired
	public DocumentServiceImpl(DocumentRepository documentRepository, ModelMapper modelMapper) {
		this.documentRepository = documentRepository;
		this.modelMapper = modelMapper;
	}
	
	
	@Override
	@SuppressWarnings("Duplicates")
	public Optional<DocumentServiceModel> addDocumentToDb(DocumentServiceModel documentServiceModel) {
		Document document = this.modelMapper.map(documentServiceModel, Document.class);
		
		
		try {
			document = this.documentRepository.save(document);
			return Optional.of(this.modelMapper.map(document, DocumentServiceModel.class));
		} catch (Exception e) {
			e.printStackTrace();
			return Optional.empty();
		}
		
	}
	
	@Override
	@SuppressWarnings("Duplicates")
	public Optional<DocumentServiceModel> getDocumentById(String documentId) {
		Optional<Document> documentOptional = this.documentRepository.findById(documentId);
		if (documentOptional.isEmpty()) {
			return Optional.empty();
		}
		DocumentServiceModel documentServiceModel = this.modelMapper.map(documentOptional.get(), DocumentServiceModel.class);
		return Optional.of(documentServiceModel);
		
	}
	
	@Override
	public List<DocumentServiceModel> getAllDocuments() {
		return Arrays.asList(this.modelMapper.map(this.documentRepository.findAll(), DocumentServiceModel[].class));
		
	}
	
	@Override
	public boolean delete(String documentId) {
		try {
			this.documentRepository.deleteById(documentId);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@Override
	public String retrieveDocumentAsHtml(String id) {
		Optional<DocumentServiceModel> documentServModOpt = getDocumentById(id);
		if (documentServModOpt.isEmpty()) {
			return "";
		}
		DocumentServiceModel document = documentServModOpt.get();
		String documentText = extractDocumentText(document);
		
		return renderDocumentHtml(documentText);
	}
	
	private String renderDocumentHtml(String documentText) {
		List<String> lines = Arrays.stream(documentText.split(System.lineSeparator())).collect(Collectors.toList());
		lines = replaceLinesHeaderTags(lines);
		lines = replaceLinesListTags(lines);
		documentText = String.join(System.lineSeparator(), lines);
		documentText = replaceBoldWrappers(documentText);
		
		StringBuilder htmlcontent = new StringBuilder();
		
		htmlcontent
				.append("<!DOCTYPE html>").append(System.lineSeparator())
				.append("<html lang=\"en\">").append(System.lineSeparator())
				.append("<body>").append(System.lineSeparator())
				.append(documentText).append(System.lineSeparator());
		
		htmlcontent
				.append("</body>").append(System.lineSeparator())
				.append("</html>").append(System.lineSeparator());
		
		return htmlcontent.toString();
	}
	
	private List<String> replaceLinesHeaderTags(List<String> lines) {
		List<String> renderedLines = new ArrayList<>();
		String regex = "^#{1,6}";
		Pattern pattern = Pattern.compile(regex);
		String renderedLine;
		for (String line : lines) {
			Matcher matcher = pattern.matcher(line);
			if (matcher.find()) {
				int matchingGroupCount = matcher.group().length();
				renderedLine = matcher.replaceFirst("<h" + matchingGroupCount + " align=\"center\">");
				renderedLine = renderedLine + "</h" + matchingGroupCount + "><hr/>";
				renderedLines.add(renderedLine);
			} else {
				renderedLines.add(line);
			}
		}
		return renderedLines;
	}
	
	private List<String> replaceLinesListTags(List<String> lines) {
		List<String> renderedLines = new ArrayList<>();
		boolean isUlTagOpened = false;
		
		String renderedLine;
		for (String line : lines) {
			
			if (line.substring(0, 1).equals("*")) {
				if (!isUlTagOpened) {
					renderedLines.add("<ul>");
					isUlTagOpened = true;
				}
				renderedLine = "<li>" + line.substring(1) + "</li>";
				renderedLines.add(renderedLine);
			} else {
				if (isUlTagOpened){
					renderedLines.add("</ul>");
					isUlTagOpened = false;
				}
				renderedLines.add(line);
			}
		}
		
		if (isUlTagOpened){
			renderedLines.add("</ul>");
		}
		
		return renderedLines;
	}
	
	private String replaceBoldWrappers(String documentText) {
		String pattern = "(\\*\\*)([^*]*)(\\*\\*)";
		
		documentText = documentText.replaceAll(pattern, "<b>$2</b>");
		
		return documentText;
	}
	
	private String extractDocumentText(DocumentServiceModel document) {
		return "#" + document.getTitle() + System.lineSeparator() +
				document.getContent();
	}
}
