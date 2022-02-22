package com.parties.block.party.infrastructure;

import com.parties.block.party.domain.BlockPartyFacade;
import com.parties.block.party.domain.BlockPartyRepository;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class BeanConfig
{
    @Bean
    RestTemplateBuilder restTemplate()
    {
        return new RestTemplateBuilder();
    }

    @Bean
    BlockPartyFacade blockPartyFacade(final BlockPartyRepository blockPartyRepository)
    {
        return new BlockPartyFacade(blockPartyRepository);
    }
}
