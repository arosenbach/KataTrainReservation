import application.ReservationsController;
import domain.service.ReservationManager;
import domain.service.WebTicketManager;
import infra.BookingReferenceServiceImpl;
import infra.TrainDataServiceImpl;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.internal.inject.AbstractContainerRequestValueFactory;

import javax.inject.Singleton;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;

public class Launcher {

    public static class ReservationManagerFactory extends AbstractContainerRequestValueFactory<ReservationManager> {

        @Override
        public ReservationManager provide() {
            return new WebTicketManager(new TrainDataServiceImpl(), new BookingReferenceServiceImpl());
        }
    }
    
    private static final URI BASE_URI = UriBuilder.fromUri("http://localhost/api/").port(9991).build();

    public static void main(String[] args) {
        ResourceConfig rc = new ResourceConfig();
        rc.register(new AbstractBinder() {

            @Override
            protected void configure() {
                bindFactory(ReservationManagerFactory.class)
                        .to(ReservationManager.class)
                        .in(Singleton.class);
            }
        });
        rc.registerClasses(ReservationsController.class);

        try {
            HttpServer server = GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
            server.start();

            System.out.println(String.format(
                    "Coltrain app started with WADL available at " + "%sapplication.wadl\nHit enter to stop it...",
                    BASE_URI));

            System.in.read();
            server.shutdownNow();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}