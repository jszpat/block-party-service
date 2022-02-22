package com.parties.block.party.infrastructure;

import com.parties.block.party.domain.BlockParty;
import org.springframework.stereotype.Component;

@Component
class BlockPartyMapper
{
    BlockParty toDomainObject(final BlockPartiesResponseDto.PartyDto partyDto)
    {
        final BlockParty.Location location = new BlockParty.Location(partyDto.getLocation(), partyDto.getStreetName(), partyDto.getPostCode());

        final BlockParty.ContactDetails contactDetails = new BlockParty.ContactDetails(partyDto.getEmailAddress(), partyDto.getWebsiteUrl());

        return BlockParty.builder()
                .location(location)
                .organizer(partyDto.getOrganizer())
                .theme(partyDto.getTheme())
                .startDate(partyDto.getStartDate())
                .endDate(partyDto.getEndDate())
                .contactDetails(contactDetails)
                .additionalInfo(partyDto.getAdditionalInfo())
                .build();
    }
}
