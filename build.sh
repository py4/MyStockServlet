if [ -z "$CATALINA_HOME" ]; then
    echo "The environment variable CATALINA_HOME must be set to the root of the Tomcat installation directory"
    exit 1
fi  

rm -rf target/*
mkdir target
mkdir target/WEB-INF
mkdir target/WEB-INF/classes
mkdir target/WEB-INF/lib

javac -sourcepath src -classpath $CATALINA_HOME/lib/servlet-api.jar:web/WEB-INF/lib/opencsv-3.7.jar:web/WEB-INF/lib/jstl-1.1.2.jar:web/WEB-INF/lib/standard-1.1.2.jar -d target/WEB-INF/classes src/ir/Epy/MyStock/*.java src/ir/Epy/MyStock/*/*.java
#cp conf/web.xml target/WEB-INF
#cp web/WEB-INF/lib/* target/WEB-INF/lib
cp -R web/* target/
