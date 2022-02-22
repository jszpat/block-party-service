package com.parties.block.party;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan("com.parties.block.party")
public class BlockPartyServiceApplication
{
	public static void main(final String[] args)
	{
		SpringApplication.run(BlockPartyServiceApplication.class, args);
	}
}
