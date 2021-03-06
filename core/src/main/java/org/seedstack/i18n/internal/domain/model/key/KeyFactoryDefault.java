/**
 * Copyright (c) 2013-2015, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.i18n.internal.domain.model.key;

import org.javatuples.Triplet;
import org.seedstack.business.domain.BaseFactory;

import java.util.Map;

/**
 * Key factory implementation.
 *
 * @author pierre.thirouin@ext.mpsa.com
 *         Date: 20/11/13
 */
public class KeyFactoryDefault extends BaseFactory<Key> implements KeyFactory {

    @Override
    public Key createKey(String name) {
        Key key = new Key();
        key.setEntityId(name);
        return key;
    }

    @Override
    public Key createKey(String name, String comment, boolean outdated) {
        Key key = new Key();
        key.setEntityId(name);
        key.setComment(comment);
        key.setOutdated(outdated);
        return key;
    }

    @Override
    public Key createKey(String name, String comment, Map<String, Triplet<String, Boolean, Boolean>> translations) {
        Key key = new Key();
        key.setEntityId(name);
        key.setComment(comment);
        for (Map.Entry<String, Triplet<String, Boolean, Boolean>> entry : translations.entrySet()) {
            key.addTranslation(entry.getKey(), entry.getValue().getValue0(), entry.getValue().getValue1(), entry.getValue().getValue2());
        }
        return key;
    }
}
