use hyper::{Body, Request, Response, Server};
use hyper::service::{make_service_fn, service_fn};
use hyper::rt::Future;

fn hello_world(_req: Request<Body>) -> Response<Body> {
    Response::new(Body::from("Hello, World!"))
}

fn main() {
    let addr = ([127, 0, 0, 1], 3000).into();

    let new_svc = make_service_fn(|_conn| {
        service_fn(hello_world)
    });

    let server = Server::bind(&addr)
        .serve(new_svc)
        .map_err(|e| eprintln!("server error: {}", e));

    hyper::rt::run(server);
}
