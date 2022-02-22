package com.parties.block.party.infrastructure;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@Validated
@ConfigurationProperties(prefix = "berlin.official.site")
record BerlinOfficialSiteProperties(@NotBlank String url, @NotBlank String blockPartiesEndpointPath) {}
