package com.parties.block.party.domain;

import java.util.List;

public interface BlockPartyRepository
{
    List<BlockParty> findAllByKeyword(String keyword);
}
