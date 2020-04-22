/*
 * Copyright 2020, Yahoo Inc.
 * Licensed under the Apache License, Version 2.0
 * See LICENSE file in project root for terms.
 */
package com.yahoo.elide.datastores.aggregation.query;

import lombok.Value;

import java.util.function.Function;

/**
 * A cache for Query results. It is a 'loading' cache, meaning on cache miss it should load the value itself.
 */
@FunctionalInterface
public interface Cache {

    /**
     * Represents a query against a particular version of the data sources. Re-executing a query should produce the same
     * results if the {@link #dataVersion} is the same.
     */
    @Value
    class Key {
        Query query;

        /**
         * Identify a particular version of the data source.
         */
        int dataVersion;
    }

    /**
     * Load Query results from cache. If results not found for provided key, it will invoke the provided loader to
     * produce the result, which may then be used to populate the cache. Exceptions from the loader should be passed
     * through, with the failure not cached.
     *
     * @param key    a key to look up in the cache.
     * @param loader function invoke to fill cache on misses.
     * @return query results from cache or loader.
     */
    Iterable<Object> get(Key key, Function<Query, Iterable<Object>> loader);
}
