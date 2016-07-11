/* ********************************************************************************
 * FindMacAddressTaskTest.java
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

package com.vmware.vrack.hms.task.oob.ipmi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Ignore;
import org.junit.Test;

import com.vmware.vrack.hms.boardservice.BoardServiceProvider;
import com.vmware.vrack.hms.common.boardvendorservice.api.IBoardService;
import com.vmware.vrack.hms.common.exception.HmsException;
import com.vmware.vrack.hms.common.notification.TaskResponse;
import com.vmware.vrack.hms.common.servernodes.api.ServerNode;
import com.vmware.vrack.hms.ipmiservice.IpmiTaskConnectorFactory;
import com.vmware.vrack.hms.task.ipmi.IpmiTaskConnector;
import com.vmware.vrack.hp.ilo.boardservice.BoardService_iLO;
import com.vmware.vrack.intel.rmm.boardservice.BoardService_S2600GZ;

/**
* Test of FindMacAddressTask functions
* @author Yagnesh Chawda
*
*/
@Ignore
public class FindMacAddressTaskTest
{
	private static Logger logger = Logger.getLogger(FindMacAddressTaskTest.class);
	
	
	@After
	public void destroyCachedConnections()
	{
		ServerNode node = new ServerNode("N1", "10.28.197.202", "root", "root123");
		try
{
	        IpmiTaskConnector connector = IpmiTaskConnectorFactory.getIpmiTaskConnector(node.getServiceObject(), 3, false, null, null);
	        IpmiTaskConnectorFactory.destroyConnector(connector, node.getServiceObject(), true);
}
catch (Exception e)
{
	logger.debug("Error in Cleanup" + e);
}
		
	}
	
	@Test(expected = HmsException.class)
	public void test_executeTask_ValidHost_NoBoardMapping() throws Exception
	{
		logger.info("TS: FindMacAddressTaskTest: test_executeTask_ValidHost_NoBoardMapping");
		TaskResponse taskResponse = new TaskResponse(new ServerNode("N1", "10.28.197.202", "root", "root123"));
		FindMacAddressTask findMacAddressTask = new FindMacAddressTask(taskResponse);
		
		/*IBoardService boardService = new BoardService_S2600GZ();
		BoardServiceProvider.addBoardService(taskResponse.getNode().getServiceObject(), boardService, true);*/
		findMacAddressTask.executeTask();
	}
	
	@Test(expected = HmsException.class)
	public void test_executeTask_InvalidHost_ValidBoardMapping() throws Exception
	{
		logger.info("TS: FindMacAddressTaskTest: test_executeTask_InvalidHost_ValidBoardMapping");
		TaskResponse taskResponse = new TaskResponse(new ServerNode("N1", "10.28.197.202", "root", "root123"));
		
		FindMacAddressTask findMacAddressTask = new FindMacAddressTask(taskResponse);
		
		IBoardService boardService = new BoardService_S2600GZ();
		BoardServiceProvider.addBoardService(taskResponse.getNode().getServiceObject(), boardService, true);
		
		findMacAddressTask.executeTask();
	}
	
	
	@Test(expected = HmsException.class)
	public void test_executeTask_InvalidHostCreds() throws Exception
	{
		logger.info("TS: FindMacAddressTaskTest: test_executeTask_InvalidHostCreds");
		TaskResponse taskResponse = new TaskResponse(new ServerNode("N1", "10.28.197.202", "root", "root123"));
		
		FindMacAddressTask findMacAddressTask = new FindMacAddressTask(taskResponse);
		
		IBoardService boardService = new BoardService_S2600GZ();
		BoardServiceProvider.addBoardService(taskResponse.getNode().getServiceObject(), boardService, true);
		
		findMacAddressTask.executeTask();
	}
	
	@Test
	public void test_executeTask_ValidHost_ValidBoardMapping() throws Exception
	{
		logger.info("TS: FindMacAddressTaskTest: test_executeTask_ValidHost_ValidBoardMapping");
		TaskResponse taskResponse = new TaskResponse(new ServerNode("N1", "10.28.197.202", "root", "root123"));
		
		FindMacAddressTask findMacAddressTask = new FindMacAddressTask(taskResponse);
		
		IBoardService boardService = new BoardService_S2600GZ();
		BoardServiceProvider.addBoardService(taskResponse.getNode().getServiceObject(), boardService, true);
		
		findMacAddressTask.executeTask();
		ServerNode node = (ServerNode) taskResponse.getNode();
		
		assertFalse("".equals(node.getOobMacAddress()));
		assertEquals(17, node.getOobMacAddress().length());
	}
	
	@Test
	public void test_executeTask_ValidHost_InvalidBoardMapping() throws Exception
	{
		logger.info("TS: FindMacAddressTaskTest: test_executeTask_ValidHost_InvalidBoardMapping");
		TaskResponse taskResponse = new TaskResponse(new ServerNode("N1", "10.28.197.202", "root", "root123"));
		FindMacAddressTask findMacAddressTask = new FindMacAddressTask(taskResponse);
		
		IBoardService boardService = new BoardService_iLO();
		BoardServiceProvider.addBoardService(taskResponse.getNode().getServiceObject(), boardService, true);
		
		findMacAddressTask.executeTask();
		ServerNode node = (ServerNode) taskResponse.getNode();
		assertTrue("0:0:0:0:0:0".equals(node.getOobMacAddress()));
	}
	
	
	@Test(expected = NullPointerException.class)
	public void test_executeTask_InvalidTaskResponse() throws Exception
	{
		logger.info("TS: FindMacAddressTaskTest: test_executeTask_InvalidTaskResponse");
		TaskResponse taskResponse = null;
		ServerNode sNode = new ServerNode("N1", "10.28.197.202", "root", "root123");

		FindMacAddressTask findMacAddressTask = new FindMacAddressTask(taskResponse);
		
		IBoardService boardService = new BoardService_S2600GZ();
		BoardServiceProvider.addBoardService(sNode.getServiceObject(), boardService, true);
		
		findMacAddressTask.executeTask();
	}
	
}
