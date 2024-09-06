/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tech.zephon.oim.utiljobs.jobs;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import oracle.iam.platform.Platform;
import oracle.iam.scheduler.api.SchedulerService;
import oracle.iam.scheduler.vo.JobDetails;
import oracle.iam.scheduler.vo.JobParameter;
import oracle.iam.scheduler.vo.TaskSupport;

/**
 * extends TaskSupport class and adds custom methods
 * @author APTEC
 */
public abstract class BaseTask extends TaskSupport {

    /** override method and throw unsupported  */
    @Override
    public HashMap getAttributes() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

     /** override method and throw unsupported  */
    @Override
    public void setAttributes() {
        throw new UnsupportedOperationException("Not supported yet.");
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
    
    /**
     * convert a list of objects into a sublist that are batchsize size
     * 
     * @param list generic list of objects
     * @param batchSize size of the sub list
     * @return 
     */
    public <T> List<List<T>> makeBatches(List<T> list, int batchSize)
    {
        int N = list.size();
        
        List<List<T>> parts = new ArrayList<List<T>>();
        
        for(int i=0;i<N;i+=batchSize)
        {
            parts.add(new ArrayList<T>(list.subList(i,Math.min(N,i+batchSize))));
            //list.removeAll(list.subList(0, batchSize));
        }
        list.clear();
        return parts;
    }
    
    /**
     * updates job parameters
     * 
     * @param taskName      name of the job
     * @param propertyName  name of the parameter
     * @param value         value of the parameter
     * @throws Exception 
     */
    public void setTaskProperties(String taskName,String propertyName,String value) throws Exception {
        
        SchedulerService scheduleOps = Platform.getService(SchedulerService.class);
        try {
            //logger.debug("Get Job " + taskName);
            JobDetails jd = scheduleOps.getJobDetail(taskName);

            if (jd == null)
            {
                throw new Exception("Job Not Found for " + taskName);
            }

            Map<String,JobParameter> parms = jd.getAttributes();

            if (parms == null)
            {
                return;
            }
            Set<String> keys = parms.keySet();

            for(String key : keys)
            {
                JobParameter jp = parms.get(key);
                if (jp.getName().equals(propertyName))
                {
                    jp.setValue(value);
                }

            }
            scheduleOps.updateJob(jd);
        } catch (Exception ex) {
            throw new Exception("SchedulerException",ex);
        }
        
    }
    
    /**
     * Reads the file containing the GPNs of the OIM users
     * @param fileName name of the file
     * @return list of OIM User logins
     * @throws Exception 
     */
    protected List<String> loadFile(String fileName) throws Exception
    {
        List<String> fileUsers = new ArrayList();
        
        BufferedReader br = null;

		try {

			String sCurrentLine;

			br = new BufferedReader(new FileReader(fileName));

			while ((sCurrentLine = br.readLine()) != null) {
				fileUsers.add(sCurrentLine);
			}

		} catch (IOException e) {
			throw e;
		} finally {
            if (br != null)
                br.close();
			
		}
        return fileUsers;
    }
    
    
    public String padRight(String s, int n) {
     return String.format("%1$-" + n + "s", s);  
}

    public String padLeft(String s, int n) {
        return String.format("%1$" + n + "s", s);  
    }

}
