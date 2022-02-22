package com.parties.block.party.infrastructure;

import com.parties.block.party.domain.BlockParty;
import com.parties.block.party.domain.BlockPartyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Slf4j
@Component
class BerlinOfficialSiteBlockPartyRepository implements BlockPartyRepository
{
    private static final String KEYWORD_QUERY_PARAM_NAME = "q";

    private final BerlinOfficialSiteProperties berlinOfficialSiteProperties;

    private final BlockPartyMapper blockPartyMapper;

    private final RestTemplate restTemplate;

    public BerlinOfficialSiteBlockPartyRepository(final BerlinOfficialSiteProperties berlinOfficialSiteProperties,
                                                  final BlockPartyMapper blockPartyMapper,
                                                  final RestTemplateBuilder restTemplateBuilder)
    {
        this.berlinOfficialSiteProperties = berlinOfficialSiteProperties;
        this.blockPartyMapper = blockPartyMapper;
        this.restTemplate = restTemplateBuilder.build();
    }

    @Override
    public List<BlockParty> findAllByKeyword(final String keyword)
    {
        final URI uri = uriForSearchingPartiesByKeyword(keyword);

        try
        {
            final BlockPartiesResponseDto partiesResponseDto = restTemplate.getForObject(uri, BlockPartiesResponseDto.class);

            if (partiesResponseDto != null)
            {
                return partiesResponseDto.getParties()
                        .stream()
                        .map(blockPartyMapper::toDomainObject)
                        .toList();
            }

            throw new IllegalStateException("Received null body for block parties request for keyword: " + keyword);
        }
        catch (final RestClientException e)
        {
            throw new IllegalStateException("Error getting block parties for keyword: " + keyword, e);
        }
    }

    private URI uriForSearchingPartiesByKeyword(final String keyword)
    {
        return UriComponentsBuilder.fromHttpUrl(berlinOfficialSiteProperties.url())
                .path(berlinOfficialSiteProperties.blockPartiesEndpointPath())
                .queryParam(KEYWORD_QUERY_PARAM_NAME, keyword)
                .build()
                .toUri();
    }
}
