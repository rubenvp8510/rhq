/*
 *
 *  * RHQ Management Platform
 *  * Copyright (C) 2005-2012 Red Hat, Inc.
 *  * All rights reserved.
 *  *
 *  * This program is free software; you can redistribute it and/or modify
 *  * it under the terms of the GNU General Public License, version 2, as
 *  * published by the Free Software Foundation, and/or the GNU Lesser
 *  * General Public License, version 2.1, also as published by the Free
 *  * Software Foundation.
 *  *
 *  * This program is distributed in the hope that it will be useful,
 *  * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  * GNU General Public License and the GNU Lesser General Public License
 *  * for more details.
 *  *
 *  * You should have received a copy of the GNU General Public License
 *  * and the GNU Lesser General Public License along with this program;
 *  * if not, write to the Free Software Foundation, Inc.,
 *  * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 *
 */

package org.rhq.enterprise.server.cassandra;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author John Sanda
 */
//@Startup
//@Singleton
public class CassandraBootstrapBean {

//    @PostConstruct
    public void loadConnectionProps() {
        InputStream stream = null;
        try {
            stream = getClass().getResourceAsStream("/cassandra-test.properties");
            Properties props = new Properties();
            props.load(stream);

            System.setProperties(props);
        } catch (IOException e) {
            throw new RuntimeException(("Failed to load cassandra-test.properties"));
        }
    }

}
