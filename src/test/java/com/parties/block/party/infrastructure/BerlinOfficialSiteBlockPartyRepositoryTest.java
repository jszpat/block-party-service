package com.parties.block.party.infrastructure;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.parties.block.party.domain.BlockParty;
import com.parties.block.party.domain.BlockPartyRepository;
import org.hamcrest.core.StringContains;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withServerError;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RestClientTest(properties = {"berlin.official.site.url=http://berlin-official-site",
        "berlin.official.site.blockPartiesEndpointPath=/block/parties"},
        components = {
                BerlinOfficialSiteBlockPartyRepository.class,
                BerlinOfficialSiteProperties.class,
                BlockPartyMapper.class})
class BerlinOfficialSiteBlockPartyRepositoryTest
{
    private static final String KEYWORD_QUERY_PARAM_NAME = "q";

    @Autowired
    private MockRestServiceServer server;

    @Autowired
    private BerlinOfficialSiteProperties berlinOfficialSiteProperties;

    @MockBean
    private BlockPartyMapper mockBlockPartyMapper;

    @Autowired
    private BlockPartyRepository blockPartyRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void findAllByKeyword_validRequest_responseSuccessfullyDeserializedAndDomainObjectReturned() throws Exception
    {
        // given
        final String keyword = "Bad";
        final URI expectedUriToHit = UriComponentsBuilder.fromHttpUrl(berlinOfficialSiteProperties.url())
                .path(berlinOfficialSiteProperties.blockPartiesEndpointPath())
                .queryParam(KEYWORD_QUERY_PARAM_NAME, keyword)
                .build()
                .toUri();

        final ClassPathResource responseResource = new ClassPathResource("berlin-official-site-response.json");
        final Path responsePath = responseResource.getFile().toPath();
        final BlockPartiesResponseDto responseDto = objectMapper.readValue(Files.readString(responsePath), BlockPartiesResponseDto.class);
        final BlockPartiesResponseDto.PartyDto partyDto = responseDto.getParties().get(0);

        final BlockParty mockBlockParty = mock(BlockParty.class);
        doReturn(mockBlockParty).when(mockBlockPartyMapper).toDomainObject(partyDto);

        server.expect(requestTo(expectedUriToHit)).andRespond(withSuccess(responseResource, MediaType.APPLICATION_JSON));

        // when
        final List<BlockParty> blockParties = blockPartyRepository.findAllByKeyword(keyword);

        // then
        assertThat(blockParties).hasSize(1);
        assertThat(blockParties.get(0)).isSameAs(mockBlockParty);
    }

    @Test
    void findAllByKeyword_serverError_IllegalStateExceptionThrown()
    {
        // given
        final String keyword = "Bad";
        server.expect(requestTo(new StringContains(""))).andRespond(withServerError());

        // when
        final Executable findAllByKeyword = () -> blockPartyRepository.findAllByKeyword(keyword);

        // then
        assertThrows(IllegalStateException.class, findAllByKeyword);
    }

    @Test
    void findAllByKeyword_nullBodyReceivedFromServer_IllegalStateExceptionThrown()
    {
        // given
        final String keyword = "Bad";
        final URI expectedUriToHit = UriComponentsBuilder.fromHttpUrl(berlinOfficialSiteProperties.url())
                .path(berlinOfficialSiteProperties.blockPartiesEndpointPath())
                .queryParam(KEYWORD_QUERY_PARAM_NAME, keyword)
                .build()
                .toUri();

        server.expect(requestTo(expectedUriToHit)).andRespond(withSuccess());

        // when
        final Executable findAllByKeyword = () ->  blockPartyRepository.findAllByKeyword(keyword);

        // then
        assertThrows(IllegalStateException.class, findAllByKeyword);
    }
}
