/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.camel.quarkus.core.deployment.devconsole;

import java.util.ArrayList;
import java.util.List;

import org.apache.camel.Consumer;
import org.apache.camel.Endpoint;
import org.apache.camel.Processor;
import org.apache.camel.Producer;
import org.apache.camel.Route;
import org.apache.camel.impl.engine.DefaultRoute;
import org.apache.camel.support.DefaultConsumer;
import org.apache.camel.support.DefaultEndpoint;

import io.quarkus.deployment.IsDevelopment;
import io.quarkus.deployment.annotations.BuildStep;
import io.quarkus.devconsole.spi.DevConsoleTemplateInfoBuildItem;

public class DevConsoleProcessor {

    @BuildStep(onlyIf = IsDevelopment.class)
    public DevConsoleTemplateInfoBuildItem collectBeanInfo() {
        List<Route> routes = new ArrayList<>();
        Endpoint endpoint = new DefaultEndpoint() {

            @Override
            public Producer createProducer() throws Exception {
                return null;
            }

            @Override
            public Consumer createConsumer(Processor processor) throws Exception {
                return new DefaultConsumer(this, processor);
            }

            @Override
            public String getEndpointBaseUri() {
                return "timer://meuTimer";
            }


            
        };
        Route r = new DefaultRoute(null, null, "R 1", null, endpoint, null);
        routes.add(r);
        //Route r;r.getConsumer().getEndpoint().getEndpointBaseUri()
        return new DevConsoleTemplateInfoBuildItem("routeInfos", routes);
    }
}