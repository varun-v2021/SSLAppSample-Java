package com.sample.ssl.client;

import java.net.URI;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.client.ClientConfig;

//This can be run separately as well once a service is listening like HelloWorldService
// Or via browser by hitting http://localhost:8080/SSLAppSample/helloworld

public class HelloWorldSSLClient2 {

	/*
	 * Terms like keyStore and trustStore are often used interchangeably and the
	 * same file can act as keystore as well as trustStore it just matter of
	 * pointing javax.net.ssl.keyStore and javax.net.ssl.trustStore properties
	 * to that file but there is a slight difference between keystore and
	 * trustStore.
	 * 
	 * A keyStore is used to store individual identity or certificate while
	 * trustStore is used to store other parties certificates signed by CA
	 * 
	 * Read more:
	 * https://javarevisited.blogspot.com/2012/03/add-list-certficates-java-
	 * keystore.html#ixzz5J7o912hR
	 * 
	 */
	static {
		System.setProperty("javax.net.debug", "all");
		System.setProperty("jdk.tls.client.protocols", "TLSv1.2");
		System.setProperty("https.protocols", "TLSv1.2");
		System.setProperty("javax.net.ssl.keyStoreType", "pkcs12");
		System.setProperty("javax.net.ssl.trustStoreType", "pkcs12");
		System.setProperty("javax.net.ssl.trustStore", "D://SSL_Cert_PKCS//sampleClientcert.p12");
		System.setProperty("javax.net.ssl.trustStorePassword", "password");
		System.setProperty("javax.net.ssl.keyStore", "D://SSL_Cert_PKCS//sampleClientcert.p12");
		System.setProperty("javax.net.ssl.keyStorePassword", "password");
		
		/*-Djavax.net.ssl.keyStoreType=pkcs12
				-Djavax.net.ssl.trustStoreType=jks
				
				-Djavax.net.ssl.keyStore=clientcertificate.p12
				
				-Djavax.net.ssl.trustStore=gridserver.keystore
				-Djavax.net.debug=ssl # very verbose debug
				-Djavax.net.ssl.keyStorePassword=$PASS
				-Djavax.net.ssl.trustStorePassword=$PASS*/

		javax.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier(new javax.net.ssl.HostnameVerifier() {

			public boolean verify(String hostname, javax.net.ssl.SSLSession sslSession) {
				if (hostname.equals("localhost")) {
					return true;
				}
				return false;
			}
		});
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ClientConfig config = new ClientConfig();
		Client client = ClientBuilder.newClient(config);
		WebTarget target = client.target(getBaseURI());
		// System.out.println(target.path("rest").path("hello").request().accept(MediaType.TEXT_PLAIN).get(String.class));
		System.out.println(target.request().accept(MediaType.TEXT_PLAIN).get(String.class));
		// System.out.println(target.request());
	}

	private static URI getBaseURI() {
		// here 8443 is the TLS port for secured communication based on TLS
		// handshake
		return UriBuilder.fromUri("https://localhost:8443/SSLAppSample/helloworld").build();
	}

}
