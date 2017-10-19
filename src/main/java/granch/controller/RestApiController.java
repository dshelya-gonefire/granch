package granch.controller;

import java.io.IOException;
import java.util.List;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import granch.model.LogEntry;
import granch.service.GitService;

@RestController
@RequestMapping("/api")
public class RestApiController {

	@Autowired
	GitService gitService;

	@RequestMapping(value = "/git", method = RequestMethod.GET)
	public ResponseEntity<List<LogEntry>> listRecentAcitivy() throws IOException, GitAPIException {
		return gitService.listRecentAcitivy();
	}
	
}