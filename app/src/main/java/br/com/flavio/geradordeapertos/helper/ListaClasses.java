package br.com.flavio.geradordeapertos.helper;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;

public class ListaClasses {
    
    /**
     * ScannerProjectClasses
     */
    public ListaClasses() {
        scan(Thread.currentThread().getContextClassLoader());
    }
    
    /**
     * scan
     *
     * @param classLoader ClassLoader
     */
    public void scan(ClassLoader classLoader) {
        if (!(classLoader instanceof URLClassLoader)) {
            return;
        }
        
        URL[] urls = ((URLClassLoader) classLoader).getURLs();
        
        for (URL url : urls) {
            File location = null;
            
            try {
                location = new File(url.toURI());
            } catch (URISyntaxException e) {
                System.out.println("ERROR: " + e.toString());
                
                return;
            }
            
            if (location.isDirectory()) {
                getClassesInDirectory(null, location);
            }
        }
    }
    
    /**
     * getClassesInDirectory
     *
     * @param parent   String
     * @param location File
     */
    public void getClassesInDirectory(String parent, File location) {
        File[] files = location.listFiles();
        
        for (File file : files) {
            StringBuilder builder = new StringBuilder();
            
            builder.append(parent).append(".").append(file.getName());
            
            String path = (parent == null ? file.getName() : builder.toString());
            
            if (file.isDirectory()) {
                getClassesInDirectory(path, file);
            } else if (file.getName().endsWith(".class")) {
                int p = path.indexOf("$");
                
                if (p != -1) {
                    path = path.substring(0, p);
                }
                
                if (path.contains("class")) {
                    System.out.println(path);
                }
            }
        }
    }
}