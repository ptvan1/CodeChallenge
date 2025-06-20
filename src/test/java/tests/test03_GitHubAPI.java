package tests;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import org.testng.annotations.Test;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class test03_GitHubAPI {
	@Test
    public static void getGitHubInfo() throws Exception {
        String url = "https://api.github.com/orgs/SeleniumHQ/repos";
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url)).build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        ObjectMapper mapper = new ObjectMapper();
        JsonNode repos = mapper.readTree(response.body());

        int totalIssues = 0;
        String mostWatched = "";
        int maxWatchers = 0;

        List<JsonNode> sortedRepos = new ArrayList<>();
        repos.forEach(sortedRepos::add);

        sortedRepos.sort((a, b) -> b.get("updated_at").asText().compareTo(a.get("updated_at").asText()));

        for (JsonNode repo : sortedRepos) {
            totalIssues += repo.get("open_issues_count").asInt();
            int watchers = repo.get("watchers_count").asInt();
            if (watchers > maxWatchers) {
                maxWatchers = watchers;
                mostWatched = repo.get("name").asText();
            }
        }
        
        System.out.println("**********\na.Total open issues: " + totalIssues + "\n");
        System.out.println("**********\nb.Sorted repositories by date updated:");
        sortedRepos.forEach(r -> System.out.println(r.get("name").asText() + " - " + r.get("updated_at").asText()));
        System.out.println("\n**********\nc.Repository has the most watchers: " + mostWatched + "\n");
    }
}
