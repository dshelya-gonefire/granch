package granch.configuration;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "git")
@Component
public class GitConfig {
	private String repository;
	private List<String> exludeRefs = new ArrayList<>();
	private List<String> monitoredUsers = new ArrayList<>();

	public String getRepository() {
		return repository;
	}

	public void setRepository(String repository) {
		this.repository = repository;
	}

	public List<String> getExludeRefs() {
		return exludeRefs;
	}

	public void setExludeRefs(List<String> exludeRefs) {
		this.exludeRefs = exludeRefs;
	}

	public List<String> getMonitoredUsers() {
		return monitoredUsers;
	}

	public void setMonitoredUsers(List<String> monitoredUsers) {
		this.monitoredUsers = monitoredUsers;
	}
}