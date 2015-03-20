/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.i18n.rest.io;


import org.seedstack.business.api.interfaces.assembler.MatchingEntityId;
import org.seedstack.business.api.interfaces.assembler.MatchingFactoryParameter;

import java.util.Map;

/**
 * Data representation for export.
 *
 * @author pierre.thirouin@ext.mpsa.com
 *         Date: 24/03/14
 */
public class DataRepresentation {

    private String key;

    private Map<String, String> value;

    /**
     * Default constructor.
     */
    public DataRepresentation() {
    }

    /**
     * Gets the locale
     *
     * @return locale code
     */
    @MatchingFactoryParameter
    @MatchingEntityId
    public String getKey() {
        return key;
    }

    /**
     * Sets the locale.
     *
     * @param key locale code
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * Gets the association of key and translation
     *
     * @return key/translation map
     */
    public Map<String, String> getValue() {
        return value;
    }

    /**
     * Sets the association of key and translation
     *
     * @param value key/translation map
     */
    public void setValue(Map<String, String> value) {
        this.value = value;
    }
}