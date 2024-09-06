/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tech.zephon.utiljobs.utils;

import java.util.HashSet;
import java.util.Set;
import oracle.iam.platformservice.vo.JarElement;

/**
 *
 * @author fforester
 */
public class MakeJarElement {

    public static Set<JarElement> getElement(String name, String type, String path) {
        
        Set<JarElement> jarElement = new HashSet();
        if ((name != null) && (name != "")) {
            if (type.equalsIgnoreCase("JavaTasks")) {
                JarElement elem = new JarElement();
                elem.type = "JavaTasks";
                elem.name = name.trim();
                elem.path = path.trim();
                jarElement.add(elem);
            } else if (type.equalsIgnoreCase("ScheduleTask")) {
                JarElement elem = new JarElement();
                elem.type = "ScheduleTask";
                elem.name = name.trim();
                elem.path = path.trim();
                jarElement.add(elem);
            } else if (type.equalsIgnoreCase("ThirdParty")) {
                JarElement elem = new JarElement();
                elem.type = "ThirdParty";
                elem.name = name.trim();
                elem.path = path.trim();
                jarElement.add(elem);
            } else if (type.equalsIgnoreCase("ICFBundle")) {
                JarElement elem = new JarElement();
                elem.type = "ICFBundle";
                elem.name = name.trim();
                elem.path = path.trim();
                jarElement.add(elem);
            } else {
                return null;
            }
        }
        return jarElement;
    }
}
