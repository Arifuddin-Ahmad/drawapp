
package mydrawapp;

/*
 * By:Arifuddin Ahmad
 * SN:1019399
 * 
 */
public class ParseException extends Exception
{
  public ParseException()
  {
    super("Error:Exception during parsing");
  }

  public ParseException(String message)
  {
    super(message);
    System.out.println("Possible Reason: " + message);
  }
}
