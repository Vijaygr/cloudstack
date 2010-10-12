/**
 * 
 */
package com.cloud.deploy;

import java.util.HashSet;
import java.util.Set;

import com.cloud.exception.InsufficientServerCapacityException;
import com.cloud.host.Host;
import com.cloud.utils.component.Adapter;
import com.cloud.vm.VirtualMachineProfile;

/**
 * Returns a deployment destination for the VM.
 */
public interface DeploymentPlanner extends Adapter {
    /**
     * plan is called to determine where a virtual machine should be running.
     * 
     * @param vm virtual machine.
     * @param plan deployment plan that tells you where it's being deployed to.
     * @param avoid avoid these data centers, pods, clusters, or hosts.
     * @return DeployDestination for that virtual machine.
     */
    DeployDestination plan(VirtualMachineProfile vm, DeploymentPlan plan, ExcludeList avoid) throws InsufficientServerCapacityException;
    
    /**
     * check() is called right before the virtual machine starts to make sure
     * the host has enough capacity.
     * 
     * @param vm virtual machine in question.
     * @param plan deployment plan used to determined the deploy destination.
     * @param dest destination returned by plan.
     * @param avoid what to avoid.
     * @return true if it's okay to start; false if not.  If false, the exclude list will include what should be excluded.
     */
    boolean check(VirtualMachineProfile vm, DeploymentPlan plan, DeployDestination dest, ExcludeList exclude);
    
    public static class ExcludeList {
        Set<Long> _dcIds;
        Set<Long> _podIds;
        Set<Long> _clusterIds;
        Set<Long> _hostIds;
        
        public void adddDataCenter(long dataCenterId) {
            if (_dcIds == null) {
                _dcIds = new HashSet<Long>();
            }
            _dcIds.add(dataCenterId);
        }
        
        public void addPod(long podId) {
            if (_podIds == null) {
                _podIds = new HashSet<Long>();
            }
            _podIds.add(podId);
        }
        
        public void addCluster(long clusterId) {
            if (_clusterIds == null) {
                _clusterIds = new HashSet<Long>();
            }
            _clusterIds.add(clusterId);
        }
        
        public void addHost(long hostId) {
            if (_hostIds == null) {
                _hostIds = new HashSet<Long>();
            }
            _hostIds.add(hostId);
        }
        
        public boolean shouldAvoid(Host host) {
            if (_dcIds != null && _dcIds.contains(host.getDataCenterId())) {
                return true;
            }
            
            if (_podIds != null && _podIds.contains(host.getPodId())) {
                return true;
            }
            
            if (_clusterIds != null && _clusterIds.contains(host.getClusterId())) {
                return true;
            }
            
            if (_hostIds != null && _hostIds.contains(host.getId())) {
                return true;
            }
            
            return false;
        }
    }
}
