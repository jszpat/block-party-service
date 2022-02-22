package com.parties.block.party.domain;

import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

@AllArgsConstructor
public class BlockPartyFacade
{
    private final BlockPartyRepository blockPartyRepository;

    public List<BlockParty> findBlockPartiesMatchingCriteria(final BlockPartySearchCriteria searchCriteria)
    {
        final String keyword = searchCriteria.keyword().orElse("");
        final List<BlockParty> blockParties = blockPartyRepository.findAllByKeyword(keyword);

        final List<Predicate<BlockParty>> predicates = new ArrayList<>();
        searchCriteria.fromDate().ifPresent(fromDate -> predicates.add(party -> !party.getEndDate().isBefore(fromDate)));
        searchCriteria.toDate().ifPresent(toDate -> predicates.add(party -> !party.getStartDate().isAfter(toDate)));

        Stream<BlockParty> blockPartiesStream = blockParties.stream();
        for (final Predicate<BlockParty> predicate : predicates)
        {
            blockPartiesStream = blockPartiesStream.filter(predicate);
        }

        return blockPartiesStream.toList();
    }
}
