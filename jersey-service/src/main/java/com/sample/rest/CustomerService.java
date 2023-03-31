package com.sample.rest;

import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/customers")
public class CustomerService {

  private final CopyOnWriteArrayList<Customer> cList = CustomerList.getInstance();

  @GET
  @Path("/all")
  @Produces(MediaType.TEXT_PLAIN)
  public String getAllCustomers() {
    return "---Customer List---\n"
        + cList.stream().map(c -> c.toString()).collect(Collectors.joining("\n"));
  }

  @GET
  @Path("/{id}")
  @Produces(MediaType.TEXT_PLAIN)
  public String getCustomer(@PathParam("id") long id) {
    Optional<Customer> match = cList.stream().filter(c -> c.getId() == id).findFirst();
    if (match.isPresent()) {
      return "---Customer---\n" + match.get().toString();
    } else {
      return "Customer not found";
    }
  }

}
