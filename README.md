Rate Limiter with Bucket4J in Spring Boot

Overview

This project demonstrates the implementation of a rate-limiting mechanism in a Spring Boot application using Bucket4J, a Java library for token-bucket rate limiting.

Why Rate Limiting?

Prevent Abuse: Protect your application from being overwhelmed by excessive requests.

Enhance Stability: Ensure fair usage of resources across all users.

Optimize Performance: Maintain consistent application performance under heavy load.

Regulatory Compliance: Meet API rate-limiting standards required by various external services.

About Bucket4J

Bucket4J is a lightweight library for implementing token-bucket-based rate-limiting algorithms. It supports both in-memory and distributed systems (e.g., Redis, Hazelcast) and provides fine-grained control over request rates.

Core Concepts:

Bucket: Represents the rate limit and holds tokens.

Bandwidth: Defines the refill rate and capacity of the bucket.

Consumption: Each request consumes tokens; if no tokens remain, the request is denied.

Implementation Details

1. Dependency

Add the following dependency to your pom.xml to use Bucket4J:

    <dependency>
    <groupId>com.bucket4j</groupId>
    <artifactId>bucket4j-core</artifactId>
    <version>8.10.1</version>
    </dependency>

2. Configuration Class: RateLimitConfig

Defines the rate-limiting rules (e.g., 5 requests per minute).

    @Configuration
    public class RateLimitConfig {
    
        @Bean
        public Bucket bucket() {
            Bandwidth limit = Bandwidth.builder()
                                       .capacity(5)
                                       .refillGreedy(5, Duration.ofMinutes(1))
                                       .build();
            return Bucket.builder()
                         .addLimit(limit)
                         .build();
        }
    }

3. Filter Class: RateLimitingFilter

This class intercepts incoming requests and applies rate-limiting logic.

    @Component
    public class RateLimitingFilter implements Filter {
    
        @Autowired
        private Bucket bucket;
    
        @Override
        public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
            if (bucket.tryConsume(1)) {
                filterChain.doFilter(servletRequest, servletResponse);
            } else {
                ((HttpServletResponse) servletResponse).setStatus(429); // Too Many Requests
            }
        }
    }


How It Works

Initialization: A Bucket is configured with a capacity of 5 tokens and refills 5 tokens every minute.

Request Handling: Each incoming request consumes 1 token.

Limit Exceeded: If the bucket is empty, the server responds with HTTP status 429 - Too Many Requests.

Rate Refresh: The bucket refills based on the defined rate (5 tokens per minute).

How to Run

1. Clone the repository: git clone https://github.com/Keshav-Maheshwari1/RateLimiter
2. Build the project: mvn clean install
3. Run the application: mvn spring-boot:run
4. Test the rate limiter using tools like Postman or cURL: curl -X GET http://localhost:8080/api/test


