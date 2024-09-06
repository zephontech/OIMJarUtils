/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tech.zephon.oim.utiljobs.jobs;

import com.thortech.util.logging.Logger;
import java.util.HashMap;
import java.util.Set;
import oracle.iam.platform.Platform;
import oracle.iam.platformservice.api.PlatformUtilsService;
import oracle.iam.platformservice.vo.JarElement;
import oracle.iam.scheduler.vo.TaskSupport;
import tech.zephon.utiljobs.utils.MakeJarElement;
import tech.zephon.utiljobs.utils.StringUtils;

/**
 *
 * @author fforester
 */
public class ReloadJar extends TaskSupport {

    private static final Logger logger = Logger.getLogger(ReloadJar.class.getName());
    
    @Override
    public void execute(HashMap hm) throws Exception {
        
        String jarType = (String)hm.get("Jar Type");
        String jarPath = (String)hm.get("Jar Path");
        String jarName = (String)hm.get("Jar Name");
        Boolean ignoreDelete = (Boolean)hm.get("Ignore Delete");
        Boolean doDelete = (Boolean)hm.get("Do Delete");
        
        
        if (StringUtils.isEmpty(jarPath))
        {
            throw new RuntimeException("Invalid Jar Path");
        }
        
        if (StringUtils.isEmpty(jarType))
        {
            throw new RuntimeException("Invalid Jar Type");
        }
        
        if (StringUtils.isEmpty(jarName))
        {
            throw new RuntimeException("Invalid Jar Name");
        }
        
        Set<JarElement> elems = MakeJarElement.getElement(jarName,jarType,jarPath);
        if (elems == null)
        {
            throw new RuntimeException("Invalid Jar Check Type,Name and Location");
        }
        
        PlatformUtilsService utilsService = (PlatformUtilsService)Platform.getService(PlatformUtilsService.class);
        if (doDelete)
        {
            try
            {
                utilsService.deleteJars(elems);
            }
            catch(Exception e)
            {
                if (!ignoreDelete)
                    throw new RuntimeException("Error Deleting Jar",e);
            }
        }
        
        try
        {
            utilsService.uploadJars(elems);
        }
        catch(Exception e)
        {
            throw new RuntimeException("Error Uploading Jar",e);
        }
        try
        {
            utilsService.purgeCache("All");
        }
        catch(Exception e)
        {
            throw new RuntimeException("Error Purging Cache",e);
        }
        logger.info("Reload Complete");
        
          
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
