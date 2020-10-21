This work devoted to the creation of a client-server application that interacts
over the HTTPS protocol with using encryption keys for SSL / TLS.

Application can be used with following steps:

1. Build and run server part in *server* folder (same for *client* side):
       
        ./gradlew bootRun
    
2. Import *server.p12* certiffcate into your browser

3. Enter *https://localhost:8080/gateway/server-data* into search box
    and see server's hello.
