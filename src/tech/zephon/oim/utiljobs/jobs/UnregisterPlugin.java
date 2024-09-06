/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tech.zephon.oim.utiljobs.jobs;

import java.util.HashMap;
import oracle.iam.platform.Platform;
import oracle.iam.platformservice.api.PlatformService;

/**
 *
 * @author fforester
 */
public class UnregisterPlugin extends BaseTask {

    @Override
    public void execute(HashMap hm) throws Exception {
        
        String pluginName = (String)hm.get("Plugin Name");
        
        if (pluginName == null || pluginName.trim().length() == 0)
        {
            throw new RuntimeException("Specify a Plugin Name");
        }
        
        try
        {
            PlatformService service = Platform.getService(PlatformService.class);
            service.unRegisterPlugin(pluginName);
        }
        catch(Exception e)
        {
            throw new RuntimeException(e);
        }
    }
    
}
