package metube.web.servlets;

import metube.domain.models.services.TubeServiceModel;
import metube.domain.models.views.AllTubesViewModel;
import metube.service.TubeService;
import metube.util.ModelMapper;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@WebServlet("/tubes/all")
public class AllTubesServlet extends HttpServlet {
	
	private final TubeService tubeService;
	private final ModelMapper modelMapper;
	
	@Inject
	public AllTubesServlet(TubeService tubeService, ModelMapper modelMapper) {
		this.tubeService = tubeService;
		this.modelMapper = modelMapper;
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<TubeServiceModel> tubeServiceModels = this.tubeService.findAll();
		List<AllTubesViewModel> allTubesViewModels =
				Arrays.asList(this.modelMapper.map(tubeServiceModels,AllTubesViewModel[].class));
		req.setAttribute("allTubes",allTubesViewModels);
		
		req.getRequestDispatcher("/jsps/all-tubes.jsp").forward(req,resp);
	}
}
