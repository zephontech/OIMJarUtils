/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tech.zephon.utiljobs.utils;

/**
 *
 * @author fforester
 */
public class StringUtils {
    
    
    public static boolean isEmpty(String str)
    {
        if (str == null)
            return true;
        if (str.trim().length() == 0)
            return true;
        return false;
    }
    
}
