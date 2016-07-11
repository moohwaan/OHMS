/* ********************************************************************************
 * SelUtil.java
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

import com.vmware.vrack.hms.common.resource.fru.SensorType;
import com.vmware.vrack.hms.common.resource.sel.ReadingType;
import com.vmware.vrack.hms.common.resource.sel.SelRecord;

public class SelUtil
{
    public synchronized static SelRecord compareSelRecord( SelRecord record, SelRecord selFilter )
    {
        if ( record != null )
        {
            if ( selFilter != null )
            {
                SensorType sensorTypeFilter = selFilter.getSensorType();
                if ( sensorTypeFilter == null
                    || ( sensorTypeFilter != null && sensorTypeFilter == record.getSensorType() ) )
                {
                    ReadingType eventFilter = selFilter.getEvent();
                    if ( eventFilter == null || ( eventFilter != null && eventFilter == record.getEvent() ) )
                    {
                        return record;
                    }
                }
                // If selFilter contains field that doesnot match with the SelRecord, return null
                return null;
            }
        }
        return record;
    }
}
