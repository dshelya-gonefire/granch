package granch.service;


import java.io.IOException;
import java.util.List;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.springframework.http.ResponseEntity;

import granch.model.LogEntry;

public interface GitService {
	
	ResponseEntity<List<LogEntry>> listRecentAcitivy() throws IOException, GitAPIException;		

}