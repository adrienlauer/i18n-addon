/**
 * Copyright (c) 2013-2015, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.i18n;

import org.seedstack.i18n.LocaleService;
import org.seedstack.i18n.internal.TranslationService;
import org.seedstack.i18n.internal.domain.model.key.Key;
import org.seedstack.i18n.internal.domain.model.key.KeyFactory;
import org.seedstack.i18n.internal.domain.model.key.KeyRepository;
import org.assertj.core.api.Assertions;
import org.javatuples.Triplet;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.seedstack.seed.it.SeedITRunner;
import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.transaction.Transactional;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

/**
 * @author pierre.thirouin@ext.mpsa.com
 * Date: 05/12/13
 */
@JpaUnit("seed-i18n-domain")
@Transactional
@RunWith(SeedITRunner.class)
public class TranslationServiceIT {

    private static final String TRANSLATION = "my translation";

    @Inject
    private KeyRepository repository;

    @Inject
    private KeyFactory factory;

    @Inject
    private TranslationService service;

    @Inject
    private LocaleService localeService;

    @Test
    public void translation_scenario() {
        String locale = "en_US";
        localeService.addLocale(locale);
        createKeyWithTranslation("name1", locale, "my translation");
        createKeyWithTranslation("name2", locale, "my second translation");

        // Get all translations for a locale
        Map<String, String> requestedTranslations = service.getTranslationsForLocale(locale);
        Assertions.assertThat(requestedTranslations.size()).isEqualTo(2);
    }

    private void createKeyWithTranslation(String key, String locale, String translation) {
        // create the map of translations
        Map<String, Triplet<String, Boolean, Boolean>> translations = new HashMap<String, Triplet<String, Boolean, Boolean>>();
        translations.put(locale, Triplet.with(translation, false, false));
        // create the key
        Key key1 = factory.createKey(key, "comment", translations);
        // persist it
        repository.persist(key1);
    }

    @After
    public void clean() {
        for (String locale : localeService.getAvailableLocales()) {
            localeService.deleteLocale(locale);
        }

    }
}
