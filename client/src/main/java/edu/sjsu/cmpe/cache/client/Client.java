package edu.sjsu.cmpe.cache.client;

//import sun.misc.Hashing;

import java.util.ArrayList;
import java.util.List;
import java.lang.Object;
import com.google.common.hash.Hashing;

public class Client {

    public static void main(String[] args) throws Exception {
        System.out.println("Starting Cache Client...");
        //CacheServiceInterface cache = new DistributedCacheService(
                //"http://localhost:3000");

        CacheServiceInterface cache1 =new DistributedCacheService("http://localhost:3000");
        CacheServiceInterface cache2 =new DistributedCacheService("http://localhost:3001");
        CacheServiceInterface cache3 =new DistributedCacheService("http://localhost:3002");
        List<CacheServiceInterface> servers = new ArrayList<CacheServiceInterface>();
        servers.add(cache1);
        servers.add(cache2);
        servers.add(cache3);

       String data[]=new String[]{"0","a","b","c","d","e","f","g","h","i","j"};

        System.out.println("PUT Operation:");
        for(int i=1;i<=10;i++) {

            int bucket = Hashing.consistentHash(Hashing.md5().hashString(Integer.toString(i)), servers.size());
            System.out.println("put("+i+"=>" +data[i]+")");
            System.out.println(data[i] + " routed to " + "server"+(bucket+1));

            servers.get(bucket).put(i,data[i]);

        }

        System.out.println("GET Operation:");
        for(int i=1;i<=10;i++)
        {
            int bucket = Hashing.consistentHash(Hashing.md5().hashString(Integer.toString(i)), servers.size());
            String value = servers.get(bucket).get(i);
            System.out.println("get(" + i + ") => " + value);
        }

        /*cache1.put(1, "foo");
        System.out.println("put(1 => foo)");

        String value = cache1.get(1);
        System.out.println("get(1) => " + value);

        System.out.println("Existing Cache Client...");*/
    }

}
