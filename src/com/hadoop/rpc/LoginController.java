package com.hadoop.rpc;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.ipc.RPC;

public class LoginController {
	public static void main(String[] args) throws IOException {
		LoginService proxy = RPC.getProxy(LoginService.class, 1L, 
				new InetSocketAddress("hadoop", 10000), new Configuration());
		String result = proxy.login("a", "123");
		System.out.println(result);
	}
}
