package com.parties.block.party.api;

import com.parties.block.party.api.dto.BlockPartyDto;
import com.parties.block.party.domain.BlockPartyFacade;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/block-parties")
@AllArgsConstructor
public class BlockPartyController
{
    private final BlockPartyFacade blockPartyFacade;

    private final ApiBlockPartyMapper apiBlockPartyMapper;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<BlockPartyDto> getAllBlockParties(final QueryParamsBlockPartySearchCriteria searchCriteria)
    {
        return blockPartyFacade.findBlockPartiesMatchingCriteria(searchCriteria)
                .stream()
                .map(apiBlockPartyMapper::toDto)
                .toList();
    }
}
