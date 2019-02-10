package metube.web.servlets;

import metube.domain.entities.enums.TubeStatus;
import metube.domain.models.service.TubeServiceModel;
import metube.service.TubeService;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/admin/tube/approve/*")
public class TubeApproveServlet extends HttpServlet {
	
	private final TubeService tubeService;
	
	@Inject
	public TubeApproveServlet(TubeService tubeService) {
		this.tubeService = tubeService;
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String[] urlParams = req.getRequestURI().split("/");
		String tubeId = urlParams[urlParams.length - 1];
		
		TubeServiceModel tubeServiceModel = this.tubeService.findTubeById(tubeId);
		tubeServiceModel.setTubeStatus(TubeStatus.APPROVED);
		this.tubeService.updateTubeStatus(tubeServiceModel);
		
		resp.sendRedirect("/admin/tube/pending");
	}


}
