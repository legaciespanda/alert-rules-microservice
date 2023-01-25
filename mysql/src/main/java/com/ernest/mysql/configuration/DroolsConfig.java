package com.ernest.mysql.configuration;

import org.kie.api.KieServices;
import org.kie.api.builder.*;
import org.kie.api.runtime.KieContainer;
import org.kie.internal.io.ResourceFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Drools Config.
 * @author Ernest Obot
 */
@Configuration
public class DroolsConfig {

    private static final String RULES_WEATHER_RULES_DRL = "rules/weather.drl";
    private final KieServices kieServices = KieServices.Factory.get();

    @Bean
    public KieContainer getKieContainer() throws Exception {

        KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
        kieFileSystem.write(ResourceFactory.newClassPathResource(RULES_WEATHER_RULES_DRL));
        KieBuilder kb = kieServices.newKieBuilder(kieFileSystem);
        kb.buildAll();

        //handle exception
        Results results = kb.getResults();
        if (results.hasMessages(Message.Level.ERROR)) {
            throw new Exception(results.getMessages().toString());
        }

        KieModule kieModule = kb.getKieModule();
        return kieServices.newKieContainer(kieModule.getReleaseId());
    }

}
