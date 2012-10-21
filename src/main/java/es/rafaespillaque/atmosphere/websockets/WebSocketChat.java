package es.rafaespillaque.atmosphere.websockets;

import java.io.IOException;

import org.atmosphere.config.service.WebSocketHandlerService;
import org.atmosphere.cpr.AtmosphereResource;
import org.atmosphere.cpr.Broadcaster;
import org.atmosphere.cpr.BroadcasterFactory;
import org.atmosphere.websocket.WebSocket;
import org.atmosphere.websocket.WebSocketHandler;

@WebSocketHandlerService
public class WebSocketChat extends WebSocketHandler {
	
//	private static final JedisPool pool = new JedisPool(new JedisPoolConfig(), "localhost");

    public WebSocketChat(){
    }
    
    @Override
    public void onOpen(WebSocket ws) {
    	System.out.println("onOpen");
        ws.resource().setBroadcaster(
              BroadcasterFactory.getDefault().lookup("broadcaster", true));
//        Jedis jedis = new Jedis("localhost");
        try {
        	String uuid = ws.resource().uuid();
			ws.write("{uuid:"+uuid+"}");
//			jedis.set(uuid, "init");
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("IOException en onOpen");
		} finally{
//			pool.returnResource(jedis);
		}
        ws.resource().suspend();
    }

    public void onTextMessage(WebSocket ws, String message) {
    	System.out.println("onText: "+message);
    	
        AtmosphereResource r = ws.resource();
        Broadcaster b = r.getBroadcaster();
        b.broadcast(message);
    }

	@Override
	public void onClose(WebSocket ws) {
		System.out.println("onClose");
		
	}
    
    
}
