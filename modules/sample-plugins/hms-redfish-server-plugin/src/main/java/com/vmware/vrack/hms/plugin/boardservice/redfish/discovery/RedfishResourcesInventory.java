/*
 * Copyright (c) 2016 Intel Corporation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.vmware.vrack.hms.plugin.boardservice.redfish.discovery;

import com.vmware.vrack.hms.plugin.boardservice.redfish.client.IRedfishWebClient;
import com.vmware.vrack.hms.plugin.boardservice.redfish.client.RedfishClientException;
import com.vmware.vrack.hms.plugin.boardservice.redfish.client.RedfishWebClient;
import com.vmware.vrack.hms.plugin.boardservice.redfish.resources.RedfishResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static java.lang.String.format;

public class RedfishResourcesInventory
    implements AutoCloseable
{
    private static final Logger LOGGER = LoggerFactory.getLogger( RedfishResourcesInventory.class );

    private IRedfishWebClient webClient = createRedfishClient();

    protected IRedfishWebClient createRedfishClient()
    {
        return new RedfishWebClient();
    }

    public <T extends RedfishResource> Set<T> getResourcesByClass( URI serviceEndpoint, Class<T> resourceClass )
    {
        Map<String, RedfishResource> allResources = discoverService( serviceEndpoint );
        Set<T> resources = new HashSet<>();
        for ( RedfishResource resource : allResources.values() )
        {
            if ( resource.getClass().isAssignableFrom( resourceClass ) )
            {
                resources.add( (T) resource );
            }
        }
        return resources;
    }

    private Map<String, RedfishResource> discoverService( URI serviceEndpoint )
    {
        Map<String, RedfishResource> resourceMap = new HashMap<>();
        ResourcesCrawler crawler = new ResourcesCrawler( webClient, serviceEndpoint );
        resourceMap.putAll( crawler.getAllResources() );
        return Collections.unmodifiableMap( resourceMap );
    }

    public <T extends RedfishResource> T getResourceByURI( URI targetUri )
        throws RedfishResourcesInventoryException
    {
        try
        {
            return (T) webClient.get( targetUri );
        }
        catch ( RedfishClientException e )
        {
            // TODO consider retrying when ProcessingException is this exception's root cause
            String message = format( "Error while reading resource at URI %s", e.getTargetUri() );
            LOGGER.error( message, e );
            throw new RedfishResourcesInventoryException( message );
        }
        catch ( Exception e )
        {
            String message = "RedfishWebClient could not be properly instantiated";
            LOGGER.error( message, e );
            throw new RedfishResourcesInventoryException( message );
        }
    }

    @Override
    public void close()
    {
        webClient.close();
    }
}
