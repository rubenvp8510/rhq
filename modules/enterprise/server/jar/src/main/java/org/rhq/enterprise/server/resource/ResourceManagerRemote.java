/*
 * RHQ Management Platform
 * Copyright (C) 2005-2008 Red Hat, Inc.
 * All rights reserved.
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation version 2 of the License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 */
package org.rhq.enterprise.server.resource;

import java.util.List;

import javax.ejb.Remote;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.rhq.core.domain.auth.Subject;
import org.rhq.core.domain.criteria.ResourceCriteria;
import org.rhq.core.domain.resource.Resource;
import org.rhq.core.domain.resource.ResourceCategory;
import org.rhq.core.domain.resource.composite.ResourceComposite;
import org.rhq.core.domain.util.PageControl;
import org.rhq.core.domain.util.PageList;

/**
 * @author Asaf Shakarchi
 * @author Jay Shaughnessy 
 * @author Simeon Pinder
 */

@SOAPBinding(style = SOAPBinding.Style.DOCUMENT)
@WebService
@Remote
public interface ResourceManagerRemote {

    // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    //
    // These methods and constants also live in the local interface, update both locations when making a change!
    //
    // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!    

    static public final String DATA_AGENT = "agent";
    static public final String DATA_AVAILABILITY = "availability";
    static public final String DATA_CURRENT_AVAILABILITY = "currentAvailability";
    static public final String DATA_EXPLICIT_GROUPS = "explicitGroups";
    static public final String DATA_IMPLICIT_GROUPS = "implicitGroups";
    static public final String DATA_PARENT_RESOURCE = "parentResource";
    static public final String DATA_PLUGIN_CONFIGURATION = "pluginConfiguration";
    static public final String DATA_PRODUCT_VERSION = "productVersion";
    static public final String DATA_RESOURCE_CONFIGURATION = "resourceConfiguration";
    static public final String DATA_RESOURCE_ERRORS = "resourceErrors";
    static public final String DATA_RESOURCE_TYPE = "resourceType";

    /**
     * Returns the Resource with the specified id.
     *
     * @param  subject The logged in user's subject.
     * @param  resourceId the id of a {@link Resource} in inventory.
     *
     * @return the resource
     * @throws FetchException if the resource represented by the resourceId parameter does not exist, or if the
     *                        passed subject does not have permission to view this resource.
     */
    @WebMethod
    Resource getResource( //
        @WebParam(name = "subject") Subject subject, //
        @WebParam(name = "resourceId") int resourceId);

    /**
     * Returns the lineage of the Resource with the specified id. The lineage is represented as a List of Resources,
     * with the first item being the root of the Resource's ancestry (or the Resource itself if it is a root Resource
     * (i.e. a platform)) and the last item being the Resource itself. Since the lineage includes the Resource itself,
     * the returned List will always contain at least one item.
     *
     * @param  subject The logged in user's subject.
     * @param  resourceId the id of a {@link Resource} in inventory
     *
     * @return the lineage of the Resource with the specified id
     * @throws FetchException on any issue. Wraps ResourceNotFoundException when necessary. 
     */
    @WebMethod
    List<Resource> findResourceLineage( //
        @WebParam(name = "subject") Subject subject, //
        @WebParam(name = "resourceId") int resourceId);

    /**
     * Removes these resources from inventory.  The resources may subsequently be rediscovered.  Note that for
     * each specified resource all children will also be removed, it it not necessary or recommended to
     * specify more than one resource in the same ancestry line.
     * 
     * @param subject The logged in user's subject.
     * @param resourceIds The resources to uninventory.
     */
    @WebMethod
    void uninventoryResources( //
        @WebParam(name = "subject") Subject subject, //
        @WebParam(name = "resourceIds") int[] resourceIds);

    @WebMethod
    PageList<ResourceComposite> findResourceComposites( //
        @WebParam(name = "subject") Subject subject, //
        @WebParam(name = "category") ResourceCategory category, //
        @WebParam(name = "typeName") String typeName, //
        @WebParam(name = "parentResourceId") int parentResourceId, //
        @WebParam(name = "searchString") String searchString, //
        @WebParam(name = "pageControl") PageControl pageControl);

    @WebMethod
    PageList<Resource> findResourcesByCriteria( //
        @WebParam(name = "subject") Subject subject, //
        @WebParam(name = "criteria") ResourceCriteria criteria);

}