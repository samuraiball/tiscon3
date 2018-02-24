package jp.co.tis.tiscon3.configuration;

import enkan.Application;
import enkan.application.WebApplication;
import enkan.endpoint.ResourceEndpoint;
import enkan.middleware.*;
import enkan.middleware.devel.*;
import enkan.middleware.doma2.DomaTransactionMiddleware;
import jp.co.tis.tiscon3.controller.ContactUsController;
import jp.co.tis.tiscon3.entity.ContactUs;
import kotowari.middleware.*;
import kotowari.middleware.serdes.ToStringBodyWriter;
import enkan.system.inject.ComponentInjector;
import kotowari.routing.Routes;
import jp.co.tis.tiscon3.controller.IndexController;
import static enkan.util.BeanBuilder.builder;
import static enkan.util.Predicates.*;
import jp.co.tis.tiscon3.controller.CardOrderController;

public class ApplicationConfiguration implements enkan.config.ApplicationFactory {

    @Override
    public Application create(ComponentInjector injector) {
        WebApplication app = new WebApplication();

        Routes routes = Routes.define( r -> {
            r.get("/").to(IndexController.class, "index");
            r.get("/cardOrder/user").to(CardOrderController.class, "inputUser");
            r.post("/cardOrder/user").to(CardOrderController.class, "inputJob");
            r.post("/cardOrder/modify").to(CardOrderController.class, "modifyUser");
            r.post("/cardOrder/modifyB").to(CardOrderController.class,"modifyB");
            r.get("/cardOrder/completed").to(CardOrderController.class, "completed");
            r.post("/cardOrder/confirm").to(CardOrderController.class, "confirm");
            r.resource(CardOrderController.class);

            r.get("/contacts").to(ContactUsController.class,"contactUs");
            r.post("/contacts/confirm").to(ContactUsController.class,"confirm");
            r.post("/contacts/modify").to(ContactUsController.class,"modify");
            r.get("/contacts/completed").to(ContactUsController.class,"completed");
            r.resource(ContactUsController.class);
        }).compile();

        app.use(new DefaultCharsetMiddleware());
        app.use(NONE, new ServiceUnavailableMiddleware(new ResourceEndpoint("/public/html/503.html")));

        app.use(envIn("development"), new StacktraceMiddleware());
        app.use(envIn("development"), new TraceWebMiddleware());

        app.use(new TraceMiddleware());
        app.use(new ContentTypeMiddleware());
        app.use(envIn("development"), new HttpStatusCatMiddleware());
        app.use(new ParamsMiddleware());
        app.use(new MultipartParamsMiddleware());
        app.use(new MethodOverrideMiddleware());
        app.use(new NormalizationMiddleware());
        app.use(new NestedParamsMiddleware());
        app.use(new CookiesMiddleware());
        app.use(new SessionMiddleware());
        app.use(new ContentNegotiationMiddleware());

        app.use(new ResourceMiddleware());
        app.use(new RenderTemplateMiddleware());
        app.use(new RoutingMiddleware(routes));
        app.use(new DomaTransactionMiddleware());
        app.use(new FormMiddleware());
        app.use(builder(new SerDesMiddleware()).set(SerDesMiddleware::setBodyWriters, new ToStringBodyWriter()).build());
        app.use(new ValidateBodyMiddleware<>());
        app.use(new ControllerInvokerMiddleware(injector));

        return app;
    }
}
