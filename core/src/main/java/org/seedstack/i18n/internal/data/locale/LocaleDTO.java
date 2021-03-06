/**
 * Copyright (c) 2013-2015, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.i18n.internal.data.locale;


import org.seedstack.business.assembler.MatchingEntityId;
import org.seedstack.business.assembler.MatchingFactoryParameter;

/**
 * @author pierre.thirouin@ext.mpsa.com
 *         Date: 20/03/14
 */
public class LocaleDTO {

    private static final int PARAM_0 = 0;
    private static final int PARAM_1 = 1;
    private static final int PARAM_2 = 2;
    private static final int PARAM_3 = 3;

    private String code;

    private String language;

    private String englishLanguage;

    private boolean defaultLocale;

    @MatchingEntityId(index= PARAM_0)
    @MatchingFactoryParameter(index= PARAM_0)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @MatchingEntityId(index= PARAM_1)
    @MatchingFactoryParameter(index= PARAM_1)
    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @MatchingEntityId(index= PARAM_2)
    @MatchingFactoryParameter(index= PARAM_2)
    public String getEnglishLanguage() {
        return englishLanguage;
    }

    public void setEnglishLanguage(String englishLanguage) {
        this.englishLanguage = englishLanguage;
    }

    @MatchingEntityId(index= PARAM_3)
    @MatchingFactoryParameter(index= PARAM_3)
    public boolean isDefaultLocale() {
        return defaultLocale;
    }

    public void setDefaultLocale(boolean defaultLocale) {
        this.defaultLocale = defaultLocale;
    }
}
