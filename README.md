# How to install
There is a Makefile provided to simplify the build steps

## build application
```make task-jar```

## build containers
```make system-build```

## deploy containers to docker
```make system-up```

## shut down the services
```make system-down```

## or all in one command
```make system-turnaround```

# How to test
Install "the REST Client" extension in VS Code [humao.rest-client](https://marketplace.visualstudio.com/items?itemName=humao.rest-client)

Open the request.http file and click on the "Send Request" links.

# How to install in Minishift
[Manual for installing to minishift](push-to-minihsift.md)

# Potential problems
- configured endpoint in the Makefile must be adapted to your local environment
  - if provided IP is not working, try localhost or check on which ip the docker container is reachable
- init.sh script for postgres is not working because of Windows line endings (running on Windows)
  -  workaround: execute in git-bash ```dos2unix init.sh```

