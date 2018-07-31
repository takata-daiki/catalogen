/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.openejb.server.telnet;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;

import javax.ejb.EJBHome;
import javax.naming.Context;
import javax.naming.NameClassPair;
import javax.naming.NameNotFoundException;
import javax.naming.NamingEnumeration;

import org.apache.openejb.ClassLoaderUtil;
import org.apache.openejb.loader.SystemInstance;
import org.apache.openejb.spi.ContainerSystem;
import org.apache.openejb.core.ivm.naming.IvmContext;

public class Lookup extends Command {

    javax.naming.Context ctx;

    {
        ContainerSystem containerSystem = SystemInstance.get().getComponent(ContainerSystem.class);
        ctx = containerSystem.getJNDIContext();
    }

    public static void register() {
        Lookup cmd = new Lookup();
        Command.register("lookup", cmd);

    }

    static String PWD = "";

    public void exec(Arguments args, DataInputStream in, PrintStream out) throws IOException {
        try {
            String name = "";
            if (args == null || args.count() == 0) {
                name = PWD;
            } else {
                name = args.get();
            }

            Object obj = null;
            try {
                obj = ctx.lookup(name);
            }
            catch (NameNotFoundException e) {
                out.print("lookup: ");
                out.print(name);
                out.println(": No such object or subcontext");
                return;
            }
            catch (Throwable e) {
                out.print("lookup: error: ");
                e.printStackTrace(new PrintStream(out));
                return;
            }

            if (obj instanceof Context) {
                list(name, in, out);
                return;
            }

            out.println("" + obj);
        }
        catch (Exception e) {
            e.printStackTrace(new PrintStream(out));
        }
    }

    public void list(String name, DataInputStream in, PrintStream out) throws IOException {
        try {
            NamingEnumeration enumeration = null;
            try {

                enumeration = ctx.list(name);
            }
            catch (NameNotFoundException e) {
                out.print("lookup: ");
                out.print(name);
                out.println(": No such object or subcontext");
                return;
            }
            catch (Throwable e) {
                out.print("lookup: error: ");
                e.printStackTrace(new PrintStream(out));
                return;
            }

            if (enumeration == null) {
                return;
            }

            while (enumeration.hasMore()) {

                NameClassPair entry = (NameClassPair) enumeration.next();
                String eName = entry.getName();
                Class eClass = null;

                if (IvmContext.class.getName().equals(entry.getClassName())) {
                    eClass = IvmContext.class;
                } else {
                    try {
                        ClassLoader cl = ClassLoaderUtil.getContextClassLoader();
                        eClass = Class.forName(entry.getClassName(), true, cl);
                    }
                    catch (Throwable t) {
                        eClass = java.lang.Object.class;
                    }
                }

                if (Context.class.isAssignableFrom(eClass)) {

                    out.print(TextConsole.TTY_Bright);
                    out.print(TextConsole.TTY_FG_Blue);
                    out.print(entry.getName());
                    out.print(TextConsole.TTY_Reset);
                } else if (EJBHome.class.isAssignableFrom(eClass)) {

                    out.print(TextConsole.TTY_Bright);
                    out.print(TextConsole.TTY_FG_Green);
                    out.print(entry.getName());
                    out.print(TextConsole.TTY_Reset);
                } else {

                    out.print(entry.getName());
                }
                out.println();
            }
        }
        catch (Exception e) {
            e.printStackTrace(new PrintStream(out));
        }
    }
}

