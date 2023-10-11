package org.cryptobot;

import express.Express;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        Express app = new Express();
        app.get("/", new ChartBot())
                .listen();
    }
}
