package com.parties.block.party.domain;

import java.time.LocalDate;
import java.util.Optional;

public interface BlockPartySearchCriteria
{
    Optional<String> keyword();

    Optional<LocalDate> fromDate();

    Optional<LocalDate> toDate();
}
