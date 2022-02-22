package com.parties.block.party.api;

import com.parties.block.party.domain.BlockPartySearchCriteria;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.util.Optional;

@EqualsAndHashCode
class QueryParamsBlockPartySearchCriteria implements BlockPartySearchCriteria
{
    private final String keyword;

    private final LocalDate fromDate;

    private final LocalDate toDate;

    QueryParamsBlockPartySearchCriteria(final String keyword, final String fromDate, final String toDate)
    {
        this.keyword = keyword;
        this.fromDate = fromDate != null ? LocalDate.parse(fromDate) : null;
        this.toDate = toDate != null ? LocalDate.parse(toDate) : null;
    }

    @Override
    public Optional<String> keyword()
    {
        return Optional.ofNullable(keyword);
    }

    @Override
    public Optional<LocalDate> fromDate()
    {
        return Optional.ofNullable(fromDate);
    }

    @Override
    public Optional<LocalDate> toDate()
    {
        return Optional.ofNullable(toDate);
    }
}
