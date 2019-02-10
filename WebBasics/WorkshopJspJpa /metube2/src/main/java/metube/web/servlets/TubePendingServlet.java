package metube.web.servlets;

import metube.domain.models.service.TubeServiceModel;
import metube.domain.models.view.PendingTubeViewModel;
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

@WebServlet("/admin/tube/pending")
public class TubePendingServlet extends HttpServlet {
	
	private final TubeService tubeService;
	private final ModelMapper modelMapper;
	
	@Inject
	public TubePendingServlet(TubeService tubeService, ModelMapper modelMapper) {
		this.tubeService = tubeService;
		this.modelMapper = modelMapper;
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<TubeServiceModel> pendingTubes = this.tubeService.findAllPendingTubes();
		List<PendingTubeViewModel> pendingTubeViewModels = Arrays.asList(this.modelMapper.map(pendingTubes,PendingTubeViewModel[].class));
		
		req.setAttribute("pendingtubes", pendingTubeViewModels);
		
		req.getRequestDispatcher("/jsp/tubes-pending.jsp").forward(req,resp);
	}
}
