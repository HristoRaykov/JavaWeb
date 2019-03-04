package springmvc.springessentials.exodia.web.controllers;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import springmvc.springessentials.exodia.domain.models.binding.ScheduleBindingModel;
import springmvc.springessentials.exodia.domain.models.services.DocumentServiceModel;
import springmvc.springessentials.exodia.domain.models.views.DocumentDetailsViewModel;
import springmvc.springessentials.exodia.domain.models.views.DocumentPrintViewModel;
import springmvc.springessentials.exodia.service.DocumentService;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Controller
public class DocumentController {
	
	private final DocumentService documentService;
	private final ModelMapper modelMapper;
	
	@Autowired
	public DocumentController(DocumentService documentService, ModelMapper modelMapper) {
		this.documentService = documentService;
		this.modelMapper = modelMapper;
	}
	
	@GetMapping("/schedule")
	public ModelAndView schedule(HttpSession session, Model model){
		if (session.getAttribute("username")==null){
			return new ModelAndView("redirect:/login");
		}
		
		return new ModelAndView("schedule");
	}
	
	@PostMapping("/schedule")
	public ModelAndView scheduleConfirm(HttpSession session, @ModelAttribute ScheduleBindingModel scheduleBindingModel,
	                                    RedirectAttributes redirectAttributes){
		if (session.getAttribute("username")==null){
			return new ModelAndView("redirect:/login");
		}
		
		DocumentServiceModel documentServiceModel = modelMapper.map(scheduleBindingModel,DocumentServiceModel.class);
		Optional<DocumentServiceModel> savedDocumentOptional =documentService.addDocumentToDb(documentServiceModel);
		if (savedDocumentOptional.isEmpty()){
			redirectAttributes.addFlashAttribute("scheduleForm",scheduleBindingModel);
			
			return new ModelAndView("redirect:/schedule");
		}
		
		
		return new ModelAndView("redirect:/details/"+savedDocumentOptional.get().getId());
	}
	
	@GetMapping("/details/{id}")
	public ModelAndView schedule(HttpSession session, @PathVariable(name = "id") String id){
		if (session.getAttribute("username")==null){
			return new ModelAndView("redirect:/login");
		}
		Optional<DocumentServiceModel> documentDetailsViewModelOptional = documentService.getDocumentById(id);
		if (documentDetailsViewModelOptional.isEmpty()){
			return new ModelAndView("redirect:/schedule");
		}
		DocumentDetailsViewModel documentDetailsViewModel = modelMapper.map(documentDetailsViewModelOptional.get(),DocumentDetailsViewModel.class);
		
		return new ModelAndView("details","document",documentDetailsViewModel);
	}
	
	@GetMapping("/print/{id}")
	public ModelAndView print(HttpSession session, @PathVariable(name = "id") String id){
		if (session.getAttribute("username")==null){
			return new ModelAndView("redirect:/login");
		}
		Optional<DocumentServiceModel> documentServiceModelOptional = documentService.getDocumentById(id);
		if (documentServiceModelOptional.isEmpty()){
			return new ModelAndView("redirect:/home");
		}
		DocumentPrintViewModel documentPrintViewModel = modelMapper.map(documentServiceModelOptional.get(),DocumentPrintViewModel.class);
		
		return new ModelAndView("print","document",documentPrintViewModel);
	}
	
	@PostMapping("/print/{id}")
	public void printPost(HttpSession session, @PathVariable("id") String id, HttpServletResponse response) throws IOException, DocumentException {
		if (session.getAttribute("username")==null){
			return;
		}
		
		String htmlContent = documentService.retrieveDocumentAsHtml(id);
		
		byte[] pdfContent = convertHtmlToPdf(htmlContent);
		
		documentService.delete(id);
		
		response.setContentType("application/pdf");
		response.setHeader("Content-disposition", "attachment; filename=document.pdf");
		response.setContentLength(pdfContent.length);
		response.getOutputStream().write(pdfContent);
		response.getOutputStream().flush();
	}
	
	private byte[] convertHtmlToPdf(String htmlContent) throws DocumentException, IOException {
		Document document = new Document();
		ByteArrayOutputStream os =new ByteArrayOutputStream();
		ByteArrayInputStream is =new ByteArrayInputStream(htmlContent.getBytes(StandardCharsets.UTF_8));
		PdfWriter pdfWriter = PdfWriter.getInstance(document,os);
		document.open();
		XMLWorkerHelper.getInstance().parseXHtml(pdfWriter,document,is);
		document.close();
		
		return os.toByteArray();
	}
	
}
