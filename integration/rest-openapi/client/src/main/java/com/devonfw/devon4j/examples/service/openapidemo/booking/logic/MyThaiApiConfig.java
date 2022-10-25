package com.devonfw.devon4j.examples.service.openapidemo.booking.logic;

import com.devonfw.devon4j.generated.client.handler.ApiClient;
import com.devonfw.devon4j.generated.client.service.BookingApi;
import com.devonfw.devon4j.generated.client.service.InvitedGuestApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * This class is a configuration for the generated api client
 * The api client can set a basepath, this path can be set with a string in the settings
 */
@Configuration
public class MyThaiApiConfig {

    /**
     * The basepath extracted from the properties file
     */
    @Value("${MyThaiApi.host.uri:}")
    private String hostInfoBasePath;

    @Bean
    public ApiClient apiClient(){
        ApiClient client = new ApiClient();

        //Overwrite basepath from properties file
        if(!hostInfoBasePath.isEmpty()){
            //Uncomment for native http client
            client.updateBaseUri(hostInfoBasePath);

            //Uncomment for non native http client
            //client.setBasePath(hostInfoBasePath);
        }

        return client;
    }

    /**
     * Function to make the generated Booking Api available in the entire application
     * @return a instance of the generated and configured Client
     */
    @Bean
    public BookingApi bookingApi(){
        return new BookingApi(apiClient());
    }

}
