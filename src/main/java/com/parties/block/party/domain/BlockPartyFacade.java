package com.parties.block.party.domain;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

@Slf4j
@AllArgsConstructor
public class BlockPartyFacade
{
    private final BlockPartyRepository blockPartyRepository;

    public List<BlockParty> findBlockPartiesMatchingCriteria(final BlockPartySearchCriteria searchCriteria)
    {
        log.debug("About to search block parties matching criteria: {}", searchCriteria);

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

        final List<BlockParty> filteredParties = blockPartiesStream.toList();
        log.debug("Returning {} block parties matching criteria: {}", filteredParties.size(), searchCriteria);

        return filteredParties;
    }
}
