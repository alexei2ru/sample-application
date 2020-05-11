# make all variables defined here available in the command called from the makefile
.EXPORT_ALL_VARIABLES:

# environment variable used in microservice
DB_SERVER=localhost

# the -Djava.library.path is a workaround for the git-bash. the jar has a property file with java.library.path=....\usr\git...
# this is interpreted as a unicode, whereas the '\us' is not unicode conform and causes an error during startup!!!
task-jar:
	mvn clean package -f ./task/pom.xml -Djava.library.path=.
task-run:
	java -jar ./task/target/task-microbundle.jar
system-build:
	docker-compose build
system-up:
	docker-compose up
system-down:
	docker-compose down -v
system-turnaround: system-down task-jar system-build system-up
