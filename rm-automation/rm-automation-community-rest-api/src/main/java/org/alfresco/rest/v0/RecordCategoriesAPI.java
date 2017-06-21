/*
 * #%L
 * Alfresco Records Management Module
 * %%
 * Copyright (C) 2005 - 2017 Alfresco Software Limited
 * %%
 * This file is part of the Alfresco software.
 * -
 * If the software was purchased under a paid Alfresco license, the terms of
 * the paid license agreement will prevail.  Otherwise, the software is
 * provided under the following open source license terms:
 * -
 * Alfresco is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * -
 * Alfresco is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * -
 * You should have received a copy of the GNU Lesser General Public License
 * along with Alfresco. If not, see <http://www.gnu.org/licenses/>.
 * #L%
 */
package org.alfresco.rest.v0;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import org.alfresco.rest.core.v0.BaseAPI;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Methods to make API requests using v0 API on record categories
 *
 * @author Oana Nechiforescu
 * @since 2.5
 */
@Component
public class RecordCategoriesAPI extends BaseAPI
{
    private static final Logger LOGGER = LoggerFactory.getLogger(RecordCategoriesAPI.class);
    private static final String RM_ACTIONS_API = "{0}rma/actions/ExecutionQueue";
    private static final String DISPOSITION_ACTIONS_API = "{0}node/{1}/dispositionschedule/dispositionactiondefinitions";

    /**
     * Creates a retention schedule for the category given as parameter
     *
     * @param user         the user creating the disposition schedule
     * @param password     the user's password
     * @param categoryName the category name to create the retention schedule for
     * @return true if the creation completed successfully
     */
    public boolean createRetentionSchedule(String user, String password, String categoryName)
    {
        String catNodeRef = NODE_REF_WORKSPACE_SPACES_STORE + getItemNodeRef(user, password, "/" + categoryName);

        try
        {
            JSONObject requestParams = new JSONObject();
            requestParams.put("name", "createDispositionSchedule");
            requestParams.put("nodeRef", catNodeRef);

            return doPostJsonRequest(user, password, requestParams, RM_ACTIONS_API);
        } catch (JSONException error)
        {
            LOGGER.error("Unable to extract response parameter", error);
        }
        return false;
    }

    /**
     * Sets retention schedule authority and instructions, also if it is applied to records or folders
     *
     * @param user             the user creating the disposition schedule
     * @param password         the user's password
     * @param retentionNodeRef the retention nodeRef
     * @return true if the creation completed successfully
     */
    public boolean setRetentionScheduleGeneralFields(String user, String password, String retentionNodeRef, Map<RETENTION_SCHEDULE, String> retentionProperties, Boolean appliedToRecords)
    {
        String dispRetentionNodeRef = NODE_PREFIX + retentionNodeRef;

        try
        {
            JSONObject requestParams = new JSONObject();
            requestParams.put("prop_rma_dispositionAuthority", getPropertyValue(retentionProperties, RETENTION_SCHEDULE.RETENTION_AUTHORITY));
            requestParams.put("prop_rma_dispositionInstructions", getPropertyValue(retentionProperties, RETENTION_SCHEDULE.RETENTION_INSTRUCTIONS));
            requestParams.put("prop_rma_recordLevelDisposition", appliedToRecords.toString());
            return doPostJsonRequest(user, password, requestParams, MessageFormat.format(UPDATE_METADATA_API, "{0}", dispRetentionNodeRef));
        } catch (JSONException error)
        {
            LOGGER.error("Unable to extract response parameter", error);
        }
        return false;
    }

    /**
     * Creates a retention schedule steps for the category given as parameter
     *
     * @param user         the user creating the disposition schedule
     * @param password     the user's password
     * @param categoryName the category name to create the retention schedule for
     * @return true if the creation completed successfully
     */
    public boolean addDispositionScheduleSteps(String user, String password, String categoryName, Map<RETENTION_SCHEDULE, String> properties)
    {
        String catNodeRef = NODE_PREFIX + getItemNodeRef(user, password, "/" + categoryName);
        {
            try
            {
                JSONObject requestParams = new JSONObject();
                addPropertyToRequest(requestParams, "name", properties, RETENTION_SCHEDULE.NAME);
                addPropertyToRequest(requestParams, "description", properties, RETENTION_SCHEDULE.DESCRIPTION);
                addPropertyToRequest(requestParams, "period", properties, RETENTION_SCHEDULE.RETENTION_PERIOD);
                addPropertyToRequest(requestParams, "ghostOnDestroy", properties, RETENTION_SCHEDULE.RETENTION_GHOST);
                addPropertyToRequest(requestParams, "periodProperty", properties, RETENTION_SCHEDULE.RETENTION_PERIOD_PROPERTY);
                addPropertyToRequest(requestParams, "events", properties, RETENTION_SCHEDULE.RETENTION_EVENTS);
                addPropertyToRequest(requestParams, "eligibleOnFirstCompleteEvent", properties, RETENTION_SCHEDULE.RETENTION_ELIGIBLE_FIRST_EVENT);

                return doPostJsonRequest(user, password, requestParams, MessageFormat.format(DISPOSITION_ACTIONS_API, "{0}", catNodeRef));
            } catch (JSONException error)
            {
                LOGGER.error("Unable to extract response parameter", error);
            }
            return false;
        }
    }

    /**
     * Delete a category
     *
     * @param username     user's username
     * @param password     its password
     * @param categoryName the name of the category
     * @return true if the delete is successful
     */
    public boolean deleteCategory(String username, String password, String categoryName)
    {
        return deleteItem(username, password, "/" + categoryName);
    }

    /**
     * Delete a sub-category
     *
     * @param username     user's username
     * @param password     its password
     * @param categoryName the name of the sub-category
     * @return true if the delete is successful
     */
    public boolean deleteSubCategory(String username, String password, String categoryName, String subCategoryName)
    {
        return deleteItem(username, password, "/" + categoryName + "/" + subCategoryName);
    }

    /**
     * Delete a folder inside a container in RM site
     *
     * @param username      user's username
     * @param password      its password
     * @param folderName    folder name
     * @param containerName the name of the category or container sin which the folder is
     * @return true if the delete is successful
     */
    public boolean deleteFolderInContainer(String username, String password, String folderName, String containerName)
    {
        return deleteItem(username, password, "/" + containerName + "/" + folderName);
    }

    /**
     * Returns a map of retention properties
     *
     * @param authority    retention authority
     * @param instructions retention authority
     * @return the map
     */
    public Map<RETENTION_SCHEDULE, String> getRetentionProperties(String authority, String instructions)
    {
        Map<RETENTION_SCHEDULE, String> retentionProperties = new HashMap<>();
        retentionProperties.put(RETENTION_SCHEDULE.RETENTION_AUTHORITY, authority);
        retentionProperties.put(RETENTION_SCHEDULE.RETENTION_INSTRUCTIONS, instructions);
        return retentionProperties;
    }
}