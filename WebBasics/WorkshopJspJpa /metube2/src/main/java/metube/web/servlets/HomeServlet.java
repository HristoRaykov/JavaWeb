package metube.web.servlets;

import metube.domain.entities.enums.TubeStatus;
import metube.domain.models.service.TubeServiceModel;
import metube.domain.models.view.alltubesviewmodels.AllTubesViewModel;
import metube.domain.models.view.alltubesviewmodels.TubeViewModel;
import metube.service.TubeService;
import org.modelmapper.ModelMapper;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {
	
	private final TubeService tubeService;
	private final ModelMapper modelMapper;
	
	@Inject
	public HomeServlet(TubeService tubeService, ModelMapper modelMapper) {
		this.tubeService = tubeService;
		this.modelMapper = modelMapper;
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<TubeServiceModel> tubeServiceModels = this.tubeService.findAll();
		tubeServiceModels = tubeServiceModels.stream()
				.filter(tsm -> tsm.getTubeStatus() == TubeStatus.APPROVED)
				.collect(Collectors.toList());
		
		List<TubeViewModel> tubeViewModels = Arrays.asList(this.modelMapper.map(tubeServiceModels, TubeViewModel[].class));
		String username = (String) req.getSession().getAttribute("username");
		AllTubesViewModel allTubesViewModel = new AllTubesViewModel(username, tubeViewModels);
		req.setAttribute("model", allTubesViewModel);
		
		req.getRequestDispatcher("/jsp/home.jsp").forward(req, resp);
	}
}
