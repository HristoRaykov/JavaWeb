package metube.web.servlets;

import metube.domain.models.binding.TubeCreateBindingModel;
import metube.domain.models.services.TubeServiceModel;
import metube.service.TubeService;
import metube.util.ModelMapper;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@WebServlet("/tubes/create")
public class TubeCreateServlet extends HttpServlet {

	private final TubeService tubeService;
	private final ModelMapper modelMapper;
	
	@Inject
	public TubeCreateServlet(TubeService tubeService, ModelMapper modelMapper) {
		this.tubeService = tubeService;
		this.modelMapper = modelMapper;
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/jsps/create-tube.jsp").forward(req,resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		TubeCreateBindingModel tubeCreateBindingModel = new TubeCreateBindingModel(
				req.getParameter( "name"),
				req.getParameter("description"),
				URLDecoder.decode(req.getParameter("youTubeLink"), "UTF-8"),
				req.getParameter("uploader")
		);
		TubeServiceModel tubeServiceModel = this.modelMapper.map(tubeCreateBindingModel,TubeServiceModel.class);
		this.tubeService.saveTube(tubeServiceModel);
		
		
		
		resp.sendRedirect(String.format("/tubes/details?title=%s",tubeServiceModel.getName()));
	}
}
