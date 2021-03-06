/*
 *
 *  Copyright 2016 Netflix, Inc.
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 *
 */
package com.netflix.genie.web.configs;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.session.SessionProperties;
import org.springframework.boot.autoconfigure.session.StoreType;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * Class to replace HTTP Session from Tomcat with one persisted to Redis for sharing session across a cluster.
 *
 * @author tgianos
 * @since 3.0.0
 */
@Configuration
@Slf4j
public class HttpSessionConfig {

    @Autowired
    private SessionProperties sessionProperties;

    /**
     * Log out that Redis based HTTP Session storage is enabled.
     */
    @PostConstruct
    public void postConstruct() {
        log.info(
            "Spring Session is configured to use {} as the store type",
            this.sessionProperties == null
                ? StoreType.NONE
                : this.sessionProperties.getStoreType()
        );
    }
}
