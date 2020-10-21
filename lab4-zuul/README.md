This work devoted to creation of a client-server router for an
application running through the gateway via Netflix Zuul Libraries.

Application can be used with following steps:

1. Build and run server part in *server* folder (same for *client* side):
       
        ./gradlew bootRun
    
2. Enter *http://localhost:8080/* into search box;

3. Open Developer Tools in your browser (usually by F12 key), then
choose Network tab;

3. Click on *Get Data From Server*;

4. Find generated request from list and click on it;

5. Find in request's *Headers* tab information about implanted header
    called *ZuulTestHeader* and its value *TestSampleValue*.
