/* ********************************************************************************
 * InbandProperties.java
 * 
 * Copyright © 2013 - 2016 VMware, Inc. All Rights Reserved.

 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at http://www.apache.org/licenses/LICENSE-2.0

 * Unless required by applicable law or agreed to in writing, software distributed
 * under the License is distributed on an "AS IS" BASIS, without warranties or
 * conditions of any kind, EITHER EXPRESS OR IMPLIED. see the License for the
 * specific language governing permissions and limitations under the License
 *
 * *******************************************************************************/
package com.vmware.vrack.hms.common.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Class that holds properties that are specific to hms-inband
 * 
 * @author Vmware
 */
@Component
public class InbandProperties
{
    private static int vsphereClientTimeoutInMs;

    public static int getVsphereClientTimeoutInMs()
    {
        return vsphereClientTimeoutInMs;
    }

    /**
     * Timeout for vsphere client to connect a host.
     * 
     * @param vsphereClientTimeoutInMs
     */
    @Value( "${vsphere.connection.timeout.ms:30000}" )
    public void setVsphereClientTimeoutInMs( int vsphereClientTimeoutInMs )
    {
        InbandProperties.vsphereClientTimeoutInMs = vsphereClientTimeoutInMs;
    }
}
