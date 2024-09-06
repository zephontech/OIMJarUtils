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
public class DeleteJar extends TaskSupport {
    
    

    private static final Logger logger = Logger.getLogger(DeleteJar.class.getName());
    
    @Override
    public void execute(HashMap hm) throws Exception {
        
        String jarType = (String)hm.get("Jar Type");
        String jarName = (String)hm.get("Jar Name");
        
        
        if (StringUtils.isEmpty(jarName))
        {
            throw new RuntimeException("Invalid Jar Path");
        }
        
        if (StringUtils.isEmpty(jarType))
        {
            throw new RuntimeException("Invalid Jar Type");
        }
        
        Set<JarElement> elems = MakeJarElement.getElement(jarName,jarType,"");
        if (elems == null)
        {
            throw new RuntimeException("Invalid Jar Check Type,Name and Location");
        }
        
        PlatformUtilsService utilsService = (PlatformUtilsService)Platform.getService(PlatformUtilsService.class);
        try
        {
            utilsService.deleteJars(elems);
        }
        catch(Exception e)
        {
            throw new RuntimeException("Error Deleting Jar",e);
        }
        
        try
        {
            utilsService.purgeCache("All");
        }
        catch(Exception e)
        {
            throw new RuntimeException("Error Purging Cache",e);
        }
        logger.info("Delete Complete");
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
