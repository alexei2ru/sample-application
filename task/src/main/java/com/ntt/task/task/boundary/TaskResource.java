package com.ntt.task.task.boundary;

import com.ntt.task.config.MemoryLeakProducer;
import com.ntt.task.config.RandomDelay;
import com.ntt.task.config.RandomError;
import com.ntt.task.task.entity.Task;
import org.eclipse.microprofile.metrics.Counter;
import org.eclipse.microprofile.metrics.annotation.Metric;
import org.eclipse.microprofile.metrics.annotation.Timed;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;

@Path("tasks")
@ApplicationScoped
@Transactional(value = Transactional.TxType.REQUIRED)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TaskResource {

    @PersistenceContext
    private EntityManager entityManager;
    @Inject
    private MemoryLeakProducer memoryLeakProducer;
    @Inject
    private RandomError randomError;
    @Inject
    private RandomDelay randomDelay;
    @Inject
    @Metric(name = "get_tasks_counter", displayName = "task counter", tags = {"endpoint=/data/tasks", "code=200"})
    private Counter successCounter;

    @Inject
    @Metric(name = "get_tasks_error_counter", displayName = "task counter", tags = {"endpoint=/data/tasks", "code=500"})
    private Counter errorCounter;

    @POST
    @Timed
    public Response createTask(final Task task, @Context final UriInfo uriInfo) {
        applyErrors();
        entityManager.persist(task);
        final UriBuilder builder = uriInfo.getAbsolutePathBuilder();
        builder.path(Long.toString(task.getId()));
        return Response.created(builder.build()).build();
    }

    @GET
    @Timed
    public List<Task> getTasks() {
        try{
            applyErrors();
            successCounter.inc();
        }catch (final Throwable throwable){
            errorCounter.inc();
            throw throwable;
        }

        final TypedQuery<Task> namedQuery = entityManager.createNamedQuery(Task.FIND_ALL, Task.class);
        namedQuery.setMaxResults(20);
        return namedQuery.getResultList();
    }

    @GET
    @Timed
    @Path("{id}")
    public Task getTask(@PathParam("id") final Long id) {
        applyErrors();
        return entityManager.find(Task.class, id);
    }

    private void applyErrors() {
        memoryLeakProducer.apply();
        randomError.apply();
        randomDelay.apply();
    }
}
