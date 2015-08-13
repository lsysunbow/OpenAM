/*
 * The contents of this file are subject to the terms of the Common Development and
 * Distribution License (the License). You may not use this file except in compliance with the
 * License.
 *
 * You can obtain a copy of the License at legal/CDDLv1.0.txt. See the License for the
 * specific language governing permission and limitations under the License.
 *
 * When distributing Covered Software, include this CDDL Header Notice in each file and include
 * the License file at legal/CDDLv1.0.txt. If applicable, add the following below the CDDL
 * Header, with the fields enclosed by brackets [] replaced by your own identifying
 * information: "Portions copyright [year] [name of copyright owner]".
 *
 * Copyright 2015 ForgeRock AS.
 */

package org.forgerock.openam.rest.devices;

import static org.forgerock.json.resource.ResourceException.*;
import static org.forgerock.json.resource.Responses.newQueryResponse;
import static org.forgerock.json.resource.Responses.newResourceResponse;
import static org.forgerock.util.promise.Promises.newExceptionPromise;
import static org.forgerock.util.promise.Promises.newResultPromise;

import java.text.ParseException;
import java.util.List;

import org.forgerock.http.context.ServerContext;
import org.forgerock.json.JsonValue;
import org.forgerock.json.resource.ActionRequest;
import org.forgerock.json.resource.ActionResponse;
import org.forgerock.json.resource.CreateRequest;
import org.forgerock.json.resource.DeleteRequest;
import org.forgerock.json.resource.InternalServerErrorException;
import org.forgerock.json.resource.PatchRequest;
import org.forgerock.json.resource.QueryRequest;
import org.forgerock.json.resource.QueryResourceHandler;
import org.forgerock.json.resource.QueryResponse;
import org.forgerock.json.resource.ReadRequest;
import org.forgerock.json.resource.ResourceException;
import org.forgerock.json.resource.ResourceResponse;
import org.forgerock.json.resource.UpdateRequest;
import org.forgerock.openam.forgerockrest.entitlements.RealmAwareResource;
import org.forgerock.openam.rest.resource.ContextHelper;
import org.forgerock.util.promise.Promise;

/**
 * REST resource for a user's trusted devices.
 *
 * @since 13.0.0
 */
public abstract class UserDevicesResource<T extends UserDevicesDao> extends RealmAwareResource {

    static final String UUID_KEY = "uuid";

    protected final T userDevicesDao;

    private final ContextHelper contextHelper;

    /**
     * Constructs a new UserDevicesResource.
     *
     * @param userDevicesDao An instance of the {@code UserDevicesDao}.
     */
    public UserDevicesResource(T userDevicesDao, ContextHelper contextHelper) {
        this.userDevicesDao = userDevicesDao;
        this.contextHelper = contextHelper;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Promise<ActionResponse, ResourceException> actionCollection(ServerContext context, ActionRequest request) {
        return newExceptionPromise(newNotSupportedException("Not supported."));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Promise<ActionResponse, ResourceException> actionInstance(ServerContext context, String resourceId,
            ActionRequest request) {
        return newExceptionPromise(newNotSupportedException("Not supported."));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Promise<ResourceResponse, ResourceException> createInstance(ServerContext context, CreateRequest request) {
        return newExceptionPromise(newNotSupportedException("Not supported."));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Promise<ResourceResponse, ResourceException> deleteInstance(ServerContext context, String resourceId,
            DeleteRequest request) {

        final String userName = contextHelper.getUserId(context);

        try {
            List<JsonValue> devices = userDevicesDao.getDeviceProfiles(userName, getRealm(context));

            JsonValue toDelete = null;
            for (JsonValue device : devices) {
                if (resourceId.equals(device.get(UUID_KEY).asString())) {
                    toDelete = device;
                    break;
                }
            }

            if (toDelete == null) {
                return newExceptionPromise(newNotFoundException("User device, " + resourceId + ", not found."));
            }

            devices.remove(toDelete);

            userDevicesDao.saveDeviceProfiles(userName, getRealm(context), devices);

            return newResultPromise(newResourceResponse(resourceId, toDelete.hashCode() + "", toDelete));
        } catch (InternalServerErrorException e) {
            return newExceptionPromise(adapt(e));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Promise<ResourceResponse, ResourceException> patchInstance(ServerContext context, String resourceId,
            PatchRequest request) {
        return newExceptionPromise(newNotSupportedException("Not supported."));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Promise<QueryResponse, ResourceException> queryCollection(ServerContext context, QueryRequest request,
            QueryResourceHandler handler) {
        try {
            final String userName = contextHelper.getUserId(context);

            for (JsonValue profile : userDevicesDao.getDeviceProfiles(userName, getRealm(context))) {
                handler.handleResource(convertValue(profile));
            }
            return newResultPromise(newQueryResponse());
        } catch (ParseException e) {
            return newExceptionPromise(newInternalServerErrorException(e.getMessage()));
        } catch (InternalServerErrorException e) {
            return newExceptionPromise(adapt(e));
        }
    }

    protected abstract ResourceResponse convertValue(JsonValue queryResult) throws ParseException;

    /**
     * {@inheritDoc}
     */
    @Override
    public Promise<ResourceResponse, ResourceException> readInstance(ServerContext context, String resourceId,
            ReadRequest request) {
        return newExceptionPromise(newNotSupportedException("Not supported."));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Promise<ResourceResponse, ResourceException> updateInstance(ServerContext context, String resourceId,
            UpdateRequest request) {
        return newExceptionPromise(newNotSupportedException("Not supported."));
    }

}