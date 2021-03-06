/**
 * Copyright (c) 2013-2015, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.i18n.rest.internal.locale;

import org.seedstack.i18n.LocaleService;
import org.seedstack.i18n.internal.domain.model.locale.Locale;
import org.seedstack.i18n.internal.domain.model.locale.LocaleFactory;
import org.seedstack.i18n.internal.domain.model.locale.LocaleRepository;
import org.seedstack.i18n.rest.internal.exception.SeedWebCheckUtils;
import org.apache.commons.lang.StringUtils;
import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.security.RequiresPermissions;
import org.seedstack.seed.transaction.Transactional;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * This REST resource provide method to access application available locales.
 *
 * @author pierre.thirouin@ext.mpsa.com
 *         Date: 26/11/13
 */
@JpaUnit("seed-i18n-domain")
@Transactional
@Path("/seed-i18n/available-locales")
public class AvailableLocalesResource {

    private static final String LOCALE_SHOULD_NOT_BE_BLANK = "Locale should not be blank.";

    @Inject
    private LocaleFinder localeFinder;

    @Inject
    private LocaleRepository localeRepository;

    @Inject
    private LocaleFactory factory;

    @Inject
    private LocaleService localeService;

    /**
     * Gets all the locales available in the application.
     *
     * @return list of locale representation or 204 (no content)
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @RequiresPermissions("seed:i18n:locale:read")
    public Response getAvailableLocales() {
        List<LocaleRepresentation> availableLocales = localeFinder.findAvailableLocales();
        if (!availableLocales.isEmpty()) {
            return Response.ok(availableLocales).build();
        }
        return Response.noContent().build();
    }

    /**
     * Gets the requested locale, if it is available in the application.
     *
     * @param localeId locale code
     * @return http status code 200 (ok) with the locale or 404 if the locale is not available.
     */
    @GET
    @Path("/{localeId}")
    @Produces(MediaType.APPLICATION_JSON)
    @RequiresPermissions("seed:i18n:locale:read")
    public Response getAvailableLocale(@PathParam("localeId") String localeId) {
        SeedWebCheckUtils.checkIfNotBlank(localeId, LOCALE_SHOULD_NOT_BE_BLANK);
        LocaleRepresentation availableLocale = localeFinder.findAvailableLocale(localeId);
        if (availableLocale != null) {
            return Response.ok(availableLocale).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    /**
     * Inserts an available locale.
     *
     * @param representation locale representation
     * @param uriInfo        uriInfo
     * @return status code 201 if created, or 409 (conflict) if the locale already exist.
     * @throws java.net.URISyntaxException if URI is not valid
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RequiresPermissions("seed:i18n:locale:write")
    public Response addAvailableLocale(LocaleRepresentation representation, @Context UriInfo uriInfo) throws URISyntaxException {
        SeedWebCheckUtils.checkIfNotNull(representation, LOCALE_SHOULD_NOT_BE_BLANK);
        Locale locale = localeRepository.load(representation.getCode());
        Locale result;
        if (locale == null) {
            localeRepository.persist(factory.create(representation.getCode(), representation.getLanguage(), representation.getEnglishLanguage()));
            result = localeRepository.load(representation.getCode());
        } else {
            return Response.status(Response.Status.CONFLICT).build();
        }

        return Response.created(new URI(uriInfo.getRequestUri() + "/" + result.getEntityId()))
                .entity(localeFinder.findAvailableLocale(result.getEntityId())).build();
    }

    /**
     * Updates the list of available locales.
     *
     * @param representations new list of available locales
     * @param uriInfo         uriInfo
     * @return status code 200 with updated representations, or 204 if representations list was empty
     * @throws java.net.URISyntaxException if URI is not valid
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RequiresPermissions("seed:i18n:locale:write")
    public Response updateAvailableLocale(List<LocaleRepresentation> representations, @Context UriInfo uriInfo) throws URISyntaxException {
        // Delete the old list of available locales
        localeRepository.clear();
        if (representations != null && !representations.isEmpty()) {
            // Gets the new list of available locales
            List<Locale> locales = new ArrayList<Locale>();
            String defaultLocale = localeService.getDefaultLocale();
            for (LocaleRepresentation representation : representations) {
                boolean isDefault = StringUtils.isNotBlank(defaultLocale) && defaultLocale.equals(representation.getCode());
                locales.add(factory.create(representation.getCode(), representation.getLanguage(), representation.getEnglishLanguage(), isDefault));
            }
            // And persist the new
            for (Locale locale : locales) {
                localeRepository.save(locale);
            }

            return Response.ok(new URI(uriInfo.getRequestUri() + "/"))
                    .entity(localeFinder.findAvailableLocales()).build();
        } else {
            return Response.noContent().build();
        }
    }

    /**
     * Deletes an available locale.
     * Requires "seed:i18n:locale:delete" permission
     *
     * @param locale locale identifier
     * @return status code 204 (no content)
     */
    @DELETE
    @Path("/{locale}")
    @RequiresPermissions("seed:i18n:locale:delete")
    public Response deleteAvailableLocale(@PathParam("locale") String locale) {
        SeedWebCheckUtils.checkIfNotBlank(locale, LOCALE_SHOULD_NOT_BE_BLANK);
        if (locale != null) {
            localeRepository.delete(localeRepository.load(locale));
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.noContent().build();
    }

    /**
     * Deletes all available locale.
     * Requires "seed:i18n:locale:delete" permission
     *
     * @return status code 204 (no content)
     */
    @DELETE
    @RequiresPermissions("seed:i18n:locale:delete")
    public Response deleteAllAvailableLocales() {
        List<Locale> locales = localeRepository.loadAll();
        for (Locale locale : locales) {
            localeRepository.delete(locale);
        }
        return Response.noContent().build();

    }
}
