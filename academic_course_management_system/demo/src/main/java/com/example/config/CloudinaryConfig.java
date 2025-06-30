// package com.example.config;

// import com.cloudinary.Cloudinary;
// import com.cloudinary.utils.ObjectUtils;
// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;

// // @Configuration
// // public class CloudinaryConfig {

// //     @Value("${cloudinary.cloud_name:default_cloud_name}")
// //     private String cloudName;

// //     @Value("${cloudinary.api_key:default_api_key}")
// //     private String apiKey;

// //     @Value("${cloudinary.api_secret:default_api_secret}")
// //     private String apiSecret;

// //     @Bean
// //     public Cloudinary cloudinary() {
// //         return new Cloudinary(ObjectUtils.asMap(
// //             "cloud_name", cloudName,
// //             "api_key", apiKey,
// //             "api_secret", apiSecret,
// //             "secure", true
// //         ));
// //     }
// // }

// @Configuration
// public class CloudinaryConfig {

//     @Value("${cloudinary.cloud_name:default_cloud_name}")
//     private String cloudName;

//     @Value("${cloudinary.api_key:default_api_key}")
//     private String apiKey;

//     @Value("${cloudinary.api_secret:default_api_secret}")
//     private String apiSecret;

//     @Bean
//     public Cloudinary cloudinary() {
//         return new Cloudinary(ObjectUtils.asMap(
//             "cloud_name", cloudName,
//             "api_key", apiKey,
//             "api_secret", apiSecret,
//             "secure", true
//         ));
//     }
// }

package com.example.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {

    private static final Logger logger = LoggerFactory.getLogger(CloudinaryConfig.class);

    @Value("${spring.cloudinary.cloud_name:default_cloud_name}")
    private String cloudName;

    @Value("${spring.cloudinary.api_key:default_api_key}")
    private String apiKey;

    @Value("${spring.cloudinary.api_secret:default_api_secret}")
    private String apiSecret;

    @Bean
    public Cloudinary cloudinary() {
        logger.info("Configuring Cloudinary: cloud_name={}, api_key={}", cloudName, apiKey);
        return new Cloudinary(ObjectUtils.asMap(
            "cloud_name", cloudName,
            "api_key", apiKey,
            "api_secret", apiSecret,
            "secure", true
        ));
    }
}