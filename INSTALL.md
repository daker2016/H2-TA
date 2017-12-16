Install
===

To set up the development environment, you should install java and some dependencies for the project. And a good IDE will help you code efficiently, we suggest [Intellij](https://www.jetbrains.com/idea/).


Install JDK
===

Follow the guide 
https://www3.ntu.edu.sg/home/ehchua/programming/howto/JDK_Howto.html


For ALPN
===
HTTP/2 requires some encryption algorithms that is not included in Java. There are two ways to add such component. The detailed guide is on 
 https://netty.io/wiki/forked-tomcat-native.html. Briefly, the solution either the native way or the Java way.

 - Native way
    
    Install `openssl` & `libapr-1`
     
 - JDK way 
 
    Add alpn boot jar to the Java boot classpath. See instructions in 
    https://www.eclipse.org/jetty/documentation/current/alpn-chapter.html


Work with Intellij
===

### Import project
File -> Open -> Choose /path/of/h2-ta/build.gradle -> Choose 'Open as Project'


Work with terminal
===
Run `./gradlew build` in the project root directory to build it.