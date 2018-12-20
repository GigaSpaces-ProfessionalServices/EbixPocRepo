package com.gigaspaces;

import org.openspaces.core.GigaSpace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * @author Denys_Novikov
 * Date: 12/19/18
 */
public class InitialLoader {

    private static Logger logger = LoggerFactory.getLogger(InitialLoader.class);

    @Autowired
    private GigaSpace gigaspace;

    public void init() {

    }
}
