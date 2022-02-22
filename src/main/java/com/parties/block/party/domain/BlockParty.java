package com.parties.block.party.domain;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
@EqualsAndHashCode
public class BlockParty
{
    Location location;

    String organizer;

    String theme;

    LocalDate startDate;

    LocalDate endDate;

    ContactDetails contactDetails;

    String additionalInfo;

    public static record Location(String name, String streetName, String postCode) {}

    public static record ContactDetails(String emailAddress, String websiteUrl) {}
}
