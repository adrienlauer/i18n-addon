/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.i18n.rest.statistic;

import org.seedstack.i18n.rest.locale.LocaleRepresentation;
import org.seedstack.business.api.interfaces.finder.Finder;

import java.util.List;

/**
 * @author PDC Date: 29/07/14
 */
@Finder
public interface StatisticFinder {

	/**
	 * build the list statistic results by locale selected.
	 * 
	 * @param localeRepresentation
	 *            the specified locale
	 * @param listResult
	 *            list of statistic results
	 */
	void findStatisticRepresentation(LocaleRepresentation localeRepresentation,
			List<StatisticRepresentation> listResult);

}