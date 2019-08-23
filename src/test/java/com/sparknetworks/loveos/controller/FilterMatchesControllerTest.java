package com.sparknetworks.loveos.controller;

import com.sparknetworks.loveos.application.filtermatches.CityDTO;
import com.sparknetworks.loveos.application.filtermatches.FilteredMatchesRetrieval;
import com.sparknetworks.loveos.application.filtermatches.Filters;
import com.sparknetworks.loveos.application.filtermatches.UserMatchedDTO;
import com.sparknetworks.loveos.application.filtermatches.filter.*;
import com.sparknetworks.loveos.domain.ProfileNotFoundException;
import com.sparknetworks.loveos.domain.UserMatchedFilter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class FilterMatchesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FilteredMatchesRetrieval filteredMatchesRetrieval;

    private static final String USER_ID = "tony";

    private static final String NON_EXISTENT_USER_ID = "geovan";

    @Test
    void should_return_not_found_when_user_does_not_exist() throws Exception {
        when(filteredMatchesRetrieval.retrieve(eq(NON_EXISTENT_USER_ID), any())).thenThrow(ProfileNotFoundException.class);

        mockMvc.perform(get("/api/v1/matches/" + NON_EXISTENT_USER_ID))
                .andExpect(status().isNotFound());
    }

    @Test
    void should_call_filtered_matches_retrieval_with_no_filter_and_response_with_status_code_200() throws Exception {
        mockMvc.perform(get("/api/v1/matches/" + USER_ID))
                .andReturn()
                .getResponse();

        verify(filteredMatchesRetrieval).retrieve(eq("tony"), argThat(argument -> argument.getFilters().isEmpty()));
    }

    @Test
    void should_call_filtered_matches_retrieval_with_all_filters_and_response_with_status_code_200() throws Exception {
        mockMvc.perform(get("/api/v1/matches/" + USER_ID +
                "?hasPhoto=true&" +
                "inContact=true&" +
                "favorited=true&" +
                "compatibilityScoreRangeFrom=0.50&" +
                "compatibilityScoreRangeTo=0.95&" +
                "ageRangeFrom=18&" +
                "ageRangeTo=25&" +
                "heightRangeFrom=160&" +
                "heightRangeTo=170&" +
                "distanceInKm=35"))
                .andReturn()
                .getResponse();

        verify(filteredMatchesRetrieval).retrieve(eq("tony"), argThat(this::checkIfAllFiltersWereLoaded));
    }

    @Test
    void should_not_allow_compatibility_score_upper_bound_greater_than_0_99() throws Exception {
        MockHttpServletResponse response = mockMvc.perform(get(("/api/v1/matches/" + USER_ID +
                "?compatibilityScoreRangeFrom=0.01" +
                "&compatibilityScoreRangeTo=1.0"))).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).isEqualTo("compatibility score range upper bound should be less than or equal to 0.99");
    }

    @Test
    void should_not_allow_compatibility_score_lower_bound_less_than_0_01() throws Exception {
        MockHttpServletResponse response = mockMvc.perform(get(("/api/v1/matches/" + USER_ID +
                "?compatibilityScoreRangeFrom=0.00" +
                "&compatibilityScoreRangeTo=0.99"))).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).isEqualTo("compatibility score range lower bound should be greater than or equal to 0.01");
    }

    @Test
    void should_not_allow_age_lower_bound_less_than_18() throws Exception {
        MockHttpServletResponse response = mockMvc.perform(get(("/api/v1/matches/" + USER_ID +
                "?ageRangeFrom=17" +
                "&ageRangeTo=95"))).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).isEqualTo("age range lower bound should be greater than or equal to 18");
    }

    @Test
    void should_not_allow_age_upper_bound_greater_than_95() throws Exception {
        MockHttpServletResponse response = mockMvc.perform(get(("/api/v1/matches/" + USER_ID +
                "?ageRangeFrom=18" +
                "&ageRangeTo=96"))).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).isEqualTo("age range upper bound should be less than or equal to 95");
    }

    @Test
    void should_not_allow_height_lower_bound_less_than_1_35() throws Exception {
        MockHttpServletResponse response = mockMvc.perform(get(("/api/v1/matches/" + USER_ID +
                "?heightRangeFrom=134" +
                "&heightRangeTo=210"))).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).isEqualTo("height range lower bound should be greater than or equal to 135");
    }

    @Test
    void should_not_allow_height_upper_bound_greater_than_2_10() throws Exception {
        MockHttpServletResponse response = mockMvc.perform(get(("/api/v1/matches/" + USER_ID +
                "?heightRangeFrom=135" +
                "&heightRangeTo=220"))).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).isEqualTo("height range upper bound should be less than or equal to 210");
    }

    @Test
    void should_not_allow_distance_in_km_less_than_30km() throws Exception {
        MockHttpServletResponse response = mockMvc.perform(get(("/api/v1/matches/" + USER_ID +
                "?distanceInKm=29"))).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).isEqualTo("distance in km should be greater than or equal to 30");
    }

    @Test
    void should_not_allow_distance_in_km_greater_than_300km() throws Exception {
        MockHttpServletResponse response = mockMvc.perform(get(("/api/v1/matches/" + USER_ID +
                "?distanceInKm=301")))
                .andReturn()
                .getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).isEqualTo("distance in km should be less than or equal to 300");
    }

    @Test
    void should_retrieve_users_matched_from_service_as_json_list_with_status_code_200() throws Exception {
        UserMatchedDTO userA = new UserMatchedDTO();
        userA.setDisplayName("userA");
        userA.setMainPhoto("file://photoinsomeplace");
        userA.setAge(20);
        userA.setCompatibilityScore(0.75);
        userA.setHeightInCm(144);
        userA.setJobTitle("Developer");
        userA.setReligion("Christian");
        userA.setCity(new CityDTO("Manchester", 50111.1, -2.04303));
        userA.setFavourite(true);

        UserMatchedDTO userB = new UserMatchedDTO();
        userB.setDisplayName("userB");
        userB.setMainPhoto("file://otherplace");
        userB.setAge(23);
        userB.setCompatibilityScore(0.10);
        userB.setHeightInCm(165);
        userB.setJobTitle("Dancer");
        userB.setReligion("Atheist");
        userB.setCity(new CityDTO("London", 50234.0, -1.11303));
        userB.setFavourite(false);

        when(filteredMatchesRetrieval.retrieve(eq("tony"), argThat(argument -> argument.getFilters().isEmpty())))
                .thenReturn(asList(userA, userB));

        ResultActions resultActions = mockMvc.perform(get("/api/v1/matches/" + USER_ID)
                .header("user", "tony")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        resultActions.andExpect(jsonPath("$[0].displayName").value("userA"))
                .andExpect(jsonPath("$[0].mainPhoto").value("file://photoinsomeplace"))
                .andExpect(jsonPath("$[0].age").value(20))
                .andExpect(jsonPath("$[0].heightInCm").value("144"))
                .andExpect(jsonPath("$[0].city.name").value("Manchester"))
                .andExpect(jsonPath("$[0].city.lat").value(50111.1))
                .andExpect(jsonPath("$[0].city.lon").value(-2.04303))
                .andExpect(jsonPath("$[0].jobTitle").value("Developer"))
                .andExpect(jsonPath("$[0].compatibilityScore").value(0.75))
                .andExpect(jsonPath("$[0].favourite").value(true))
                .andExpect(jsonPath("$[0].religion").value("Christian"));

        resultActions.andExpect(jsonPath("$[1].displayName").value("userB"))
                .andExpect(jsonPath("$[1].mainPhoto").value("file://otherplace"))
                .andExpect(jsonPath("$[1].age").value(23))
                .andExpect(jsonPath("$[1].heightInCm").value(165))
                .andExpect(jsonPath("$[1].city.name").value("London"))
                .andExpect(jsonPath("$[1].city.lat").value(50234.0))
                .andExpect(jsonPath("$[1].city.lon").value(-1.11303))
                .andExpect(jsonPath("$[1].jobTitle").value("Dancer"))
                .andExpect(jsonPath("$[1].compatibilityScore").value(0.10))
                .andExpect(jsonPath("$[1].favourite").value(false))
                .andExpect(jsonPath("$[1].religion").value("Atheist"));
    }

    private boolean checkIfAllFiltersWereLoaded(Filters argument) {
        Stream<UserMatchedFilter> filtersStream = argument.getFilters().stream();
        List<Class> userMatchedFilterClasses = filtersStream.map(f -> f.getClass()).collect(toList());
        return userMatchedFilterClasses.containsAll(getAllAvailableFiltersClasses());
    }

    private List<Class<? extends UserMatchedFilter>> getAllAvailableFiltersClasses() {
        return asList(
                HasPhotoFilter.class,
                ContactsExchangedFilter.class,
                FavoritedFilter.class,
                CompatibilityScoreFilter.class,
                AgeFilter.class,
                HeightFilter.class,
                DistanceRadiusFilter.class
        );
    }
}