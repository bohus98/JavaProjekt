package xbohuc00;

public class ZlaHodnotaException extends java.lang.Exception {
    public ZlaHodnotaException()
    {
        super("Zadali ste zlu hodnotu");
    }

    public ZlaHodnotaException(String msg)
    {
        super(msg);
    }
}