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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.PostConstruct;
import java.util.Objects;

/**
 * @author Taejin Koo
 * @author emeroad
 */
public class GrpcSslReceiverProperties {

    protected final Logger logger = LogManager.getLogger(getClass());

    private final boolean enable;
    private final BindAddress bindAddress;
    private final GrpcSslProperties grpcSslConfiguration;

    GrpcSslReceiverProperties(boolean enable,
                              BindAddress bindAddress,
                              GrpcSslProperties grpcSslConfiguration) {
        this.enable = enable;

        this.bindAddress = Objects.requireNonNull(bindAddress, "bindAddress");
        this.grpcSslConfiguration = Objects.requireNonNull(grpcSslConfiguration, "grpcSslConfiguration");
    }

    @PostConstruct
    public void log() {
        this.logger.info("enable:{}", this.enable);
        this.logger.info("bindAddress:{}", bindAddress);
        this.logger.info("grpcSslConfiguration:{}", grpcSslConfiguration);
    }

    public boolean isEnable() {
        return enable;
    }

    public BindAddress getBindAddress() {
        return bindAddress;
    }

    public GrpcSslProperties getGrpcSslProperties() {
        return grpcSslConfiguration;
    }

    @Override
    public String toString() {
        return "GrpcSslReceiverProperties{" + "enable=" + enable +
                ", bindAddress=" + bindAddress +
                ", grpcSslConfiguration=" + grpcSslConfiguration +
                '}';
    }
}