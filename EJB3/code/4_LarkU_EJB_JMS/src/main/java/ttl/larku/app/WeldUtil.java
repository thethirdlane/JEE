package ttl.larku.app;

import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;

public class WeldUtil
{
    public static final WeldUtil INSTANCE = new WeldUtil ();

    private static Weld weld;
    private static WeldContainer container;

    public WeldUtil() 
    {
        weld = new Weld ();
        container = weld.initialize ();
        Runtime.getRuntime ().addShutdownHook (new Thread ()
        {
            @Override
            public void run ()
            {
                weld.shutdown ();
            }
        });
    }

    public <T> T getBean (Class<T> type)
    {
        return container.instance ().select (type).get ();
    }
    
    public static WeldContainer getWeldContainer() {
	return container;
    }

    public static Weld getWeld() {
	return weld;
    }
}
