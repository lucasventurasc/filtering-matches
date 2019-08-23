package com.sparknetworks.loveos.infrastructure;

import com.sparknetworks.loveos.application.filtermatches.FilteredMatchesRetrieval;
import com.sparknetworks.loveos.domain.MatchesRepository;
import com.sparknetworks.loveos.domain.ProfileRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;
import java.io.IOException;

@Configuration
@ComponentScan("com.sparknetworks.loveos.controller")
public class SpringControllerConfiguration implements WebMvcConfigurer {

	@Value("${application.in-json-file-matches-repository.path}")
	private Resource inJsonFileMatchesRepositoryPath;

	@Value("${application.in-json-file-profiles-repository.path}")
	private Resource inJsonFileProfilesRepositoryPath;

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD", "TRACE", "CONNECT");
	}

	@Bean
	public FilteredMatchesRetrieval filteredMatchesRetrieval() {
		return new FilteredMatchesRetrieval(profileRepository());
	}

	@Bean
	public MatchesRepository matchesRepository() {
		try {
			return new InJsonFileMatchesRepository(new File(inJsonFileMatchesRepositoryPath.getURI()));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Bean
	public ProfileRepository profileRepository() {
		return inMemoryProfileRepository();
	}

	@Bean
	public InJsonFileProfileRepository inMemoryProfileRepository() {
		try {
			return new InJsonFileProfileRepository(new File(inJsonFileProfilesRepositoryPath.getURI()), matchesRepository());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
