package com.parties.block.party.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class BlockPartyFacadeTest
{
    private final BlockPartyRepository mockRepository = mock(BlockPartyRepository.class);

    private final BlockPartyFacade blockPartyFacade = new BlockPartyFacade(mockRepository);

    @Test
    void findBlockPartiesMatchingCriteria_fromDateFilterSpecified_partiesTakingPlaceAtSpecifiedDayOrLaterReturned()
    {
        // given
        final LocalDate fromDate = LocalDate.now();

        final BlockPartySearchCriteria mockSearchCriteria = mock(BlockPartySearchCriteria.class);
        doReturn(Optional.empty()).when(mockSearchCriteria).keyword();
        doReturn(Optional.empty()).when(mockSearchCriteria).toDate();
        doReturn(Optional.of(fromDate)).when(mockSearchCriteria).fromDate();

        final BlockParty ineligibleMockParty = mock(BlockParty.class);
        doReturn(fromDate.minusDays(3)).when(ineligibleMockParty).getEndDate();

        final BlockParty eligibleMockParty = mock(BlockParty.class);
        doReturn(fromDate).when(eligibleMockParty).getEndDate();

        final BlockParty anotherMockEligibleParty = mock(BlockParty.class);
        doReturn(fromDate.plusDays(35)).when(anotherMockEligibleParty).getEndDate();

        final List<BlockParty> allParties = List.of(ineligibleMockParty, eligibleMockParty, anotherMockEligibleParty);
        doReturn(allParties).when(mockRepository).findAllByKeyword("");

        // when
        final List<BlockParty> blockParties = blockPartyFacade.findBlockPartiesMatchingCriteria(mockSearchCriteria);

        // then
        assertThat(blockParties).containsExactly(eligibleMockParty, anotherMockEligibleParty);
    }

    @Test
    void findBlockPartiesMatchingCriteria_toDateFilterSpecified_partiesTakingPlaceAtSpecifiedDayOrEarlierReturned()
    {
        // given
        final LocalDate toDate = LocalDate.now();

        final BlockPartySearchCriteria mockSearchCriteria = mock(BlockPartySearchCriteria.class);
        doReturn(Optional.empty()).when(mockSearchCriteria).keyword();
        doReturn(Optional.empty()).when(mockSearchCriteria).fromDate();
        doReturn(Optional.of(toDate)).when(mockSearchCriteria).toDate();

        final BlockParty eligibleMockParty = mock(BlockParty.class);
        doReturn(toDate.minusDays(3)).when(eligibleMockParty).getStartDate();

        final BlockParty anotherEligibleMockParty = mock(BlockParty.class);
        doReturn(toDate).when(anotherEligibleMockParty).getStartDate();

        final BlockParty ineligibleMockParty = mock(BlockParty.class);
        doReturn(toDate.plusDays(35)).when(ineligibleMockParty).getStartDate();

        final List<BlockParty> allParties = List.of(eligibleMockParty, anotherEligibleMockParty, ineligibleMockParty);
        doReturn(allParties).when(mockRepository).findAllByKeyword("");

        // when
        final List<BlockParty> blockParties = blockPartyFacade.findBlockPartiesMatchingCriteria(mockSearchCriteria);

        // then
        assertThat(blockParties).containsExactly(eligibleMockParty, anotherEligibleMockParty);
    }

    @Test
    void findBlockPartiesMatchingCriteria_datePeriodSpecified_partiesTakingPlaceAtProvidedPeriodReturned()
    {
        // given
        final LocalDate fromDate = LocalDate.now();
        final LocalDate toDate = fromDate.plusDays(7);

        final BlockPartySearchCriteria mockSearchCriteria = mock(BlockPartySearchCriteria.class);
        doReturn(Optional.empty()).when(mockSearchCriteria).keyword();
        doReturn(Optional.of(fromDate)).when(mockSearchCriteria).fromDate();
        doReturn(Optional.of(toDate)).when(mockSearchCriteria).toDate();

        final BlockParty eligibleMockParty = mock(BlockParty.class);
        doReturn(fromDate.plusDays(4)).when(eligibleMockParty).getStartDate();
        doReturn(toDate.plusDays(3)).when(eligibleMockParty).getEndDate();

        final BlockParty anotherEligibleMockParty = mock(BlockParty.class);
        doReturn(fromDate.minusDays(3)).when(anotherEligibleMockParty).getStartDate();
        doReturn(toDate.minusDays(3)).when(anotherEligibleMockParty).getEndDate();

        final BlockParty ineligibleMockParty = mock(BlockParty.class);
        doReturn(fromDate.minusDays(7)).when(ineligibleMockParty).getStartDate();
        doReturn(fromDate.minusDays(1)).when(ineligibleMockParty).getEndDate();

        final BlockParty anotherIneligibleMockParty = mock(BlockParty.class);
        doReturn(toDate.plusDays(5)).when(anotherIneligibleMockParty).getStartDate();
        doReturn(toDate.plusDays(12)).when(anotherIneligibleMockParty).getEndDate();

        final List<BlockParty> allParties = List.of(eligibleMockParty,
                anotherEligibleMockParty,
                ineligibleMockParty,
                anotherIneligibleMockParty);

        doReturn(allParties).when(mockRepository).findAllByKeyword("");

        // when
        final List<BlockParty> blockParties = blockPartyFacade.findBlockPartiesMatchingCriteria(mockSearchCriteria);

        // then
        assertThat(blockParties).containsExactly(eligibleMockParty, anotherEligibleMockParty);
    }

    @Test
    void findBlockPartiesMatchingCriteria_keywordFilterSpecified_allPartiesMatchingKeywordReturned()
    {
        // given
        final String keyword = "cake";

        final BlockPartySearchCriteria mockSearchCriteria = mock(BlockPartySearchCriteria.class);
        doReturn(Optional.of(keyword)).when(mockSearchCriteria).keyword();
        doReturn(Optional.empty()).when(mockSearchCriteria).fromDate();
        doReturn(Optional.empty()).when(mockSearchCriteria).toDate();

        final List<BlockParty> allParties = List.of(mock(BlockParty.class), mock(BlockParty.class));
        doReturn(allParties).when(mockRepository).findAllByKeyword(keyword);

        // when
        final List<BlockParty> blockParties = blockPartyFacade.findBlockPartiesMatchingCriteria(mockSearchCriteria);

        // then
        assertThat(blockParties).containsExactlyElementsOf(allParties);
    }

    @Test
    void findBlockPartiesMatchingCriteria_noKeywordSpecified_repositoryQueriedWithAnEmptyStringKeyword()
    {
        // given
        final BlockPartySearchCriteria mockSearchCriteria = mock(BlockPartySearchCriteria.class);
        doReturn(Optional.empty()).when(mockSearchCriteria).keyword();
        doReturn(Optional.empty()).when(mockSearchCriteria).fromDate();
        doReturn(Optional.empty()).when(mockSearchCriteria).toDate();

        // when
        blockPartyFacade.findBlockPartiesMatchingCriteria(mockSearchCriteria);

        // then
        verify(mockRepository).findAllByKeyword("");
    }
}
