/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tech.zephon.oim.utiljobs.jobs;

import com.thortech.util.logging.Logger;
import java.util.HashMap;
import oracle.iam.platform.Platform;
import oracle.iam.platformservice.api.PlatformUtilsService;
import tech.zephon.utiljobs.utils.StringUtils;

/**
 *
 * @author fforester
 */
public class PurgeCache extends BaseTask {
    
    private static final Logger logger = Logger.getLogger(PurgeCache.class.getName());

    @Override
    public void execute(HashMap hm) throws Exception {
        
        String type = (String)hm.get("Type");
        if (StringUtils.isEmpty(type))
        {
            throw new RuntimeException("Specify a cache type");
        }
        
        PlatformUtilsService utilsService = (PlatformUtilsService)Platform.getService(PlatformUtilsService.class);
        try
        {
            utilsService.purgeCache(type);
        }
        catch(Exception e)
        {
            throw new RuntimeException("Error Purging Cache:" + e.getMessage(),e);
        }
        logger.info("Purge Complete");
    }

}
