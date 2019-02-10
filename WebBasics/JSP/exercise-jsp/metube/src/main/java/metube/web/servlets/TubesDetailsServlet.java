package metube.web.servlets;

import metube.domain.models.services.TubeServiceModel;
import metube.domain.models.views.TubeDetailsViewModel;
import metube.service.TubeService;
import metube.util.ModelMapper;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/tubes/details")
public class TubesDetailsServlet extends HttpServlet {
	
	private final TubeService tubeService;
	private final ModelMapper modelMapper;
	
	@Inject
	public TubesDetailsServlet(TubeService tubeService, ModelMapper modelMapper) {
		this.tubeService = tubeService;
		this.modelMapper = modelMapper;
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String tubeName = req.getParameter("title");
		
		TubeServiceModel tubeServiceModel = this.tubeService.getTubeByName(tubeName);
		TubeDetailsViewModel tubeDetailsViewModel = this.modelMapper.map(tubeServiceModel,TubeDetailsViewModel.class);
		req.setAttribute("tubeDetailsModel",tubeDetailsViewModel);
		
		req.getRequestDispatcher("/jsps/details-tube.jsp").forward(req,resp);
	}
	
}
