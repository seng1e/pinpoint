/*
 * Copyright 2021 NAVER Corp.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.navercorp.pinpoint.collector.grpc.config;

import com.navercorp.pinpoint.collector.receiver.BindAddress;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@EnableConfigurationProperties
@TestPropertySource(locations = "classpath:test-pinpoint-collector.properties")
@ContextConfiguration(classes = {GrpcAgentDataSslReceiverConfiguration.class, GrpcSpanSslReceiverConfiguration.class})
@ExtendWith(SpringExtension.class)
public class GrpcSpanSslReceiverConfigurationTest {

    @Autowired
    @Qualifier(GrpcSpanSslReceiverConfiguration.SPAN_SSL_PROPERTIES)
    private GrpcSslReceiverProperties properties;

    @Test
    public void properties() {
        assertEquals(Boolean.TRUE, properties.isEnable());
        BindAddress bindAddress = properties.getBindAddress();
        assertEquals("3.3.3.3", bindAddress.getIp());
        assertEquals(39443, bindAddress.getPort());
    }

    @Test
    public void grpcSslConfiguration() throws IOException {
        GrpcSslProperties sslConfiguration = properties.getGrpcSslProperties();

        assertEquals(Boolean.TRUE, sslConfiguration.isEnable());
        assertEquals("jdk", sslConfiguration.getProviderType());

        Resource keyFileUrl = sslConfiguration.getKeyResource();
        File keyFile = keyFileUrl.getFile();
        assertEquals("server0.pem", keyFile.getName());
        assertEquals(Boolean.TRUE, keyFile.exists());

        Resource keyCertFileUrl = sslConfiguration.getKeyCertChainResource();
        File keyCertFile = keyCertFileUrl.getFile();
        assertEquals("server0.key", keyCertFile.getName());
        assertEquals(Boolean.TRUE, keyCertFile.exists());
    }
}