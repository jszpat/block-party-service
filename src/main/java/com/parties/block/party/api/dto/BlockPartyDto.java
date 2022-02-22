package com.parties.block.party.api.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
@EqualsAndHashCode
public class BlockPartyDto
{
    LocationDto location;

    String organizer;

    String theme;

    LocalDate startDate;

    LocalDate endDate;

    ContactDetailsDto contactDetails;

    String additionalInfo;

    public static record ContactDetailsDto(String emailAddress, String websiteUrl) {}

    public static record LocationDto(String name, String streetName, String postCode) {}
}
