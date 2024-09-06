/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tech.zephon.oim.utiljobs.jobs;

import com.thortech.util.logging.Logger;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import oracle.iam.platform.Platform;
import oracle.iam.platformservice.api.PlatformService;

/**
 *
 * @author fforester
 */
public class RegisterPlugin extends BaseTask {
    
    private Logger logger = Logger.getLogger(this.getClass().getName());

    @Override
    public void execute(HashMap hm)  {
        
        logger.info("Starting:" + this.getDateTime());
        
        String zipFilePath = (String)hm.get("Zip File Path");
        
        if (zipFilePath == null || zipFilePath.trim().length() == 0)
        {
            throw new RuntimeException("Specify a file path");
        }
        int fileSize = 0;
        FileInputStream fis;
        
        try
        {
            File file = new File(zipFilePath);
            fileSize = (int) file.length();
            if (fileSize == 0)
            {
                throw new RuntimeException("File is Empty");
            }
            fis = new FileInputStream(file);
        }
        catch(FileNotFoundException e)
        {
            throw new RuntimeException(e.getMessage());
        }
        catch(Exception e)
        {
            throw new RuntimeException(e);
        }
        
        byte[] b = new byte[fileSize];
        
        try
        {
            int bytesRead = fis.read(b, 0, fileSize);
            while (bytesRead < fileSize) {
                bytesRead += fis.read(b, bytesRead, fileSize - bytesRead);
            }
            fis.close();
        }
        catch(IOException e)
        {
            throw new RuntimeException(e);
        }
        
        try
        {
            PlatformService service = Platform.getService(PlatformService.class);
            service.registerPlugin(b);
        }
        catch(Exception e)
        {
            throw new RuntimeException(e);
        }
        
            
        logger.info("Ending:" + this.getDateTime());
        
        
    }
    
}
