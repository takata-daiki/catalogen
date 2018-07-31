/**
 * Count class displays integers from 1 to n
 *
 * Implement the Runnable interface so that it can be
 *   executed as a separate thread
 */
public class Count implements Runnable
{
 private int n;

 /** Constructor */
 public Count( int n )
 {
   this.n = n;
 }

 public void run()
 {
   // Create another thread here
   Thread t = new Thread( new Echo( 'D', 1000 ) );
   t.start();

   try
   {
     for ( int i = 1 ; i <= n ; i++ )
     {
       System.out.print( "[" + i + "]" );
       if ( i == 20 )
       {
         t.join();   // wait until t has completed its execution
       }
     }
   }
   catch ( InterruptedException ex )
   {
     /* ignore */
   }
 }
}
