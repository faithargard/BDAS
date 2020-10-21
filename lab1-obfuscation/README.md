This work devoted to obfuscation / deobfuscation data contained in XML file.

Application can be used in several ways:

1. Create .jar package with dependencies and use:
       
        mvn compile assembly:single
        target/package_name.jar [option] [inputFilePath]
       
   where
    - [option] - argument which contains *obfuscate* or *deobfuscate* option
    - [inputFilePath] - argument which contains *absolute path to the input file*

2. Only compile app with used earlier arguments:
    
        mvn compile exec:java -Dexec.args="[option] [inputFilePath]"
        
3. Run tests for application:

        mvn test
