/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tech.zephon.oim.utiljobs.jobs;

import com.thortech.util.logging.Logger;
import java.util.HashMap;
import oracle.iam.platform.Platform;
import oracle.iam.platformservice.api.PlatformUtilsService;
import oracle.iam.scheduler.vo.TaskSupport;
import tech.zephon.utiljobs.utils.StringUtils;

/**
 *
 * @author fforester
 */
public class PurgeCache extends TaskSupport {
    
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

    @Override
    public HashMap getAttributes() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setAttributes() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
}
