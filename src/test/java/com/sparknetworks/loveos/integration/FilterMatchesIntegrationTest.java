package com.sparknetworks.loveos.integration;

import com.sparknetworks.loveos.application.filtermatches.UserMatchedDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * In the current stage the application is reading the matches from a JSON file, in that moment
 * it was not necessary to introduce a database, the data used on that test is read from files
 * located into test/java/resources/integration-test/[matches and profiles]
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class FilterMatchesIntegrationTest {

    @LocalServerPort
    private int localPort;

    @Autowired
    private TestRestTemplate restTemplate;

    private String host;

    @BeforeEach
    void initialise() {
         this.host = "http://localhost:" + localPort;
    }

    @Test
    void should_list_all_matches_from_repository_when_no_filter() {
        ResponseEntity<List<UserMatchedDTO>> response = restCall(HttpMethod.GET, "/api/v1/matches/tonyxpto23");

        assertThat(response.getBody()).hasSize(25);
    }

    @Test
    void should_return_just_the_match_with_compatibility_score_0_99_when_filtering_by_it_and_should_bring_all_its_values_filled_in_result() {
        URI url = UriComponentsBuilder
                .fromHttpUrl(host + "/api/v1/matches/tonyxpto23")
                .queryParam("compatibilityScoreRangeFrom", 0.99)
                .queryParam("compatibilityScoreRangeTo", 0.99)
                .build().toUri();
        ResponseEntity<List<UserMatchedDTO>> response = restCall(HttpMethod.GET, url.toString());

        assertThat(response.getBody()).hasSize(1);

        UserMatchedDTO katherine = getOnlyUserMatched(response);
        assertThat(katherine.getDisplayName()).isEqualTo("Katherine");
        assertThat(katherine.getAge()).isEqualTo(39);
        assertThat(katherine.getJobTitle()).isEqualTo("Lawyer");
        assertThat(katherine.getHeightInCm()).isEqualTo(177);
        assertThat(katherine.getCity().getName()).isEqualTo("London");
        assertThat(katherine.getCity().getLat()).isEqualTo(51.509865);
        assertThat(katherine.getCity().getLon()).isEqualTo(-0.118092);
        assertThat(katherine.getMainPhoto()).isEqualTo("http://thecatapi.com/api/images/get?format=src&type=gif");
        assertThat(katherine.getCompatibilityScore()).isEqualTo(0.99);
        assertThat(katherine.getContactsExchanged()).isEqualTo(50);
        assertThat(katherine.isFavourite()).isEqualTo(true);
        assertThat(katherine.getReligion()).isEqualTo("Atheist");
    }

    private UserMatchedDTO getOnlyUserMatched(ResponseEntity<List<UserMatchedDTO>> response) {
        return response.getBody().get(0);
    }

    private ResponseEntity<List<UserMatchedDTO>> restCall(HttpMethod httpMethod, String url) {
        return restTemplate.exchange(url, httpMethod, null, new ParameterizedTypeReference<List<UserMatchedDTO>>() {});
    }
}
