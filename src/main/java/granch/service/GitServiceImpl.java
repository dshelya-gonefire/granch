package granch.service;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ListBranchCommand.ListMode;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import granch.configuration.GitConfig;
import granch.model.LogEntry;

@Service("gitService")
public class GitServiceImpl implements GitService{
	public static final Logger LOGGER = LoggerFactory.getLogger(GitServiceImpl.class);

	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	private static final String REMOTE_REF_SPEC = "refs/remotes/origin/";
	
	@Autowired
	public GitConfig gitConfig;

	@Override
	public ResponseEntity<List<LogEntry>> listRecentAcitivy() throws IOException, GitAPIException {		
		try (
			Repository repo = new FileRepositoryBuilder().setGitDir(new File(gitConfig.getRepository() + "/.git")).build();
			Git git = new Git(repo)
		) {			
			List<LogEntry> entries = git.branchList()
				.setListMode(ListMode.REMOTE).call()
				.parallelStream()
				.map(r -> mapToLog(r, repo))
				.filter(this::isValidEntry)
				.sorted()
				.collect(Collectors.toList());
			
			return new ResponseEntity<>(entries, HttpStatus.OK); 
		}
	}

	private boolean isValidEntry(LogEntry entry) {
		return entry!=null 
			&& !gitConfig.getExludeRefs().contains(entry.getBranchName()) 
			&& gitConfig.getMonitoredUsers().stream().anyMatch(a -> entry.getAuthorEmail().contains(a));
	}
	
	private LogEntry mapToLog(Ref ref, Repository repo) { 		
		try {
			LogEntry result = new LogEntry();
			RevCommit headCommit = LogEntry.getCommit(ref.getObjectId(), repo);
			
			result.setBranchName(ref.getName().replace(REMOTE_REF_SPEC, ""));
			result.setCommitDateTime(headCommit.getAuthorIdent().getWhen());
			result.setCommitDate(DATE_FORMAT.format(result.getCommitDateTime()));
			result.setAuthor(headCommit.getAuthorIdent().getName());
			result.setAuthorEmail(headCommit.getAuthorIdent().getEmailAddress());
			result.setCommitHash(headCommit.abbreviate(8).name());
			result.setCommitMessage(headCommit.getShortMessage());			
			
			return result;
		} catch (Exception e) {
			LOGGER.error("Unable to read commit info", e);
			return null;
		}
	}
	
	@Scheduled(fixedDelay = 10000)
	public void fetchRemoteChanges() throws Exception {
		LOGGER.info("Fetching git repository");

		ProcessBuilder pb = new ProcessBuilder("git", "fetch");
		pb.directory(new File(gitConfig.getRepository()));
		Process p = pb.start();
		p.waitFor();
	} 
}
