/* ********************************************************************************
 * RestServicesFactory.java
 * 
 * Copyright © 2013 - 2016 VMware, Inc. All Rights Reserved.

 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at http://www.apache.org/licenses/LICENSE-2.0

 * Unless required by applicable law or agreed to in writing, software distributed
 * under the License is distributed on an "AS IS" BASIS, without warranties or
 * conditions of any kind, EITHER EXPRESS OR IMPLIED. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * *******************************************************************************/
package com.vmware.vrack.hms.rest.services;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import com.vmware.vrack.hms.rest.exception.ExceptionHandler;
import com.vmware.vrack.hms.rest.exception.HMSRestExceptionHandler;

public class RestServicesFactory
    extends Application
{
    private static Set<Class<?>> classes = new HashSet<Class<?>>();

    public RestServicesFactory()
    {
        // initialize restful services
        classes.add( ServerRestService.class );
        classes.add( SwitchRestService.class );
        classes.add( HMSManagementRestService.class );
        classes.add( SubscriberRestService.class );
        classes.add( HMSRestExceptionHandler.class );
        classes.add( HmsServiceFilters.class );
        classes.add( InventoryRestService.class );
        classes.add( AboutRestService.class );
        classes.add( ExceptionHandler.class );
        classes.add( ComponentEventRestService.class );
        classes.add( UpgradeRestService.class );
    }

    @Override
    public Set<Class<?>> getClasses()
    {
        return classes;
    }
}
