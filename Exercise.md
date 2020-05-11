# Openshift exercise DRAFT
1. create project test
2. create database by browser catalog (console)
   1. open console
   2. select postgres database
   3. insert parameters
   4. check created resources by console and cli
   5. use port-forwarding to connect to the database
   6. connect to the database via vs code
   7. execute init script on database (Creates tables for application)
3. create task service by CLi
   1. build with maven
   2. build image (**docker** or docker-compose)
   3. tag image correctly
   4. login to docker registry
   5. push task image to registry (namespace openshift) 
   6. create new app
   7. fix parameter
      1. plain text 
      2. use secret from database template
   8. create readiness probe
   9. create health probe
   10. set project quota limits
4.  export template from your project (optional)
    1.  introduce template parameter
    2.  create secret
    3.  create pvc
5.  Apply template to new project. (optional)
    1.  check the application   
