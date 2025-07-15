package hu.banfalvig;

import hu.banfalvig.service.GreetingService;
import hu.banfalvig.service.SantaClausService;
import hu.banfalvig.util.GenerateToken;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.SecurityContext;
import lombok.RequiredArgsConstructor;
import org.eclipse.microprofile.jwt.JsonWebToken;

@RequiredArgsConstructor
@PermitAll
@Path("/")
public class GreetingResource {

    private final JsonWebToken jwt;
    private final GreetingService greetingService;
    private final SantaClausService santaClausService;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("greeting/{name}")
    public String greeting(String name) {
        return greetingService.greeting(name);
    }

    @GET
    @RolesAllowed({"User", "Admin"})
    @Produces(MediaType.TEXT_PLAIN)
    @Path("security")
    public String security(@Context SecurityContext ctx) {
        return getResponseString(ctx) + " birthDay: " + jwt.getClaim("birthdate");
    }

    @GET
    @RolesAllowed({"User", "Admin"})
    @Produces(MediaType.TEXT_PLAIN)
    @Path("hello")
    public String hello() {
        return "Hello from quarkus";
    }

    @GET
    @Path("token")
    public void token() {
        GenerateToken.token();
    }

    @POST
    @Path("book")
    public void book() {
        santaClausService.createBook("Harry Potter", "Rowling");
    }

    private String getResponseString(SecurityContext ctx) {
        String name;
        if (ctx.getUserPrincipal() == null) {
            name = "anonymous";
        } else if (!ctx.getUserPrincipal().getName().equals(jwt.getName())) {
            throw new InternalServerErrorException("Principal and JsonWebToken names do not match");
        } else {
            name = ctx.getUserPrincipal().getName();
        }
        return String.format("hello %s,"
                        + " isHttps: %s,"
                        + " authScheme: %s,"
                        + " hasJWT: %s",
                name, ctx.isSecure(), ctx.getAuthenticationScheme(), jwt.getClaimNames() != null);
    }
}
