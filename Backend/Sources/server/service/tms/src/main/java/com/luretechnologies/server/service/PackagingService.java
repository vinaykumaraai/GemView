/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.luretechnologies.server.service;

import com.luretechnologies.server.data.display.tms.AppDisplay;
import com.luretechnologies.server.data.display.tms.HeartbeatAppInfoDisplay;
import java.util.List;

/**
 *
 * @author michael
 */
public interface PackagingService {
    
    
    /**
     *
     * @param terminalId
     * @param appDisplayList
     * @param heartbeatAppInfoDisplayList
     * @throws Exception
     */
    public void createPackage(Long terminalId, List<AppDisplay> appDisplayList, List<HeartbeatAppInfoDisplay> heartbeatAppInfoDisplayList) throws Exception;
}
