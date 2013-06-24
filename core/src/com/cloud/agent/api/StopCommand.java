// Licensed to the Apache Software Foundation (ASF) under one
// or more contributor license agreements.  See the NOTICE file
// distributed with this work for additional information
// regarding copyright ownership.  The ASF licenses this file
// to you under the Apache License, Version 2.0 (the
// "License"); you may not use this file except in compliance
// with the License.  You may obtain a copy of the License at
//
//   http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing,
// software distributed under the License is distributed on an
// "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
// KIND, either express or implied.  See the License for the
// specific language governing permissions and limitations
// under the License.
package com.cloud.agent.api;

import com.cloud.vm.VirtualMachine;

public class StopCommand extends RebootCommand {
    String vnet;
    private boolean isProxy=false;
    private String urlPort=null;
    private String publicConsoleProxyIpAddress=null;
    private boolean cleanupOnly;
    boolean executeInSequence = false;

    protected StopCommand() {
    }

    public StopCommand(VirtualMachine vm, boolean isProxy, String urlPort, String publicConsoleProxyIpAddress, boolean cleanupOnly, boolean executeInSequence) {
    	super(vm);
    	this.isProxy = isProxy;
    	this.urlPort = urlPort;
    	this.publicConsoleProxyIpAddress = publicConsoleProxyIpAddress;
        this.cleanupOnly = cleanupOnly;
    }

    public StopCommand(VirtualMachine vm, String vnet, boolean executeInSequence) {
        super(vm);
        this.vnet = vnet;
        this.executeInSequence = executeInSequence;
    }
    
    public boolean isCleanupOnly() {
        return cleanupOnly;
    }

    public StopCommand(VirtualMachine vm, boolean executeInSequence) {
        super(vm);
        this.executeInSequence = executeInSequence;
    }

    public StopCommand(String vmName, boolean executeInSequence) {
        super(vmName);
        this.executeInSequence = executeInSequence;
    }

    public String getVnet() {
        return vnet;
    }

    @Override
    public boolean executeInSequence() {
        return executeInSequence;
    }

	public boolean isProxy() {
		return isProxy;
	}

	public String getURLPort() {
		return urlPort;
	}

	public String getPublicConsoleProxyIpAddress() {
		return publicConsoleProxyIpAddress;
	}

}
