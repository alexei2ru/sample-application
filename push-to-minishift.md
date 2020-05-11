## Configure shell
```
eval $(minishift oc-env --shell bash)
eval $(minishift docker-env --shell bash)
```

## Build project
```
make task-jar
```

## Build images
```
make system-build
```

## create project
```
oc new-project bootcamp
```

## Tag image
```
docker tag ntt/task $(minishift openshift registry)/bootcamp/task
```

## create app via image stream
```
docker login -u developer -p $(oc whoami -t) $(minishift openshift registry)

docker push $(minishift openshift registry)/bootcamp/task

oc new-app bootcamp/task:latest -n bootcamp
oc expose service task -n bootcamp
```

# create by template 
```
oc process -f application-template.yaml -p DB_PASSWORD=dbpassword | oc create -f -
```

# oc commands
```
oc delete all --all -n bootcamp
oc delete project bootcamp
```