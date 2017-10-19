package granch.model;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;

public class LogEntry implements Serializable, Comparable<LogEntry> {
	private static final long serialVersionUID = 3072240074238593012L;
	private static final Map<ObjectId, RevCommit> refCache = new HashMap<>();

	private transient Date commitDateTime;

	private String branchName;
	private String author;
	private String authorEmail;
	private String commitHash;
	private String commitDate;
	private String commitMessage;

	public static RevCommit getCommit(ObjectId id, Repository repo)
			throws IncorrectObjectTypeException, MissingObjectException, IOException {
		RevCommit commit = refCache.getOrDefault(id, repo.parseCommit(id));
		refCache.put(id, commit);
		return commit;
	}

	@Override
	public int compareTo(LogEntry another) {
		return another.getCommitDateTime().compareTo(getCommitDateTime());
	}

	@Override
	public int hashCode() {
		return 57 + ((commitHash == null) ? 0 : commitHash.hashCode());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;

		LogEntry other = (LogEntry) obj;
		if (commitHash == null) {
			if (other.commitHash != null)
				return false;
		} else if (!commitHash.equals(other.commitHash))
			return false;

		return true;
	}

	public String getAuthorEmail() {
		return authorEmail;
	}

	public void setAuthorEmail(String authorEmail) {
		this.authorEmail = authorEmail;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getCommitHash() {
		return commitHash;
	}

	public void setCommitHash(String commitHash) {
		this.commitHash = commitHash;
	}

	public String getCommitDate() {
		return commitDate;
	}

	public void setCommitDate(String commitDate) {
		this.commitDate = commitDate;
	}

	public String getCommitMessage() {
		return commitMessage;
	}

	public void setCommitMessage(String commitMessage) {
		this.commitMessage = commitMessage;
	}

	public Date getCommitDateTime() {
		return commitDateTime;
	}

	public void setCommitDateTime(Date commitDateTime) {
		this.commitDateTime = commitDateTime;
	}
}