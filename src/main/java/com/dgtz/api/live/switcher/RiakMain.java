package com.dgtz.api.live.switcher;


import com.basho.riak.client.api.RiakClient;
import com.basho.riak.client.api.commands.buckets.FetchBucketProperties;
import com.basho.riak.client.api.commands.buckets.StoreBucketProperties;
import com.basho.riak.client.api.commands.kv.DeleteValue;
import com.basho.riak.client.api.commands.kv.FetchValue;
import com.basho.riak.client.api.commands.kv.StoreValue;
import com.basho.riak.client.core.RiakCluster;
import com.basho.riak.client.core.RiakNode;
import com.basho.riak.client.core.operations.FetchBucketPropsOperation;
import com.basho.riak.client.core.query.BucketProperties;
import com.basho.riak.client.core.query.Location;
import com.basho.riak.client.core.query.Namespace;
import com.basho.riak.client.core.query.RiakObject;
import com.basho.riak.client.core.util.BinaryValue;

import java.net.UnknownHostException;

/**
 * BroCast.
 * Copyright: Sardor Navruzov
 * 2013-2016.
 */
public class RiakMain {

    public static class Book {
        public String title;
        public String author;
        public String body;
        public String isbn;
        public Integer copiesOwned;
    }

    // This will create a client object that we can use to interact with Riak
    private static RiakCluster setUpCluster() throws UnknownHostException {
        // This example will use only one node listening on localhost:10017
        RiakNode node = new RiakNode.Builder()
                .withRemoteAddress("192.168.1.159")
                .withRemotePort(8098)
                .build();

        // This cluster object takes our one node as an argument
        RiakCluster cluster = new RiakCluster.Builder(node)
                .build();

        // The cluster must be started to work, otherwise you will see errors
        cluster.start();

        return cluster;
    }

    public static void main( String[] args ) {
        try {

            RiakClient client = RiakClient.newClient(8098, "192.168.1.159");
            Namespace ns = new Namespace("TestBucket");

            // If the bucket does not exist in Riak, it will be created with the default properties when you query for them.
            FetchBucketProperties fetchProps = new FetchBucketProperties.Builder(ns).build();

            FetchBucketPropsOperation.Response fetchResponse = client.execute(fetchProps);
            BucketProperties bp = fetchResponse.getBucketProperties();

            System.out.println(bp.getR().toString());
            // By using the StoreBucketProperties command,
            // you can specify properties' values.
            //
            // If the bucket already exists in Riak, the bucket
            // properties will be updated.
            //
            // Only those properties that you specify will be updated,
            // there is no need to fetch the bucket properties to edit them.
            StoreBucketProperties storeProps =
                    new StoreBucketProperties.Builder(ns)
                            .withNVal(2).withR(1).build();

            client.execute(storeProps);

            client.shutdown();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
