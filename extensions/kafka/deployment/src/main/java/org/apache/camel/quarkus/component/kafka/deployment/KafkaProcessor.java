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
package org.apache.camel.quarkus.component.kafka.deployment;

import java.util.List;

import io.quarkus.deployment.annotations.BuildStep;
import io.quarkus.deployment.annotations.ExecutionTime;
import io.quarkus.deployment.annotations.Record;
import io.quarkus.deployment.builditem.FeatureBuildItem;
import io.quarkus.deployment.builditem.nativeimage.ServiceProviderBuildItem;
import org.apache.camel.component.kafka.KafkaComponent;
import org.apache.camel.quarkus.component.kafka.CamelKafkaRecorder;
import org.apache.camel.quarkus.core.deployment.spi.CamelRuntimeBeanBuildItem;

class KafkaProcessor {
    private static final String FEATURE = "camel-kafka";

    @BuildStep
    FeatureBuildItem feature() {
        return new FeatureBuildItem(FEATURE);
    }

    @BuildStep
    @Record(ExecutionTime.RUNTIME_INIT)
    CamelRuntimeBeanBuildItem createCamelKafkaComponent(
            CamelKafkaRecorder recorder,
            // We want Quarkus to configure the ServiceBindingConverter bits before this step
            List<ServiceProviderBuildItem> serviceProviders) {
        return new CamelRuntimeBeanBuildItem(
                "kafka",
                KafkaComponent.class.getName(),
                recorder.createKafkaComponent());
    }
}
