/*
 *
 *  Copyright 2018 Netflix, Inc.
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
package com.netflix.genie.common.dto.v4;

import com.google.common.collect.ImmutableSet;
import com.netflix.genie.common.exceptions.GeniePreconditionException;
import lombok.Value;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.Set;

/**
 * Representation of various criterion options available. Used for determining which cluster and command are used to
 * execute a job.
 *
 * @author tgianos
 * @since 4.0.0
 */
@Value
public class Criterion {

    private final String id;
    private final String name;
    private final String status;
    private final ImmutableSet<String> tags;

    Criterion(final Builder builder) throws GeniePreconditionException {
        this.id = builder.bId;
        this.name = builder.bName;
        this.status = builder.bStatus;
        this.tags = builder.bTags == null ? ImmutableSet.of() : ImmutableSet.copyOf(builder.bTags);

        if (
            StringUtils.isEmpty(this.id)
                && StringUtils.isEmpty(this.name)
                && StringUtils.isEmpty(this.status)
                && this.tags.isEmpty()
            ) {
            throw new GeniePreconditionException("Invalid criterion. One of the fields must have a valid value");
        }
    }

    /**
     * Get the id of the resource desired if it exists.
     *
     * @return {@link Optional} wrapping the id
     */
    public Optional<String> getId() {
        return Optional.ofNullable(this.id);
    }

    /**
     * Get the name of the resource desired if it exists.
     *
     * @return {@link Optional} wrapping the name
     */
    public Optional<String> getName() {
        return Optional.ofNullable(this.name);
    }

    /**
     * Get the desired status of the resource if it has been set by the creator.
     *
     * @return {@link Optional} wrapping the status
     */
    public Optional<String> getStatus() {
        return Optional.ofNullable(this.status);
    }

    /**
     * Get the set of tags the creator desires the resource to have if any were set.
     *
     * @return An immutable set of strings. Any attempt at modification will throw an exception.
     */
    public Set<String> getTags() {
        return this.tags;
    }

    /**
     * Builder for creating a Criterion instance.
     *
     * @author tgianos
     * @since 4.0.0
     */
    public static class Builder {
        private String bId;
        private String bName;
        private String bStatus;
        private ImmutableSet<String> bTags;

        /**
         * Set the id of the resource (cluster, command, etc) to use.
         *
         * @param id The id
         * @return The builder
         */
        public Builder withId(@Nullable final String id) {
            this.bId = id;
            return this;
        }

        /**
         * Set the name of the resource (cluster, command, etc) to search for.
         *
         * @param name The name of the resource
         * @return The builder
         */
        public Builder withName(@Nullable final String name) {
            this.bName = name;
            return this;
        }

        /**
         * Set the status to search for. Overrides default status of resource in search algorithm.
         *
         * @param status The status to override the default with
         * @return The builder
         */
        public Builder withStatus(@Nullable final String status) {
            this.bStatus = status;
            return this;
        }

        /**
         * Set the tags to use in the search.
         *
         * @param tags The tags
         * @return The builder
         */
        public Builder withTags(@Nullable final Set<String> tags) {
            this.bTags = tags == null ? ImmutableSet.of() : ImmutableSet.copyOf(tags);
            return this;
        }

        /**
         * Build an immutable Criterion. A precondition exception will be thrown if all of the fields are empty.
         *
         * @return A criterion which is completely immutable.
         * @throws GeniePreconditionException A valid Criterion must have at least one field populated
         */
        public Criterion build() throws GeniePreconditionException {
            return new Criterion(this);
        }
    }
}
