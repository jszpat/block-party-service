package com.parties.block.party.infrastructure;

import com.parties.block.party.domain.BlockParty;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class BlockPartyMapperTest
{
    private final BlockPartyMapper blockPartyMapper = new BlockPartyMapper();

    @Test
    void toDomainObject_domainObjectReturnedBasedOnDto()
    {
        // given
        final String locationName = "Bad Belzig";
        final String theme = "57. Burgfestwoche Bad Belzig und 22. Altstadtsommer";
        final String streetName = "Bad Belzig Strasse";
        final String postCode = "14806";
        final String organizer = "Bad Belziger Festverein, Wiesenburger Str. 6, 14806 Bad Belzig";
        final LocalDate startDate = LocalDate.now();
        final LocalDate endDate = startDate.plusDays(5);
        final String email = "kontakt@festverein-bad-belzig.de";
        final String websiteUrl = "www.festverein-bad-belzig.de";
        final String additionalInfo = "no info";

        final BlockPartiesResponseDto.PartyDto partyDto = new BlockPartiesResponseDto.PartyDto(locationName,
                theme, streetName, postCode, startDate, endDate, organizer, email, websiteUrl, additionalInfo);

        // when
        final BlockParty blockParty = blockPartyMapper.toDomainObject(partyDto);

        // then
        assertThat(blockParty.getTheme()).isEqualTo(theme);
        assertThat(blockParty.getStartDate()).isEqualTo(startDate);
        assertThat(blockParty.getEndDate()).isEqualTo(endDate);
        assertThat(blockParty.getOrganizer()).isEqualTo(organizer);
        assertThat(blockParty.getAdditionalInfo()).isEqualTo(additionalInfo);

        final BlockParty.Location location = blockParty.getLocation();
        assertThat(location.name()).isEqualTo(locationName);
        assertThat(location.streetName()).isEqualTo(streetName);
        assertThat(location.postCode()).isEqualTo(postCode);

        final BlockParty.ContactDetails contactDetails = blockParty.getContactDetails();
        assertThat(contactDetails.emailAddress()).isEqualTo(email);
        assertThat(contactDetails.websiteUrl()).isEqualTo(websiteUrl);
    }
}
