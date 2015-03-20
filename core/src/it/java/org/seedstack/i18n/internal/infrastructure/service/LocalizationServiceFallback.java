/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.i18n.internal.infrastructure.service;

import org.seedstack.i18n.api.LocaleService;
import org.seedstack.i18n.api.LocalizationService;
import org.seedstack.i18n.internal.domain.model.key.Key;
import org.seedstack.i18n.internal.domain.model.key.KeyFactory;
import org.seedstack.i18n.internal.domain.model.key.KeyRepository;
import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.seedstack.seed.it.SeedITRunner;
import org.seedstack.seed.persistence.jpa.api.JpaUnit;
import org.seedstack.seed.transaction.api.Transactional;

import javax.inject.Inject;

/**
 * @author pierre.thirouin@ext.mpsa.com
 *         Date: 25/06/2014
 */
@JpaUnit("seed-i18n-domain")
@Transactional
@RunWith(SeedITRunner.class)
public class LocalizationServiceFallback {

    @Inject
    private LocalizationService localizationService;

    @Inject
    private LocaleService localeService;

    @Inject
    private KeyRepository keyRepository;

    @Inject
    private KeyFactory keyFactory;

    @Test
    public void localize_should_fallback_to_parent_locale() {
        localeService.addLocale("fr");
        localeService.addLocale("fr-BE");

        Key key = keyFactory.createKey("key");
        key.addTranslation("fr", "youpi", false, false);
        keyRepository.persist(key);

        String actualTranslation = localizationService.localize("fr-BE", "key");

        Assertions.assertThat(actualTranslation).isEqualTo("youpi");
    }

    @Test
    public void localize_fallback_without_translation() {
        localeService.addLocale("fr");
        localeService.addLocale("fr-BE");

        String actualTranslation = localizationService.localize("fr-BE", "key");

        Assertions.assertThat(actualTranslation).isEqualTo("[key]");
    }

    @After
    public void clean() {
        for (Key key : keyRepository.loadAll()) {
            keyRepository.delete(key);
        }
        for (String locale : localeService.getAvailableLocales()) {
            localeService.deleteLocale(locale);
        }

    }
}