javac moviemanager/*.java
jar cfe ../moviemanager.jar moviemanager.MovieManagerApp -C . moviemanager
cd ..
java -jar moviemanager.jar