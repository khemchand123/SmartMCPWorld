package elastic.search.wrapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class ElasticProxyController {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${elasticsearch.url:http://54.66.84.237:9200}")
    private String elasticUrl;

    @Value("${elasticsearch.username:khem}")
    private String esUser;

    @Value("${elasticsearch.password:Sohna@1997$Mayur@2024}")
    private String esPass;

    @GetMapping("/{index}")
    public ResponseEntity<String> proxyToIndex(
            @PathVariable("index") String index,
            @RequestParam Map<String, String> queryParams) {

        queryParams.remove("sessionId");

        StringBuilder queryBuilder = new StringBuilder();
        if (!queryParams.isEmpty()) {
            queryBuilder.append("?");
            queryParams.forEach((k, v) ->
                    queryBuilder.append(k).append("=").append(v).append("&"));
            queryBuilder.setLength(queryBuilder.length() - 1);
        }

        String targetUrl = elasticUrl + "/" + index + queryBuilder.toString();

        HttpHeaders headers = new HttpHeaders();
        String auth = esUser + ":" + esPass;
        String encodedAuth = Base64Utils.encodeToString(auth.getBytes());
        headers.add(HttpHeaders.AUTHORIZATION, "Basic " + encodedAuth);

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    targetUrl,
                    HttpMethod.GET,
                    new HttpEntity<>(headers),
                    String.class
            );
            return ResponseEntity.status(response.getStatusCode()).body(response.getBody());

        } catch (HttpClientErrorException.NotFound e) {
            // ✅ Handle "index not found"
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"error\":\"Index '" + index + "' not found\"}");

        } catch (HttpClientErrorException e) {
            // ✅ Handle other client errors
            return ResponseEntity.status(e.getStatusCode())
                    .body("{\"error\":\"" + e.getStatusText() + "\"}");

        } catch (Exception e) {
            // ✅ Handle unexpected errors
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\":\"Internal server error: " + e.getMessage() + "\"}");
        }
    }


    @GetMapping("/{index}/{search}")
    public ResponseEntity<String> proxyToSearch(
            @PathVariable("index") String index,
            @PathVariable("search") String search,
            @RequestParam Map<String, String> queryParams) {

        queryParams.remove("sessionId");

        StringBuilder queryBuilder = new StringBuilder();
        if (!queryParams.isEmpty()) {
            queryBuilder.append("?");
            queryParams.forEach((k, v) ->
                    queryBuilder.append(k).append("=").append(v).append("&"));
            queryBuilder.setLength(queryBuilder.length() - 1);
        }

        String targetUrl = elasticUrl + "/" + index + "/" + search + queryBuilder.toString();


        HttpHeaders headers = new HttpHeaders();
        String auth = esUser + ":" + esPass;
        String encodedAuth = Base64Utils.encodeToString(auth.getBytes());
        headers.add(HttpHeaders.AUTHORIZATION, "Basic " + encodedAuth);

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    targetUrl,
                    HttpMethod.GET,
                    new HttpEntity<>(headers),
                    String.class
            );
           // return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
            Map<String, Object> result = new HashMap<>();
            Map<String, Object> responseMap = new HashMap<>();

            responseMap.put("type", "text");
            responseMap.put("text", response.getBody().toString());

            result.put("response", responseMap);

            return ResponseEntity.status(response.getStatusCode()).body(result.toString());


        } catch (HttpClientErrorException.NotFound e) {
            // ✅ Handle "index not found"
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"error\":\"Index '" + index + "' not found\"}");

        } catch (HttpClientErrorException e) {
            // ✅ Handle other client errors
            return ResponseEntity.status(e.getStatusCode())
                    .body("{\"error\":\"" + e.getStatusText() + "\"}");

        } catch (Exception e) {
            // ✅ Handle unexpected errors
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\":\"Internal server error: " + e.getMessage() + "\"}");
        }
    }

}
