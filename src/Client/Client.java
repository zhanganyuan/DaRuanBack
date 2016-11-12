package Client;

import Module.transfer.Request;
import Utils.PubString;

/**
 * Created by anyuan on 2016/11/10.
 */
public class Client {
    public static void main(String[] args) throws InterruptedException {
//        Request request = new Request(PubString.QUERY_TYPE, PubString.TABLE_CHANNEL);
//        request.addQueryParam("Channel_ID", "=", "1");
        Request request = new Request(PubString.SYNC_TYPE,null);
        request.addDeleteOrSyncParam("user_ID","1");
        ClientThread clientThread = new ClientThread(request);
        clientThread.start();
    }
}
