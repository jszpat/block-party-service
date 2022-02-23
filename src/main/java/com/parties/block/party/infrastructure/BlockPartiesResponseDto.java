package com.parties.block.party.infrastructure;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@EqualsAndHashCode
class BlockPartiesResponseDto
{
    private final List<PartyDto> parties;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public BlockPartiesResponseDto(@JsonProperty("index") final List<PartyDto> parties)
    {
        this.parties = parties;
    }

    @Getter
    @EqualsAndHashCode
    static class PartyDto
    {
        private final String location;

        private final String theme;

        private final String streetName;

        private final String postCode;

        private final LocalDate startDate;

        private final LocalDate endDate;

        private final String organizer;

        private final String emailAddress;

        private final String websiteUrl;

        private final String additionalInfo;

        PartyDto(@JsonProperty("ort") final String location,
                 @JsonProperty("bezeichnung") final String theme,
                 @JsonProperty("strasse") final String streetName,
                 @JsonProperty("plz") final String postCode,
                 @JsonProperty("von") final LocalDate startDate,
                 @JsonProperty("bis") final LocalDate endDate,
                 @JsonProperty("veranstalter") final String organizer,
                 @JsonProperty("mail") final String emailAddress,
                 @JsonProperty("www") final String websiteUrl,
                 @JsonProperty("bemerkungen") final String additionalInfo)
        {
            this.location = location;
            this.theme = theme;
            this.streetName = streetName;
            this.postCode = postCode;
            this.startDate = startDate;
            this.endDate = endDate;
            this.organizer = organizer;
            this.emailAddress = emailAddress;
            this.websiteUrl = websiteUrl;
            this.additionalInfo = additionalInfo;
        }
    }
}
