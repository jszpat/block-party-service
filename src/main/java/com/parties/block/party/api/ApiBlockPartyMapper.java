package com.parties.block.party.api;

import com.parties.block.party.api.dto.BlockPartyDto;
import com.parties.block.party.domain.BlockParty;
import org.springframework.stereotype.Component;

@Component
class ApiBlockPartyMapper
{
    BlockPartyDto toDto(final BlockParty blockParty)
    {
        final BlockParty.Location location = blockParty.getLocation();
        final BlockPartyDto.LocationDto locationDto = new BlockPartyDto.LocationDto(location.name(), location.streetName(), location.postCode());

        final BlockParty.ContactDetails contactDetails = blockParty.getContactDetails();
        final BlockPartyDto.ContactDetailsDto contactDetailsDto = new BlockPartyDto.ContactDetailsDto(contactDetails.emailAddress(), contactDetails.websiteUrl());

        return BlockPartyDto.builder()
                .location(locationDto)
                .startDate(blockParty.getStartDate())
                .endDate(blockParty.getEndDate())
                .organizer(blockParty.getOrganizer())
                .theme(blockParty.getTheme())
                .contactDetails(contactDetailsDto)
                .additionalInfo(blockParty.getAdditionalInfo())
                .build();
    }
}
