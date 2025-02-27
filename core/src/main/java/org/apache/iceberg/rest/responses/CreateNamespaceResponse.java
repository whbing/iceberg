/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.apache.iceberg.rest.responses;

import java.util.Map;
import java.util.Objects;
import org.apache.iceberg.catalog.Namespace;
import org.apache.iceberg.relocated.com.google.common.base.MoreObjects;
import org.apache.iceberg.relocated.com.google.common.base.Preconditions;
import org.apache.iceberg.relocated.com.google.common.collect.ImmutableMap;
import org.apache.iceberg.relocated.com.google.common.collect.Maps;

/**
 * Represents a REST response for a request to create a namespace / database.
 */
public class CreateNamespaceResponse {

  private Namespace namespace;
  private Map<String, String> properties;

  public CreateNamespaceResponse() {
    // Required for Jackson deserialization.
  }

  private CreateNamespaceResponse(Namespace namespace, Map<String, String> properties) {
    this.namespace = namespace;
    this.properties = properties;
    validate();
  }

  CreateNamespaceResponse validate() {
    Preconditions.checkArgument(namespace != null, "Invalid namespace: null");
    return this;
  }

  public Namespace namespace() {
    return namespace;
  }

  public Map<String, String> properties() {
    return properties != null ? properties : ImmutableMap.of();
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("namespace", namespace)
        .add("properties", properties)
        .toString();
  }

  public static Builder builder() {
    return new Builder();
  }

  static class Builder {
    private Namespace namespace;
    private final ImmutableMap.Builder<String, String> properties = ImmutableMap.builder();

    private Builder() {
    }

    public Builder withNamespace(Namespace ns) {
      Preconditions.checkNotNull(ns, "Invalid namespace: null");
      this.namespace = ns;
      return this;
    }

    public Builder setProperties(Map<String, String> props) {
      Preconditions.checkNotNull(props,
          "Invalid collection of properties: null");
      Preconditions.checkArgument(!props.containsKey(null),
          "Invalid property to set: null");
      Preconditions.checkArgument(!props.containsValue(null),
          "Invalid value to set for properties %s: null", Maps.filterValues(props, Objects::isNull).keySet());
      properties.putAll(props);
      return this;
    }

    public CreateNamespaceResponse build() {
      return new CreateNamespaceResponse(namespace, properties.build());
    }
  }
}

