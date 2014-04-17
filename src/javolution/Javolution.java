/*
 * Javolution - Java(TM) Solution for Real-Time and Embedded Systems
 * Copyright (C) 2006 - Javolution (http://javolution.org/)
 * All rights reserved.
 * 
 * Permission to use, copy, modify, and distribute this software is
 * freely granted, provided that this notice is preserved.
 */
package javolution;

import java.lang.CharSequence;

import javolution.context.LogContext;
import javolution.testing.TestContext;
import javolution.testing.TimeContext;
import javolution.text.Text;

/** 
 * <p> This class contains the library {@link #main} method for
 *     versionning, self-tests, and performance analysis.</p>
 * <p> It is also the base class for the library benchmarks and 
 *     self-tests.</p>
 *
 * @author  <a href="mailto:jean-marie@dautelle.com">Jean-Marie Dautelle</a>
 * @version 1.0, September 14, 2004
 */
public class Javolution {
    
    /**
     * Holds the version information.
     */
    public final static String VERSION = "@VERSION@";

    /**
     * Default constructor.
     */
    protected Javolution() {
    }

    /**
     * The library {@link #main} method.
     * The archive <code>javolution.jar</code> is auto-executable.
     * [code]
     *    java -jar javolution.jar version <i>(show version information)</i>
     *    java -jar javolution.jar test <i>(perform self-tests)</i>
     *    java -jar javolution.jar perf <i>(run benchmark)</i>
     * [/code]
     * Configurable are read from system properties.
     * 
     *
     * @param  args the option arguments.
     * @throws Exception if a problem occurs.
     */
    public static void main(String[] args) throws Exception {
        LogContext.enter(LogContext.SYSTEM_OUT); // Output results to System.out
        try {
            LogContext.info("Javolution - Java(TM) Solution for Real-Time and Embedded Systems");
            LogContext.info("Version " + VERSION + " (http://javolution.org)");
            LogContext.info("");
            if (args.length > 0) {
                if (args[0].equals("version")) {
                    return;
                } else if (args[0].equals("test")) {
                    builtInTests();
                    return;
                } else if (args[0].equals("perf")) {
                    TimeContext.enter();
                    try {
                        builtInTests();
                    } finally {
                        TimeContext.exit();
                    }
                    return;
                }
            }
            LogContext.info("Usage: java -jar javolution.jar [arg]");
            LogContext.info("where arg is one of:");
            LogContext.info("    version (to show version information only)");
            LogContext.info("    test    (to perform self-tests)");
            LogContext.info("    perf    (to run benchmark)");
        } finally {
            LogContext.exit();
        }
    }

    /**
     * Performs Built-In-Tests.
     */
    private static void builtInTests() {

         LogContext.info("Load Configurable Parameters from System.getProperties()...");
         javolution.lang.Configurable.read(System.getProperties());
         LogContext.info("");
        
        Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
        new ContextTestSuite().run();
        new UtilTestSuite().run();
        
        TestContext.info("More tests coming soon...");

    }

    /**
     * Converts the specified String as CharSequence (String is a 
     * CharSequence only for J2SE 1.4+).
     * 
     * @param str the String to convert.
     * @return <code>this</code> or a text wrapper.
     */
    public static CharSequence j2meToCharSeq(Object str) {
        return (CharSequence) str;
    }
}