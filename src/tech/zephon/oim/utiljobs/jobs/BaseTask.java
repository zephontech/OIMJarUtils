/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tech.zephon.oim.utiljobs.jobs;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import oracle.iam.scheduler.vo.TaskSupport;

/**
 * extends TaskSupport class and adds custom methods
 * @author APTEC
 */
public abstract class BaseTask extends TaskSupport {

    /** override method and throw unsupported  */
    @Override
    public HashMap getAttributes() {
        return null;
    }

     /** override method and throw unsupported  */
    @Override
    public void setAttributes() {
    }
    /**
     * get current datetime
     * 
     * @return   datetime 
     */
    public String getDateTime()
    {
        Date date = new Date(System.currentTimeMillis());
        DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        return(df.format(date));
    }
    
}
