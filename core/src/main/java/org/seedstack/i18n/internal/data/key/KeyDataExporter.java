/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.i18n.internal.data.key;

import org.seedstack.i18n.internal.domain.model.key.KeyRepository;
import org.seedstack.business.api.interfaces.assembler.Assemblers;
import org.seedstack.seed.core.spi.data.DataExporter;
import org.seedstack.seed.core.spi.data.DataSet;
import org.seedstack.seed.persistence.jpa.api.JpaUnit;
import org.seedstack.seed.transaction.api.Transactional;

import javax.inject.Inject;
import java.util.Iterator;

/**
 * @author pierre.thirouin@ext.mpsa.com
 *         Date: 14/03/14
 */
@JpaUnit("seed-i18n-domain")
@Transactional
@DataSet(group="seed-i18n", name="key")
public class KeyDataExporter implements DataExporter<KeyDTO> {

    @Inject
    private KeyRepository keyRepository;

    @Inject
    private Assemblers assemblers;

    @Override
    public Iterator<KeyDTO> exportData() {
        return assemblers.assembleDtoFromAggregate(KeyDTO.class, keyRepository.loadAll()).iterator();
    }
}