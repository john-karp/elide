package com.yahoo.elide.spring.config;

import com.google.common.collect.Sets;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Set;

@Slf4j
@Data
@AllArgsConstructor
public class InMemoryClassLoader extends ClassLoader {
	
    private Set<String> classNames = Sets.newHashSet();
    
    public InMemoryClassLoader(ClassLoader parent, Set<String> classNames) {
        super(parent);
        setClassNames(classNames);
    }
    
    public void setClassNames(Set<String> classNames) {
    	this.classNames = classNames;
    }
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        return super.findClass(name);
    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        return super.loadClass(name);
    }

    @Override
    protected URL findResource(String name) {
    	log.info("Finding Resource "+name +" in "+classNames);
        if (classNames.contains(name.replace("/", ".").replace(".class", ""))) {
            try {
            	log.info("Returning Resource "+"file://" + name);
                return new URL("file://" + name);
            } catch (MalformedURLException e) {
                throw new IllegalStateException(e);
            }
        }
        return super.findResource(name);
    }

}