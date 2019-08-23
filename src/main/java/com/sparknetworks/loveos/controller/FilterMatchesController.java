package com.sparknetworks.loveos.controller;

import com.sparknetworks.loveos.application.filtermatches.FilteredMatchesRetrieval;
import com.sparknetworks.loveos.application.filtermatches.Filters;
import com.sparknetworks.loveos.application.filtermatches.UserMatchedDTO;
import com.sparknetworks.loveos.application.filtermatches.UserMatchedFilterBuilder;
import com.sparknetworks.loveos.domain.ProfileNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/v1/matches/{user}")
public class FilterMatchesController {

    private FilteredMatchesRetrieval filteredMatchesRetrieval;

    @Autowired
    public FilterMatchesController(FilteredMatchesRetrieval filteredMatchesRetrieval) {
        this.filteredMatchesRetrieval = filteredMatchesRetrieval;
    }

    @GetMapping
    public ResponseEntity<List<UserMatchedDTO>> filterMatches(@PathVariable("user") String user, @Validated QueryFilter queryFilter) {
        UserMatchedFilterBuilder userMatchedFiltersBuilder = new UserMatchedFilterBuilder();
        Filters filters = userMatchedFiltersBuilder
                .shouldHavePhoto(queryFilter.getHasPhoto().equals("true"))
                .shouldBeFavorited(queryFilter.getFavorited().equals("true"))
                .shouldHaveExchangedContacts(queryFilter.getInContact().equals("true"))
                .shouldHaveCompatibilityScoreRange(queryFilter.getCompatibilityScoreRange())
                .shouldBeAgedBetween(queryFilter.getAgeRange())
                .shouldHasAHeightBetween(queryFilter.getHeightRange())
                .distanceInKmFromRelatedProfileCityShouldBeLessThan(queryFilter.getDistanceInKm())
                .build();

        List<UserMatchedDTO> userMatchedDTOS = filteredMatchesRetrieval.retrieve(user, filters);

        return ResponseEntity.ok(userMatchedDTOS);
    }

    @ExceptionHandler(ProfileNotFoundException.class)
    public ResponseEntity<String> profileNotFoundException() {
        return ResponseEntity.notFound().build();
    }


    @ExceptionHandler(BindException.class)
    public ResponseEntity<String> exceptionHandlerBindException(BindException bindException) {
        return ResponseEntity.badRequest().body(bindException.getFieldError().getDefaultMessage());
    }
}
